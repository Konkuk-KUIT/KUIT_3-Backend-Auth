package kuit3.backend.controller;

import kuit3.backend.common.response.BaseResponse;
import kuit3.backend.dto.category.CategoryResponse;
import kuit3.backend.dto.category.CategoryStoreResponse;
import kuit3.backend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    // 카테고리 목록 조회하기
    @GetMapping("")
    public BaseResponse<List<CategoryResponse>> getCategories(){
        return new BaseResponse<>(categoryService.getCategories());
    }

    // 해당 카테고리 음식점 목록 조회하기
    @GetMapping("/{categoryId}")
    public BaseResponse<List<CategoryStoreResponse>> CategoryStoreController(
            @PathVariable Long categoryId,
            @RequestParam(name = "min-order-fee", required = false) int minOrderFee){
        return new BaseResponse<>(categoryService.getStore(categoryId,minOrderFee));
    }


}
