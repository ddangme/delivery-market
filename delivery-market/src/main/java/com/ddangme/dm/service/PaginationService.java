package com.ddangme.dm.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class PaginationService {

    private static final int PAGE_LENGTH = 5;

    public List<Integer> getPaginationLength(int currentPage, int totalPage) {
        System.out.println("currentPage = " + currentPage);
        System.out.println("totalPage = " + totalPage);
        int start = Math.max(currentPage - (PAGE_LENGTH / 2), 0);
        int end = Math.min(start + PAGE_LENGTH, totalPage);

        System.out.println("start = " + start);
        System.out.println("end = " + end);

        return IntStream.range(start, end).boxed().toList();
    }

    public int currentLength() {
        return PAGE_LENGTH;
    }
}
