package kuit3.backend.dto.restaurants;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

@Getter
@Setter
@NoArgsConstructor
public class PostRestaurantRequest {

    @Nullable
    @Length(max = 25, message = "name: 최대 {max}자리까지 가능합니다")
    private String name;

    @Nullable
    @Length(max = 10, message = "category: 최대 {max}자리까지 가능합니다")
    private String category;

    @NotNull(message = "min_price: {NotBlank}")
    private Integer min_price;

    @NotBlank(message = "phoneNumber: {NotBlank}")
    @Length(max = 20, message = "phoneNumber: 최대 {max}자리까지 가능합니다")
    private String phone_number;

    @Nullable
    private String profileImage;

    @Nullable
    private String store_image;
}
