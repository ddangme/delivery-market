package com.ddangme.dm.service.category;

import com.ddangme.dm.dto.AdminURL;
import com.ddangme.dm.dto.Response;
import com.ddangme.dm.dto.category.CategoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {
    private final RestTemplate restTemplate;


    public List<CategoryResponse> findAll() {
        URI uri = UriComponentsBuilder.fromHttpUrl(AdminURL.GET_ALL_CATEGORIES.getUrl())
                .build()
                .toUri();




        ResponseEntity<Response<List<CategoryResponse>>> responseEntity =
                restTemplate.exchange(uri,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {});

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return Objects.requireNonNull(responseEntity.getBody()).getResult();
        } else {
            return findAll();
        }
    }
}
