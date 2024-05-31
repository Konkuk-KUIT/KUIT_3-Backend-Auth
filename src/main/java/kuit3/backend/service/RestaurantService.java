package kuit3.backend.service;

import kuit3.backend.dao.RestaurantDao;
import kuit3.backend.dto.restaurant.GetRestaurantResponse;
import kuit3.backend.dto.restaurant.PostRestaurantRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantDao restaurantDao;

    public long makeNewRestaurant(PostRestaurantRequest postRestaurantRequest) {
        return restaurantDao.createRestaurant(postRestaurantRequest);
    }

    public List<GetRestaurantResponse> getRestaurants(double star, Long lastId) {
        return restaurantDao.getRestaurants(star, lastId);
    }

}
