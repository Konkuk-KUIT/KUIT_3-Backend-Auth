package kuit3.backend.dto.wishlist;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@RequiredArgsConstructor
public class PostWishlistRequest {
    @NotNull
    private long userId;
    @NotNull
    private long shopId;
}
