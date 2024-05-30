package kuit3.backend.dto.store;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostStoreResponse {

    private long storeId;
    private String jwt;
}
