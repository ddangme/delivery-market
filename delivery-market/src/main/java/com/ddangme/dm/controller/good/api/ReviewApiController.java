package com.ddangme.dm.controller.good.api;

import com.ddangme.dm.dto.member.MemberPrincipal;
import com.ddangme.dm.dto.review.ReviewRequest;
import com.ddangme.dm.service.review.ReviewManageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewApiController {

    private final ReviewManageService reviewService;

    @PostMapping
    public ResponseEntity<Void> saveReview(@AuthenticationPrincipal MemberPrincipal principal,
                                           ReviewRequest request, @RequestParam(required=false) List<MultipartFile> photos) throws IOException {

        reviewService.save(request, photos, principal.getId());
        return ResponseEntity.ok().build();
    }
}
