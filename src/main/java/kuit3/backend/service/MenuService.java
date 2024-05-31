package kuit3.backend.service;

import kuit3.backend.common.exception.DatabaseException;
import kuit3.backend.dao.MenuDao;
import kuit3.backend.dto.menu.GetMenuResponse;
import kuit3.backend.dto.menu.PatchMenuRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static kuit3.backend.common.response.status.BaseExceptionResponseStatus.DATABASE_ERROR;


@Slf4j
@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuDao menuDao;

    public List<GetMenuResponse> searchByKeyword(String keyword) {
        return menuDao.getMenusByKeyword(keyword);
    }


    public void modifyPrice(Long menuId, PatchMenuRequest patchMenuRequest) {
        int affectedRow = menuDao.modifyPrice(menuId, patchMenuRequest.getPrice());
        if(affectedRow == -1){
            throw new DatabaseException(DATABASE_ERROR);
        }
    }
}
