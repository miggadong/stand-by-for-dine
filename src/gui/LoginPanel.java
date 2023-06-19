package gui;

import model.entity.Customer;
import model.service.CustomerService;
import model.service.DBManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



// 1번째 화면 - 로그인
public class LoginPanel extends JFrame{

    private JLabel labId;
    private JLabel labPwd;
    private JTextField texId;
    private JTextField texPwd;
    private JButton btnLogin;
    private JButton btnJoin;
    private LoginPanel loginPanel;

    DBManager dbManager = new DBManager();
    CustomerService customerService = new CustomerService();
    Customer customer = new Customer();


    public LoginPanel(){
        init();
        addListener();
    }

    public void init() {
        Dimension labSize = new Dimension(80,50);
        int texSize = 10;
        Dimension btnSize = new Dimension(100,25);
        JPanel loginPanel = new JPanel(new GridLayout(3,2,5,0));
        //this.setContentPane(loginPanel);

        //TODO : texId set을 안해서 값으로 인식을 못함.
        labId = new JLabel("ID");
        labId.setPreferredSize(labSize);
        labId.setForeground(Color.black);
        texId = new JTextField(texSize);
        texId.setBackground(Color.orange);
        texId.setForeground(Color.black);
        labPwd = new JLabel("Password");
        labPwd.setPreferredSize(labSize);
        labPwd.setForeground(Color.black);
        texPwd = new JTextField(texSize);
        texPwd.setBackground(Color.orange);
        texPwd.setForeground(Color.black);
        btnLogin = new JButton("Login");
        btnLogin.setPreferredSize(btnSize);
        btnLogin.setBackground(Color.white);
        btnJoin = new JButton("Join");
        btnJoin.setPreferredSize(btnSize);
        btnJoin.setBackground(Color.white);

        loginPanel.add(labId);
        loginPanel.add(texId);
        loginPanel.add(labPwd);
        loginPanel.add(texPwd);
        loginPanel.add(btnLogin);
        loginPanel.add(btnJoin);
        loginPanel.setSize(350,320);
        add(loginPanel,BorderLayout.SOUTH);
        loginPanel.setBackground(Color.orange);



        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BorderLayout());
        imagePanel.setSize(350, 150);
        imagePanel.setBackground(Color.orange);

        JLabel imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon("C:\\Users\\pgh99\\Workspace\\School_workspace\\Stand_by_for_dine\\picture\\loginJPEG.jpg");
        imageLabel.setIcon(imageIcon);

        imagePanel.add(imageLabel);
        add(imagePanel, BorderLayout.NORTH);
        imagePanel.setBackground(Color.ORANGE);





        setSize(350,380);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }

    public void addListener(){
        //TODO: 먹통수정, for문 제대로 안돌음. 검사 안거침, for문 들어가서 그냥 빠져나옴
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customer = customerService.login(texId,texPwd);
                System.out.println(customer);
                if(customer .getCusId()== null){
                    JOptionPane.showMessageDialog(loginPanel,"존재하지 않는 계정!");
                }
                else{
                    new jtabbedPane(customer);
                    setVisible(false);
                }
            }
        });

        btnJoin.addActionListener(e -> {
            new JoinPanel();
            setVisible(false);
        });
    }
}
