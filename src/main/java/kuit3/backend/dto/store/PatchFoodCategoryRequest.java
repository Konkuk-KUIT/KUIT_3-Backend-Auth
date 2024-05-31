package kuit3.backend.dto.store;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchFoodCategoryRequest {
    @NotNull(message = "Food category: {NotNull}")
    private String foodCategory;
}