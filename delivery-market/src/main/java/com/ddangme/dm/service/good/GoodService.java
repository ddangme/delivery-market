package com.ddangme.dm.service.good;

import com.ddangme.dm.dto.AdminURL;
import com.ddangme.dm.dto.PageResponse;
import com.ddangme.dm.dto.Response;
import com.ddangme.dm.dto.category.CategoryResponse;
import com.ddangme.dm.dto.good.GoodResponse;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.objectweb.asm.TypeReference;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.core.ParameterizedTypeReference;
import com.google.gson.reflect.TypeToken;
import org.springframework.data.domain.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GoodService {
    private final RestTemplate restTemplate;
    private final Gson gson;


//    public Page<GoodResponse> getGoods() {
//        URI uri = UriComponentsBuilder.fromHttpUrl(AdminURL.GET_ALL_GOODS.getUrl())
//                .build()
//                .toUri();
//
//        ResponseEntity<PageResponse<GoodResponse>> responseEntity =
//                restTemplate.exchange(uri,
//                        HttpMethod.GET,
//                        null,
//                        new ParameterizedTypeReference<>() {});
//
//        if (responseEntity.getStatusCode() == HttpStatus.OK) {
//            return Objects.requireNonNull(responseEntity.getBody().getResult());
//        } else {
//            return getGoods();
//        }
//    }

    public Page<GoodResponse> getGoods(Pageable pageable)  {
        URI uri = UriComponentsBuilder.fromHttpUrl(AdminURL.GET_ALL_GOODS.getUrl())
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .build()
                .toUri();

        ResponseEntity<String> responseEntity = restTemplate
                .exchange(uri, HttpMethod.GET, null, String.class);

        JsonObject result = gson.fromJson(responseEntity.getBody(), JsonObject.class)
                .getAsJsonObject("result");
        List<GoodResponse> content = gson.fromJson(result.getAsJsonArray("content"), new TypeToken<List<GoodResponse>>(){}.getType());
        Pageable pageableResponse = gson.fromJson(result.get("pageable"), PageRequest.class);

        long totalElements = result.getAsJsonPrimitive("totalElements").getAsLong();

        return new PageImpl<>(content, pageableResponse, totalElements);
    }

//    동작 하는 코드!!!! 삭제하지 말 것!!!
//    public List<GoodResponse> getGoods()  {
//        URI uri = UriComponentsBuilder.fromHttpUrl(AdminURL.GET_ALL_GOODS.getUrl())
//                .build()
//                .toUri();
//
//        ResponseEntity<String> responseEntity = restTemplate
//                .exchange(uri, HttpMethod.GET, null, String.class);
//
//        JsonObject resultObject = gson
//                .fromJson(responseEntity.getBody(), JsonObject.class)
//                .getAsJsonObject("result");
//
//        return gson.fromJson(resultObject.getAsJsonArray("content"), new TypeToken<List<GoodResponse>>(){}.getType());
//    }
}
