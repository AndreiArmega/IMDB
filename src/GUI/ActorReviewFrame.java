package GUI;

import ProductionTree.Actor;
import ProductionTree.Rating;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ActorReviewFrame extends JFrame {

    private final Actor actor;
    private JComboBox<Integer> ratingComboBox;
    private JTextArea reviewTextArea;

    public ActorReviewFrame(Actor actor) {
        this.actor = actor;

        setTitle("Actor Review");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeComponents();
    }

    private void initializeComponents() {
        setLayout(new GridLayout(4, 2, 10, 10));

        JLabel actorNameLabel = new JLabel("Actor Name: " + actor.getActorName());
        JLabel ratingLabel = new JLabel("Rating:");
        JLabel reviewLabel = new JLabel("Review:");

        ratingComboBox = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        ratingComboBox.setBackground(Color.WHITE);

        reviewTextArea = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(reviewTextArea);

        JButton submitButton = new JButton("Submit Review");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitReview();
            }
        });

        add(actorNameLabel);
        add(new JLabel());
        add(ratingLabel);
        add(ratingComboBox);
        add(reviewLabel);
        add(scrollPane);
        add(new JLabel());
        add(submitButton);

        setVisible(true);
    }

    private void submitReview() {
        int selectedRating = (Integer) ratingComboBox.getSelectedItem();
        String reviewMessage = reviewTextArea.getText();

        if (reviewMessage.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a review message.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Rating rating = new Rating("User", selectedRating, reviewMessage);
        if (actor.ratings == null) {
            actor.ratings = new ArrayList<>();
        }
        actor.ratings.add(rating);

        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Actor sampleActor = new Actor("Sample Actor", new ArrayList<>(), "Sample Biography");
                ActorReviewFrame actorReviewFrame = new ActorReviewFrame(sampleActor);
            }
        });
    }
}
