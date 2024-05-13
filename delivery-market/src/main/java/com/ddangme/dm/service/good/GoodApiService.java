package com.ddangme.dm.service.good;

import com.ddangme.dm.dto.AdminURL;
import com.ddangme.dm.dto.PageResponseCustom;
import com.ddangme.dm.dto.good.GoodSaleDetailResponse;
import com.ddangme.dm.dto.good.GoodResponse;
import com.ddangme.dm.dto.good.GoodSaleResponse;
import com.ddangme.dm.exception.DMException;
import com.ddangme.dm.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GoodApiService {
    private final RestTemplate restTemplate;

    public PageResponseCustom<List<GoodResponse>> getGoods(Pageable pageable) {
        URI uri = UriComponentsBuilder.fromHttpUrl(AdminURL.ALL_GOODS.getUrl())
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .build()
                .toUri();

        RequestEntity<Void> request = RequestEntity
                .get(uri)
                .accept(MediaType.APPLICATION_JSON)
                .build();

        return restTemplate.exchange(request, new ParameterizedTypeReference<PageResponseCustom<List<GoodResponse>>>() {}).getBody();
    }

    public PageResponseCustom<List<GoodSaleResponse>> findGoodsInCategory(Pageable pageable, Long categoryId) {
        try {
            URI uri = UriComponentsBuilder.fromHttpUrl(AdminURL.GOODS_IN_CATEGORY.getUrl() + categoryId)
                    .queryParam("page", pageable.getPageNumber())
                    .queryParam("size", pageable.getPageSize())
                    .build()
                    .toUri();

            RequestEntity<Void> request = RequestEntity
                    .get(uri)
                    .accept(MediaType.APPLICATION_JSON)
                    .build();
            return restTemplate.exchange(request, new ParameterizedTypeReference<PageResponseCustom<List<GoodSaleResponse>>>() {}).getBody();
        } catch (HttpClientErrorException exception) {
            throw new DMException(ErrorCode.ADMIN_SERVER_ERROR, exception.getMessage());
        }
    }

    public GoodSaleDetailResponse findGoodDetail(Long goodId) {
        try {
            RequestEntity<Void> request = RequestEntity.get(AdminURL.GOOD_DETAIL.getUrl() + goodId).build();
            return restTemplate.exchange(request, GoodSaleDetailResponse.class).getBody();
        } catch (HttpClientErrorException exception) {
            throw new DMException(ErrorCode.ADMIN_SERVER_ERROR, exception.getMessage());
        }
    }

}
