package Miscellaneous;

import Users.Admin;
import Users.Contributor;
import Users.Regular;
import Users.User;
import main.IMDB;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NotificationService implements Subject {
    private List<Observer> regularObservers = new ArrayList<>();
    private List<Observer> contributorObservers = new ArrayList<>();
    private List<Observer> adminObservers = new ArrayList<>();

    @Override
    public void addObserver(Observer observer) {
        if (observer instanceof Regular) {
            regularObservers.add(observer);
        } else if (observer instanceof Contributor) {
            contributorObservers.add(observer);
        } else if (observer instanceof Admin) {
            adminObservers.add(observer);
        }
    }
   public void addAllObservers() throws IOException, ParseException {
       for( User observer : IMDB.getInstance().getUsers())
       {
           addObserver((Observer) observer);
       }
   }
    @Override
    public void removeObserver(Observer observer) {
        regularObservers.remove(observer);
        contributorObservers.remove(observer);
        adminObservers.remove(observer);
    }

    public static void notifyObserver(String not, User observer)
    {

            if (observer instanceof Regular) {
                Regular regular = (Regular) observer;
                regular.notifications.add(not);
            }
       else
            if (observer instanceof Contributor) {
                Contributor contributor = (Contributor) observer;
                contributor.notifications.add(not);
            }
        else
            if (observer instanceof Admin) {
                Admin admin = (Admin ) observer;
                admin.notifications.add(not);
            }

    }
    @Override
    public void notifyObservers(String notification) {

        for(Observer observer : this.regularObservers)
        {
            if (observer instanceof Regular) {
                Regular regular = (Regular) observer;
                regular.notifications.add(notification);
            }
        }
        for(Observer observer : this.contributorObservers)
        {
            if (observer instanceof Contributor) {
                Contributor contributor = (Contributor) observer;
                contributor.notifications.add(notification);
            }
        }
        for(Observer observer : this.adminObservers)
        {
            if (observer instanceof Admin) {
                Admin admin = (Admin ) observer;
                admin.notifications.add(notification);
            }
        }
    }


}
