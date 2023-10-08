package by.gurinovich.bamper.DTO.user;

import lombok.Data;

@Data
public class ReviewDTO {
    private Long id;
    private String title;
    private String text;
    private Integer stars;
}
