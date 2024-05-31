package kuit3.backend.service;


import kuit3.backend.dao.StoreDao;
import kuit3.backend.dto.store.GetStoreResponse;
import kuit3.backend.dto.store.PostStoreRequest;
import kuit3.backend.dto.store.PostStoreResponse;
import kuit3.backend.dto.store.PutStoreRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreDao storeDao;

    // 새로운 Store 생성
    public PostStoreResponse createStore(PostStoreRequest postStoreRequest) {
        long storeId = storeDao.createStore(postStoreRequest);

        return new PostStoreResponse(storeId);
    }

    // Store 조회
    public GetStoreResponse getStoreById(long storeId) {
        return storeDao.getStoreById(storeId);
    }

    // 모든 Store 조회
    public List<GetStoreResponse> getAllStores(long lastId, int size) {
        return storeDao.getAllStores(lastId, size);
    }

    // 카테고리가 Food인 스토어들을 조회하는 메서드
    public List<GetStoreResponse> getStoresByCategory(String category) {
        return storeDao.getStoresByCategory(category);
    }

    // Store 정보 수정
    public int updateStore(long storeId, PutStoreRequest putStoreRequest) {
        return storeDao.updateStore(storeId, putStoreRequest);
    }

    // Store 삭제
    public int deleteStore(long storeId) {
        return storeDao.deleteStore(storeId);
    }
}
