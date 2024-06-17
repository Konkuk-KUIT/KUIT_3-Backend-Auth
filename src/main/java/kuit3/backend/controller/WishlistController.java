package kuit3.backend.controller;


import kuit3.backend.common.response.BaseResponse;
import kuit3.backend.dto.wishlist.GetWishlistRequest;
import kuit3.backend.dto.wishlist.PostWishlistRequest;
import kuit3.backend.dto.wishlist.PostWishlistResponse;
import kuit3.backend.service.WishlistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static kuit3.backend.util.BindingResultUtils.getErrorMessages;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/wishlist")
public class WishlistController {
    private final WishlistService wishlistService;
    @PostMapping("")
    public BaseResponse<PostWishlistResponse> getWishlist(@Validated @RequestBody PostWishlistRequest wishLisRreq, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new RuntimeException( getErrorMessages(bindingResult));
        }
        return new BaseResponse<>(wishlistService.createWishlist(wishLisRreq));
    }
    @GetMapping("")
    public BaseResponse<Object> getWishlist(@Validated @RequestBody GetWishlistRequest wishLisRreq, BindingResult bindingResult){

        return new BaseResponse<>(wishlistService.getWishlist(wishLisRreq));
    }
}
