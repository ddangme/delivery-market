package com.ddangme.dmadmin.service.goods;

import com.ddangme.dmadmin.dto.category.CategoryRequest;
import com.ddangme.dmadmin.exception.DMAdminException;
import com.ddangme.dmadmin.exception.ErrorCode;
import com.ddangme.dmadmin.model.goods.Category;
import com.ddangme.dmadmin.repository.AdminRepository;
import com.ddangme.dmadmin.repository.category.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("[카테고리] 비즈니스 로직")
@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private AdminRepository adminRepository;

    @Test
    void 하위_카테고리_없는_상위_카테고리_등록() {
        CategoryRequest request = new CategoryRequest();
        request.setName("parent");
        request.setChildName(List.of());

        Category entity = request.toEntity();
        given(categoryRepository.findByName(request.getName()))
                .willReturn(Optional.empty());

        // When
        categoryService.save(entity);

        // Then
        then(categoryRepository).should().save(any(Category.class));

    }

    @Test
    void 하위_카테고리_있는_상위_카테고리_등록() {
        CategoryRequest request = new CategoryRequest();
        request.setName("parent");
        request.setChildName(List.of("child1", "child2"));
        Category category = request.toEntity();

        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        categoryService.save(category);

        then(categoryRepository).should().save(any(Category.class));
        Set<Category> childCategories = category.getChildCategories();
        for (Category childCategory : childCategories) {
            assertThat(childCategory.getParent()).isEqualTo(category);
        }
    }

    @Test
    void 상위_카테고리에_이미_있는_카테고리명_입력_시_오류() {
        String duplicateName = "duplicate";
        CategoryRequest request = new CategoryRequest();
        request.setName(duplicateName);
        request.setChildName(List.of());

        given(categoryRepository.findByName(duplicateName)).willReturn(Optional.of(new Category(duplicateName)));

        assertThatThrownBy(() -> categoryService.save(request.toEntity()))
                .isInstanceOf(DMAdminException.class)
                .hasMessage(ErrorCode.DUPLICATE_CATEGORY_NAME.getMessage());
    }

    @Test
    void 하위_카테고리에_이미_있는_카테고리명_입력_시_오류() {
        String duplicateName = "duplicate";
        CategoryRequest request = new CategoryRequest();
        request.setName("parent");
        request.setChildName(List.of(duplicateName, "child2"));

        given(categoryRepository.findByName("parent")).willReturn(Optional.empty());
        given(categoryRepository.findByName(duplicateName)).willReturn(Optional.of(new Category(duplicateName)));

        assertThatThrownBy(() -> categoryService.save(request.toEntity()))
                .isInstanceOf(DMAdminException.class)
                .hasMessage(ErrorCode.DUPLICATE_CATEGORY_NAME.getMessage());
    }


    @Test
    void 하위_카테고리와_상위_카테고리명_동일하게_입력_시_오류() {
        CategoryRequest request = new CategoryRequest();
        request.setName("parent");
        request.setChildName(List.of("parent", "child1"));


        assertThatThrownBy(() -> categoryService.save(request.toEntity()))
                .isInstanceOf(DMAdminException.class)
                .hasMessage(ErrorCode.DUPLICATE_PARENT_CATEGORY_NAME.getMessage());
    }

    @Test
    void 하위_카테고리에_중복_된_값_입력_시_오류() {
        CategoryRequest request = new CategoryRequest();
        request.setName("parent");
        request.setChildName(List.of("child1", "child1"));


        assertThatThrownBy(() -> categoryService.save(request.toEntity()))
                .isInstanceOf(DMAdminException.class)
                .hasMessage(ErrorCode.DUPLICATE_CATEGORY_NAME.getMessage());
    }

    @Test
    void 상위_카테고리을_16자_이상으로_입력_시_오류() {
                CategoryRequest request = new CategoryRequest();
        request.setName("가나다라마바사아자차카타파하가나");
        request.setChildName(List.of("child1", "child2"));

        assertThatThrownBy(() -> categoryService.save(request.toEntity()))
                .isInstanceOf(DMAdminException.class)
                .hasMessage(ErrorCode.UNABLE_LENGTH_CATEGORY_NAME.getMessage());
    }


    @Test
    void 하위_카테고리을_16자_이상으로_입력_시_오류() {
        CategoryRequest request = new CategoryRequest();
        request.setName("parent");
        request.setChildName(List.of("가나다라마바사아자차카타파하가나", "child2"));

        assertThatThrownBy(() -> categoryService.save(request.toEntity()))
                .isInstanceOf(DMAdminException.class)
                .hasMessage(ErrorCode.UNABLE_LENGTH_CATEGORY_NAME.getMessage());
    }

    @Test
    void 상위_카테고리를_null로_입력_시_오류() {
        CategoryRequest request = new CategoryRequest();
        request.setName(null);
        request.setChildName(List.of("child1", "child2"));

        assertThatThrownBy(() -> categoryService.save(request.toEntity()))
                .isInstanceOf(DMAdminException.class)
                .hasMessage(ErrorCode.BAD_REQUEST.getMessage());
    }


    @Test
    void 상위_카테고리를_공백으로_등록_시도_시_에러() {
        CategoryRequest request = new CategoryRequest();
        request.setName("");
        request.setChildName(List.of("child1", "child2"));

        assertThatThrownBy(() -> categoryService.save(request.toEntity()))
                .isInstanceOf(DMAdminException.class)
                .hasMessage(ErrorCode.UNABLE_LENGTH_CATEGORY_NAME.getMessage());
    }

    @Test
    void 상위_카테고리를_1글자로_등록_시도_시_에러() {
        CategoryRequest request = new CategoryRequest();
        request.setName("1");
        request.setChildName(List.of("child1", "child2"));

        assertThatThrownBy(() -> categoryService.save(request.toEntity()))
                .isInstanceOf(DMAdminException.class)
                .hasMessage(ErrorCode.UNABLE_LENGTH_CATEGORY_NAME.getMessage());
    }

    @Test
    void 하위_카테고리를_1글자로_등록_시도_시_에러() {
        CategoryRequest request = new CategoryRequest();
        request.setName("parent");
        request.setChildName(List.of("1", "child2"));

        assertThatThrownBy(() -> categoryService.save(request.toEntity()))
                .isInstanceOf(DMAdminException.class)
                .hasMessage(ErrorCode.UNABLE_LENGTH_CATEGORY_NAME.getMessage());
    }
