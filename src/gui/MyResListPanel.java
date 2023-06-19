package gui;

import model.entity.Customer;
import model.entity.Restaurant;
import model.service.CustomerService;
import model.service.RestaurantService;
import model.service.ReviewService;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

//가게 주인이 자기 가게를 선택할수있는 페이지
public class MyResListPanel extends JPanel {
    Restaurant restaurant;
    ArrayList<Restaurant> list = new ArrayList<>();
    Customer customer;
    JButton btnPre;
    JButton btnNext;
    JButton btnReview;
    JButton btnMenu;
    JButton btnUpdate;
    JButton btnChat;
    JLabel resLabel;
    String average;
    JLabel resName;
    JLabel resPhone;
    JLabel resCate;
    JLabel resLocation;
    JLabel queLabel;
    JLabel score;
    int i = 0;

    public MyResListPanel(String custId){
        setLayout(new BorderLayout());
        ReviewService reviewService = new ReviewService();
        RestaurantService restaurantService = new RestaurantService();
        list = restaurantService.searchByCust(custId);
        average = reviewService.average(list.get(i).getResId());

        JPanel northPanel = new JPanel(new FlowLayout());
        btnReview = new JButton("리뷰보기");
        btnChat = new JButton("문의받기");
        northPanel.add(btnReview);
        northPanel.add(btnChat);
        add(northPanel,BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        btnPre = new JButton("이전");
        btnPre.setEnabled(false);
        btnNext = new JButton("다음");
        btnNext.setEnabled(false);

        JPanel resPanel = new JPanel(new GridLayout(2,1));
        resLabel = new JLabel("식당 이름 : " + list.get(i).getResName());
        resPhone = new JLabel("식당 전화번호 : " + list.get(i).getResPhone());
        resCate = new JLabel("식당 분류 : " + list.get(i).getCategory());
        resLocation = new JLabel("식당 주소 : " + list.get(i).getLocation());
        queLabel = new JLabel("식당 공지 : " + list.get(i).getDescription());
        score = new JLabel("식당 평점 : " + average);

        centerPanel.add(btnPre,BorderLayout.WEST);
        centerPanel.add(resLabel,BorderLayout.CENTER);
        centerPanel.add(btnNext,BorderLayout.EAST);
        add(centerPanel,BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new FlowLayout());
        btnMenu = new JButton("메뉴 수정");
        btnUpdate = new JButton("가게 정보 수정");
        add(southPanel,BorderLayout.SOUTH);

        setPreferredSize(new Dimension(730,660));
        addListener();
        setVisible(true);
    }

    public void addListener(){
        ReviewService reviewService = new ReviewService();

        btnUpdate.addActionListener(e -> {
            new UpdateResPanel(list.get(i));
        });

        btnMenu.addActionListener(e->{
            if(list.get(i).getResId() >= 5){
                JOptionPane.showMessageDialog(this,"서비스 준비중!");
            }
            else {
                new gui.UpdateMenuPanel(list.get(i).getResId());
            }
        });

        btnReview.addActionListener(e->{
            new gui.MyReviewPanel(list.get(i), customer.getCusId());
        });

        btnChat.addActionListener(e ->{
            try {
                InetAddress ia = InetAddress.getLocalHost();
                String ip_str = ia.toString();
                String ip = ip_str.substring(ip_str.indexOf("/")+1);
                new gui.Part2(ip,5000,list.get(i).getResName());

            } catch (UnknownHostException he){
                he.printStackTrace();
            }
        });

        btnPre.addActionListener(e->{
            i = i-1;
            if(i == 0) {btnPre.setEnabled(false);}
            average = reviewService.average(list.get(i).getResId());
            resName.setText("식당 이름 : " + list.get(i).getResName());
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
            resName.setText("식당 이름 : " + list.get(i).getResName());
            resPhone.setText("식당 전화번호 : " + list.get(i).getResPhone());
            resCate.setText("식당 분류 : " + list.get(i).getCategory());
            resLocation.setText("식당 주소 : " + list.get(i).getLocation());
            queLabel.setText("식당 공지 : " + list.get(i).getDescription());
            score.setText("식당 평점 : " + average);

            btnPre.setEnabled(true);
        });
    }
}
