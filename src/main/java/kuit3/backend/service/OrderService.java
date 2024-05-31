package kuit3.backend.service;

import kuit3.backend.dao.OrderDao;
import kuit3.backend.dto.order.GetOrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderDao orderDao;

    public List<GetOrderResponse> getOrdersByUserId(long userId, long lastSeenId) {
        return orderDao.getOrdersByUserId(userId, lastSeenId);
    }
}