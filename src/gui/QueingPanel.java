package gui;

import model.entity.Customer;
import model.service.CustomerService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class QueingPanel extends JPanel{
    private JLabel label;
    Customer customer;
    CustomerService customerService = new CustomerService();
    JLabel status;
    private JButton f5;
    private JButton enter;
    private JButton quit;

    public QueingPanel(Customer customer){
        int i = 6;
        this.customer = customer;
        JPanel pan = new JPanel();

        f5 = new JButton("새로고침");
        enter = new JButton("입장");
        quit = new JButton("취소");

        if (customer.getQue() == null){
            status = new JLabel("현재 대기중인 식당이 없습니다!");
        } else{
            status = new JLabel("현재 대기 순서는 " + i + "번 입니다!");
        }

        pan.add(f5);
        pan.add(status);
        pan.add(enter);
        pan.add(quit);

        f5.addActionListener(e -> {
            status.setText("현재 대기 순서는 " + i + "번 입니다!");
        });
        enter.addActionListener(e -> {
            customer.setQue("");
        });
        quit.addActionListener(e -> {
            customer.setQue("");
        });
        setSize(600,600);
        setLocation(400,400);
        setVisible(true);
    }
}
