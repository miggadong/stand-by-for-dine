package model.service;


import java.util.regex.Pattern;

public class MemberDataCheck {

    public static boolean checkName(String Name) {

        boolean check1 = Name.length() >= 1;
        boolean check2 = Pattern.matches("^[ㄱ-ㅎ 가-힣]*$", Name);
        return check1 && (check2);
    }

    public static boolean checkPNum(String PNum) {

        boolean check1 = PNum.length() >= 5;
        boolean check2 = Pattern.matches("^[0-9]{2,4}-[0-9]{2,4}-[0-9]{2,4}$", PNum);
        boolean check3 = Pattern.matches("^[0-9]{2,4}[0-9]{2,4}[0-9]{2,4}$", PNum);
        return check1 && (check2 || check3);
    }

}
