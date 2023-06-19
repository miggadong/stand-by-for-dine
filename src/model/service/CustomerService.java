package model.service;

import com.sun.security.auth.module.LdapLoginModule;
import gui.JoinPanel;
import gui.LoginPanel;
import gui.Part2;
import model.entity.Customer;
import model.entity.Restaurant;
import network.Server;

import javax.swing.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;

/* TODO : 리뷰, 찜, 이용 내역, 대기현황, 가게찾기, 사장님과 대화 기능,
    카테고리 검색기능, 메뉴조회
 */
public class CustomerService {
    private JoinPanel joinPanel;
    private LoginPanel loginPanel;
    private String query;
    private Statement stmt = null;
    private ResultSet rs;
    private Boolean idChecked = false;
    private Customer customer = new Customer();
    private DBManager dbManager = new DBManager();
    private Server server = new Server();
    private Connection conn = dbManager.getCon();

    public Customer login(JTextField id,JTextField pwd) { // 로그인
        String custId = id.getText();
        String password = pwd.getText();
        String sql = "select * from customer where cust_id=? and cust_pwd=?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try { // id와pwd가 일치하는지 검사
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, custId);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();
            if (rs.next()) { // 일치하면 로그인하는 customer의 정보값 세팅
                customer.setData(rs.getString("cust_id"), rs.getString("cust_pwd"),
                        rs.getString("cust_name"), rs.getString("cust_phone"), rs.getInt("auth"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(loginPanel,"존재하지 않는 계정!");
        } finally {
            dbManager.closeDB(pstmt, rs);
        }
        return customer;
    }
    //회원가입
    public void create(JTextField id, JTextField pwd, JTextField name, JTextField phone) {
        String custId = id.getText();
        String custPwd = pwd.getText();
        String custName = name.getText();
        String custPhone = phone.getText();
        PreparedStatement psmt = null;
        try {
            System.out.println(custId+custPwd+custName+custPhone);
            query = "INSERT INTO customer(cust_id,cust_pwd,cust_name,cust_phone,auth) VALUES(?,?,?,?,1)";
            psmt = conn.prepareStatement(query);
            psmt.setString(1, custId);
            psmt.setString(2, custPwd);
            psmt.setString(3, custName);
            psmt.setString(4, custPhone);

            psmt.executeUpdate();

            JOptionPane.showMessageDialog(joinPanel,"가입을 환영합니다!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbManager.closeDB(psmt);
        }
    }
    public void update(String id,String pwd,String phone) {
        PreparedStatement psmt = null;
        try {
            System.out.println(id+pwd+phone);
            query = "UPDATE customer SET cust_pwd = ?, " +
                    "cust_phone = ? WHERE cust_id LIKE '" + id +"'";
            psmt = conn.prepareStatement(query);
            psmt.setString(1, pwd);
            psmt.setString(2, phone);

            psmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbManager.closeDB(psmt);
        }
    }

    public void queue(String id,String resName){
        PreparedStatement psmt = null;
        try {
            query = "UPDATE customer SET que = '" + resName + "' WHERE custid = '" + id + "'";
            psmt = conn.prepareStatement(query);
            psmt.executeUpdate();
        } catch (SQLException e){
        e.printStackTrace();
        } finally {
            dbManager.closeDB(psmt);
        }
    }

    //중복검사
    public Boolean findById(String id) {
        String cust_id = id;
        String sql = "select * from customer where cust_id=?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Boolean isExist = false;

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, cust_id);
            rs = pstmt.executeQuery();
            if (rs.next()) {// true면 가입 불가
                isExist = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbManager.closeDB(pstmt,rs);
        }
        return isExist;
    }
}
