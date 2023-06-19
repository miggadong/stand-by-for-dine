package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import model.entity.Restaurant;
import model.entity.Review;
import model.service.ReviewService;

public class MyReviewListPanel extends JPanel {

    private JPanel panel;
    private JTable table;
    private Review review = new Review();
    private ReviewService reviewService = new ReviewService();
    private ArrayList<Review> list = new ArrayList<>();
    private DefaultTableModel tModel;
    private JPanel entry;

    public MyReviewListPanel(String custId) {
        setLayout(new BorderLayout());
        setSize(1300, 800);

        list = reviewService.readMyReview(custId);

        String[] member = {"식당","아이디", " 리뷰", "평점", "생성일" };

        tModel = new DefaultTableModel(member, 0);

        for (int i = 0; i < list.size(); i++) {
            String resName = list.get(i).getResName();
            String id1 = list.get(i).getCustId();
            String review = list.get(i).getReview();
            Integer score = list.get(i).getScore();
            String createDate = list.get(i).getReviewAt();

            Object[] data = {resName,id1,review,score,createDate };

            tModel.addRow(data);
        }

        table = new JTable(tModel);
        table.setFont(new Font("돋움", Font.PLAIN, 10));
        table.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        setPreferredSize(new Dimension(600,700));
    }
}
