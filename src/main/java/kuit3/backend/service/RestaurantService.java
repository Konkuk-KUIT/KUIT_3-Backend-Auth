package kuit3.backend.service;



import kuit3.backend.dao.RestaurantDao;
import kuit3.backend.dto.restaurants.GetRestaurantResponse;
import kuit3.backend.dto.restaurants.PostRestaurantRequest;
import kuit3.backend.dto.restaurants.PostRestaurantResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantDao restaurantDao;

    public PostRestaurantResponse newRestaurant(PostRestaurantRequest postRestaurantRequest) {
        log.info("[RestaurantService.newRestaurant]");


        // TODO: 3. DB insert & userId 반환
        long userId = restaurantDao.createRestaurant(postRestaurantRequest);


        return new PostRestaurantResponse(userId);
    }


    public List<GetRestaurantResponse> getRestaurants(String name, String email, String status) {
        log.info("[RestaurantService.getRestaurants]");
        return restaurantDao.getRestaurants(name, email, status);
    }
}
