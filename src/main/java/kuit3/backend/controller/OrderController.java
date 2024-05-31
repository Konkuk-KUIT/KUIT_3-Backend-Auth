package kuit3.backend.controller;

import kuit3.backend.common.argument_resolver.PreAuthorize;
import kuit3.backend.common.exception.UserException;
import kuit3.backend.common.response.BaseResponse;
import kuit3.backend.dto.order.GetOrderResponse;
import kuit3.backend.dto.user.*;
import kuit3.backend.service.OrderService;
import kuit3.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static kuit3.backend.common.response.status.BaseExceptionResponseStatus.INVALID_USER_STATUS;
import static kuit3.backend.common.response.status.BaseExceptionResponseStatus.INVALID_USER_VALUE;
import static kuit3.backend.util.BindingResultUtils.getErrorMessages;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    /**
     * 특정 회원의 주문 목록 조회
     */
    @GetMapping("/my") //non-offset paging
    public BaseResponse<List<GetOrderResponse>> getOrdersOfLoggedInUser(@PreAuthorize Long userId,
                                                                        @RequestParam int lastSeenId) {
        return new BaseResponse<>(orderService.getOrdersByUserId(userId, lastSeenId));
    }
}