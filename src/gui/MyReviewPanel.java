package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import model.entity.Restaurant;
import model.entity.Review;
import model.service.ReviewService;

public class MyReviewPanel extends JFrame {

    private JPanel panel;
    private JTable table;
    private JTextField reviewLabel;
    private JButton btnScore1;
    private JButton btnScore2;
    private JButton btnScore3;
    private  JButton btnScore4;
    private  JButton btnScore5;
    private  JButton btnReview;
    private JButton logout;
    private Review review = new Review();
    private ReviewService reviewService = new ReviewService();
    private Container container = getContentPane();
    private ArrayList<Review> list = new ArrayList<>();
    private DefaultTableModel tModel;
    private JPanel entry;

    public MyReviewPanel(Restaurant restaurant, String custId) {
        super("리뷰 게시판");
        super.setResizable(true);
        setLayout(new BorderLayout());
        setSize(1300, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        entry = new JPanel(new BorderLayout());
        panel = new JPanel();
        setContentPane(entry);
        reviewLabel = new JTextField(30);
        reviewLabel.setText("리뷰를 남겨주세요!");
        btnScore1 = new JButton("1점");
        btnScore2 = new JButton("2점");
        btnScore3 = new JButton("3점");
        btnScore4 = new JButton("4점");
        btnScore5 = new JButton("5점");
        btnReview = new JButton("리뷰 등록");
        panel.add(reviewLabel);
        panel.add(btnScore1);
        panel.add(btnScore2);
        panel.add(btnScore3);
        panel.add(btnScore4);
        panel.add(btnScore5);
        panel.add(btnReview);
        entry.add(panel,BorderLayout.NORTH);

        review.setScore(5);
        btnScore1.addActionListener(e -> {
            review.setScore(1);
            JOptionPane.showMessageDialog(this,"평점 1점 주셨습니다!");
        });

        btnScore2.addActionListener(e -> {
            review.setScore(2);
            JOptionPane.showMessageDialog(this,"평점 2점 주셨습니다!");
        });

        btnScore3.addActionListener(e ->{
            review.setScore(3);
            JOptionPane.showMessageDialog(this,"평점 3점 주셨습니다!");
        });

        btnScore4.addActionListener(e -> {
            review.setScore(4);
            JOptionPane.showMessageDialog(this,"평점 4점 주셨습니다!");
        });

        btnScore5.addActionListener(e->{
            review.setScore(5);
            JOptionPane.showMessageDialog(this,"평점 5점 주셨습니다!");
        });

        list = reviewService.readReview(restaurant.getResId());

        String[] member = {"아이디", " 리뷰", "평점", "생성일" };

        tModel = new DefaultTableModel(member, 0);

        for (int i = 0; i < list.size(); i++) {
            String id1 = list.get(i).getCustId();
            String review = list.get(i).getReview();
            Integer score = list.get(i).getScore();
            String createDate = list.get(i).getReviewAt();

            Object[] data = {id1,review,score,createDate };

            tModel.addRow(data);
        }

        btnReview.addActionListener(e -> {
            LocalDateTime now = LocalDateTime.now();
            String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm--ss"));
            reviewService.createReview(restaurant.getResId(),custId,reviewLabel.getText(),review.getScore(),restaurant.getResName());
            Object[] data = {custId,reviewLabel.getText(),review.getScore(),formatedNow};
            tModel.addRow(data);
            this.repaint();
        });

        table = new JTable(tModel);
        table.setFont(new Font("돋움", Font.PLAIN, 20));
        table.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(table);
        entry.add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }
}
