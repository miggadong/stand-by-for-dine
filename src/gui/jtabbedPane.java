package gui;

import model.entity.Customer;
import model.entity.Restaurant;
import model.entity.Review;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;


public class jtabbedPane extends JFrame {
    private JTabbedPane jTabbedPane;

    Restaurant restaurant;
    JTabbedPane tab;

    SearchPanel j1;
    JPanel j2;


    JPanel j3;

    ImageIcon icon;

    int i = 0;


    private ArrayList<Review> list = new ArrayList<>();

    private Color color = null;



    public jtabbedPane(Customer customer) {

        if (jTabbedPane == null) {
            tab = new JTabbedPane(JTabbedPane.LEFT);



            icon = new ImageIcon("C:\\Users\\pgh99\\Workspace\\School_workspace\\Stand_by_for_dine\\picture\\체크.jpg");


            JPanel one = new JPanel() {
                public void paintComponent(Graphics g){
                    g.drawImage(icon.getImage(), 0, 0, null);
                    setOpaque(false); //그림을 표시하게 설정,투명하게 조절
                    super.paintComponent(g);
                }
            };

            j1 = new SearchPanel(customer);
            JPanel two = new JPanel() {
                public void paintComponent(Graphics g){
                    g.drawImage(icon.getImage(), 0, 0, null);
                    setOpaque(false); //그림을 표시하게 설정,투명하게 조절
                    super.paintComponent(g);
                }
            };
            j2 = new JPanel();
            j2.setSize(1000,800);



            JButton button1 = new JButton("내 정보 수정");
            JButton button2 = new JButton("리뷰");

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(button1);
            buttonPanel.add(button2);
            buttonPanel.setBackground(Color.YELLOW);
            button1.setBackground(Color.white);
            button2.setBackground(Color.white);

            JPanel centerPanel = new JPanel();

            two.add(buttonPanel, BorderLayout.NORTH);
            two.add(centerPanel,BorderLayout.CENTER);



            button1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    button1.setBackground(Color.ORANGE);
                    button2.setBackground(Color.WHITE);
                    centerPanel.removeAll();
                    centerPanel.add(new my_information(customer));
                    centerPanel.revalidate();
                    centerPanel.repaint();


                }
            }); button1.setBackground(Color.WHITE);


            button2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    button2.setBackground(Color.ORANGE);
                    button1.setBackground(Color.WHITE);
                    centerPanel.removeAll();
                    centerPanel.add(new MyReviewListPanel(customer.getCusId()));
                    centerPanel.revalidate();
                    centerPanel.repaint();
                }
            });
            button2.setBackground(Color.WHITE);



            JPanel three = new JPanel() {
                public void paintComponent(Graphics g){
                    g.drawImage(icon.getImage(), 0, 0, null);
                    setOpaque(false); //그림을 표시하게 설정,투명하게 조절
                    super.paintComponent(g);
                }
            };

            j3 = new JPanel();
            j3.setSize(1000,800);



            JButton button3 = new JButton("가게 추가");
            JButton button4 = new JButton("가게 수정");

            JPanel buttonPanel2 = new JPanel();

            buttonPanel2.add(button3);
            buttonPanel2.add(button4);
            buttonPanel2.setBackground(Color.YELLOW);
            button3.setBackground(Color.white);
            button4.setBackground(Color.white);

            JPanel centerPanel2 = new JPanel();

            three.add(buttonPanel2, BorderLayout.NORTH);
            three.add(centerPanel2,BorderLayout.CENTER);



            button3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    button3.setBackground(Color.ORANGE);
                    button4.setBackground(Color.WHITE);
                    centerPanel2.removeAll();
                    centerPanel2.add(new RegistResPanel(customer));
                    centerPanel2.revalidate();
                    centerPanel2.repaint();


                }
            });
            button3.setBackground(Color.WHITE);

            button4.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    button4.setBackground(Color.ORANGE);
                    button3.setBackground(Color.WHITE);
                    centerPanel2.removeAll();
                    centerPanel2.add(new MyResListPanel(customer.getCusId()));
                    centerPanel2.revalidate();
                    centerPanel2.repaint();

                }
            });
            button4.setBackground(Color.WHITE);

            one.add(j1);
            two.add(j2);
            three.add(j3);

            tab.addTab("가게 찾기" , one);
            tab.addTab("내 정보", two);
            //tab.add("채팅 하기", three);
            tab.add("내 가게 관리", three);

            tab.setBackgroundAt(0, Color.YELLOW);
            tab.setBackgroundAt(1, Color.YELLOW);
            tab.setBackgroundAt(2, Color.YELLOW);



            Font tabFont = tab.getFont().deriveFont(Font.ITALIC, 30);
            tab.setFont(tabFont);

            setTitle("식당 대기 프로그램");
            getContentPane().add(tab, BorderLayout.CENTER);
            setSize(1000, 800);
            setVisible(true);

        }

    }

    public class chat extends JPanel {
        JButton chatbtn;
        Customer customer;

        public chat(Customer customer) {
            chatbtn = new JButton("채팅 시작하기");
            this.customer = customer;
            add(chatbtn);

            chatbtn.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed (ActionEvent e){
                    try {
                        InetAddress ia = InetAddress.getLocalHost();
                        String ip_str = ia.toString();
                        String ip = ip_str.substring(ip_str.indexOf("/") + 1);
                        new Part2(ip, 5000, customer);
                        setVisible(true);
                    } catch (UnknownHostException he) {
                        he.printStackTrace();
                    }
                }
            });
        }
    }
}