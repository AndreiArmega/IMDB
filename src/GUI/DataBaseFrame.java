package GUI;

import ProductionTree.*;
import Users.Regular;
import Users.User;
import main.IMDB;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class DataBaseFrame extends JFrame {

       public User user;
    private boolean showingActorsList = true;

    public DataBaseFrame(User user,int mode) {
        this.user = user;
        initializeUI(mode);
    }

    private void initializeUI(int mode) {
        setTitle("Database");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        if(mode ==1 ){
            try {
                showProductionsList(mainPanel);
            } catch (IOException | ParseException ex) {
                throw new RuntimeException(ex);
            }
        }
       if(mode ==2)
       {
           try {
               showActorsList(mainPanel);
           } catch (IOException | ParseException ex) {
               throw new RuntimeException(ex);
           }
       }
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setValue(verticalScrollBar.getMaximum());
        getContentPane().add(scrollPane);



        setLocationRelativeTo(null);
        setVisible(true);
    }
    public void refreshUI(JPanel mainPanel) {

        mainPanel.removeAll();


        try {
            if (showingActorsList) {
                showActorsList(mainPanel);
            } else {
                showProductionsList(mainPanel);
            }
        } catch (IOException | ParseException ex) {
            throw new RuntimeException(ex);
        }


        mainPanel.revalidate();
        mainPanel.repaint();
    }
    private void toggleList(JPanel mainPanel) {

        mainPanel.removeAll();
        if (showingActorsList) {
            try {
                showProductionsList(mainPanel);
                showingActorsList = false;
            } catch (IOException | ParseException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            try {
                showActorsList(mainPanel);

                showingActorsList = true;
            } catch (IOException | ParseException ex) {
                throw new RuntimeException(ex);
            }
        }
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showActorsList(JPanel mainPanel) throws IOException, ParseException {
        IMDB imdb = IMDB.getInstance();
        ArrayList<Actor> actorArrayList = imdb.getActorList();

        for (Actor actor : actorArrayList) {

            StringBuilder result = new StringBuilder();
            result.append(actor.getActorName()).append("\n");
            if (actor.getPerformanceActor() != null) {
                for (FilmPairNameType film : actor.getPerformanceActor()) {
                    result.append("Film Name: ").append(film.getName()).append("\n");
                    result.append("Film Type: ").append(film.getType()).append("\n");
                }
            }
            result.append("Biography: ").append(actor.getBiografie()).append("\n\n");
            if(actor.ratings != null)
            {
                for(Rating rating: actor.ratings)
                {
                    result.append(rating.displayRating()).append("\n");
                }
            }
            ImageIcon actorImage = new ImageIcon(actor.fileNameToImage);
            ActorPanel actorPanel = new ActorPanel(result.toString(), actorImage);
            JPanel buttonPanel = new JPanel(new FlowLayout());

            if(!(user.actorFavorites.contains(actor)))
            {
                JButton addToFavoritesButton = new JButton("Add to Favorites");
                addToFavoritesButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        user.addFavoriteActor(actor);
                    }
                });
                buttonPanel.add(addToFavoritesButton);
            }

            mainPanel.add(actorPanel);
            mainPanel.add(buttonPanel);
            mainPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        }
    }



    private void showProductionsList(JPanel mainPanel) throws IOException, ParseException {
        IMDB imdb = IMDB.getInstance();
        ArrayList<Object> productionArrayList = imdb.getProductionList();

        for (Object production : productionArrayList) {
            Production prod = (Production) production;


            JTextArea textArea = new JTextArea();
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);


            textArea.append(prod.getProductionTitle() + "\n");
            textArea.append(prod.plot + "\n");
            textArea.append("Average Rating: " + prod.ratingAvg + "\n");
            textArea.append("Directors :");
            for(String director : prod.proddirectorNames)
            {
                textArea.append(director + "   ");
            }
            textArea.append("\n");
            textArea.append("Actors :");
            for(String actor : prod.prodActorList)
            {
                textArea.append(actor + "   ");
            }
            textArea.append("\n");

            textArea.append("Genre list : ");
            for (Genre genre : prod.prodGenreList)
            {
                textArea.append(genre.toString()+ "   ");
            }
           textArea.append("\n");
            if(prod instanceof  Movie)
            {
                Movie movie = (Movie) prod;
                textArea.append("Movie duration: " +movie.duration+ "\n");
                textArea.append("Release year: "+movie.releaseYear+ "\n");
            } else if (prod instanceof  Series) {
                Series series = (Series) prod;
                textArea.append("Release year: "+series.releaseYear+ "\n");
                textArea.append("Nr. of seasons : "+series.nrOfSeasons+ "\n");

                if (series.episoadePeSezoane != null && !series.episoadePeSezoane.isEmpty()) {
                    series.episoadePeSezoane.forEach((season, episodes) -> {


                        textArea.append(season);
                        textArea.append(": ");
                        for (Episode episode : episodes) {

                            textArea.append(episode.displayInfo());
                            textArea.append("  \n");
                        }
                        textArea.append("\n\n");
                    });
                }

            }


            JButton addToFavoritesButton = new JButton("Add to Favorites");
            mainPanel.add(addToFavoritesButton, BorderLayout.EAST);

            addToFavoritesButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    user.addProductionFavorite(prod);
                }
            });

            if (user instanceof Regular) {
                if(prod.prodRatingList!=null) {
                    for (Rating rating : prod.prodRatingList) {
                        textArea.append(rating.displayRating() + "\n");

                        if (rating.userName.equals(user.getUsername())) {
                            JButton deleteReviewButton = new JButton("Delete Review");
                            mainPanel.add(deleteReviewButton, BorderLayout.EAST);

                            deleteReviewButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    prod.prodRatingList.remove(rating);

                                    toggleList(mainPanel);
                                }
                            });
                        }
                    }
                }
                JButton addReviewButton = new JButton("Add Review");
                mainPanel.add(addReviewButton, BorderLayout.EAST);

                addReviewButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new RevFrame(user.getUsername(), prod);

                    }
                });
            }

            mainPanel.add(textArea);

            mainPanel.add(new JSeparator(SwingConstants.HORIZONTAL));

        }

    }


}
