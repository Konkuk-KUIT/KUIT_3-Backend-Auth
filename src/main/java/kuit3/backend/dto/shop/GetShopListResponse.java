package kuit3.backend.dto.shop;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class GetShopListResponse {
    private List<GetShopResponseEntity> shopList;
    private boolean hasNextPage;
}
