package gui;

import model.entity.Customer;
import model.entity.Restaurant;
import model.service.CustomerService;
import model.service.RestaurantService;
import model.service.ReviewService;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class SearchPanel extends JPanel{

    JTextField address;
    JButton btn;
    String items[] = {"이름","카테고리"};
    JLabel resName;
    JLabel resPhone;
    JLabel resCate;
    JLabel resLocation;
    JComboBox<String> combo;
    Restaurant restaurant = new Restaurant();
    String name = "";
    JLabel score;
    JButton btnChat;
    JButton btnMenu;
    public JLabel mapLabel;
    ArrayList<Restaurant> list = new ArrayList<>();
    JButton btnQue;
    JLabel queLabel;
    Socket socket;
    BufferedReader in;
    StringBuffer text;
    String str;
    Customer customer;
    JButton btnReview;
    JButton btnPre;
    JButton btnNext;
    int i = 0;
    int j = 6;
    String average = "5.00";
    public SearchPanel(Customer customer){
        this.customer = customer;
        //JFrame frm = new JFrame("식당 검색");                    // 프레임 생성
        //frm.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);     // 프레임의 X 클릭 시 지도창만 종료.
        setLayout(new BorderLayout());                  // JFrame 안쪽 영역.

        JPanel pan = new JPanel();
        add(pan);
        address = new JTextField(50);
        btn = new JButton("클릭");                // JFrame 안쪽 영역에 들어갈 클릭 버튼
        combo = new JComboBox<>(items);
        pan.add(combo);
        pan.add(address);
        pan.add(btn);
        pan.setPreferredSize(new Dimension(0, 100));

        JPanel pan1 = new JPanel();
        add(pan1);
        resName = new JLabel("식당 이름 : ");
        resPhone = new JLabel("식당 전화번호 : ");
        resCate = new JLabel("식당 분류 : ");
        resLocation = new JLabel("식당 주소 : ");
        queLabel = new JLabel("식당 공지 : ");
        score = new JLabel("식당 평점 : ");
        btnChat = new JButton("문의하기");
        btnQue = new JButton("줄서기!");
        btnMenu = new JButton("메뉴 보기");
        btnReview = new JButton("리뷰 하기");


        pan1.setLayout(new GridLayout(5, 1));                  // 지도 하단 그리드 4행 1열로 생성.
        pan1.add(resName);
        pan1.add(resPhone);
        pan1.add(resCate);
        pan1.add(resLocation);
        pan1.add(queLabel);
        pan1.add(score);
        pan1.add(btnChat);
        btnChat.setEnabled(false);
        pan1.add(btnQue);
        btnQue.setEnabled(false);
        pan1.add(btnMenu);
        btnMenu.setEnabled(false);
        pan1.add(btnReview);
        btnReview.setEnabled(false);
        pan1.setPreferredSize(new Dimension(0, 100));

        JPanel centerPanel = new JPanel(new BorderLayout());
        mapLabel = new JLabel("");
        btnPre = new JButton("이전");
        btnPre.setEnabled(false);
        btnNext = new JButton("다음");
        btnNext.setEnabled(false);

        centerPanel.add(btnPre, BorderLayout.WEST);
        centerPanel.add(mapLabel, BorderLayout.CENTER);//중간패널
        centerPanel.add(btnNext,BorderLayout.EAST);

        add(pan, BorderLayout.NORTH);
        add(centerPanel,BorderLayout.CENTER);
        add(pan1, BorderLayout.SOUTH);                        // 하단 pan1 세팅

        setPreferredSize(new Dimension(730, 660));
        addListener();
    }

    public void addListener(){
        RestaurantService restaurantService = new RestaurantService();
        CustomerService customerService = new CustomerService();
        ReviewService reviewService = new ReviewService();
        btn.addActionListener(e -> {
            list.clear();
            average = "5.00";
            String field = (String)combo.getSelectedItem();
            if(field.equals("이름")) {
                String addr = address.getText();
                list = restaurantService.searchByName(addr);
                average = reviewService.average(list.get(i).getResId());
                if(list.isEmpty()){
                    JOptionPane.showMessageDialog(this,"검색결과가 없습니다!");
                } else {
                    restaurantService.naverMaps(list.get(i).getLocation(), this);
                    resName.setText("식당 이름 : " + list.get(i).getResName());

                    name = list.get(i).getResName();
                    resPhone.setText("식당 전화번호 : " + list.get(i).getResPhone());
                    resCate.setText("식당 분류 : " + list.get(i).getCategory());
                    resLocation.setText("식당 주소 : " + list.get(i).getLocation());
                    queLabel.setText("식당 공지 : " + list.get(i).getDescription());
                    score.setText("식당 평점 : " + average);

                    btnChat.setEnabled(true);
                    btnMenu.setEnabled(true);
                    btnReview.setEnabled(true);
                    btnQue.setEnabled(false);
                    if(list.get(i).isOpen()){
                        btnQue.setEnabled(true);
                    }
                    btnNext.setEnabled(true);
                    if(list.size() <= i+1){
                        btnNext.setEnabled(false);
                    }
                }
            }
            else{
                String addr = address.getText();
                list = restaurantService.searchByCategory(addr);
                if(list.isEmpty()){
                    JOptionPane.showMessageDialog(this, "검색결과가 존재하지 않습니다!");
                }
                average = reviewService.average(list.get(i).getResId());

                restaurantService.naverMaps(list.get(i).getLocation(),this);
                resName.setText("식당 이름 : " + list.get(i).getResName());
                name = list.get(i).getResName();
                resPhone.setText("식당 전화번호 : " + list.get(i).getResPhone());
                resCate.setText("식당 분류 : " + list.get(i).getCategory());
                resLocation.setText("식당 주소 : " + list.get(i).getLocation());
                queLabel.setText("식당 공지 : " + list.get(i).getDescription());
                score.setText("식당 평점 : " + average);
                btnChat.setEnabled(true);
                btnMenu.setEnabled(true);
                btnQue.setEnabled(false);
                if(list.get(i).isOpen()){
                    btnQue.setEnabled(true);
                }
                btnNext.setEnabled(true);
                if(list.size() < i+2){
                    btnNext.setEnabled(false);
                }
            }
        });

        btnChat.addActionListener(e ->{
            try {
                InetAddress ia = InetAddress.getLocalHost();
                String ip_str = ia.toString();
                String ip = ip_str.substring(ip_str.indexOf("/")+1);
                if(customer.getCusId() != list.get(i).getCustId()) {
                    new gui.Part2(ip, 5000, customer);
                }
                else {
                    new gui.Part2(ip,5000,list.get(i).getResName());
                }
            } catch (UnknownHostException he){
                he.printStackTrace();
            }
        });

        btnQue.addActionListener(e -> {

            int i = 6;
            String message = "식당에서" + 6 + "번째 대기 중입니다!";
            Object[] options = { "취소","새로고침"};

            int ch = JOptionPane.showOptionDialog(
                    SearchPanel.this,
                    message,
                    "줄서기",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]
            );



            if (ch == 1) {
                while(i > 1) {
                    i = i -2;
                    String message2 =  "식당에서" + i + "번째 대기 중입니다!";
                    Object[] options2;
                    if (i == 0) {
                        options2 = new Object[]{"취소","새로고침","입장"};
                    } else {
                        options2 = new Object[]{"취소","새로고침"};
                    }


                    int ch2 = JOptionPane.showOptionDialog(
                            SearchPanel.this,
                            message2,
                            "알림",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.INFORMATION_MESSAGE,
                            null,
                            options2,
                            options2[0]);

                    if (ch2 == 0) {
                        break;
                    } else if (ch2 == 2 && i == 0) {
                        JOptionPane.showMessageDialog(
                                SearchPanel.this,
                                "지금 입장하시겠습니까?",
                                "알림",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                        break;
                    }
                }
            }
        });

        btnMenu.addActionListener(e->{
            if(list.get(i).getResId() >= 5){
                JOptionPane.showMessageDialog(this,"준비중입니다!");
            }
            else {
                new gui.menuPanel(list.get(i).getResId());
            }
        });

        btnReview.addActionListener(e->{
            new gui.MyReviewPanel(list.get(i), customer.getCusId());
        });

        btnPre.addActionListener(e->{
            i = i-1;
            if(i == 0) {btnPre.setEnabled(false);}
            average = reviewService.average(list.get(i).getResId());
            restaurantService.naverMaps(list.get(i).getLocation(),this);
            resName.setText("식당 이름 : " + list.get(i).getResName());
            name = list.get(i).getResName();
            resPhone.setText("식당 전화번호 : " + list.get(i).getResPhone());
            resCate.setText("식당 분류 : " + list.get(i).getCategory());
            resLocation.setText("식당 주소 : " + list.get(i).getLocation());
            queLabel.setText("식당 공지 : " + list.get(i).getDescription());
            score.setText("식당 평점 : " + average);
            btnNext.setEnabled(true);
        });

        btnNext.addActionListener(e->{
            i = i+1;
            if(list.size() <= i+1){
                btnNext.setEnabled(false);
            }
            average = reviewService.average(list.get(i).getResId());
            restaurantService.naverMaps(list.get(i).getLocation(),this);
            resName.setText("식당 이름 : " + list.get(i).getResName());
            name = list.get(i).getResName();
            resPhone.setText("식당 전화번호 : " + list.get(i).getResPhone());
            resCate.setText("식당 분류 : " + list.get(i).getCategory());
            resLocation.setText("식당 주소 : " + list.get(i).getLocation());
            queLabel.setText("식당 공지 : " + list.get(i).getDescription());
            score.setText("식당 평점 : " + average);

            btnPre.setEnabled(true);
        });
    }
}
