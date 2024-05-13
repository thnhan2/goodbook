package com.soa.goodbook.repos;

import com.soa.goodbook.domain.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RatingPagingRepository extends PagingAndSortingRepository<Rating, String> {

    @Query(value = "{bookId:  ?0}")
    Page<Rating> findAllByBookId(String bookId, Pageable pageable);
}
