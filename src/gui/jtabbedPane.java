package gui;

import model.entity.Customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class jtabbedPane extends JFrame {
    private JTabbedPane jTabbedPane;
    JTabbedPane tab;
    SearchPanel j1;
    JPanel j2;

    //chat j3;

    RegistResPanel j4;
    JPanel j5;
    ImageIcon imageIcon = new ImageIcon();
    public jtabbedPane(Customer customer) {
        imageIcon = new ImageIcon("C:\\Users\\pgh99\\Workspace\\School_workspace\\Stand_by_for_dine\\picture\\체크.jpg");
        if (jTabbedPane == null) {
            tab = new JTabbedPane(JTabbedPane.LEFT);

            JPanel one = new JPanel()
                {
                    public void paintComponent(Graphics g){
                    g.drawImage(imageIcon.getImage(), 0, 0, null);
                    setOpaque(false); //그림을 표시하게 설정,투명하게 조절
                    super.paintComponent(g);
                }
                };

            j1 = new SearchPanel(customer);

            JPanel two = new JPanel(){
                public void paintComponent(Graphics g){
                    g.drawImage(imageIcon.getImage(), 0, 0, null);
                    setOpaque(false); //그림을 표시하게 설정,투명하게 조절
                    super.paintComponent(g);
                }
            };//추가
            j2 = new JPanel();
            j2.setSize(1000,800);

            JPanel five = new JPanel(){
                public void paintComponent(Graphics g){
                    g.drawImage(imageIcon.getImage(), 0, 0, null);
                    setOpaque(false); //그림을 표시하게 설정,투명하게 조절
                    super.paintComponent(g);
                }
            };
            j5 = new JPanel();
            j5.setSize(1000,800);


            JButton button1 = new JButton("내 정보 수정");
            JButton button2 = new JButton("리뷰");
            JButton button3 = new JButton("줄서기 현황");

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(button1);
            buttonPanel.add(button2);
            buttonPanel.add(button3);
            buttonPanel.setBackground(Color.YELLOW);
            button1.setBackground(Color.white);
            button2.setBackground(Color.white);
            button3.setBackground(Color.white);




            JPanel centerPanel = new JPanel();

            two.add(buttonPanel, BorderLayout.NORTH);
            two.add(centerPanel,BorderLayout.CENTER);

            button1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    button1.setBackground(Color.ORANGE);
                    button2.setBackground(Color.WHITE);
                    button3.setBackground(Color.WHITE);
                    centerPanel.removeAll();
                    centerPanel.add(new my_information(customer));
                    centerPanel.revalidate();
                    centerPanel.repaint();


                }
            });

            button2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    button2.setBackground(Color.ORANGE);
                    button1.setBackground(Color.WHITE);
                    button3.setBackground(Color.WHITE);
                    centerPanel.removeAll();
                    centerPanel.add(new my_information(customer));
                    centerPanel.revalidate();
                    centerPanel.repaint();


                }
            });
            button1.setBackground(Color.WHITE);

            button2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    button2.setBackground(Color.ORANGE);
                    button1.setBackground(Color.WHITE);
                    button3.setBackground(Color.WHITE);
                    centerPanel.removeAll();
                    centerPanel.add(new MyReviewListPanel(customer.getCusId()));
                    centerPanel.revalidate();
                    centerPanel.repaint();
                }
            });
            button2.setBackground(Color.WHITE);

            button3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    button3.setBackground(Color.ORANGE);
                    button1.setBackground(Color.WHITE);
                    button2.setBackground(Color.WHITE);
                    centerPanel.removeAll();
                    centerPanel.add(new JPanel());
                    centerPanel.revalidate();
                    centerPanel.repaint();
                }
            });
            button3.setBackground(Color.WHITE);

            JButton btn1 = new JButton("가게 추가");
            JButton btn2 = new JButton("가게 관리");
            JPanel btnPanel = new JPanel();
            btnPanel.add(btn1);
            btnPanel.add(btn2);
            btnPanel.setBackground(Color.orange);
            btn1.setBackground(Color.white);
            btn2.setBackground(Color.white);

            JPanel centerPanel2 = new JPanel();
            five.add(btnPanel,BorderLayout.NORTH);
            five.add(centerPanel2,BorderLayout.CENTER);


            btn1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    centerPanel.removeAll();
                    centerPanel.add(new RegistResPanel(customer));
                    centerPanel.revalidate();
                    centerPanel.repaint();
                }
            });

            btn2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(customer.getAuth() == 0){
                        JOptionPane.showMessageDialog(five,"보유하신 매장이 없습니다!");
                    } else {
                        centerPanel.removeAll();
                        centerPanel.add(new MyResListPanel(customer.getCusId()));
                        centerPanel.revalidate();
                        centerPanel.repaint();
                    }
                }
            });

           // JPanel three = new JPanel();
            //j3 = new chat(customer);

           // JPanel four = new JPanel();
            //j4 = new RegistResPanel(customer);


            one.add(j1);
            two.add(j2);
            //three.add(j3);
            five.add(j5);

            tab.addTab("가게 찾기", one);
            tab.addTab("내 정보", two);
           // tab.add("채팅 하기", three);
            tab.add("내 가게 관리", five);

            one.setBackground(Color.orange);
            two.setBackground(Color.orange);
            five.setBackground(Color.orange);
           // three.setBackground(Color.orange);
            //four.setBackground(Color.orange);

            setTitle("식당 대기 프로그램");
            getContentPane().add(tab, BorderLayout.CENTER);
            setSize(1000, 800);
            tab.setBackgroundAt(0, Color.YELLOW);
            tab.setBackgroundAt(1, Color.YELLOW);
            tab.setBackgroundAt(2, Color.YELLOW);



            Font tabFont = tab.getFont().deriveFont(Font.ITALIC, 30);
            tab.setFont(tabFont);
            //setBackground(Color.BLACK);
            setVisible(true);

        }

    }

    /*public class chat extends JPanel {
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
                        new Part2(ip, 5000,customer);
                        setVisible(true);
                    } catch (UnknownHostException he) {
                        he.printStackTrace();
                    }
                }
            });
        }
    }*/
}
