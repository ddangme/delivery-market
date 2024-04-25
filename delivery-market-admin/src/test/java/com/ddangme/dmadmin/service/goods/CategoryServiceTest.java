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
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

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