package kuit3.backend.service;



import kuit3.backend.dao.RestaurantDao;
import kuit3.backend.dto.restaurants.GetRestaurantResponse;
import kuit3.backend.dto.restaurants.PostRestaurantRequest;
import kuit3.backend.dto.restaurants.PostRestaurantResponse;
import kuit3.backend.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantDao restaurantDao;
    private final JwtProvider jwtProvider;

    public PostRestaurantResponse newRestaurant(PostRestaurantRequest postRestaurantRequest) {
        log.info("[RestaurantService.newRestaurant]");

        // TODO: 3. DB insert & userId 반환
        long storeId = restaurantDao.createRestaurant(postRestaurantRequest);


        return new PostRestaurantResponse(storeId);
    }


    public List<GetRestaurantResponse> getRestaurants(String name, String email, String status) {
        log.info("[RestaurantService.getRestaurants]");
        return restaurantDao.getRestaurants(name, email, status);
    }
}
