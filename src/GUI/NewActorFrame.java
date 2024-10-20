package GUI;

import ProductionTree.Actor;
import ProductionTree.FilmPairNameType;
import main.IMDB;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class NewActorFrame extends JFrame {
    private JTextField actorNameField;
    private JTextArea performancesArea;
    private JTextField biographyField;
    private JTextField pictureField;

    public NewActorFrame() {
        setTitle("Add New Actor");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        actorNameField = new JTextField();
        performancesArea = new JTextArea();
        biographyField = new JTextField();
        pictureField = new JTextField();

        JButton addButton = new JButton("Add Actor");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String actorName = actorNameField.getText();
                String performancesText = performancesArea.getText();
                String biography = biographyField.getText();
                String picture = pictureField.getText();

                if (!actorName.isEmpty() && !performancesText.isEmpty() && !biography.isEmpty() && !picture.isEmpty()) {
                    ArrayList<FilmPairNameType> performances = parsePerformances(performancesText);

                    Actor newActor = new Actor(actorName, performances, biography);
                    newActor.setPicture(picture);

                   try{
                       IMDB imdb = IMDB.getInstance();
                       imdb.actorList.add(newActor);
                   } catch (IOException ex) {
                       throw new RuntimeException(ex);
                   } catch (ParseException ex) {
                       throw new RuntimeException(ex);
                   }

                    JOptionPane.showMessageDialog(null, "Actor added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        panel.add(new JLabel("Actor Name:"));
        panel.add(actorNameField);

        panel.add(new JLabel("Performances (comma-separated):"));
        panel.add(new JScrollPane(performancesArea));

        panel.add(new JLabel("Biography:"));
        panel.add(biographyField);

        panel.add(new JLabel("Picture File Name:"));
        panel.add(pictureField);

        panel.add(new JLabel(""));
        panel.add(addButton);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private ArrayList<FilmPairNameType> parsePerformances(String performancesText) {
        ArrayList<FilmPairNameType> performances = new ArrayList<>();
        String[] performanceArray = performancesText.split(",");

        for (String performance : performanceArray) {
            String[] parts = performance.trim().split("\\s+");
            if (parts.length == 2) {
                String filmName = parts[0];
                String filmType = parts[1];
                performances.add(new FilmPairNameType(filmName, filmType));
            }
        }

        return performances;
    }


}
