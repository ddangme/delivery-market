package com.ddangme.dmadmin.service.goods;

import com.ddangme.dmadmin.dto.AdminDTO;
import com.ddangme.dmadmin.dto.goods.CategoryDTO;
import com.ddangme.dmadmin.exception.DMAdminException;
import com.ddangme.dmadmin.exception.ErrorCode;
import com.ddangme.dmadmin.model.Admin;
import com.ddangme.dmadmin.model.goods.Category;
import com.ddangme.dmadmin.repository.AdminRepository;
import com.ddangme.dmadmin.repository.goods.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

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
    void 부모가_있는_카테고리_등록() {
        // Given
        Long parentId = 1L;

        Category parentCategory = createCategory(parentId, null);
        CategoryDTO dto = createCategoryDTO(2L, parentId);

        given(categoryRepository.findById(parentId)).willReturn(Optional.of(parentCategory));

        // When
        categoryService.save(dto);

        // Then
        then(categoryRepository).should().findById(parentId);
        then(categoryRepository).should().save(any(Category.class));

    }

    @Test
    void 부모가_없는_카테고리_등록() {
        // Given
        CategoryDTO dto = createCategoryDTO(1L, null);
        given(categoryRepository.save(any(Category.class)))
                .willReturn(createCategory(1L, null));

        // When
        categoryService.save(dto);

        // Then
        then(categoryRepository).should().save(any(Category.class));
    }

    @Test
    void 이미_있는_카테고리_이름으로_등록_시도_시_에러() {
        // Given
        CategoryDTO dto = new CategoryDTO("이미 있는 이름", null);
        given(categoryRepository.findByName("이미 있는 이름")).willReturn(Optional.of(createCategory(1L, null)));

        DMAdminException exception = assertThrows(DMAdminException.class, () -> {
            categoryService.save(dto);
        });

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.DUPLICATE_CATEGORY_NAME);
    }

    @Test
    void 존재하지_않는_부모_아이디로_등록_시도_시_에러() {
        CategoryDTO dto = new CategoryDTO("가나다라", 1L);
        given(categoryRepository.findById(dto.getParentId())).willReturn(Optional.empty());

        DMAdminException exception = assertThrows(DMAdminException.class, () -> {
            categoryService.save(dto);
        });

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.NOT_EXIST_PARENT_CATEGORY);

    }

    @Test
    void 이름을_16자_이상으로_등록_시도_시_에러() {
        CategoryDTO dto = new CategoryDTO("가나다라마바사아자차카타파하가나", null);
        DMAdminException exception = assertThrows(DMAdminException.class, () -> {
            categoryService.save(dto);
        });

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.UNABLE_LENGTH_CATEGORY_NAME);
    }

    @Test
    void 이름을_null로_등록_시도_시_에러() {
        CategoryDTO dto = new CategoryDTO(null, null);
        DMAdminException exception = assertThrows(DMAdminException.class, () -> {
            categoryService.save(dto);
        });

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.UNABLE_LENGTH_CATEGORY_NAME);
    }


    @Test
    void 이름을_공백으로_등록_시도_시_에러() {
        CategoryDTO dto = new CategoryDTO(" ", null);
        DMAdminException exception = assertThrows(DMAdminException.class, () -> {
            categoryService.save(dto);
        });

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.UNABLE_LENGTH_CATEGORY_NAME);
    }

    @Test
    void 이름을_입력하지_않고_등록_시도_시_에러() {
        CategoryDTO dto = new CategoryDTO("", null);
        DMAdminException exception = assertThrows(DMAdminException.class, () -> {
            categoryService.save(dto);
        });

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.UNABLE_LENGTH_CATEGORY_NAME);
    }
    @Test
    void 이름을_1글자로_등록_시도_시_에러() {
        CategoryDTO dto = new CategoryDTO("가", null);
        DMAdminException exception = assertThrows(DMAdminException.class, () -> {
            categoryService.save(dto);
        });

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.UNABLE_LENGTH_CATEGORY_NAME);
    }

    @Test
    void 선택한_카테고리가_없는_경우_삭제_시도_시_에러() {
        List<Long> categoryIds = new ArrayList<>();

        AdminDTO adminDTO = createAdminDTO();

        DMAdminException exception = assertThrows(DMAdminException.class, () -> {
            categoryService.delete(categoryIds, adminDTO);
        });

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.NOT_CHOICE_CATEGORY);
    }

    @Test
    void 하위_카테고리가_있는_상위_카테고리_삭제_시도_시_에러() {
        Long parentId = 1L;
        Category childCategory = createCategory(2L, parentId);
        Category parentCategory = createCategoryWithChild(parentId, Set.of(childCategory));

        List<Long> categoryIds = List.of(parentId);
        AdminDTO adminDTO = createAdminDTO();

        given(adminRepository.findById(adminDTO.getId())).willReturn(Optional.of(adminDTO.toEntity()));
        given(categoryRepository.findById(parentId)).willReturn(Optional.of(parentCategory));

        DMAdminException exception = assertThrows(DMAdminException.class, () -> {
            categoryService.delete(categoryIds, adminDTO);
        });

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.EXIST_CHILD_CATEGORY);
    }

    @Test
    void 카테고리_삭제_DELETE_AT과_DELETE_BY에_데이터_입력() {
        List<Long> categoryIds = List.of(1L);

        Category category = createCategory(1L, null);

        AdminDTO adminDTO = createAdminDTO();
        given(adminRepository.findById(adminDTO.getId())).willReturn(Optional.of(adminDTO.toEntity()));
        given(categoryRepository.findById(1L)).willReturn(Optional.of(category));


        // When
        categoryService.delete(categoryIds, adminDTO);

        // Then
        assertThat(category.getDeletedAt()).isNotNull();
        assertThat(category.getDeletedBy()).isNotNull();

    }


    private CategoryDTO createCategoryDTO(Long id, Long parentId) {
        return new CategoryDTO(id,
                "category_name",
                parentId,
                LocalDateTime.now(),
                any(AdminDTO.class),
                LocalDateTime.now(),
                any(AdminDTO.class),
                null,
                null,
                Set.of());
    }

    private Category createCategory(Long id, Long parentId) {
        return new Category(id,
                "category_name",
                parentId,
                Set.of());
    }

    private AdminDTO createAdminDTO() {
        return new AdminDTO(1L, "email", "password", "string", "nickname");
    }

    private Category createCategoryWithChild(Long id, Set<Category> childrens) {
        return new Category(id,
                "category_name",
                null,
                childrens);
    }

}