package kuit3.backend.controller;


import kuit3.backend.common.exception.RestaurantException;
import kuit3.backend.common.exception.UserException;
import kuit3.backend.common.response.BaseResponse;
import kuit3.backend.dto.restaurants.GetRestaurantResponse;
import kuit3.backend.dto.restaurants.PostRestaurantRequest;
import kuit3.backend.dto.restaurants.PostRestaurantResponse;
import kuit3.backend.service.RestaurantService;
import kuit3.backend.util.BindingResultUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static kuit3.backend.common.response.status.BaseExceptionResponseStatus.*;
import static kuit3.backend.util.BindingResultUtils.getErrorMessages;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    /**
     * 식당 목록 조회
     */
    @GetMapping("")
    public BaseResponse<List<GetRestaurantResponse>> getRestaurants(
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false, defaultValue = "") String category,
            @RequestParam(required = false, defaultValue = "open") String status) {
        log.info("[RestaurantController.getRestaurants]");

        // Check if status is null or invalid
        if (status == null || (!status.equals("open") && !status.equals("deleted") && !status.equals("active"))) {
            throw new RestaurantException(INVALID_RESTAURANT_STATUS);
        }

        return new BaseResponse<>(restaurantService.getRestaurants(name, category, status));
    }

    /**
     * 식당 추가
     */
    @PostMapping("")
    public BaseResponse<PostRestaurantResponse> newRestaurant(@Validated @RequestBody PostRestaurantRequest postRestaurantRequest, BindingResult bindingResult) {
        log.info("[RestaurantController.newRestaurant]");

        if (bindingResult.hasErrors()) {
            throw new RestaurantException(INVALID_RESTAURANT_VALUE, BindingResultUtils.getErrorMessages(bindingResult));
        }
        return new BaseResponse<>(restaurantService.newRestaurant(postRestaurantRequest));
    }

}
