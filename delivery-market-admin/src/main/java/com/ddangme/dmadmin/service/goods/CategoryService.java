package com.ddangme.dmadmin.service.goods;

import com.ddangme.dmadmin.dto.goods.CategoryDTO;
import com.ddangme.dmadmin.exception.DMAdminException;
import com.ddangme.dmadmin.exception.ErrorCode;
import com.ddangme.dmadmin.model.goods.Category;
import com.ddangme.dmadmin.repository.goods.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Page<CategoryDTO> search(Pageable pageable) {
        return categoryRepository.searchParents(pageable)
                .map(CategoryDTO::fromEntity);
    }

    @Transactional
    public void save(CategoryDTO dto) {
        validate(dto);
        Category category = dto.toEntity();

        categoryRepository.save(category);
    }

    private void validate(CategoryDTO dto) {
        if (dto.getName() == null || dto.getName().isEmpty()) {
            throw new DMAdminException(ErrorCode.UNABLE_LENGTH_CATEGORY_NAME);
        }
        dto.trim();

        if (dto.getName().length() < 2 || dto.getName().length() > 15) {
            throw new DMAdminException(ErrorCode.UNABLE_LENGTH_CATEGORY_NAME);
        }

        if (dto.getParentId() != null) {
            categoryRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new DMAdminException(ErrorCode.NOT_EXIST_PARENT_CATEGORY));
        }

        categoryRepository.findByName(dto.getName())
                .ifPresent(existCategory -> {
                    throw new DMAdminException(ErrorCode.DUPLICATE_CATEGORY_NAME);
                });
    }

}
