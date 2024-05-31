package kuit3.backend.dto.restaurant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantMenuRequest {

    @NotBlank
//    @Length(max = 25, message = "menuName: 최대 {max}자리까지 가능합니다")
    private String menuName;

    @NotNull(message = "값을 입력해주세요")
    @PositiveOrZero(message = "가격은 0 이상이어야 합니다")
    private int price;
}