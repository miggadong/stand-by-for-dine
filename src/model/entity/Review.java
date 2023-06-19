package model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review {
    private Integer reviewId;
    private Integer resId;
    private String custId;
    private String review;
    private Integer score;
    private String reviewAt;
    private String resName;
}
