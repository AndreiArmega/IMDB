package GUI;

import ProductionTree.Actor;
import ProductionTree.FilmPairNameType;
import ProductionTree.Movie;
import ProductionTree.Series;
import Users.*;
import main.IMDB;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;


import static GUI.FavoriteActors.getActorInfo;
import static GUI.FrameMethods.*;

public class LoggedInFrame extends JFrame {

    public LoggedInFrame(User user) {

        setSize(1500, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setFocusable(true);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                requestFocusInWindow();
            }
        });
        JButton menuButton = createInvisibleButton();
        menuButton.setBounds(125, 12, 71, 35);
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuWindow menuWindow = new MenuWindow(user);
            }
        });

        ImageIcon arrow = new ImageIcon("C:\\Users\\Armega\\Desktop\\java\\imdbtema\\IMDB\\src\\Miscellaneous\\arrow-40166_640.png");
        ImageIcon arrow2 = new ImageIcon("C:\\Users\\Armega\\Desktop\\java\\imdbtema\\IMDB\\src\\Miscellaneous\\arrow-40166_641.png");
        JButton nextButton = new JButton();
        JButton prevButton = new JButton();
        nextButton.setIcon(arrow);
        nextButton.setBackground(Color.BLACK);
        nextButton.setBorderPainted(false);
        prevButton.setIcon(arrow2);
        prevButton.setBackground(Color.BLACK);
        prevButton.setBorderPainted(false);
        nextButton.setBounds(1000,390,50,60);
        prevButton.setBounds(51,390,50,60);
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
        JPopupMenu popupMenu = createPopupMenu(user,this);
        JPopupMenu favmenu = createFavoritesPopupMenu(user);

        JButton triggerButton =createInvisibleButton();
        JButton favoritesButton = createInvisibleButton();
        JButton DataBase = createInvisibleButton();
        DataBase.setBounds(19,9,83,37);
        favoritesButton.setBounds(1140, 13, 150, 32);
        Font font2= new Font("Arial",Font.BOLD,16);
        Font font3= new Font("Arial",Font.BOLD,14);
        triggerButton.setText("     "+user.getUsername());
        triggerButton.setFont(font3);
        triggerButton.setForeground(Color.WHITE);
        triggerButton.setBounds(1300, 13, 120, 30);
        favoritesButton.setFont(font2);
        favoritesButton.setText("Favorites");
        favoritesButton.setForeground(Color.WHITE);
        favoritesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                favmenu.show(favoritesButton, e.getX(), e.getY());
            }

        });
        triggerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                popupMenu.show(triggerButton, e.getX(), e.getY());
            }
        });
        DataBase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DataBaseFrame dataFrame = new DataBaseFrame(user,0);
            }
        });
        JTextField searchBar = new JTextField();
        searchBar.setFont(font2);
        setPlaceholder(searchBar, "Search IMDb");
        setCustomBorder(searchBar, Color.WHITE);
        searchBar.setBounds(280, 9, 670, 37);
        JButton searchButton =createInvisibleButton();
        searchBar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performSearch(searchBar);
                }
            }
        });
        searchButton.setBounds(950,9,40,37);
        searchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                performSearch(searchBar);
            }
        });
        ImageIcon icon = new ImageIcon("C:\\Users\\Armega\\Desktop\\java\\imdbtema\\IMDB\\src\\Miscellaneous\\new_bar.png");
        ImageIcon icon1 = new ImageIcon("C:\\Users\\Armega\\Desktop\\java\\imdbtema\\IMDB\\src\\Miscellaneous\\body.jpg");
        JLabel lbl = new JLabel(icon);
        JLabel lbl1 = new JLabel(icon1);
        lbl.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
        lbl1.setBounds(100, 60, icon1.getIconWidth(), icon1.getIconHeight());
       if(!(user instanceof Regular))
        {
            JButton button = createInvisibleButton();
            button.setText("Requests");
            button.setFont(font3);
            button.setBounds(1005,11,110,35);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        RequestFrame fr = new RequestFrame(user);
                }
            });
            getContentPane().add(button);
        }


        getContentPane().add(searchBar);
        getContentPane().add(searchButton);
        getContentPane().add(menuButton);
        getContentPane().add(DataBase);
        getContentPane().add(favoritesButton);
        getContentPane().add(triggerButton);
        getContentPane().add(lbl);
        getContentPane().add(nextButton);
        getContentPane().add(prevButton);
        getContentPane().add(smallSlider);
        getContentPane().add(smallSlider2);
        getContentPane().add(smallSlider3);
        getContentPane().add(sliderPanel);
        getContentPane().add(lbl1);
        getContentPane().setBackground(Color.BLACK);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
    }
    private JPopupMenu createFavoritesPopupMenu(User user) {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem favoriteProductionsItem = new JMenuItem("Favorite Productions");
        JMenuItem favoriteActorsItem = new JMenuItem("Favorite Actors");
        // Add ActionListeners to the menu items
        favoriteProductionsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                  FavoriteProds favprodframe = new FavoriteProds(user);
            }
        });
        favoriteActorsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FavoriteActors favoriteActors = new FavoriteActors(user);
            }
        });
        // Add menu items to the popup menu
        popupMenu.add(favoriteProductionsItem);
        popupMenu.add(favoriteActorsItem);
        return popupMenu;
    }
    private JPopupMenu createPopupMenu(User user, LoggedInFrame frame) {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem logoutMenuItem = new JMenuItem("Logout");
        JMenuItem notificationsMenuItem = new JMenuItem("Notifications");
        JMenuItem checkExperienceMenuItem = new JMenuItem("Check Experience");

        logoutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user.logout(frame);
            }
        });

        notificationsMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NotificationsFrame noti = new NotificationsFrame(user);
            }
        });

        checkExperienceMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Current Experience: " + user.userExperience, "Experience", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        popupMenu.add(logoutMenuItem);
        popupMenu.add(notificationsMenuItem);
        popupMenu.add(checkExperienceMenuItem);

        return popupMenu;
    }
    private static void performSearch(JTextField searchBar) {
        try {
            IMDB imdb = IMDB.getInstance();
            for (Actor actor : imdb.getActorList()) {
                if (actor.getActorName().equals(searchBar.getText())) {
                    JFrame actorDetailsFrame = new JFrame();
                    actorDetailsFrame.setSize(800, 600);
                    String actorInfo = getActorInfo(actor);
                    ActorPanel actorPanel = new ActorPanel(actorInfo, new ImageIcon(actor.fileNameToImage));
                    actorDetailsFrame.getContentPane().add(actorPanel);
                    actorDetailsFrame.setLocationRelativeTo(null);
                    actorDetailsFrame.setVisible(true);
                }
            }
            for(Object production : imdb.getProductionList())
            {
                if(production instanceof Movie)
                {
                    Movie movie  =(Movie) production;
                    if(((Movie) production).getProductionTitle().equalsIgnoreCase(searchBar.getText()))
                    {
                        JFrame movieDetails = new JFrame();
                        movieDetails.setSize(800,600);
                        String movieInfo = movie.getMovieInfo();
                        movieDetails.setLayout(new BorderLayout());
                        JTextArea textArea = new JTextArea(movieInfo);
                        textArea.setEditable(false);
                        JScrollPane scrollPane = new JScrollPane(textArea);

                        movieDetails.add(scrollPane, BorderLayout.CENTER);
                        movieDetails.setVisible(true);

                    }
                }else if(production instanceof Series)
                {
                    Series series = (Series)  production;
                    if(((Series) production).getProductionTitle().equals(searchBar.getText()))
                    {
                        JFrame seriesDetails = new JFrame();
                        seriesDetails.setSize(800,600);
                        String seriesInfo = series.buildProductionInfo();
                        seriesDetails.setLayout(new BorderLayout());
                        JTextArea textArea = new JTextArea(seriesInfo);
                        textArea.setEditable(false);
                        JScrollPane scrollPane = new JScrollPane(textArea);
                          seriesDetails.add(scrollPane, BorderLayout.CENTER);
                          seriesDetails.setVisible(true);
                    }
                }
            }
        } catch (IOException | ParseException ex) {
            throw new RuntimeException(ex);
        }
    }

}

