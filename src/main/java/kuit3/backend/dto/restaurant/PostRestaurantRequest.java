package kuit3.backend.dto.restaurant;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostRestaurantRequest {

    @NotBlank(message = "category: {NotBlank}")
    private String category;

    @NotBlank(message = "store_name: {NotBlank}")
    private String store_name;

    @NotBlank(message = "delivery_area: {NotBlank}")
    private String delivery_area;

}
