package com.ddangme.dm.service.review;

import com.ddangme.dm.dto.review.ReviewRequest;
import com.ddangme.dm.exception.DMException;
import com.ddangme.dm.exception.ErrorCode;
import com.ddangme.dm.model.good.GoodOption;
import com.ddangme.dm.model.member.Member;
import com.ddangme.dm.model.order.OrderGood;
import com.ddangme.dm.model.review.Review;
import com.ddangme.dm.repository.good.GoodOptionRepository;
import com.ddangme.dm.repository.member.MemberRepository;
import com.ddangme.dm.repository.order.OrderGoodRepository;
import com.ddangme.dm.repository.review.ReviewRepository;
import com.ddangme.dm.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewManageService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final GoodOptionRepository optionRepository;
    private final OrderGoodRepository orderGoodRepository;
    private final FileService fileService;

    @Transactional
    public void save(ReviewRequest request, List<MultipartFile> photos, Long memberId) throws IOException {
        Member member = findMember(memberId);
        GoodOption option = findGoodOption(request.getOptionId());
        OrderGood orderGood = findOrderGood(request.getOrderGoodId());
        validateReview(request.getContent(), photos, orderGood);

        Review review = new Review(option, orderGood, request.getRating(), request.getSecret(), request.getContent(), fileService.saveReviewPhotos(photos));
        member.addPoint(review.getPoint());

        reviewRepository.save(review);
    }

    public void validateReview(String content, List<MultipartFile> photos, OrderGood orderGood) {
        orderGood.validateReviewWrite();

        if (content == null || content.length() < 10 || content.length() > 1000) {
            throw new DMException(ErrorCode.REVIEW_LENGTH);
        }

        if (photos.size() > 8) {
            throw new DMException(ErrorCode.COUNT_ERROR_REVIEW_PHOTO);
        }
    }


    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new DMException(ErrorCode.NOT_FOUND_ACCOUNT));
    }

    private GoodOption findGoodOption(Long goodOptionId) {
        return optionRepository.findById(goodOptionId)
                .orElseThrow(() -> new DMException(ErrorCode.BAD_REQUEST));
    }

    private OrderGood findOrderGood(Long orderGoodId) {
        return orderGoodRepository.findById(orderGoodId)
                .orElseThrow(() -> new DMException(ErrorCode.BAD_REQUEST));
    }
}
