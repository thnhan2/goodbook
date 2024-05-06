package com.soa.goodbook.repos;

import com.soa.goodbook.domain.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;


public interface RatingRepository extends MongoRepository<Rating, String> {

    boolean existsByIdIgnoreCase(String id);

    @Query("{'userId' : ?0, 'bookId': ?1}")
    boolean existsByUserIdAndBookId(String userId, String bookId);

    Optional<Rating> findByUserIdAndBookId(String userId, String bookId);

}
