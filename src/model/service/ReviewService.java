package model.service;

import gui.MyReviewPanel;
import model.entity.Review;

import javax.swing.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ReviewService {
    Review review = new Review();
    ArrayList<Review> list = new ArrayList<>();
    private String query;
    private String query2;
    private Statement stmt = null;
    private ResultSet rs;
    private MyReviewPanel myReviewPanel;
    private DBManager dbManager = new DBManager();
    private Connection conn = dbManager.getCon();

    public void createReview(Integer resId, String custId, String review, Integer score,String resName) {
        PreparedStatement psmt = null;
        try {
            query = "INSERT INTO review(res_id, cust_id, review, score,res_name) VALUES(?,?,?,?,?)";
            psmt = conn.prepareStatement(query);
            psmt.setInt(1, resId);
            psmt.setString(2, custId);
            psmt.setString(3,review);
            psmt.setInt(4,score);
            psmt.setString(5,resName);

            psmt.executeUpdate();

            JOptionPane.showMessageDialog(myReviewPanel,"리뷰가 등록되었습니다!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbManager.closeDB(psmt);
        }
    }

    public ArrayList<Review> readReview(Integer resId){
        PreparedStatement psmt = null;
        try {
            query = "SELECT * FROM review WHERE res_id LIKE '" + resId + "'";
            psmt = conn.prepareStatement(query);
            rs = psmt.executeQuery();
            while (rs.next()){
                review = new Review();
                review.setCustId(rs.getString("cust_id"));
                review.setReview(rs.getString("review"));
                review.setScore(rs.getInt("score"));
                review.setReviewAt(rs.getString("review_at"));
                list.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbManager.closeDB(psmt);
        }
        return list;
    }

    public ArrayList<Review> readMyReview(String custId){
        PreparedStatement psmt = null;
        try {
            query = "SELECT * FROM review WHERE cust_id LIKE '" + custId + "'";
            psmt = conn.prepareStatement(query);
            rs = psmt.executeQuery();
            while (rs.next()){
                review = new Review();
                review.setResName(rs.getString("res_name"));
                review.setResId(rs.getInt("res_id"));
                review.setCustId(rs.getString("cust_id"));
                review.setReview(rs.getString("review"));
                review.setScore(rs.getInt("score"));
                review.setReviewAt(rs.getString("review_at"));
                list.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbManager.closeDB(psmt);
        }
        return list;
    }


    public String average(Integer resId){
        PreparedStatement psmt = null;
        double average = 5.00;
        String result = "5.00";
        DecimalFormat df = new DecimalFormat("0.00");
        try {
            query = "SELECT AVG(score) as avg FROM review WHERE res_id LIKE '" + resId + "'";
            psmt = conn.prepareStatement(query);
            rs = psmt.executeQuery();
            if (rs.next()){
                average = rs.getInt("avg");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbManager.closeDB(psmt);
        }
        if (average == 0.00) {average = 5.00;}
        result = df.format(average);
        return result;
    }
}