//
//    @Test
//    void 선택한_카테고리가_없는_경우_삭제_시도_시_에러() {
//        List<Long> categoryIds = new ArrayList<>();
//
//        AdminDTO adminDTO = newAdminDTO();
//
//        DMAdminException exception = assertThrows(DMAdminException.class, () -> {
//            categoryService.delete(categoryIds, adminDTO);
//        });
//
//        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.NOT_CHOICE_CATEGORY);
//    }
//
//
//    @Test
//    void 하위_카테고리가_있는_상위_카테고리_삭제_시도_시_에러() {
//        Long parentId = 1L;
//        Category category = new Category(parentId, "parent", null, Set.of(
//                new Category("child1"),
//                new Category("child2")
//        ));
//
//        List<Long> categoryIds = List.of(parentId);
//        AdminDTO adminDTO = newAdminDTO();
//
//        given(adminRepository.findById(adminDTO.getId())).willReturn(Optional.of(adminDTO.toEntity()));
//        given(categoryRepository.findById(parentId)).willReturn(Optional.of(category));
//
//        DMAdminException exception = assertThrows(DMAdminException.class, () -> {
//            categoryService.delete(categoryIds, adminDTO);
//        });
//
//        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.EXIST_CHILD_CATEGORY);
//    }
//
//    @Test
//    void 카테고리_삭제_DELETE_AT과_DELETE_BY에_데이터_입력() {
//        List<Long> categoryIds = List.of(1L);
//
//        Category category = new Category("parent");
//
//        AdminDTO adminDTO = newAdminDTO();
//        given(adminRepository.findById(adminDTO.getId())).willReturn(Optional.of(adminDTO.toEntity()));
//        given(categoryRepository.findById(1L)).willReturn(Optional.of(category));
//
//
//        // When
//        categoryService.delete(categoryIds, adminDTO);
//
//        // Then
//        assertThat(category.getDeletedAt()).isNotNull();
//        assertThat(category.getDeletedBy()).isNotNull();
//    }
//
//
//    private static AdminDTO newAdminDTO() {
//        return new AdminDTO(1L, "email@test.com", "password", "name", "nickname");
//    }


}