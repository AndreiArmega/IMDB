package GUI;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.ListIterator;

public class ImageSlider extends JPanel {
    private JPanel imagePanel;
    private LinkedList<String> filePaths;
    private JButton nextButton;
    private JButton prevButton;
    private ListIterator<String> iterator;
    private Timer autoChangeTimer;

    public ImageSlider(JPanel imagePanel, JButton nextButton, JButton prevButton, int mode) {

        filePaths = new LinkedList<>();
        if(mode == 1) {
            filePaths.add("C:\\Users\\Armega\\Desktop\\java\\imdbtema\\IMDB\\src\\Miscellaneous\\5b39dc2e8d377-breaking-bad-anniversary-10-year-cast-reunion-photoshoot-5b361591b3476__880.jpg");
            filePaths.add("C:\\Users\\Armega\\Desktop\\java\\imdbtema\\IMDB\\src\\Miscellaneous\\06-yv-Inception-01.jpg");
            filePaths.add("C:\\Users\\Armega\\Desktop\\java\\imdbtema\\IMDB\\src\\Miscellaneous\\61f016062b43ff00185e6ca4.jpg");
            filePaths.add("C:\\Users\\Armega\\Desktop\\java\\imdbtema\\IMDB\\src\\Miscellaneous\\squid.jpg");
        }else if(mode ==2)
        {
            filePaths.add("C:\\Users\\Armega\\Desktop\\java\\imdbtema\\IMDB\\src\\Miscellaneous\\inceptionmic.png");
            filePaths.add("C:\\Users\\Armega\\Desktop\\java\\imdbtema\\IMDB\\src\\Miscellaneous\\fightclubmic.png");
            filePaths.add("C:\\Users\\Armega\\Desktop\\java\\imdbtema\\IMDB\\src\\Miscellaneous\\squidmic.jpg");
            filePaths.add("C:\\Users\\Armega\\Desktop\\java\\imdbtema\\IMDB\\src\\Miscellaneous\\300px-Breaking_Bad_Poster.jpg");
        }else if(mode ==3)
        {
            filePaths.add("C:\\Users\\Armega\\Desktop\\java\\imdbtema\\IMDB\\src\\Miscellaneous\\fightclubmic.png");
            filePaths.add("C:\\Users\\Armega\\Desktop\\java\\imdbtema\\IMDB\\src\\Miscellaneous\\squidmic.jpg");
            filePaths.add("C:\\Users\\Armega\\Desktop\\java\\imdbtema\\IMDB\\src\\Miscellaneous\\300px-Breaking_Bad_Poster.jpg");
            filePaths.add("C:\\Users\\Armega\\Desktop\\java\\imdbtema\\IMDB\\src\\Miscellaneous\\inceptionmic.png");
        }
        else if(mode ==4)
        {
            filePaths.add("C:\\Users\\Armega\\Desktop\\java\\imdbtema\\IMDB\\src\\Miscellaneous\\squidmic.jpg");
            filePaths.add("C:\\Users\\Armega\\Desktop\\java\\imdbtema\\IMDB\\src\\Miscellaneous\\300px-Breaking_Bad_Poster.jpg");
            filePaths.add("C:\\Users\\Armega\\Desktop\\java\\imdbtema\\IMDB\\src\\Miscellaneous\\inceptionmic.png");
            filePaths.add("C:\\Users\\Armega\\Desktop\\java\\imdbtema\\IMDB\\src\\Miscellaneous\\fightclubmic.png");

        }
        this.imagePanel = imagePanel;
        this.nextButton = nextButton;
        this.prevButton = prevButton;
        iterator = filePaths.listIterator();


        imagePanel.setBackground(Color.BLACK);

        add(imagePanel);

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Display the next image
                displayImage(true,imagePanel);
            }
        });
        add(nextButton);


        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayImage(false,imagePanel);
            }
        });
        add(prevButton);
        imagePanel.setBounds(50, 50, 700, 400);
        displayImage(true,imagePanel);

        autoChangeTimer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayImage(true,imagePanel);
            }
        });
        autoChangeTimer.start();
    }

    private void displayImage(boolean next, JPanel imgPanel) {
        imgPanel.removeAll();

        String currentFilePath;
        if (next) {
            if (iterator.hasNext()) {
                currentFilePath = iterator.next();
            } else {
                iterator = filePaths.listIterator();
                currentFilePath = iterator.next();
            }
        } else {
            if (iterator.hasPrevious()) {
                currentFilePath = iterator.previous();
            } else {
                iterator = filePaths.listIterator(filePaths.size());
                currentFilePath = iterator.previous();
            }
        }

        ImageIcon imageIcon = new ImageIcon(currentFilePath);
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setBounds(50, 50, 900, 600);

        imgPanel.add(imageLabel);

        imgPanel.revalidate();
        imgPanel.repaint();
    }


}


