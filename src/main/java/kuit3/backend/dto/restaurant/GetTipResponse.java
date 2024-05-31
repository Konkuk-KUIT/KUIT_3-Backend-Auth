package kuit3.backend.dto.restaurant;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetTipResponse {
    @NotBlank(message = "delivery_tip: {NotBlank}")
    private double delivery_tip;
}
