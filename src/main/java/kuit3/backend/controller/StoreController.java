package kuit3.backend.controller;

import kuit3.backend.common.exception.UserException;
import kuit3.backend.common.response.BaseResponse;
import kuit3.backend.dto.store.GetStoreAddressResponse;
import kuit3.backend.dto.store.GetStoreResponse;
import kuit3.backend.dto.store.PatchFoodCategoryRequest;
import kuit3.backend.dto.store.PostStoreRequest;
import kuit3.backend.service.StoreService;
import kuit3.backend.util.BindingResultUtils;
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
@RequestMapping("/stores")
public class StoreController {
    private final StoreService storeService;

    @PostMapping("")
    public BaseResponse<Long> registerStore(@Validated @RequestBody PostStoreRequest storeRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new UserException(INVALID_USER_VALUE, getErrorMessages(bindingResult));
        }
        long storeId = storeService.registerStore(storeRequest);
        return new BaseResponse<>(storeId);
    }


    @GetMapping("")
    public BaseResponse<List<GetStoreResponse>> getAllStores() {
        List<GetStoreResponse> stores = storeService.getAllStores();
        return new BaseResponse<>(stores);
    }

    @GetMapping("/{storeId}")
    public BaseResponse<GetStoreResponse> getStoreById(@PathVariable long storeId) {
        GetStoreResponse storeResponse = storeService.getStoreById(storeId);
        return new BaseResponse<>(storeResponse);
    }

    // 푸드 카테고리 수정
    @PatchMapping("/{storeId}/food_category")
    public BaseResponse<Void> updateFoodCategory(@PathVariable long storeId,
                                                 @Validated @RequestBody PatchFoodCategoryRequest request,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new UserException(INVALID_USER_VALUE, BindingResultUtils.getErrorMessages(bindingResult));
        }
        storeService.modifyFoodCategory(storeId, request.getFoodCategory());
        return new BaseResponse<>(null);
    }

    // 특정 스토어 주소 가져오기
    @GetMapping("/{storeId}/address")
    public BaseResponse<GetStoreAddressResponse> getAddress(@PathVariable long storeId) {
        String address = storeService.getStoreAddress(storeId);
        return new BaseResponse<>(new GetStoreAddressResponse(storeId,address));
    }
}
