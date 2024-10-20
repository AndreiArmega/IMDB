package GUI;

import ProductionTree.Actor;
import ProductionTree.FilmPairNameType;
import ProductionTree.Rating;
import Users.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FavoriteActors extends JFrame {

    private User user;



    public FavoriteActors(User user) {
        this.user = user;
        initializeUI();
    }


    private void initializeUI() {
        setTitle("Favorite Actors");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        mainPanel.setBackground(Color.BLACK);

        for (Actor actor : user.actorFavorites) {

            String actorInfo = getActorInfo(actor);

            ImageIcon actorImage = new ImageIcon(actor.fileNameToImage);

            ActorPanel actorPanel = new ActorPanel(actorInfo, actorImage,mainPanel, user,actor);
            mainPanel.add(actorPanel);


            mainPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        }

        if (user.actorFavorites.size() > 0) {
            mainPanel.remove(mainPanel.getComponentCount() - 1);
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        getContentPane().add(scrollPane);

        setLocationRelativeTo(null);
        setVisible(true);
    }
    public static String getActorInfo(Actor actor)
    {
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
        String actorInfo = result.toString();
        return actorInfo;
    }


}
