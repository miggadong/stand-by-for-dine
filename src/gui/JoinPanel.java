package gui;

import model.entity.Customer;
import model.service.CustomerService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class JoinPanel extends JFrame {
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
    public JoinPanel(){
        init();
        addListener();
    }

    public void init(){
        Dimension labSize = new Dimension(80,30);
        int texSize = 10;
        Dimension btnSize = new Dimension(100,25);
        JPanel joinPanel = new JPanel(new GridLayout(5,2,5,0));
        this.setContentPane(joinPanel);
        joinPanel.setBackground(Color.orange);


        //TODO : texId set을 안해서 값으로 인식을 못함.
        labId = new JLabel("ID");
        labId.setPreferredSize(labSize);
        texId = new JTextField(texSize);
        labPwd = new JLabel("Password");
        labPwd.setPreferredSize(labSize);
        texPwd = new JTextField(texSize);
        labName = new JLabel("이름");
        labName.setPreferredSize(labSize);
        texName = new JTextField(texSize);
        labPhone = new JLabel("전화번호");
        labPhone.setPreferredSize(labSize);
        texPhone = new JTextField(texSize);
        btnFind = new JButton("중복 검사");
        btnFind.setPreferredSize(btnSize);
        btnFind.setBackground(Color.WHITE);
        btnJoin = new JButton("가입");
        btnJoin.setPreferredSize(btnSize);
        btnJoin.setBackground(Color.WHITE);

        joinPanel.add(labId);
        joinPanel.add(texId);
        joinPanel.add(labPwd);
        joinPanel.add(texPwd);
        joinPanel.add(labName);
        joinPanel.add(texName);
        joinPanel.add(labPhone);
        joinPanel.add(texPhone);
        joinPanel.add(btnFind);
        joinPanel.add(btnJoin);

        btnJoin.setEnabled(false);

        setSize(350,150);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void addListener(){
        CustomerService customerService = new CustomerService();
        btnFind.addActionListener(e -> { //중복검사
            boolean isExist = customerService.findById(texId.getText());
            if(isExist){
                JOptionPane.showMessageDialog(this,"중복입니다");
            } else {
                JOptionPane.showMessageDialog(this,"가입 가능한 아이디입니다!");
                btnJoin.setEnabled(true);
            }
        });

        btnJoin.addActionListener(e -> { // DB에 정보 삽입하며 가입완료
            customerService.create(texId,texPwd,texName,texPhone);
                new LoginPanel();
                setVisible(false);
        });
    }
}
