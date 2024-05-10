package com.ddangme.dm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponseCustom<T> {

    private T content;
    private int totalPages;
    private int totalElements;
    private int number;

    @Data
    @AllArgsConstructor
    static class Page {
        private int totalPages;
        private int totalElements;
        private int number;
    }

    public Page getPage() {
        return new Page(totalPages, totalElements, number);
    }


    /**
     * "pageable": {
     *         "sort": {
     *             "empty": true,
     *             "sorted": false,
     *             "unsorted": true
     *         },
     *         "offset": 0,
     *         "pageNumber": 0,
     *         "pageSize": 20,
     *         "paged": true,
     *         "unpaged": false
     *     },
     *     "last": true,
     *     "totalPages": 1,
     *     "totalElements": 1,
     *     "first": true,
     *     "size": 20,
     *     "number": 0,
     *     "sort": {
     *         "empty": true,
     *         "sorted": false,
     *         "unsorted": true
     *     },
     *     "numberOfElements": 1,
     *     "empty": false
     */
}
