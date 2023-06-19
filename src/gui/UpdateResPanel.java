package gui;

import com.sun.tools.javac.Main;
import model.entity.Customer;
import model.entity.Restaurant;
import model.service.DBManager;
import model.service.MemberDataCheck;
import model.service.RestaurantService;
import model.service.SHA;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class UpdateResPanel extends JFrame {
    Customer customer;
    Main main;
    Connection con;
    JLabel la_id;
    JLabel la_name;
    JLabel la_password;
    JLabel la_password2;
    JLabel la_password3;
    JLabel la_phone;
    JLabel la_open;
    JTextField t_name;
    JTextField t_phone;
    JTextField t_description;
    JLabel la_description;
    JButton open;
    JButton close;
    JButton bt_modified; //       ư
    JButton bt_remove; // Ż
    private RestaurantService restaurantService = new RestaurantService();
    private Container container = getContentPane();

    String shaPw = null; // ȸ       , Ż 𿡼
    String shaPassword = null; // ȸ       , Ż 𿡼
    String Password2 = null;

    String memberId;
    String memberPassword;
    String memberName;
    String memberPhone;


    public UpdateResPanel(Restaurant restaurant) {

        setLayout(new BorderLayout());

        //this.setSize(500,600);
        //this.setLocation(600,200);
        JPanel p_panel = new JPanel(new GridLayout(4,1));
        JPanel p_east = new JPanel(); //
        JPanel p_west = new JPanel(); //
        JPanel p_center = new JPanel(); //
        JPanel p_south = new JPanel(); //
        Dimension labSize = new Dimension(80, 30);
        Dimension TexSize = new Dimension(80, 30);

        la_name = new JLabel("식당 이름");
        la_name.setPreferredSize(labSize);
        la_phone = new JLabel("연락처");
        la_phone.setPreferredSize(labSize);
        la_open = new JLabel("오픈 여부");
        la_open.setPreferredSize(labSize);
        open = new JButton("오픈");
        close = new JButton("휴업");
        la_description = new JLabel("공지사항");
        la_description.setPreferredSize(labSize);
        t_name = new JTextField(15);
        t_name.setPreferredSize(TexSize);
        t_name.setEnabled(false);
        t_phone = new JTextField(15);
        t_phone.setPreferredSize(TexSize);
        t_phone.setEnabled(false);
        t_description = new JTextField(30);
        t_description.setPreferredSize(TexSize);
        bt_modified = new JButton("업데이트");

        Dimension btnSize = new Dimension(100, 25);
        bt_modified.setPreferredSize(btnSize);

        JPanel btnPanel = new JPanel();
        btnPanel.add(open);
        btnPanel.add(close);


        p_west.setPreferredSize(new Dimension(100, 100));
        p_center.setPreferredSize(new Dimension(100, 100));
        p_south.setPreferredSize(new Dimension(100, 100));
        p_east.setPreferredSize(new Dimension(300, 100));

        p_panel.add(la_name);
        p_panel.add(t_name);
        p_panel.add(la_open);
        p_panel.add(btnPanel);
        p_panel.add(la_description);
        p_panel.add(t_description);
        p_panel.add(la_phone);
        p_panel.add(t_phone);
        /*p_west.add(la_name);
        p_east.add(t_name);
        p_west.add(la_open);
        p_east.add(open);
        p_east.add(close);
        p_west.add(la_description);
        p_east.add(t_description);
        p_west.add(la_phone);
        p_east.add(t_phone);*/

        p_south.add(bt_modified);
        /*
        add(p_east, BorderLayout.EAST);
        add(p_west, BorderLayout.WEST);
        add(p_center, BorderLayout.CENTER);
        add(p_south, BorderLayout.SOUTH);
         */

        String resName = restaurant.getResName();
        String resPhone = restaurant.getResPhone();
        String resDes = restaurant.getDescription();

        t_name.setText(resName);
        t_phone.setText(resPhone);
        t_description.setText(resDes);

        //
        open.addActionListener(e -> {
            restaurantService.updateOpen(restaurant.getResId());
            JOptionPane.showMessageDialog(this,"오픈했습니다!");
        });

        close.addActionListener(e -> {
            restaurantService.updateClose(restaurant.getResId());
            JOptionPane.showMessageDialog(this,"마감했습니다!");
        });

        bt_modified.addActionListener((e) -> {
            restaurantService.update(restaurant.getResId(), t_description.getText());
            JOptionPane.showMessageDialog(this,"업데이트 완료!");
        });

        setPreferredSize(new Dimension(600, 700));
        add(p_panel,BorderLayout.CENTER);
        add(p_south, BorderLayout.SOUTH);
        setVisible(true);
        pack();
    }
}
