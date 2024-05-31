package kuit3.backend.controller;


import kuit3.backend.common.exception.StoreException;
import kuit3.backend.common.response.BaseResponse;
import kuit3.backend.dto.store.GetStoreResponse;
import kuit3.backend.dto.store.PostStoreRequest;
import kuit3.backend.dto.store.PostStoreResponse;
import kuit3.backend.dto.store.PutStoreRequest;
import kuit3.backend.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static kuit3.backend.common.response.status.BaseExceptionResponseStatus.INVALID_STORE_VALUE;
import static kuit3.backend.common.response.status.BaseExceptionResponseStatus.STORE_NOT_FOUND;
import static kuit3.backend.util.BindingResultUtils.getErrorMessages;


@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;

    // 새로운 Store 생성
    @PostMapping("")
    public BaseResponse<PostStoreResponse> createStore(@Validated @RequestBody PostStoreRequest postStoreRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            throw new StoreException(INVALID_STORE_VALUE, getErrorMessages(bindingResult));
        }

        return new BaseResponse<>(storeService.createStore(postStoreRequest));
    }

    // Store 조회
    @GetMapping("/{storeId}")
    public BaseResponse<GetStoreResponse> getStoreById(@PathVariable long storeId) {
        GetStoreResponse store = storeService.getStoreById(storeId);
        if (store != null) {
            return new BaseResponse<>(store);
        } else {
            throw new StoreException(STORE_NOT_FOUND);
        }
    }

    // 모든 Store 조회
    @GetMapping("")
    public BaseResponse<List<GetStoreResponse>> getAllStores(
            @RequestParam(required = false, defaultValue = "0") long lastId,
            @RequestParam(required = false, defaultValue = "20") int size
    ) {
        List<GetStoreResponse> stores = storeService.getAllStores(lastId, size);
        return new BaseResponse<>(stores);
    }

    // Store 정보 수정
    @PutMapping("/{storeId}")
    public BaseResponse<Void> updateStore(@Validated @PathVariable long storeId, @RequestBody PutStoreRequest putStoreRequest, BindingResult bindingResult) {
        int updatedRows = storeService.updateStore(storeId, putStoreRequest);

        if (bindingResult.hasErrors()){
            throw new StoreException(INVALID_STORE_VALUE, getErrorMessages(bindingResult));
        }

        if (updatedRows > 0) {
            return new BaseResponse<>(null);
        } else {
            throw new StoreException(STORE_NOT_FOUND);
        }
    }

    // Store 삭제
    @DeleteMapping("/{storeId}")
    public BaseResponse<Void> deleteStore(@PathVariable long storeId) {
        int deletedRows = storeService.deleteStore(storeId);
        if (deletedRows > 0) {
            return new BaseResponse<>(null);
        } else {
            throw new StoreException(STORE_NOT_FOUND);
        }
    }
}
