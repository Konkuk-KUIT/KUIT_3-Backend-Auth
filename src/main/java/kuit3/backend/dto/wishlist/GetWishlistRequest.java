package kuit3.backend.dto.wishlist;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class GetWishlistRequest {
    @NotNull
    private long userId;
}
