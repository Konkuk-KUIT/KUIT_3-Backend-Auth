package kuit3.backend.dto.wishlist;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class GetWishlistResponse {
    private long shopId;
    private String shopName;

}
