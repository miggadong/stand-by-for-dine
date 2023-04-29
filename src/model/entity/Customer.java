package model.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Customer {
    private String cusName;
    private String cusPhone;
}
