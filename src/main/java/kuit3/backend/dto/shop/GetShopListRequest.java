package kuit3.backend.dto.shop;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.NumberFormat;

@Getter
@Setter
@RequiredArgsConstructor
public class GetShopListRequest {
    @NotBlank(message = "foodCategory: {NotBlank}")
    @Length(max = 10, message = "foodCategory: 최대 {max}자리까지 가능합니다")
    private String foodCategory;

    @NotBlank(message = "address: {NotBlank}")
    @Length(max = 50, message = "address: 최대 {max}자리까지 가능합니다")
    private String address;

    @NotNull
    private long lastId;

    @NotNull
    @Min(value = 1, message = "Value must be greater than or equal to 1")
    private int number = 10;
}
