package GUI;

import ProductionTree.*;
import main.IMDB;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewProductionFrame extends JFrame {
    private JTextField productionTitleField;
    private JTextField directorNamesField;
    private JTextField actorListField;
    private JTextArea plotArea;
    private JTextField releaseYearField;
    private JTextField genreField;
    private JComboBox<String> productionTypeComboBox;
    private JTextField durationField;
    private JTextField nrOfSeasonsField;
    private JTextField episodesField;

    public NewProductionFrame() {
        setTitle("Create New Production");
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        productionTitleField = new JTextField();
        directorNamesField = new JTextField();
        actorListField = new JTextField();
        plotArea = new JTextArea();
        releaseYearField = new JTextField();
        genreField = new JTextField();
        durationField = new JTextField();
        nrOfSeasonsField = new JTextField();
        episodesField = new JTextField();

        productionTypeComboBox = new JComboBox<>(new String[]{"Movie", "Series"});

        productionTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateFieldsVisibility();
            }
        });

        JButton createButton = new JButton("Create");
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = productionTitleField.getText();
                String directorNames = directorNamesField.getText();
                String actorList = actorListField.getText();
                String plot = plotArea.getText();
                String releaseYear = releaseYearField.getText();
                Genre genre = Genre.valueOf(genreField.getText());

                if (!title.isEmpty() && !directorNames.isEmpty() && !actorList.isEmpty()
                        && !plot.isEmpty() && !releaseYear.isEmpty()) {
                    try {
                        IMDB imdb = IMDB.getInstance();

                        String productionType = productionTypeComboBox.getSelectedItem().toString();

                        Production newProduction;
                        if (productionType.equalsIgnoreCase("Movie")) {
                            newProduction = new Movie();
                            ((Movie) newProduction).setReleaseYear(Long.parseLong(releaseYear));
                            ((Movie) newProduction).setGenres(new ArrayList<>(Arrays.asList(genre)));
                            ((Movie) newProduction).setDuration(durationField.getText());

                        } else if (productionType.equalsIgnoreCase("Series")) {
                            newProduction = new Series();
                            ((Series) newProduction).setReleaseYear(Long.parseLong(releaseYear));
                            ((Series) newProduction).setGenres(new ArrayList<>(Arrays.asList(genre)));
                            ((Series) newProduction).setNrOfSeasons(Integer.parseInt(nrOfSeasonsField.getText()));
                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid production type!", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        newProduction.setProductionTitle(title);
                        newProduction.prodRatingList = new ArrayList<>();
                        newProduction.setProddirectorNames(new ArrayList<>(Arrays.asList(directorNames.split(","))));
                        newProduction.setProdActorList(new ArrayList<>(Arrays.asList(actorList.split(","))));
                        newProduction.setPlot(plot);

                        if (newProduction instanceof Movie) {
                            imdb.movieList.add((Movie) newProduction);
                        } else if (newProduction instanceof Series) {
                            imdb.seriesList.add((Series) newProduction);
                        }

                        JOptionPane.showMessageDialog(null, "Production created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(10, 2));
        panel.add(new JLabel("Production Type:"));
        panel.add(productionTypeComboBox);
        panel.add(new JLabel("Production Title:"));
        panel.add(productionTitleField);
        panel.add(new JLabel("Genres: "));
        panel.add(genreField);
        panel.add(new JLabel("Director Names (comma-separated):"));
        panel.add(directorNamesField);
        panel.add(new JLabel("Actor Names (comma-separated):"));
        panel.add(actorListField);
        panel.add(new JLabel("Plot:"));
        panel.add(new JScrollPane(plotArea));
        panel.add(new JLabel("Release Year:"));
        panel.add(releaseYearField);
        panel.add(new JLabel("Duration (minutes):"));
        panel.add(durationField);
        panel.add(new JLabel("Number of Seasons:"));
        panel.add(nrOfSeasonsField);

        panel.add(new JLabel(""));
        panel.add(createButton);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);

        updateFieldsVisibility();
    }

    private void updateFieldsVisibility() {
        String selectedType = productionTypeComboBox.getSelectedItem().toString();
        boolean isMovie = selectedType.equalsIgnoreCase("Movie");
        boolean isSeries = selectedType.equalsIgnoreCase("Series");

        durationField.setVisible(isMovie);
        nrOfSeasonsField.setVisible(isSeries);
    }


}
