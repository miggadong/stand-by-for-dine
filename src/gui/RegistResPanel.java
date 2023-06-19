package gui;

import model.entity.Customer;
import model.service.CustomerService;
import model.service.RestaurantService;

import javax.swing.*;
import java.awt.*;

public class RegistResPanel extends JPanel {
    private JLabel labId;
    private JLabel labPwd;
    private JLabel labName;
    private JLabel labPhone;
    private JTextField texId;
    private JTextField texPwd;
    private JTextField texName;
    private JTextField texPhone;
    private JButton btnFind;
    private JButton btnJoin;

    CustomerService customerService;
    Customer customer;
    public RegistResPanel(Customer customer){
        this.customer = customer;
        init();
        addListener();
    }

    public void init(){
        setLayout(new BorderLayout());
        JPanel p_east = new JPanel(); //
        JPanel p_west = new JPanel(); //
        JPanel p_center = new JPanel(); //
        JPanel p_south = new JPanel(); //
        Dimension labSize = new Dimension(80,30);
        Dimension TexSize = new Dimension(80,30);

        //TODO : texId set을 안해서 값으로 인식을 못함.
        labId = new JLabel("가게 이름");
        labId.setPreferredSize(labSize);
        labPwd = new JLabel("전화번호");
        labPwd.setPreferredSize(labSize);
        labName = new JLabel("카테고리");
        labName.setPreferredSize(labSize);
        labPhone = new JLabel("도로명주소");
        labPhone.setPreferredSize(labSize);
        btnJoin = new JButton("식당 등록");
        texId = new JTextField(15);
        texId.setPreferredSize(TexSize);
        texName = new JTextField(15);
        texName.setPreferredSize(TexSize);
        texPwd = new JTextField(15);
        texPwd.setPreferredSize(TexSize);
        texPhone = new JTextField(15);
        texPhone.setPreferredSize(TexSize);
        btnJoin = new JButton("식당 등록");
        Dimension btnSize = new Dimension(100,25);
        btnJoin.setPreferredSize(btnSize);

        p_west.setPreferredSize(new Dimension(100, 100));
        p_center.setPreferredSize(new Dimension(100, 100));
        p_south.setPreferredSize(new Dimension(100, 100));
        p_east.setPreferredSize(new Dimension(300, 100));
        add(p_west);
        add(p_center);
        add(p_south);
        add(p_east);


        p_west.add(labId);
        p_east.add(texId);
        p_west.add(labPwd);
        p_east.add(texPwd);
        p_west.add(labName);
        p_east.add(texName);
        p_west.add(labPhone);
        p_east.add(texPhone);
        p_south.add(btnJoin);

        add(p_west,BorderLayout.WEST);
        add(p_center,BorderLayout.CENTER);
        add(p_south,BorderLayout.SOUTH);
        add(p_east,BorderLayout.EAST);




        //setSize(350,150);
        //this.setLocationRelativeTo(null);
        this.setVisible(true);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(730, 660));
    }

    public void addListener(){
        RestaurantService restaurantService = new RestaurantService();
        btnJoin.addActionListener(e -> { // DB에 정보 삽입하며 가입완료
            restaurantService.create(customer,texId,texPwd,texName,texPhone);
            JOptionPane.showMessageDialog(this, "등록 완료되었습니다!");
            texId.setText("");
            texName.setText("");
            texPhone.setText("");
            texPwd.setText("");
        });
    }
}
