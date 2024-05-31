package kuit3.backend.controller;

import kuit3.backend.common.argument_resolver.PreAuthorize;
import kuit3.backend.common.exception.RestaurantException;
import kuit3.backend.common.response.BaseResponse;
import kuit3.backend.dto.restaurant.GetRestaurantResponse;
import kuit3.backend.dto.restaurant.PostRestaurantRequest;
import kuit3.backend.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static kuit3.backend.common.response.status.BaseExceptionResponseStatus.INVALID_RESTAURANT_VALUE;
import static kuit3.backend.util.BindingResultUtils.getErrorMessages;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping("")
    public BaseResponse<Long> makeNewRestaurant(@Validated @RequestBody PostRestaurantRequest postRestaurantRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RestaurantException(INVALID_RESTAURANT_VALUE, getErrorMessages(bindingResult));
        }
        return new BaseResponse<>(restaurantService.makeNewRestaurant(postRestaurantRequest));
    }

    @GetMapping("")
    public BaseResponse<List<GetRestaurantResponse>> getRestaurant(
            @RequestParam(required = false) Double star,
            @RequestParam(required = false) Long lastId) {
        return new BaseResponse<>(restaurantService.getRestaurants(star, lastId));
    }

}
