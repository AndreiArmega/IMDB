package GUI;

import ProductionTree.Movie;
import ProductionTree.Production;
import ProductionTree.Series;
import main.IMDB;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class MovieModificationPanel<T extends Production> extends JPanel {
    private JTextField productionTitleField;
    private JTextField directorNamesField;
    private JTextField actorListField;
    private JTextArea plotArea;
    private JTextField releaseYearField;

    public MovieModificationPanel() {
        setLayout(new GridLayout(6, 2));

        productionTitleField = new JTextField();
        directorNamesField = new JTextField();
        actorListField = new JTextField();
        plotArea = new JTextArea();
        releaseYearField = new JTextField();

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = productionTitleField.getText();
                Production production = findProductionByTitle(title);

                if (production != null) {
                    MovieModificationFrame<Production> modificationFrame = new MovieModificationFrame<>(production);
                } else {
                    JOptionPane.showMessageDialog(null, "Production not found!");
                }
            }
        });

        add(new JLabel("Enter Production Title:"));
        add(productionTitleField);

        add(confirmButton);
    }

    private Production findProductionByTitle(String title) {
        try {
            IMDB imdb = IMDB.getInstance();

            for (Movie movie : imdb.movieList) {
                if (movie.getProductionTitle().equals(title)) {
                    return movie;
                }
            }
            for (Series series : imdb.seriesList) {
                if (series.getProductionTitle().equals(title)) {
                    return series;
                }
            }

        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    class MovieModificationFrame<T extends Production> extends JFrame {
        private JTextField productionTitleField;
        private JTextField directorNamesField;
        private JTextField actorListField;
        private JTextArea plotArea;
        private JTextField releaseYearField;

        public MovieModificationFrame(T production) {
            setTitle("Modify Production Information");
            setSize(400, 400);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            productionTitleField = new JTextField(production.getProductionTitle());
            directorNamesField = new JTextField(String.join(", ", production.proddirectorNames));
            actorListField = new JTextField(String.join(", ", production.prodActorList));
            plotArea = new JTextArea(production.plot);
            releaseYearField = new JTextField(String.valueOf(getReleaseYear(production)));
            JButton saveButton = new JButton("Save Changes");
            saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    production.productionTitle = productionTitleField.getText();
                    production.proddirectorNames = splitAndTrim(directorNamesField.getText(), ",");
                    production.prodActorList = splitAndTrim(actorListField.getText(), ",");
                    production.plot = plotArea.getText();
                    updateReleaseYear(production, releaseYearField.getText());

                    dispose();
                }
            });

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(6, 2));
            panel.add(new JLabel("Production Title:"));
            panel.add(productionTitleField);
            panel.add(new JLabel("Director Names (comma-separated):"));
            panel.add(directorNamesField);
            panel.add(new JLabel("Actor Names (comma-separated):"));
            panel.add(actorListField);
            panel.add(new JLabel("Plot:"));
            panel.add(new JScrollPane(plotArea));
            panel.add(new JLabel("Release Year:"));
            panel.add(releaseYearField);
            // No additionalField for Series

            panel.add(new JLabel(""));
            panel.add(saveButton);

            add(panel);
            setLocationRelativeTo(null);
            setVisible(true);
        }

        private ArrayList<String> splitAndTrim(String input, String delimiter) {
            return Arrays.stream(input.split(delimiter))
                    .map(String::trim)
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        private Long getReleaseYear(T production) {
            if (production instanceof Movie) {
                return ((Movie) production).releaseYear;
            } else if (production instanceof Series) {
                return ((Series) production).releaseYear;
            }
            return 0L;
        }

        private void updateReleaseYear(T production, String text) {
            try {
                Long releaseYear = Long.parseLong(text);
                if (production instanceof Movie) {
                    ((Movie) production).releaseYear = releaseYear;
                } else if (production instanceof Series) {
                    ((Series) production).releaseYear = releaseYear;
                }
            } catch (NumberFormatException e) {
                // Handle the case where the input is not a valid Long
                e.printStackTrace();
            }
        }
    }
}
