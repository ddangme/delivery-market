package com.ddangme.dm.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

@Getter
@AllArgsConstructor
@JsonDeserialize(using = PageResponse.PageDeserializer.class)
public class PageResponse<T> {
    private String resultCode;
    private PageImpl<T> result;

    public static class PageDeserializer<T> extends JsonDeserializer<PageImpl<T>> {
        @Override
        public PageImpl<T> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            ObjectMapper mapper = (ObjectMapper) p.getCodec();
            JsonNode rootNode = mapper.readTree(p);
            JsonNode contentNode = rootNode.get("content");
            List<T> content = mapper.convertValue(contentNode, new TypeReference<>() {});
            JsonNode pageableNode = rootNode.get("pageable");
            Pageable pageable = mapper.convertValue(pageableNode, PageRequest.class);
            JsonNode totalElementsNode = rootNode.get("totalElements");
            long totalElements = (totalElementsNode != null) ? totalElementsNode.asLong() : 0;
            return new PageImpl<>(content, pageable, totalElements);
        }
    }
}