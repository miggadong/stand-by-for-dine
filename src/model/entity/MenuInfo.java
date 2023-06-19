package model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuInfo {
    private Integer menuId;
    private Integer resId;
    private Integer price;
    private String menuName;
    private String status;
    private Integer menuNo;
    private String imageRoot;

    public void setData(int resId, int price, String menuName, String status, int menuNo){
        this.resId = resId;
        this.price = price;
        this.menuName = menuName;
        this.status = status;
        this.menuNo = menuNo;
    }
}
