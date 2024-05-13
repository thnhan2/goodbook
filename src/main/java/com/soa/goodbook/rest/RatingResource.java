package com.soa.goodbook.rest;

import com.soa.goodbook.model.RatingDTO;
import com.soa.goodbook.repos.RatingPagingRepository;
import com.soa.goodbook.service.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.websocket.server.PathParam;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/ratings", produces = MediaType.APPLICATION_JSON_VALUE)
public class RatingResource {

    private final RatingService ratingService;
    private final RatingPagingRepository ratingPagingRepository;
    private final ServerProperties serverProperties;

    public RatingResource(final RatingService ratingService,
                          RatingPagingRepository ratingPagingRepository, ServerProperties serverProperties) {
        this.ratingService = ratingService;
        this.ratingPagingRepository = ratingPagingRepository;
        this.serverProperties = serverProperties;
    }

    @GetMapping("/{bookId}/v2/details")
    public ResponseEntity<List<RatingDTO>> getRatingOfBookById(@PathVariable("bookId")String bookId) {
        return ResponseEntity.ok(ratingService.findAllByBookId(bookId));
    }
    @GetMapping
    public ResponseEntity<List<RatingDTO>> getAllRatings() {
        return ResponseEntity.ok(ratingService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RatingDTO> getRating(@PathVariable(name = "id") final String id) {
        return ResponseEntity.ok(ratingService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<String> createRating(@RequestBody @Valid final RatingDTO ratingDTO) {
        final String createdId = ratingService.create(ratingDTO);
        return new ResponseEntity<>('"' + createdId + '"', HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateRating(@PathVariable(name = "id") final String id,
            @RequestBody @Valid final RatingDTO ratingDTO) {
        ratingService.update(id, ratingDTO);
        return ResponseEntity.ok('"' + id + '"');
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteRating(@PathVariable(name = "id") final String id) {
        ratingService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{bookId}/rating-count")
    @Operation(summary = "Get number of rating of a book based on book ID.")
    public ResponseEntity<Map<String,String>> getRatingsCount(@PathVariable(name = "bookId") final String bookId) {
        Map<String, String> map = new HashMap<>();
        long count = ratingService.getRatingByBookId(bookId);
        map.put("bookId", bookId);
        map.put("rating_count", String.valueOf(count));
        return ResponseEntity.ok(map);
    }

    @GetMapping("/{id}/review-count")
    @Operation(summary = "Get number of review text of a book base on book ID.")
    public ResponseEntity<Map<String,String>> getReviewsCount(@PathVariable(name = "id") final String id) {
        Map<String, String> map = new HashMap<>();
        long count = ratingService.getNumberOfReviewTextByBookId(id);
        map.put("bookId", id);
        map.put("review_count", String.valueOf(count));
        return ResponseEntity.ok(map);
    }

    @GetMapping("/{bookId}/details")
    @Operation(summary = "get rating review paging")
    public ResponseEntity<List<RatingDTO>> getRatingPaging(@PathVariable("bookId") String bookId, @RequestParam("page") int page, @RequestParam("offset") int offset) {
        return ResponseEntity.ok(ratingService.getRatingOfBookPaging(bookId, page, offset));
    }

    @GetMapping("/{bookId}/ratings")
    @Operation(summary = "get rating by star")
    public ResponseEntity<Integer> getNumberRatingByStar(@RequestParam ("star") int star, @PathVariable String bookId) {
        return ResponseEntity.ok(ratingService.getRatingOfBookWithStar(star, bookId));
    }

}
