package com.ddangme.dmadmin.service.good;

import com.ddangme.dmadmin.dto.admin.AdminDTO;
import com.ddangme.dmadmin.dto.category.CategoryDTO;
import com.ddangme.dmadmin.dto.category.CategoryRequest;
import com.ddangme.dmadmin.exception.DMAdminException;
import com.ddangme.dmadmin.exception.ErrorCode;
import com.ddangme.dmadmin.model.good.Category;
import com.ddangme.dmadmin.repository.category.CategoryRepository;
import com.ddangme.dmadmin.service.category.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
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
    void 정상_등록_하위_없는_카테고리() {
        CategoryRequest request = new CategoryRequest();
        request.setName("parent");
        request.setChildName(List.of());

        CategoryDTO dto = request.toDTO();

        given(categoryRepository.findByName(request.getName()))
                .willReturn(Optional.empty());

        // When
        categoryService.save(dto);

        // Then
        then(categoryRepository).should().save(any(Category.class));

    }



    @Test
    void 정상_등록_하위_있는_카테고리() {
        CategoryRequest request = new CategoryRequest();
        request.setName("parent");
        request.setChildName(List.of("child1", "child2"));
        CategoryDTO dto = request.toDTO();
        Category category = dto.toEntity();

        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        categoryService.save(dto);

        then(categoryRepository).should().save(any(Category.class));
        Set<Category> childCategories = category.getChildCategories();
        for (Category childCategory : childCategories) {
            assertThat(childCategory.getParent()).isEqualTo(category);
        }
    }

    @Test
    void 오류_등록_상위_카테고리명_중복() {
        String duplicateName = "duplicate";
        CategoryRequest request = new CategoryRequest();
        request.setName(duplicateName);
        request.setChildName(List.of());

        given(categoryRepository.findByName(duplicateName)).willReturn(Optional.of(new Category(duplicateName)));

        assertThatThrownBy(() -> categoryService.save(request.toDTO()))
                .isInstanceOf(DMAdminException.class)
                .hasMessage(ErrorCode.DUPLICATE_CATEGORY_NAME.getMessage());
    }

    @Test
    void 오류_등록_하위_카테고리명_중복() {
        String duplicateName = "duplicate";
        CategoryRequest request = new CategoryRequest();
        request.setName("parent");
        request.setChildName(List.of(duplicateName, "child2"));

        given(categoryRepository.findByName("parent")).willReturn(Optional.empty());
        given(categoryRepository.findByName(duplicateName)).willReturn(Optional.of(new Category(duplicateName)));

        assertThatThrownBy(() -> categoryService.save(request.toDTO()))
                .isInstanceOf(DMAdminException.class)
                .hasMessage(ErrorCode.DUPLICATE_CATEGORY_NAME.getMessage());
    }


    @Test
    void 오류_등록_상위_하위_카테고리명_동일() {
        CategoryRequest request = new CategoryRequest();
        request.setName("parent");
        request.setChildName(List.of("parent", "child1"));


        assertThatThrownBy(() -> categoryService.save(request.toDTO()))
                .isInstanceOf(DMAdminException.class)
                .hasMessage(ErrorCode.DUPLICATE_PARENT_CATEGORY_NAME.getMessage());
    }

    @Test
    void 오류_등록_하위_카테고리끼리_중복() {
        CategoryRequest request = new CategoryRequest();
        request.setName("parent");
        request.setChildName(List.of("child1", "child1"));


        assertThatThrownBy(() -> categoryService.save(request.toDTO()))
                .isInstanceOf(DMAdminException.class)
                .hasMessage(ErrorCode.DUPLICATE_CATEGORY_NAME.getMessage());
    }


    @ParameterizedTest
    @MethodSource("lengthErrorCategoryName")
    void 오류_등록_카테고리명_길이(CategoryRequest request) {
        assertThatThrownBy(() -> categoryService.save(request.toDTO()))
                .isInstanceOf(DMAdminException.class)
                .hasMessage(ErrorCode.UNABLE_LENGTH_CATEGORY_NAME.getMessage());
    }

    static List<CategoryRequest> lengthErrorCategoryName() {
        return List.of(
                new CategoryRequest("가나다라마바사아자차카타파하가나", List.of("child1", "child2")),
                new CategoryRequest("", List.of("child1", "child2")),
                new CategoryRequest("1", List.of("child1", "child2")),
                new CategoryRequest("parent", List.of("가나다라마바사아자차카타파하가나", "child2")),
                new CategoryRequest("parent", List.of("1", "child2")),
                new CategoryRequest("parent", List.of("", "child2")),
                new CategoryRequest("parent", List.of("child1", "가나다라마바사아자차카타파하가나")),
                new CategoryRequest("parent", List.of("child1", "1")),
                new CategoryRequest("parent", List.of("child1", "")),
                new CategoryRequest("parent", List.of("child1", "", ""))
        );
    }

    @Test
    void 오류_등록_상위_카테고리명_null() {
        CategoryRequest request = new CategoryRequest();
        request.setName(null);
        request.setChildName(List.of("child1", "child2"));

        assertThatThrownBy(() -> categoryService.save(request.toDTO()))
                .isInstanceOf(DMAdminException.class)
                .hasMessage(ErrorCode.BAD_REQUEST.getMessage());
    }

    @Test
    void 오류_등록_하위_카테고리_null() {
        CategoryRequest request = new CategoryRequest();
        request.setName("parent");
        request.setChildName(null);

        assertThatThrownBy(() -> categoryService.save(request.toDTO()))
                .isInstanceOf(DMAdminException.class)
                .hasMessage(ErrorCode.BAD_REQUEST.getMessage());
    }

    @Test
    void 정상_삭제() {
        CategoryRequest request = new CategoryRequest();
        request.setName("parent");
        request.setChildName(List.of());
        CategoryDTO dto = request.toDTO();
        dto.setId(1L);
        Category entity = dto.toEntity();

        AdminDTO adminDTO = newAdminDTO();


        given(adminRepository.findById(adminDTO.getId())).willReturn(Optional.of(adminDTO.toEntity()));
        given(categoryRepository.findById(dto.getId())).willReturn(Optional.of(entity));

        // When
        categoryService.delete(List.of(dto.getId()), adminDTO);

        // Then
        assertThat(entity.getDeletedAt()).isNotNull();
        assertThat(entity.getDeletedBy()).isNotNull();
    }


    @ParameterizedTest
    @NullAndEmptySource
    void 오류_삭제_선택한_카테고리_null_or_empty(List<Long> categoryIds) {
        AdminDTO adminDTO = newAdminDTO();

        assertThatThrownBy(() -> categoryService.delete(categoryIds, adminDTO))
                .isInstanceOf(DMAdminException.class)
                .hasMessage(ErrorCode.NOT_CHOICE_CATEGORY.getMessage());
    }


    @Test
    void 하위_카테고리가_있는_상위_카테고리_삭제_시도_시_에러() {
        CategoryRequest request = new CategoryRequest();
        request.setName("parent");
        request.setChildName(List.of("child1", "child2"));
        CategoryDTO dto = request.toDTO();
        dto.setId(1L);

        AdminDTO adminDTO = newAdminDTO();

        given(adminRepository.findById(adminDTO.getId())).willReturn(Optional.of(adminDTO.toEntity()));
        given(categoryRepository.findById(dto.getId())).willReturn(Optional.of(dto.toEntity()));

        assertThatThrownBy(() -> categoryService.delete(List.of(dto.getId()), adminDTO))
                .isInstanceOf(DMAdminException.class)
                .hasMessage(ErrorCode.EXIST_CHILD_CATEGORY.getMessage());
    }


    private AdminDTO newAdminDTO() {
        return new AdminDTO(1L, "email@test.com", "password", "name", "nickname");
    }

}