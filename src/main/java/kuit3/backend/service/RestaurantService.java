package kuit3.backend.service;

import kuit3.backend.common.exception.RestaurantException;
import kuit3.backend.common.exception.UserException;
import kuit3.backend.dao.RestaurantDao;
import kuit3.backend.dto.restaurant.GetRestaurantResponse;
import kuit3.backend.dto.restaurant.PostRestaurantRequest;
import kuit3.backend.dto.restaurant.RestaurantDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static kuit3.backend.common.response.status.BaseExceptionResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantDao restaurantDao;

    public long registerRestaurant(PostRestaurantRequest postRestaurantRequest) {
        validatePhone(postRestaurantRequest.getPhone());
        return restaurantDao.createRestaurant(postRestaurantRequest);
    }

    public void updateRestaurantStatus(long restaurantid, String status) {
        if (restaurantDao.modifyRestaurantStatus(restaurantid, status) != 1) {
            throw new RestaurantException(RESTAURANT_NOT_FOUND);
        }
    }

    public void updateRestaurantDetails(long restaurantid, PostRestaurantRequest postRestaurantRequest) {
        if (restaurantDao.modifyRestaurantDetails(restaurantid, postRestaurantRequest.getName(), postRestaurantRequest.getLocation(),
                postRestaurantRequest.getPhone(), postRestaurantRequest.getCategory(), postRestaurantRequest.getMinOrderAmount()) != 1) {
            throw new RestaurantException(RESTAURANT_NOT_FOUND);
        }
    }

    public List<GetRestaurantResponse> getRestaurants(String name, String location, String status, int page, int size) {
        int offset = page * size;
        List<RestaurantDto> restaurantDtos = restaurantDao.getRestaurants(name, location, status, offset, size);
        return restaurantDtos.stream()
                .map(dto -> new GetRestaurantResponse(
                        dto.getRestaurantid(),
                        dto.getName(),
                        dto.getLocation(),
                        dto.getPhone(),
                        dto.getCategory(),
                        dto.getMinOrderAmount(),
                        dto.getStatus()))
                .collect(Collectors.toList());
    }

    public GetRestaurantResponse getRestaurantById(long restaurantid) {
        RestaurantDto dto = restaurantDao.getRestaurantById(restaurantid);
        if (dto == null) {
            throw new RestaurantException(RESTAURANT_NOT_FOUND);
        }

        return new GetRestaurantResponse(
                dto.getRestaurantid(),
                dto.getName(),
                dto.getLocation(),
                dto.getPhone(),
                dto.getCategory(),
                dto.getMinOrderAmount(),
                dto.getStatus());
    }

    private void validatePhone(String phone) {
        if (restaurantDao.hasDuplicatePhone(phone)) {
            throw new RestaurantException(DUPLICATE_PHONE);
        }
    }
}