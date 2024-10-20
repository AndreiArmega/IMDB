package GUI;

import ProductionTree.Actor;
import ProductionTree.FilmPairNameType;
import main.IMDB;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ActorModificationPanel extends JPanel {
    private JTextField actorNameField;

    public ActorModificationPanel() {
        setLayout(new GridLayout(2, 2));

        actorNameField = new JTextField();
        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String actorName = actorNameField.getText();
                Actor actor = findActorByName(actorName);

                if (actor != null) {
                    ActorModificationFrame modificationFrame = new ActorModificationFrame(actor);
                } else {
                    JOptionPane.showMessageDialog(null, "Actor not found!");
                }
            }
        });

        add(new JLabel("Enter Actor Name:"));
        add(actorNameField);
        add(new JLabel(""));
        add(confirmButton);
    }

    private Actor findActorByName(String actorName) {

        try {
            IMDB imdb = IMDB.getInstance();

            for (Actor actor : imdb.getActorList()) {
                if (actor.getActorName().equalsIgnoreCase(actorName)) {
                    return actor;
                }
            }

        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    class ActorModificationFrame extends JFrame {
        private JTextField actorNameField;
        private JTextArea biografieArea;
        private JTextArea performancesArea;

        public ActorModificationFrame(Actor actor) {
            setTitle("Modify Actor Information");
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            actorNameField = new JTextField(actor.getActorName());
            biografieArea = new JTextArea(actor.getBiografie());
            performancesArea = new JTextArea( actor.getPerformanceString());

            JButton saveButton = new JButton("Save Changes");
            saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Update the actor object with the modified information
                    actor.setActorName(actorNameField.getText());
                    actor.setBiografie(biografieArea.getText());
                    System.out.println(performancesArea.getText());
                    System.out.println(parsePerformances(performancesArea.getText()));
                    actor.setPerformances(parsePerformances(performancesArea.getText()));

                    dispose();
                }
            });

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(4, 2));
            panel.add(new JLabel("Actor Name:"));
            panel.add(actorNameField);
            panel.add(new JLabel("Biography:"));
            panel.add(new JScrollPane(biografieArea));
            panel.add(new JLabel("Performances (comma-separated):"));
            panel.add(new JScrollPane(performancesArea));
            panel.add(new JLabel(""));
            panel.add(saveButton);

            add(panel);
            setLocationRelativeTo(null);
            setVisible(true);
        }

        private ArrayList<FilmPairNameType> parsePerformances(String performances) {
            ArrayList<FilmPairNameType> performanceList = new ArrayList<>();

            StringTokenizer tokenizer = new StringTokenizer(performances, ",");
            while (tokenizer.hasMoreTokens()) {
                String performance = tokenizer.nextToken().trim();
                String[] parts = performance.split("\\s+", 2);

                if (parts.length == 2) {
                    String name = parts[0].trim();
                    String type = parts[1].trim();
                    FilmPairNameType filmPair = new FilmPairNameType(name, type);
                    performanceList.add(filmPair);
                }
            }

            return performanceList;
        }



    }
}
