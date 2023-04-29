package model.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Restaurant {
    private String resName;
    private String resPhone;
    boolean open;
    private String category;
    private String menu;
    private String location;
}