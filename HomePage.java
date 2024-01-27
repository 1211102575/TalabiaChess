import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class HomePage {
    HomePageController controller = new HomePageController();

    HomePage(HomePageController controller){
        this.controller = controller;

        JFrame firstPage = new JFrame("Talabia Chess");
        firstPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        firstPage.setSize(new Dimension(600,600));

        //create background
        ImageIcon background = new ImageIcon("background.jpeg");
        JLabel container = new JLabel(background);
        firstPage.setContentPane(container);
        firstPage.setLayout(new BoxLayout(firstPage.getContentPane(), BoxLayout.Y_AXIS));

        //create the tittle
        JLabel name = new JLabel("Let's play Talabia Chess", SwingConstants.CENTER);
        name.setFont(new Font("Chiller",Font.BOLD,170));
        name.setForeground(Color.white);
        //put name at center
        name.setAlignmentX(Component.CENTER_ALIGNMENT); 

        Font forteFont = new Font("forte", Font.PLAIN, 20);
        //create color buttons
        JButton newGameButton = new JButton("New Game");
        newGameButton.setPreferredSize(new Dimension(150,50));
        newGameButton.setBackground(Color.orange);
        newGameButton.setForeground(Color.darkGray);
        newGameButton.setFocusPainted(false);
        newGameButton.setBorderPainted(false);
        newGameButton.setFont(forteFont);

        JButton loadGameButton = new JButton ("Load Game");
        loadGameButton.setPreferredSize(new Dimension(150,50));
        loadGameButton.setBackground(Color.ORANGE);
        loadGameButton.setForeground(Color.black);
        loadGameButton.setFocusPainted(false);
        loadGameButton.setBorderPainted(false);
        loadGameButton.setFont(forteFont);

        JButton exitButton = new JButton("Exit");
        exitButton.setPreferredSize(new Dimension(150,50));
        exitButton.setBackground(Color.ORANGE);
        exitButton.setForeground(Color.black);
        exitButton.setFocusPainted(false);
        exitButton.setBorderPainted(false);
        exitButton.setFont(forteFont);

        //create actionListener for buttons
        
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.handleNewGameButton();
                firstPage.dispose();
            }
        });

        loadGameButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.handleLoadGameButton();
            }
        });


        exitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.handleExitButton();
            }
        });
        

        //create a panel on center and put the tittle and buttons inside
        JPanel centerPanel = new JPanel();
        centerPanel.setPreferredSize(new Dimension(300,400));
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
        centerPanel.add(newGameButton);
        centerPanel.add(loadGameButton);
        centerPanel.add(exitButton);
        centerPanel.setOpaque(false);

        //create some spacing in box layout
        firstPage.add(Box.createRigidArea(new Dimension(0, 220)));
        firstPage.add(name);// make the name higher than centerpanel
        firstPage.add(Box.createRigidArea(new Dimension(0, 140)));
        firstPage.add(centerPanel);
        
        
        firstPage.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                Image image = background.getImage();
                Image newImg = image.getScaledInstance(firstPage.getWidth(), firstPage.getHeight(), java.awt.Image.SCALE_SMOOTH);
                ImageIcon icon = new ImageIcon(newImg);
                container.setIcon(icon);
            }
        });
        firstPage.setVisible(true);
    }

}