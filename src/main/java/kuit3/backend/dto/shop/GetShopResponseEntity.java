package kuit3.backend.dto.shop;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class GetShopResponseEntity {
    private long shopId;
    private String shopName;
    private String shopCallNumber;
    private String address;
}
