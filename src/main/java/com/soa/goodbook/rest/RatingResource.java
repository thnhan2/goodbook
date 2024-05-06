package com.soa.goodbook.rest;

import com.soa.goodbook.model.RatingDTO;
import com.soa.goodbook.service.RatingService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/ratings", produces = MediaType.APPLICATION_JSON_VALUE)
public class RatingResource {

    private final RatingService ratingService;

    public RatingResource(final RatingService ratingService) {
        this.ratingService = ratingService;
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

}
