package GUI;

import ProductionTree.Production;
import Users.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class FavoriteProds extends JFrame {
    private User user;

    public FavoriteProds(User user) {
        this.user = user;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Favorite Productions");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
       if(user.productionFavorites==null)
       {
           user.productionFavorites= new ArrayList<>();
       }
        for (Production prod : user.productionFavorites) {

            StringBuilder result = new StringBuilder();
            result.append("Title: ").append(prod.getProductionTitle()).append("\n");
            result.append("Plot: ").append(prod.plot).append("\n");
            double avg = prod.ratingAvg;
            String average = String.valueOf(avg);
            result.append("Average Rating: ").append(average).append("\n");

            JButton removeFromFavoritesButton = new JButton("Remove from Favorites");
            removeFromFavoritesButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    user.removeProductionFavorite(prod);
                }
            });

            JTextArea textArea = new JTextArea(result.toString());
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);

            mainPanel.add(textArea);
            mainPanel.add(removeFromFavoritesButton);
            mainPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        }

           System.out.println("this is the end");
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        getContentPane().add(scrollPane);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
