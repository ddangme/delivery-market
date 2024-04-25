package com.ddangme.dmadmin.service.goods;

import com.ddangme.dmadmin.dto.goods.CategoryDTO;
import com.ddangme.dmadmin.model.goods.Category;
import com.ddangme.dmadmin.repository.goods.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
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
        CategoryDTO dto = createdCategoryDTO();
        given(categoryRepository.save(any(Category.class)))
                .willReturn(createdCategory());

        // When
        categoryService.save(dto);

        // Then
        then(categoryRepository).should().save(any(Category.class));
    }

    @Test
    void 부모가_없는_카테고리_등록() {
        // Given
        CategoryDTO dto = createdNoParentCategoryDTO();
        given(categoryRepository.save(any(Category.class)))
                .willReturn(createdNoParentCategory());

        // When
        categoryService.save(dto);

        // Then
        then(categoryRepository).should().save(any(Category.class));
    }

    private CategoryDTO createdNoParentCategoryDTO() {
        return new CategoryDTO(1L,
                "category_name",
                null,
                LocalDateTime.now(),
                1L,
                LocalDateTime.now(),
                1L,
                null,
                null);
    }


    private CategoryDTO createdCategoryDTO() {
        return new CategoryDTO(2L,
                "category_name",
                1L,
                LocalDateTime.now(),
                1L,
                LocalDateTime.now(),
                1L,
                null,
                null);
    }

    private Category createdNoParentCategory() {
        return new Category(1L,
                "category_name",
                null,
                Set.of());
    }


    private Category createdCategory() {
        return new Category(2L,
                "category_name",
                1L,
                Set.of());
    }
}