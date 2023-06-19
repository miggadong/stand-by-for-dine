package model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    private int member_no;
    private String cusId;
    private String cusPwd;
    private String cusName;
    private String cusPhone;
    private Integer auth;
    private String que;
    private boolean admin;
    private boolean showTrayMessage = false;


    public void setData(String cusId, String cusPwd, String cusName, String cusPhone, Integer auth) {
        // this.member_no = member_no;
        this.cusId = cusId;
        this.cusPwd = cusPwd;
        this.cusName = cusName;
        this.cusPhone = cusPhone;
        this.auth = auth;
    }
    /*public int getMember_no() {
        return member_no;
    }*/

    public String getCusId() {
        return cusId;
    }

    public String getCusPwd() {
        return cusPwd;
    }

    public String getCusName() {
        return cusName;
    }

    public String getCusPhone() {
        return cusPhone;
    }


    public boolean isShowTrayMessage() {
        return showTrayMessage;
    }

    public void setShowTrayMessage(boolean showTrayMessage) {
        this.showTrayMessage = showTrayMessage;
    }
}