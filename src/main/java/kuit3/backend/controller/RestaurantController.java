package kuit3.backend.controller;


import kuit3.backend.common.exception.RestaurantException;
import kuit3.backend.common.response.BaseResponse;
import kuit3.backend.dto.restaurant.GetSearchResponse;
import kuit3.backend.dto.restaurant.GetTipResponse;
import kuit3.backend.dto.restaurant.PostRestaurantRequest;
import kuit3.backend.dto.restaurant.PostRestaurantResponse;
import kuit3.backend.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static kuit3.backend.common.response.status.BaseExceptionResponseStatus.INVALID_USER_VALUE;
import static kuit3.backend.util.BindingResultUtils.getErrorMessages;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;

    @PostMapping("")
    public BaseResponse<PostRestaurantResponse> register(@Validated @RequestBody PostRestaurantRequest postRestaurantRequest, BindingResult bindingResult) {
        log.info("[UserController.signUp]");
        if (bindingResult.hasErrors()) {
            throw new RestaurantException(INVALID_USER_VALUE, getErrorMessages(bindingResult));
        }
        return new BaseResponse<>(restaurantService.register(postRestaurantRequest));
    }

    @GetMapping("/{restaurantId}/delivery_tip")
    public BaseResponse<GetTipResponse>  getTip(@PathVariable long restaurantId){
        return new BaseResponse<>(restaurantService.getTip(restaurantId));
    }

    @GetMapping("/search")
    public BaseResponse<List<GetSearchResponse>> search(@RequestParam("sort-by") String sort_by, @RequestParam("min-starts") double min_starts, @RequestParam("min-order-fee") double min_order_fee, @RequestParam long page){
        return new BaseResponse<>(restaurantService.search(sort_by, min_starts, min_order_fee, page));
    }


}
