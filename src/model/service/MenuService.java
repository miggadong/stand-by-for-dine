package model.service;

import model.entity.MenuInfo;

import java.sql.*;
import java.util.ArrayList;

public class MenuService {
    private String query;
    private Statement stmt = null;
    private ResultSet rs;
    MenuInfo menu = new MenuInfo();
    private DBManager dbManager = new DBManager();
    private Connection conn = dbManager.getCon();

    public void createMenu(Integer resId, Integer price, String menuName) {
        PreparedStatement psmt = null;
        try {
            query = "INSERT INTO menu(resId,price,menuName) VALUES(?,?,?)";
            psmt = conn.prepareStatement(query);
            psmt.setInt(1, resId);
            psmt.setInt(2, price);
            psmt.setString(3, menuName);

            psmt.executeUpdate();

            //JOptionPane.showMessageDialog(resMenuPanel,"메뉴가 등록되었습니다!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbManager.closeDB(psmt);
        }
    }

    public MenuInfo readMenu(Integer resId, Integer menuNo){
        String sql = "select * from menu where res_id = ? AND menu_no = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try { // id와pwd가 일치하는지 검사
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, resId);
            pstmt.setInt(2,menuNo);
            rs = pstmt.executeQuery();
            if (rs.next()) { // 일치하면 menu의 정보값 세팅
                menu.setData(rs.getInt("res_id"),rs.getInt("price"),
                        rs.getString("menu_name"),rs.getString("status"),
                        rs.getInt("menu_no"));
        } }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbManager.closeDB(pstmt, rs);
        }
        return menu;
    }

    public void updateMenu(Integer resId, String price, String status, Integer menuNo){
        PreparedStatement psmt = null;
        try {
            query = "UPDATE menu SET price = ?, " +
                    "status = ? WHERE res_id LIKE '" + resId +"'"+"AND menu_no LIKE '" + menuNo +"'";
            psmt = conn.prepareStatement(query);
            psmt.setString(1, price);
            psmt.setString(2, status);

            psmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbManager.closeDB(psmt);
        }
    }
}
