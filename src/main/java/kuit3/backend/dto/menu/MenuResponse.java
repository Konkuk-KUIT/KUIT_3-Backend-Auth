package kuit3.backend.dto.menu;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MenuResponse {
    private Long menuId;
    private String menuName;
    private int price;
    private String optionName;
}
