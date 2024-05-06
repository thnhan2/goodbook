package com.soa.goodbook.domain;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
@Getter
@Setter
public class Rating {

    @Id
    private String id;

    @Setter
    @CreatedDate
    private LocalDateTime dateCreated;

    @Max(5)
    @Min(1)
    private Integer rating;

    @LastModifiedDate
    private LocalDateTime lastUpdated;

    @Size(max = 255)
    private String reviewText;

    @Size(max = 255)
    private String bookId;

    @Size(max = 255)
    private String userId;

}
