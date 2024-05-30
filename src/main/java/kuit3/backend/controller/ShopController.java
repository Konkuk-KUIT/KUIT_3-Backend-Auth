package kuit3.backend.controller;


import com.fasterxml.jackson.databind.annotation.JsonValueInstantiator;
import kuit3.backend.common.exception.ShopException;
import kuit3.backend.common.response.BaseResponse;
import kuit3.backend.dto.shop.*;
import kuit3.backend.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static kuit3.backend.common.response.status.BaseExceptionResponseStatus.INVALID_SHOP_VALUE;
import static kuit3.backend.util.BindingResultUtils.getErrorMessages;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/shop")
public class ShopController {
    private final ShopService shopService;

    /**
    * shop 추가
     * */
    @PostMapping("")
    public BaseResponse<PostShopResponse> signUp(@Validated @RequestBody PostShopRequest postShopRequest, BindingResult bindingResult) {
        log.info("[ShopController.addShop]");
        if (bindingResult.hasErrors()) {
            throw new ShopException(INVALID_SHOP_VALUE, getErrorMessages(bindingResult));
        }
        return new BaseResponse<>(shopService.addShop(postShopRequest));
    }
    @GetMapping("/list")
    public BaseResponse<List<Shop>> getShops(@RequestParam(required = false) String category, @RequestParam(required = false) String address) {
        if (category != null && address != null) {
            // 카테고리와 주소로 가게 검색
            return new BaseResponse<>(shopService.getShopsByCategoryAndAddress(category, address));

        } else if (category != null) {
            // 카테고리로 가게 검색
            return new BaseResponse<>(shopService.getShopsByCategory(category));
        } else if (address != null) {
            // 주소로 가게 검색
            return new BaseResponse<>(shopService.getShopsByAddress(address));
        } else {
            // 카테고리와 주소가 주어지지 않은 경우 모든 가게 목록 반환
            return new BaseResponse<>( shopService.getAllShops());
        }
    }
    @GetMapping("/list-page")
    public BaseResponse<GetShopListResponse> getShopPage(@Validated @RequestBody GetShopListRequest getShopListRequest) {
        return new BaseResponse<>(shopService.getShopList(getShopListRequest));
    }
    @GetMapping("/detail")
    public BaseResponse<List<Shop>> getShopDetail(@Validated @RequestParam long shopId, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ShopException(INVALID_SHOP_VALUE, getErrorMessages(bindingResult));
        }
        return new BaseResponse<>(shopService.getShopById(shopId));
    }

    @GetMapping("/food-categories")
    public List<FoodCategory> getAllFoodCategories() {
        return shopService.getAllFoodCategories();
    }

    @PostMapping("/food-categories/{foodCategory}")
    public BaseResponse<String> postAllFoodCategories(@Range(min=1,max=10) @PathVariable String foodCategory) {
        return new BaseResponse<>(shopService.addFoodCategory(foodCategory));
    }
}
