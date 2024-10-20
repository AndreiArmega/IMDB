package ProductionTree;
import com.sun.nio.sctp.NotificationHandler;

import javax.swing.*;
import java.util.ArrayList;

public class Actor implements Comparable{
    private String actorName;
    public ArrayList<FilmPairNameType> performancesActor;
    private String biografie;
    public String fileNameToImage;

    public ArrayList<Rating>  ratings;

    public Actor(){}
    public String getPerformanceString() {
        StringBuilder performanceString = new StringBuilder();

        if (performancesActor != null) {
            for (FilmPairNameType film : performancesActor) {
                performanceString.append(film.getName()).append(" ").append(film.getType()).append(", ");
            }
            if (performanceString.length() > 2) {
                performanceString.setLength(performanceString.length() - 2);
            }
        }

        return performanceString.toString();
    }

    public void setPerformances(ArrayList<FilmPairNameType> performances) {
        this.performancesActor = performances;
    }

    public Actor(String actorName , ArrayList<FilmPairNameType> performances , String biografie)
    {
        this.actorName = actorName;
        this.performancesActor = performances;
        this.biografie = biografie;
    }
    public void  setPicture(String picture)
    {
        this.fileNameToImage = picture;
    }
    public String getActorName() {
        return actorName;
    }



    public void setActorName(String actorName) {
        this.actorName = actorName;
    }
    public void setBiografie(String biografie)
    {
        this.biografie = biografie;
    }
    public  String getBiografie()
    {
        return this.biografie;
    }

    public ArrayList<FilmPairNameType> getPerformanceActor() {
        return this.performancesActor;
    }

    public void setOnePerformance(FilmPairNameType performance) {
        this.performancesActor.add(performance);
    }
    public void setManyPerformances(ArrayList<FilmPairNameType> performances)
    {
        this.performancesActor = performances;
    }
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(actorName).append("\n");
        if(performancesActor != null)
        for (FilmPairNameType film : performancesActor) {
            result.append("Film Name: ").append(film.getName()).append("\n");
            result.append("Film Type: ").append(film.getType()).append("\n");

        }
        result.append("Biography: ").append(biografie).append("\n\n");
        return result.toString();
    }


    @Override
    public int compareTo(Object o) {
        if(o instanceof Actor) {
            Actor otherActor = (Actor) o;
            return this.actorName.compareTo(otherActor.getActorName());
        }
        else
            return 0;
    }
}
