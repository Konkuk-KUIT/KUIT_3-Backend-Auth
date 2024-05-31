package kuit3.backend.service;

import kuit3.backend.common.exception.RestaurantException;
import kuit3.backend.dao.MenuDao;
import kuit3.backend.dao.RestaurantDao;
import kuit3.backend.dto.menu.GetMenuResponse;
import kuit3.backend.dto.menu.PostMenuRequest;
import kuit3.backend.dto.restaurant.GetRestaurantResponse;
import kuit3.backend.dto.restaurant.PostRestaurantMenuRequest;
import kuit3.backend.dto.restaurant.PostRestaurantRequest;
import kuit3.backend.dto.restaurant.PostRestaurantResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static kuit3.backend.common.response.status.BaseExceptionResponseStatus.DUPLICATE_MENU;
import static kuit3.backend.common.response.status.BaseExceptionResponseStatus.RESTAURANT_NOT_FOUND;


@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantDao restaurantDao;
    private final MenuDao menuDao;

    public PostRestaurantResponse createRestaurant(PostRestaurantRequest postRestaurantRequest){
        log.info("[RestaurantService.createRestaurant]");
        long restaurantId = restaurantDao.create(postRestaurantRequest);
        return new PostRestaurantResponse(restaurantId);
    }

    public List<GetRestaurantResponse> findRestaurantsByCategory(int categoryId) {
        return restaurantDao.findRestaurantsByCategory(categoryId);
    }

    public Long addMenu(long restaurantId, PostRestaurantMenuRequest postRestaurantMenuRequest) {
        log.info("addMenu :: ");

        validateRestaurant(restaurantId);

        validateDuplicateMenu(restaurantId, postRestaurantMenuRequest.getName());

        PostMenuRequest menuRequest = new PostMenuRequest(
                postRestaurantMenuRequest.getName(),
                postRestaurantMenuRequest.getImage_url(),
                postRestaurantMenuRequest.getDescription(),
                postRestaurantMenuRequest.getPrice(),
                restaurantId
        );
        return menuDao.createMenu(menuRequest);
    }

    private void validateDuplicateMenu(long restaurantId, String name) {
        if(menuDao.hasDuplicateMenu(restaurantId, name)){
            throw new RestaurantException(DUPLICATE_MENU);
        }
    }

    private void validateRestaurant(long restaurantId) {
        if(!restaurantDao.existsWithId(restaurantId)){
            throw new RestaurantException(RESTAURANT_NOT_FOUND);
        }
    }

    public List<GetMenuResponse> getMenus(long restaurantId) {
//        if(restaurantDao.doesExistById(restaurantId)){
            return menuDao.getMenus(restaurantId);
//        }
//        return null;
    }

    public void modifyStatusAsClosed(Long restaurantId) {
        restaurantDao.modifyStatusAsClosed(restaurantId);
    }

    public void modifyBusinnessHour(Long restaurantId, String businessHour) {
        restaurantDao.modifyBusinessHour(restaurantId, businessHour);
    }

    public List<GetRestaurantResponse> search(String keyword, Integer minStar, String maxDeliveryFee) {
        if(minStar != null){
            return restaurantDao.search(keyword, minStar.toString(), maxDeliveryFee);
        }
        return restaurantDao.search(keyword, null, maxDeliveryFee);
    }
}
