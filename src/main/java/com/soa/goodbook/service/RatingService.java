package com.soa.goodbook.service;

import com.soa.goodbook.domain.Rating;
import com.soa.goodbook.model.RatingDTO;
import com.soa.goodbook.repos.RatingRepository;
import com.soa.goodbook.util.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class RatingService {

    private final RatingRepository ratingRepository;

    public RatingService(final RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public List<RatingDTO> findAll() {
        final List<Rating> ratings = ratingRepository.findAll(Sort.by("id"));
        return ratings.stream()
                .map(rating -> mapToDTO(rating, new RatingDTO()))
                .toList();
    }

    public RatingDTO get(final String id) {
        return ratingRepository.findById(id)
                .map(rating -> mapToDTO(rating, new RatingDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final RatingDTO ratingDTO) {
        Optional<Rating> ratingOptional  = ratingRepository.findByUserIdAndBookId(ratingDTO.getUserId(), ratingDTO.getBookId());
        if (ratingOptional.isPresent()) {
            Rating rating = ratingOptional.get();
            String id = rating.getId();
            ratingDTO.setId(id);
            ratingDTO.setDateCreated(rating.getDateCreated());
            update(id, ratingDTO);
            return id;
        }

        final Rating rating = new Rating();
        ratingDTO.setDateCreated(LocalDateTime.now());
        ratingDTO.setLastUpdated(LocalDateTime.now());
        mapToEntity(ratingDTO, rating);
        Rating savedRating = ratingRepository.save(rating);
        return savedRating.getId();
    }

    public void update(final String id, final RatingDTO ratingDTO) {
        final Rating rating = ratingRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        ratingDTO.setDateCreated(rating.getDateCreated());
        if (ratingDTO.getRating() == null) {
        ratingDTO.setRating(rating.getRating());
        }

        if (ratingDTO.getReviewText() == null) {
            ratingDTO.setReviewText(rating.getReviewText());
        } else if (ratingDTO.getReviewText().isEmpty()) {
            ratingDTO.setReviewText("");
        }

        ratingDTO.setLastUpdated(LocalDateTime.now());
        mapToEntity(ratingDTO, rating);
        ratingRepository.save(rating);
    }

    public void delete(final String id) {
        ratingRepository.deleteById(id);
    }

    private RatingDTO mapToDTO(final Rating rating, final RatingDTO ratingDTO) {
        ratingDTO.setId(rating.getId());
        ratingDTO.setDateCreated(rating.getDateCreated());
        ratingDTO.setRating(rating.getRating());
        ratingDTO.setLastUpdated(rating.getLastUpdated());
        ratingDTO.setReviewText(rating.getReviewText());
        ratingDTO.setBookId(rating.getBookId());
        ratingDTO.setUserId(rating.getUserId());
        return ratingDTO;
    }

    private Rating mapToEntity(final RatingDTO ratingDTO, final Rating rating) {
        rating.setDateCreated(ratingDTO.getDateCreated());
        rating.setRating(ratingDTO.getRating());
        rating.setLastUpdated(ratingDTO.getLastUpdated());
        rating.setReviewText(ratingDTO.getReviewText());
        rating.setBookId(ratingDTO.getBookId());
        rating.setUserId(ratingDTO.getUserId());
        return rating;
    }

    public boolean idExists(final String id) {
        return ratingRepository.existsByIdIgnoreCase(id);
    }

}
