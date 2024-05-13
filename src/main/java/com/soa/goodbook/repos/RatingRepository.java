package com.soa.goodbook.repos;

import com.soa.goodbook.domain.Rating;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;


public interface RatingRepository extends MongoRepository<Rating, String> {

    boolean existsByIdIgnoreCase(String id);

    List<Rating> findAllRatingByBookId(String bookId);

        @Query("{'userId' : ?0, 'bookId': ?1}")
    boolean existsByUserIdAndBookId(String userId, String bookId);

    Integer countRatingByRatingEquals(int star, String bookId);

    Optional<Rating> findByUserIdAndBookId(String userId, String bookId);

    @Query("{'bookId': ?0}")
    List<Rating> countRatingsByBookId(String bookId);

    @Query(value = "{bookId:  ?0, reviewText:  {$ne:  null, $ne:  ''}}", count = true)
    Long countReviewTextByBookId(String bookId);



}
