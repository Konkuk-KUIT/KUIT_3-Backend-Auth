package kuit3.backend.service;

import kuit3.backend.dao.RestaurantDao;
import kuit3.backend.dto.restaurant.MenuUpdateRequest;
import kuit3.backend.dto.restaurant.RestaurantMenuRequest;
import kuit3.backend.dto.restaurant.RestaurantMenuResponse;
import kuit3.backend.dto.restaurant.RestaurantOrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantDao restaurantDao;

    public List<RestaurantOrderResponse> getOrders(Long restaurantId) {
        return restaurantDao.getOrders(restaurantId);
    }

    public List<RestaurantMenuResponse> getMenu(Long restaurantId) {
        return restaurantDao.getMenu(restaurantId);
    }

    public void createMenu(Long restaurantId, RestaurantMenuRequest menuRequest) {
        restaurantDao.createMenu(restaurantId,menuRequest);
    }

    public void modifyMenuStatus_delete(Long restaurantId, Long menuId) {
        restaurantDao.modifyMenuStatus_delete(restaurantId,menuId);
    }

    public void updateMenu(Long restaurantId, Long menuId, MenuUpdateRequest menuUpdateRequest) {
        restaurantDao.modifyMenu(restaurantId,menuId,menuUpdateRequest);
    }

    public void modifyStatus_delete(Long restaurantId) {
        restaurantDao.modifyStatus_delete(restaurantId);
    }
}
