package GUI;

import Miscellaneous.ReviewExperienceStrategy;
import ProductionTree.Production;
import ProductionTree.Rating;
import Users.Admin;
import Users.Contributor;
import Users.User;
import main.IMDB;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import static Miscellaneous.NotificationService.notifyObserver;

public class RevFrame extends JFrame {
    private final String username;
    private final Production production;
    private JComboBox<Integer> ratingComboBox;
    private JTextArea messageArea;

    public RevFrame(String username, Production production) {
        this.username = username;
        this.production = production;

        setTitle("IMDb Review");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create components
        JLabel usernameLabel = new JLabel("Username: " + username);
        JLabel ratingLabel = new JLabel("Rating:");
        JLabel messageLabel = new JLabel("Review Message:");

        ratingComboBox = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        ratingComboBox.setBackground(Color.WHITE);

        messageArea = new JTextArea(5, 20);
        JScrollPane messageScrollPane = new JScrollPane(messageArea);

        JButton submitButton = new JButton("Submit Review");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitReview();
                try {

                        for( User user: IMDB.getInstance().getUsers())
                        {
                            if(user instanceof Contributor )
                            {

                                Contributor contributor = (Contributor) user;
                                if(contributor.prodContr!=null) {

                                    if (contributor.prodContr.contains(production.getProductionTitle())) {
                                        System.out.println("It gets here");
                                        if(contributor.notifications == null) {contributor.notifications = new ArrayList<>();}
                                        notifyObserver(contributor.getUsername() + " has reviewed your production" + production.getProductionTitle() + "->"
                                                + ratingComboBox.getSelectedItem().toString(), contributor);
                                    }
                                }
                            } else if (user instanceof Admin) {
                                Admin admin = (Admin) user;
                                if(admin.prodContr!=null) {

                                    if (admin.prodContr.contains(production.getProductionTitle())) {
                                        System.out.println("It gets here");
                                        if(admin.notifications == null) {admin.notifications = new ArrayList<>();}
                                        notifyObserver(admin.getUsername() + " has reviewed your production" + production.getProductionTitle() + "->"
                                                + ratingComboBox.getSelectedItem().toString(), admin);
                                    }
                                }
                            }
                        }

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        setLayout(new GridLayout(4, 2, 10, 10));

        add(usernameLabel);
        add(new JLabel());
        add(ratingLabel);
        add(ratingComboBox);
        add(messageLabel);
        add(messageScrollPane);
        add(new JLabel());
        add(submitButton);

        setVisible(true);
    }

    private void submitReview() {
        int selectedRating = (Integer) ratingComboBox.getSelectedItem();
        String message = messageArea.getText();

        if (message.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a review message.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Rating rating = new Rating(username,selectedRating,message);
        production.addReview(rating);
        int nratings = production.prodRatingList.toArray().length;
        int sum = 0;
        for(Rating rating1 : production.prodRatingList)
        {
            sum+=rating1.nota;


        }
        double fin = (sum + selectedRating)/(nratings+1);
        production.ratingAvg =fin;
        JOptionPane.showMessageDialog(this, "Review submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        dispose();
    }


}



