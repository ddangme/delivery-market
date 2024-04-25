package com.ddangme.dmadmin.service.goods;

import com.ddangme.dmadmin.dto.goods.CategoryDTO;
import com.ddangme.dmadmin.exception.DMAdminException;
import com.ddangme.dmadmin.exception.ErrorCode;
import com.ddangme.dmadmin.model.goods.Category;
import com.ddangme.dmadmin.repository.goods.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
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

    @Test
    void 부모가_있는_카테고리_등록() {
        // Given
        Long parentId = 1L;

        Category parentCategory = createdCategory(parentId, null);
        CategoryDTO dto = createdCategoryDTO(2L, parentId);

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
        CategoryDTO dto = createdCategoryDTO(1L, null);
        given(categoryRepository.save(any(Category.class)))
                .willReturn(createdCategory(1L, null));

        // When
        categoryService.save(dto);

        // Then
        then(categoryRepository).should().save(any(Category.class));
    }

    @Test
    void 이미_있는_카테고리_이름으로_등록_시도_시_에러() {
        // Given
        CategoryDTO dto = new CategoryDTO("이미 있는 이름", null);
        given(categoryRepository.findByName("이미 있는 이름")).willReturn(Optional.of(createdCategory(1L, null)));

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


    private CategoryDTO createdCategoryDTO(Long id, Long parentId) {
        return new CategoryDTO(id,
                "category_name",
                parentId,
                LocalDateTime.now(),
                1L,
                LocalDateTime.now(),
                1L,
                null,
                null);
    }

    private Category createdCategory(Long id, Long parentId) {
        return new Category(id,
                "category_name",
                parentId,
                Set.of());
    }
}