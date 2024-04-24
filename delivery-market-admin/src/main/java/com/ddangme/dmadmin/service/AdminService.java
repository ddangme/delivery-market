package com.ddangme.dmadmin.service;

import com.ddangme.dmadmin.dto.AdminDTO;
import com.ddangme.dmadmin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final AdminRepository adminRepository;

    public Optional<AdminDTO> findByEmail(String email) {
        return adminRepository.findByEmail(email)
                .map(AdminDTO::fromEntity);
    }

}
