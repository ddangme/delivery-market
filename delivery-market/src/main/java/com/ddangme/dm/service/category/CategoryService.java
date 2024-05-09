package com.ddangme.dm.service.category;

import com.ddangme.dm.dto.category.CategoryResponse;
import com.ddangme.dm.repository.MemberRepository;
import com.ddangme.dm.repository.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;

    public List<CategoryResponse> findAll() {
        return categoryRepository.findIdAndNameByParentIdIsNullOrderByName()
                .stream()
                .map(CategoryResponse::fromEntity)
                .toList();
    }
}
