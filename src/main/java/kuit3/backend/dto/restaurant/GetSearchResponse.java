package kuit3.backend.dto.restaurant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetSearchResponse {
    private String store_name;
    private double rating;
    private long like;
}
