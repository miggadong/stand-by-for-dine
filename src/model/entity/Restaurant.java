package model.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Restaurant {
    private Integer resId;
    private String resName;
    private String resPhone;
    private String category;
    private String location; // 단순주소 저장할 변수
    private boolean open;
    private String description;
    private String custId;

    public void setData(Integer id, String name, String phone, String category, String location, boolean open, String description) {
        this.resId = id;
        this.resName = name;
        this.resPhone = phone;
        this.category = category;
        this.location = location;
        this.open = open;
        this.description = description;
    }
}