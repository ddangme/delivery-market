package com.ddangme.dmadmin.service.goods;

import com.ddangme.dmadmin.dto.goods.CategoryDTO;
import com.ddangme.dmadmin.exception.DMAdminException;
import com.ddangme.dmadmin.exception.ErrorCode;
import com.ddangme.dmadmin.model.goods.Category;
import com.ddangme.dmadmin.repository.goods.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public void save(CategoryDTO dto) {
        Category category = dto.toEntity();

        if (dto.getParentId() != null) {
            categoryRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new DMAdminException(ErrorCode.NOT_EXIST_PARENT_CATEGORY));
        }

        categoryRepository.findByName(dto.getName())
                .ifPresent(existCategory -> {
                    throw new DMAdminException(ErrorCode.DUPLICATE_CATEGORY_NAME, existCategory.getName());
                });

        categoryRepository.save(category);
    }

}
