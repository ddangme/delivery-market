package com.ddangme.dm.service.good;

import com.ddangme.dm.dto.AdminURL;
import com.ddangme.dm.dto.PageResponseCustom;
import com.ddangme.dm.dto.good.GoodResponse;
import com.ddangme.dm.dto.good.GoodSaleResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GoodService {
    private final RestTemplate restTemplate;
    private final Gson gson;

    public PageResponseCustom<List<GoodResponse>> getGoods(Pageable pageable) {
        URI uri = UriComponentsBuilder
                .fromHttpUrl(AdminURL.GET_ALL_GOODS.getUrl())
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .build()
                .toUri();

        ResponseEntity<PageResponseCustom<List<GoodResponse>>> responseEntity = restTemplate
                .exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});

        return responseEntity.getBody();
    }

    public PageResponseCustom<List<GoodSaleResponse>> findGoodsInCategory(Pageable pageable, Long categoryId) {
        URI uri = UriComponentsBuilder.fromHttpUrl(AdminURL.GET_GOODS_IN_CATEGORY.getUrl() + categoryId)
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .build()
                .toUri();

        ResponseEntity<PageResponseCustom<List<GoodSaleResponse>>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );

        return responseEntity.getBody();
    }

}
