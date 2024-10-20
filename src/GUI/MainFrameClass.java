package GUI;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import static GUI.FrameMethods.*;
import static GUI.FrameMethods.ErrButton;


public class MainFrameClass extends JFrame  {
    private JTextField searchBar;
    public MainFrameClass() {
        setResizable(false);
        setLayout(null);
        setSize(1520, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.BLACK);
        setFocusable(true);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                requestFocusInWindow();
            }
        });
        searchBar = new JTextField();
        ImageIcon imageIcon = new ImageIcon("C:\\Users\\Armega\\Desktop\\java\\imdbtema\\IMDB\\src\\Miscellaneous\\imdb2.png");
        ImageIcon bodyicon = new ImageIcon("C:\\Users\\Armega\\Desktop\\java\\imdbtema\\IMDB\\src\\Miscellaneous\\body.jpg");
        ImageIcon arrow = new ImageIcon("C:\\Users\\Armega\\Desktop\\java\\imdbtema\\IMDB\\src\\Miscellaneous\\arrow-40166_640.png");
        ImageIcon arrow2 = new ImageIcon("C:\\Users\\Armega\\Desktop\\java\\imdbtema\\IMDB\\src\\Miscellaneous\\arrow-40166_641.png");
        JButton menuButton = createInvisibleButton();
        JButton signInButton = createInvisibleButton();
        JButton nextButton = new JButton();
        JButton prevButton = new JButton();
        JButton actors = new JButton("Actors");
        JButton  watchListDenied = createInvisibleButton();

        nextButton.setIcon(arrow);
        nextButton.setBackground(Color.BLACK);
        nextButton.setBorderPainted(false);
        prevButton.setIcon(arrow2);
        prevButton.setBackground(Color.BLACK);
        prevButton.setBorderPainted(false);
        nextButton.setBounds(1000,390,50,60);
        prevButton.setBounds(51,390,50,60);
        actors.setBounds(1150, 15, 87, 32);
        watchListDenied.setBounds(1280,15,115,32);
        ErrButton(watchListDenied,"Please log in to acces your watch list");
        JPanel sliderPanel = new JPanel();
        ImageSlider slider = new ImageSlider(sliderPanel,nextButton,prevButton,1);

        JPanel smallSlider = new JPanel();
        ImageSlider small = new ImageSlider(smallSlider,nextButton,prevButton,2);
        smallSlider.setBounds(1208,150,212,170);
        smallSlider.setBackground(Color.BLACK);

        JPanel smallSlider2 = new JPanel();
        ImageSlider small2 = new ImageSlider(smallSlider2,nextButton,prevButton,3);
        smallSlider2.setBounds(1208,350,212,170);
        smallSlider2.setBackground(Color.BLACK);

        JPanel smallSlider3 = new JPanel();
        ImageSlider small3 = new ImageSlider(smallSlider3,nextButton,prevButton,4);
        smallSlider3.setBounds(1208,550,212,170);
        smallSlider3.setBackground(Color.BLACK);


        sliderPanel.setBounds(100 ,120 ,900,600);
       sliderPanel.setBackground(Color.BLACK);
        menuButton.setBounds(105, 15, 103, 32);
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuWindow menuWindow = new MenuWindow(null);
            }
        });
        signInButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SignInFrame sif = new SignInFrame(MainFrameClass.this);
            }
        });

        Font font = new Font("Arial", Font.PLAIN, 18); // Adjust the font size (18 in this example)
        Font font2= new Font("Arial",Font.BOLD,16);
        searchBar.setFont(font);


        actors.setFont(font2);
        actors.setBackground(new Color(33, 32, 32));
        actors.setForeground(Color.WHITE);
        actors.setBorderPainted(false);
        actors.setFocusPainted(false);

       setPlaceholder(searchBar, "Search IMDb");
        setCustomBorder(searchBar, Color.WHITE);
        searchBar.setBounds(300, 10, 780, 40);
        signInButton.setBounds(1400, 15, 87, 32);

        JLabel imageLabel = new JLabel(imageIcon);
        JLabel body = new JLabel(bodyicon);
        imageLabel.setBounds(0, 0, imageIcon.getIconWidth(), 60);
        getContentPane().setBackground(Color.BLACK);
        body.setBounds(400,45,1020,800);

        //getContentPane().add(body);
        getContentPane().add(searchBar);
        getContentPane().add(signInButton);

        getContentPane().add(imageLabel);
        getContentPane().add(menuButton);
        getContentPane().add(watchListDenied);
        getContentPane().add(nextButton);
        getContentPane().add(prevButton);
        getContentPane().add(smallSlider);
        getContentPane().add(smallSlider2);
        getContentPane().add(smallSlider3);
        getContentPane().add(sliderPanel);


        getContentPane().add(body);
        setLocationRelativeTo(null);
        setVisible(true);

    }


}
