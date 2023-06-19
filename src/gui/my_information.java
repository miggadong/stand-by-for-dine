package gui;

import com.sun.tools.javac.Main;
import model.entity.Customer;
import model.service.DBManager;
import model.service.MemberDataCheck;
import model.service.SHA;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class my_information extends JPanel {

    DBManager dbManager = new DBManager();

    Customer customer;

    Main main;
    Connection con;
    JLabel la_id;
    JLabel la_name;
    JLabel la_password;
    JLabel la_password2;
    JLabel la_password3;
    JLabel la_phone;
    JTextField t_id;
    JTextField t_name;
    JPasswordField t_password;
    JPasswordField t_password2;
    JPasswordField t_password3;

    JTextField t_phone;
    JButton bt_modified; //       ư
    JButton bt_remove; // Ż


    String shaPw = null; // ȸ       , Ż 𿡼
    String shaPassword = null; // ȸ       , Ż 𿡼
    String Password2 = null;

    String memberId;
    String memberPassword;
    String memberName;
    String memberPhone;


    public my_information(Customer customer) {
        this.customer = customer;
        con = dbManager.getCon();
        setLayout(new BorderLayout());

        //this.setSize(500,600);
        //this.setLocation(600,200);

        JPanel p_east = new JPanel(); //
        JPanel p_west = new JPanel(); //
        JPanel p_center = new JPanel(); //
        JPanel p_south = new JPanel(); //
        Dimension labSize = new Dimension(80,30);
        Dimension TexSize = new Dimension(80,30);

        la_id = new JLabel("아이디");
        la_id.setPreferredSize(labSize);
        la_name = new JLabel("이름");
        la_name.setPreferredSize(labSize);
        la_password = new JLabel("비밀번호");
        la_password.setPreferredSize(labSize);
        la_password2 = new JLabel("변경할 비밀번호");
        la_password2.setPreferredSize(labSize);
        la_password3 = new JLabel("변경할 비밀번호 확인");
        la_password3.setPreferredSize(labSize);
        la_phone = new JLabel("연락처");
        la_phone.setPreferredSize(labSize);
        t_id = new JTextField(15);
        t_id.setPreferredSize(TexSize);
        t_name = new JTextField(15);
        t_name.setPreferredSize(TexSize);
        t_password = new JPasswordField(15);
        t_password.setPreferredSize(TexSize);
        t_password2 = new JPasswordField(15);
        t_password2.setPreferredSize(TexSize);
        t_password3 = new JPasswordField(15);
        t_password3.setPreferredSize(TexSize);
        t_phone = new JTextField(15);
        t_phone.setPreferredSize(TexSize);
        bt_modified = new JButton("수정");

        Dimension btnSize = new Dimension(100,25);
        bt_modified.setPreferredSize(btnSize);


        p_west.setPreferredSize(new Dimension(100, 100));
        p_center.setPreferredSize(new Dimension(100, 100));
        p_south.setPreferredSize(new Dimension(100, 100));
        p_east.setPreferredSize(new Dimension(300, 100));

        add(p_west);
        add(p_center);
        add(p_south);
        add(p_east);

        p_west.add(la_id);
        p_east.add(t_id).setEnabled(false);
        p_west.add(la_name);
        p_east.add(t_name).setEnabled(false);
        p_west.add(la_password);
        p_east.add(t_password);
        p_west.add(la_password2);
        p_east.add(t_password2);
        p_west.add(la_password3);
        p_east.add(t_password3);
        p_west.add(la_phone);
        p_east.add(t_phone);

        p_south.add(bt_modified);
        add(p_east, BorderLayout.EAST);
        add(p_west, BorderLayout.WEST);
        add(p_center, BorderLayout.CENTER);
        add(p_south, BorderLayout.SOUTH);


        memberId = customer.getCusId();
        memberName = customer.getCusName();
        memberPassword = customer.getCusPwd();
        memberPhone = customer.getCusPhone();

        t_id.setText(memberId);
        t_name.setText(memberName);
        //  н
        t_password.setText(memberPassword);
        t_phone.setText(memberPhone);

        //
        bt_modified.addActionListener((e) -> {
            modified();
        });
        setPreferredSize(new Dimension(600, 700));
    }


    // ȸ
    public void modified() {// update
        DBManager dbManager = new DBManager();
        shaPassword = new String(t_password.getPassword());//
        String shaPassword2 = new String(t_password2.getPassword());//

        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        ResultSet rs = null;
        String sql = "select cust_pwd from customer where cust_pwd='" + shaPw + "'";
        try {
            pstmt = dbManager.getCon().prepareStatement(sql); //      (       con.prepareStatement(sql);
            rs = pstmt.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (rs.next()) {//   й ȣ         Ʒ           {
                if (shaPassword2.equals("")) {
                    if (MemberDataCheck.checkName(t_name.getText())
                            && MemberDataCheck.checkPNum(t_phone.getText())) {

                        String sql2 = "update customer set cust_name=?, cust_phone=?";
                        pstmt2 = dbManager.getCon().prepareStatement(sql2);
                        pstmt2.setString(1, t_name.getText());
                        pstmt2.setString(2, t_phone.getText());
                        pstmt2.setInt(3, customer.getMember_no());
                        pstmt2.executeUpdate();
                        t_password.setText("");
                        JOptionPane.showMessageDialog(this, "수정성공");
                    } else {
                        JOptionPane.showMessageDialog(this, "이름 또는 전화번호를 올바르게 작성하세요");
                    }
                } /*else if (shaPw2.equals(shaPw3)) { //          й ȣ    ԷµǾ  ְ ,      й ȣ     ġ Ǿ           й ȣ
                if (MemberDataCheck.checkEmail(t_email.getText())
                        && MemberDataCheck.checkPhone(t_phone.getText())) {
                    String sql2 = "update member  set password=?, email=?, phone=? where member_no=?";
                    pstmt2 = con.prepareStatement(sql2);
                    pstmt2.setString(1, shaPw3);
                    pstmt2.setString(2, t_email.getText());
                    pstmt2.setString(3, t_phone.getText());
                    pstmt2.setInt(4, cs.userInfo.getMember_no());
                    pstmt2.executeUpdate();
                    t_password.setText("");
                    t_password2.setText("");
                    t_password3.setText("");

                    JOptionPane.showMessageDialog(this, "        ");
                } else {
                    JOptionPane.showMessageDialog(this, "  ȭ  ȣ  Ǵ   ̸            ùٸ     ۼ  ϼ   ");
                }
            } else { //          й ȣ    ԷµǾ     ,   ġ              й ȣ
                JOptionPane.showMessageDialog(this, "         й ȣ     ġ      ʽ  ϴ .");
            }
        } else{
            JOptionPane.showMessageDialog(this, "  й ȣ     ġ      ʽ  ϴ ");
        } */
            }
        } catch (
                HeadlessException e) {
            e.printStackTrace();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }/*finally {
            DBManager.closeDB(pstmt, rs);
            DBManager.closeDB(pstmt2);
        }*/
    }
}
