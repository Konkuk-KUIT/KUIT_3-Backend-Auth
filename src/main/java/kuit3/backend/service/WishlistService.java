package kuit3.backend.service;


import kuit3.backend.common.exception.UserException;
import kuit3.backend.common.response.status.ResponseStatus;
import kuit3.backend.dao.ShopDao;
import kuit3.backend.dao.UserDao;
import kuit3.backend.dao.WishlistDao;
import kuit3.backend.dto.wishlist.GetWishlistRequest;
import kuit3.backend.dto.wishlist.GetWishlistResponse;
import kuit3.backend.dto.wishlist.PostWishlistRequest;
import kuit3.backend.dto.wishlist.PostWishlistResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static kuit3.backend.common.response.status.BaseExceptionResponseStatus.INVALID_SHOP_ID;
import static kuit3.backend.common.response.status.BaseExceptionResponseStatus.INVALID_USER_ID;


@Slf4j
@Service
@RequiredArgsConstructor
public class WishlistService {

    final private WishlistDao wishlistDao;
    private final UserDao userDao;
    private final ShopDao shopDao;
    public void validationWishlistPost(PostWishlistRequest wishlistRequest){
        if(!userDao.isExistId(wishlistRequest.getUserId())){
            throw new UserException(INVALID_USER_ID);
        }
        if(!shopDao.isExistId(wishlistRequest.getShopId())){
            throw new UserException(INVALID_SHOP_ID);
        }
    }

    public PostWishlistResponse createWishlist(PostWishlistRequest wishlistRequest){
        validationWishlistPost(wishlistRequest);
        return wishlistDao.createWishlist(wishlistRequest);

    }

    public List<GetWishlistResponse> getWishlist(GetWishlistRequest wishlistRequest){
        if(!userDao.isExistId(wishlistRequest.getUserId())){
            throw new UserException(INVALID_USER_ID);
        }
        return wishlistDao.findShopsByUserId(wishlistRequest);

    }
    //public void deleteWishlist()
}
