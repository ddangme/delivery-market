package com.ddangme.dm.repository.review;

import com.ddangme.dm.model.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
