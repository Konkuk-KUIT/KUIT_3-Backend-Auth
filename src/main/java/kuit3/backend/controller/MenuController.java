package kuit3.backend.controller;

import kuit3.backend.common.response.BaseResponse;
import kuit3.backend.dto.menu.GetMenuResponse;
import kuit3.backend.dto.menu.PatchMenuRequest;
import kuit3.backend.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/menus")
public class MenuController {
    private final MenuService menuService;

    @GetMapping("")
    public BaseResponse<List<GetMenuResponse>> searchByKeyword(@RequestParam("keyword") String keyword){
        log.info("menuController.searchByKeyword :: " + keyword);
        return new BaseResponse<>(menuService.searchByKeyword(keyword));
    }

    /*
    메뉴 가격 변경
     */
    @PatchMapping("/{menuId}")
    public BaseResponse<String> modifyPrice(@PathVariable Long menuId,
                                            @RequestBody PatchMenuRequest patchMenuRequest){
        log.info("modifyPrice :: request = " +  patchMenuRequest);
        menuService.modifyPrice(menuId, patchMenuRequest);
        return new BaseResponse<>(null);
    }
}
