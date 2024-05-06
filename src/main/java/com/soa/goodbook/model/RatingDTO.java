package com.soa.goodbook.model;

import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RatingDTO {

    @Size(max = 255)
//    @RatingIdValid
    private String id;

    private LocalDateTime dateCreated;

    private Integer rating;

    private LocalDateTime lastUpdated;

    @Size(max = 255)
    private String reviewText;

    @Size(max = 255)
    private String bookId;

    @Size(max = 255)
    private String userId;

}
