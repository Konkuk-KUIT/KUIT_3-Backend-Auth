package kuit3.backend.service;

import kuit3.backend.common.exception.RestaurantException;
import kuit3.backend.dao.RestaurantDao;
import kuit3.backend.dto.restaurant.GetSearchResponse;
import kuit3.backend.dto.restaurant.GetTipResponse;
import kuit3.backend.dto.restaurant.PostRestaurantRequest;
import kuit3.backend.dto.restaurant.PostRestaurantResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static kuit3.backend.common.response.status.BaseExceptionResponseStatus.DUPLICATE_STORENAME;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantDao restaurantDao;

    public PostRestaurantResponse register(PostRestaurantRequest postRestaurantRequest) {

        // category,delivery_area는 enum으로 검사셋 만들어서 체크해야하나?
        validateStoreName(postRestaurantRequest.getStore_name());

        long storeId = restaurantDao.createRestaurant(postRestaurantRequest);

        return new PostRestaurantResponse(storeId);
    }

    private void validateStoreName(String storeName) {
        if(restaurantDao.hasDuplicateStoreName(storeName)){
            throw new RestaurantException(DUPLICATE_STORENAME);
        }
    }

    public GetTipResponse getTip(long restaurantId) {
        return restaurantDao.getTip(restaurantId);
    }

    public List<GetSearchResponse> search(String sortBy, double minStarts, double minOrderFee, long page) {
        return restaurantDao.search(sortBy,minStarts, minOrderFee, page);
    }
}
