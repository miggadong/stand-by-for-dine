package gui;

import model.entity.MenuInfo;
import model.entity.Restaurant;
import model.service.MenuService;

import javax.swing.*;
import java.awt.*;



public class menuPanel extends JFrame {
    MenuInfo menu = new MenuInfo();


    int count = 0;
    String show = "";

    Container container = getContentPane();
    Restaurant restaurant;

    JLabel imageLabel;
    JPanel imagePanel;
    JLabel nameLabel;
    JLabel priceLabel;
    JLabel soldOutLabel;

    private JScrollPane scrollPane;
    String[] imagePaths = new String[4];



    public menuPanel(Integer resId) {




        setPreferredSize(new Dimension(500, 400));
        setTitle("메뉴판");
        JPanel menuPanel = new JPanel(new GridLayout(4,1));




        if(resId ==1){
            imagePaths = new String[]{
                    "C:\\Users\\pgh99\\Workspace\\School_workspace\\Stand_by_for_dine\\picture\\bob_1.jpeg",
                    "C:\\Users\\pgh99\\Workspace\\School_workspace\\Stand_by_for_dine\\picture\\bob_2.jpeg",
                    "C:\\Users\\pgh99\\Workspace\\School_workspace\\Stand_by_for_dine\\picture\\bob_3.jpeg",
                    "C:\\Users\\pgh99\\Workspace\\School_workspace\\Stand_by_for_dine\\picture\\bob_4.jpeg"
            };
        }


        if (resId == 2) {
            imagePaths = new String[]{
                    "C:\\Users\\pgh99\\Workspace\\School_workspace\\Stand_by_for_dine\\picture\\shin_1.jpegg",
                    "C:\\Users\\pgh99\\Workspace\\School_workspace\\Stand_by_for_dine\\picture\\shin_2.jpeg",
                    "C:\\Users\\pgh99\\Workspace\\School_workspace\\Stand_by_for_dine\\picture\\shin_3.jpeg",
                    "C:\\Users\\pgh99\\Workspace\\School_workspace\\Stand_by_for_dine\\picture\\shin_4.jpeg"
            };
        }

        if (resId == 3) {
            imagePaths = new String[]{
                    "C:\\Users\\pgh99\\Workspace\\School_workspace\\Stand_by_for_dine\\picture\\chai_1.jpeg",
                    "C:\\Users\\pgh99\\Workspace\\School_workspace\\Stand_by_for_dine\\picture\\chai_2.jpeg",
                    "C:\\Users\\pgh99\\Workspace\\School_workspace\\Stand_by_for_dine\\picture\\chai_3.jpeg",
                    "C:\\Users\\pgh99\\Workspace\\School_workspace\\Stand_by_for_dine\\picture\\chai_4.jpeg"
            };
        }

        if (resId == 4) {
            imagePaths = new String[]{
                    "C:\\Users\\pgh99\\Workspace\\School_workspace\\Stand_by_for_dine\\picture\\orto_1.jpeg",
                    "C:\\Users\\pgh99\\Workspace\\School_workspace\\Stand_by_for_dine\\picture\\orto_2.jpeg",
                    "C:\\Users\\pgh99\\Workspace\\School_workspace\\Stand_by_for_dine\\picture\\orto_3.jpeg",
                    "C:\\Users\\pgh99\\Workspace\\School_workspace\\Stand_by_for_dine\\picture\\orto_4.jpeg"
            };
        }

        if(resId > 4) {
            imagePaths = new String[]{
                    "C:\\Users\\pgh99\\Workspace\\School_workspace\\Stand_by_for_dine\\picture\\noImage.png",
                    "C:\\Users\\pgh99\\Workspace\\School_workspace\\Stand_by_for_dine\\picture\\noImage.png",
                    "C:\\Users\\pgh99\\Workspace\\School_workspace\\Stand_by_for_dine\\picture\\noImage.png",
                    "C:\\Users\\pgh99\\Workspace\\School_workspace\\Stand_by_for_dine\\picture\\noImage.png",
            };
        }

        for (int i = 0; i < 4; i++) {
            MenuService menuService = new MenuService();
            menu = menuService.readMenu(resId,i);
            JPanel imagePanel = new JPanel();
            imagePanel.setBackground(Color.PINK);

            Dimension labSize = new Dimension(130,50);
            JLabel nameLabel = new JLabel(menu.getMenuName());
            JLabel priceLabel = new JLabel(menu.getPrice().toString());
            JLabel sellLabel = new JLabel(menu.getStatus());
            setLayout(new BorderLayout());

            String imagePath = imagePaths[i % imagePaths.length];
            ImageIcon imageIcon = new ImageIcon(imagePath);
            Image image = imageIcon.getImage().getScaledInstance(272, 202, Image.SCALE_DEFAULT);
            ImageIcon resizedImageIcon = new ImageIcon(image);
            JLabel imageLabel = new JLabel(resizedImageIcon);
            imagePanel.add(imageLabel, BorderLayout.NORTH);
            imageLabel.setSize(400,600);

            /*JLabel imageLabel2 = new JLabel();
            ImageIcon checkIcon = new ImageIcon("C:\\Users\\migga\\OneDrive\\사진\\picture\\체크.jpeg");
            imageLabel2.setIcon(checkIcon);
            imagePanel.add(imageLabel2);*/



            JPanel labelPanel = new JPanel();
            labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
            labelPanel.add(nameLabel);
            labelPanel.add(priceLabel);
            labelPanel.add(sellLabel);
            labelPanel.setPreferredSize(labSize);
            labelPanel.setBackground(Color.WHITE);


            imagePanel.setSize(272,600);
            imagePanel.add(labelPanel, BorderLayout.SOUTH);
            menuPanel.add(imagePanel);
        }


        scrollPane = new JScrollPane(menuPanel);


        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        menuPanel.setSize(400,600);


        /*JPanel contentPane = new JPanel();
        contentPane.setBackground(Color.PINK);
        setContentPane(contentPane);*/


        add(scrollPane, BorderLayout.NORTH);
        container.setLayout(new BorderLayout());
        //container.setSize(500,600);
        container.add("Center", scrollPane);
        setVisible(true);
        pack();


       /* add(menuPanel, BorderLayout.NORTH);
        container.setLayout(new BorderLayout());
        container.add("Center", menuPanel);
        setVisible(true);*/

    }}