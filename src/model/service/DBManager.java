package model.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class DBManager {
    private Connection con = null;

    final private String driver = "com.mysql.cj.jdbc.Driver";
    final private String url = "jdbc:mysql://localhost:3306/standby?userUnicode=true&serverTimezone=Asia/Seoul";
    final private String user = "root";
    final private String password = "root";

    public DBManager() {
        connect();
    }

    public Connection getCon(){
        if(con == null){
            connect();
            if(con == null){
                getCon();
            }
        }
        return con;
    }

    private void connect() {
        if (con == null) {
            try {
                Class.forName(driver);
                this.con = DriverManager.getConnection(url, user, password);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (this.con == null) {
                System.out.println("DB 연결실패");
            } else {
                System.out.println("DB 연결 성공");
            }
        }

    }

    public void closeDB() {
        if (this.con != null) {
            try {
                this.con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeDB(PreparedStatement pstmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeDB(PreparedStatement pstmt) {

        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}