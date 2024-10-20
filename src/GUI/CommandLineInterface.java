package GUI;

import ProductionTree.*;
import RequestTree.Request;
import Users.*;
import main.IMDB;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class CommandLineInterface {

    public void start() throws IOException, ParseException {
        Scanner scanner = new Scanner(System.in);
  mainMenu:
        while (true) {
            printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.println("Welcome back , enter your credentials\n");
                    System.out.println("Please enter email");
                    String mail = scanner.nextLine();
                    System.out.println("Please enter password");
                    String password = scanner.nextLine();
                    IMDB imdb = getInstance();
                     Credentials credentials= new Credentials.Builder(mail,password).build();
                   for(User user : imdb.getUsers())
                   {

                       if(user.getCredentials().getEmail().equals(mail)&&user.getCredentials().getPassword().equals(password))
                       {
                          System.out.println("Welcome back user " + user.getUsername() + "!");
                           System.out.println("Username " + user.getUsername());
                           System.out.println("User experience:" + user.getUserExperience());

                           boolean loggedInFlag = true;

                           while (loggedInFlag) {
                               printLoggedInMenu();
                               System.out.println("Choose action:");
                               int choice2;
                               try {
                                   choice2 = scanner.nextInt();
                                   scanner.nextLine();
                               } catch (java.util.InputMismatchException e) {
                                   System.out.println("Invalid input. Please enter a number.");
                                   scanner.nextLine(); // Clear the invalid input
                                   continue;
                               }

                               switch ((choice2))
                               {
                                   case 1:
                                       for(Object production:imdb.getProductionList()) {
                                           Production production1 = (Production) production;
                                           System.out.println(production1.productionTitle);
                                           System.out.println(production1.proddirectorNames);
                                           System.out.println(production1.prodActorList);
                                           System.out.println(production1.prodGenreList);
                                           for (Rating rating : production1.prodRatingList) {
                                               System.out.print(rating.displayRating() + " ");
                                           }
                                           System.out.println(production1.plot);
                                           System.out.println(production1.ratingAvg);
                                           if (production1 instanceof Movie) {
                                               Movie movie = (Movie) production1;
                                               System.out.println(movie.releaseYear);
                                               System.out.println(movie.duration + "\n");
                                           } else if (production1 instanceof Series) {
                                               Series series = (Series) production1;
                                               System.out.println(series.nrOfSeasons);
                                               if (series.episoadePeSezoane != null && !series.episoadePeSezoane.isEmpty()) {
                                                   series.episoadePeSezoane.forEach((season, episodes) -> {

                                                       System.out.println(season + ": ");
                                                       for (Episode episode : episodes) {

                                                           System.out.println(episode.displayInfo() + "\n");
                                                       }
                                                       System.out.println("\n");
                                                   });
                                               }
                                               System.out.println("\n\n");
                                           }
                                       }
                                       break;
                                   case 2:
                                         for(Actor actor : imdb.getActorList())
                                         {
                                             System.out.println(actor.toString());

                                         }
                                       break;
                                   case 3:
                                          for(String notification : user.getNotifications())
                                          {
                                              System.out.println(notification);
                                          }
                                       break;

                                   case 4:
                                       System.out.println("Please enter the name of the actor you want to search");
                                      String actorName =  scanner.nextLine();
                                      int ok=0;
                                       for (Actor actor : imdb.getActorList()) {
                                           if (actor.getActorName().equals(actorName)) {
                                               ok=1;
                                               System.out.println(actor.toString());

                                           }
                                       }
                                       if(ok == 0)
                                           System.out.println("No such actor has been found");
                                       break;

                                   case 5:
                                       print5Menu();
                                       System.out.println("Please select action:");
                                       int choice3 = scanner.nextInt();
                                       scanner.nextLine();

                                       switch (choice3) {
                                           case 1:
                                               // Add actor to favorites
                                               System.out.println("Please enter the name of the actor you want to add to favorites");
                                               String actorNameToAdd = scanner.nextLine();
                                               ok = 0;
                                               Iterator<Actor> actorIterator = imdb.getActorList().iterator();
                                               while (actorIterator.hasNext()) {
                                                   Actor actor = actorIterator.next();
                                                   if (actor.getActorName().equals(actorNameToAdd)) {
                                                       ok = 1;
                                                       user.actorFavorites.add(actor);
                                                       System.out.println(actor.getActorName() + " has been added to your favorites");
                                                       break;
                                                   }
                                               }
                                               if (ok == 0)
                                                   System.out.println("No such actor has been found");
                                               break;

                                           case 2:
                                               // Add movie to favorites
                                               System.out.println("Please enter the name of the movie you want to add to favorites:");
                                               String movieNameToAdd = scanner.nextLine();
                                               ok = 0;
                                               Iterator<Movie> movieIterator = imdb.movieList.iterator();
                                               while (movieIterator.hasNext()) {
                                                   Movie movie = movieIterator.next();
                                                   if (movie.getProductionTitle().equals(movieNameToAdd)) {
                                                       ok = 1;
                                                       user.addProductionFavorite(movie);
                                                       System.out.println(movie.getProductionTitle() + " has been added to your favorites");
                                                       break;
                                                   }
                                               }
                                               if (ok == 0)
                                                   System.out.println("No such movie has been found");
                                               break;

                                           case 3:
                                               // Add series to favorites
                                               System.out.println("Please enter the name of the series you want to add to favorites:");
                                               String seriesNameToAdd = scanner.nextLine();
                                               ok = 0;
                                               Iterator<Series> seriesIterator = imdb.seriesList.iterator();
                                               while (seriesIterator.hasNext()) {
                                                   Series series = seriesIterator.next();
                                                   if (series.getProductionTitle().equals(seriesNameToAdd)) {
                                                       ok = 1;
                                                       user.addProductionFavorite(series);
                                                       System.out.println(series.getProductionTitle() + " has been added to your favorites");
                                                       break;
                                                   }
                                               }
                                               if (ok == 0)
                                                   System.out.println("No such series has been found");
                                               break;

                                           case 4:
                                               // Delete actor from favorites
                                               System.out.println("Please enter the name of the actor you want to remove from favorites");
                                               String actorNameToRemove = scanner.nextLine();
                                               Iterator<Actor> actorIteratorToRemove = user.actorFavorites.iterator();
                                               while (actorIteratorToRemove.hasNext()) {
                                                   Actor actor = actorIteratorToRemove.next();
                                                   if (actor.getActorName().equals(actorNameToRemove)) {
                                                       actorIteratorToRemove.remove();
                                                       System.out.println(actorNameToRemove + " has been removed from your favorites");
                                                       break;
                                                   }
                                               }
                                               break;

                                           case 5:
                                               // Delete movie from favorites
                                               System.out.println("Please enter the name of the movie you want to remove from favorites");
                                               String movieNameToRemove = scanner.nextLine();
                                               Iterator<Production> movieIteratorToRemove = user.productionFavorites.iterator();
                                               while (movieIteratorToRemove.hasNext()) {
                                                   Production movie = movieIteratorToRemove.next();
                                                   if (movie.getProductionTitle().equals(movieNameToRemove)) {
                                                       movieIteratorToRemove.remove();
                                                       System.out.println(movieNameToRemove + " has been removed from your favorites");
                                                       break;
                                                   }
                                               }
                                               break;

                                           case 6:
                                               // Delete series from favorites
                                               System.out.println("Please enter the name of the series you want to remove from favorites");
                                               String seriesNameToRemove = scanner.nextLine();
                                               Iterator<Production> seriesIteratorToRemove = user.productionFavorites.iterator();
                                               while (seriesIteratorToRemove.hasNext()) {
                                                   Production series = seriesIteratorToRemove.next();
                                                   if (series.getProductionTitle().equals(seriesNameToRemove)) {
                                                       seriesIteratorToRemove.remove();
                                                       System.out.println(seriesNameToRemove + " has been removed from your favorites");
                                                       break;
                                                   }
                                               }
                                               break;
                                       }
                                       break;

                                   case 6:
                                       if(user instanceof Admin || user instanceof Contributor) {
                                           System.out.println("1) Add user :");
                                           System.out.println("2) Delete user");
                                           System.out.println("Please select action:");
                                           int choice4 = scanner.nextInt();
                                           scanner.nextLine();
                                           switch (choice4) {
                                               case 2:
                                                   System.out.println("Type the user you wnat to delete");
                                                   String username = scanner.nextLine();
                                                   for (User user1 : imdb.getUsers()) {
                                                       if (user1.getUsername().equals(username)) {
                                                           Admin admin = (Admin) UserFactory.createUser(AccountType.ADMIN);
                                                           admin.removeUser(user1);
                                                       }
                                                   }
                                                   break;
                                               case 1:
                                                   Admin admin = (Admin) UserFactory.createUser(AccountType.ADMIN);
                                                   admin.addUser();

                                                   break;
                                           }
                                       }else
                                       {
                                           System.out.println("You dont have permissiopn to do this");
                                       }
                                       break;

                                   case 7:
                                       if(user instanceof Admin || user instanceof Contributor) {
                                           print7Menu();
                                           System.out.println("Please select action:");
                                           int choice5 = scanner.nextInt();
                                           scanner.nextLine();
                                           Admin admin = (Admin) UserFactory.createUser(AccountType.ADMIN);

                                           switch (choice5) {
                                               case 2:
                                                   admin.addActorSystem();
                                                   break;
                                               case 1:
                                                   System.out.println("type actor name to delete");
                                                   String nam = scanner.nextLine();
                                                   admin.removeActorSystem(nam,user);
                                                   break;
                                               case 3:
                                                   System.out.println("type prod name to delete");
                                                   String prname = scanner.nextLine();
                                                   admin.removeProductionSystem(prname,  user);
                                                   break;
                                               case 4:
                                                   System.out.println("What type of prod to add?(Movie/Series)");
                                                   AccountType type = AccountType.valueOf(scanner.nextLine());
                                                   admin.addProductionSystem(String.valueOf(type));
                                                   break;
                                           }
                                       }else System.out.println("you cant do this");
                                       break;

                                   case 8:
                                       if(!(user instanceof Regular)) {
                                           System.out.println("what production would you like to update");
                                           String name = scanner.nextLine();
                                           for (Object production : imdb.getProductionList()) {
                                               Production prod = (Production) production;
                                               if (prod.getProductionTitle().equals(name)) {
                                                   System.out.println("What field would you like to update");
                                                   String option = scanner.nextLine();
                                                   if (option.equalsIgnoreCase("title")) {
                                                       System.out.println("Choose new title");
                                                       String newtitle = scanner.nextLine();
                                                       prod.setProductionTitle(newtitle);
                                                   } else if (option.equalsIgnoreCase("plot")) {
                                                       System.out.println("Choose new plot");
                                                       String newplot = scanner.nextLine();
                                                       prod.setProductionTitle(newplot);
                                                   } else if (option.equalsIgnoreCase("duration")) {
                                                       Movie movie = (Movie) prod;
                                                       String duration = scanner.nextLine();
                                                       movie.setDuration(duration);

                                                   } else if (option.equalsIgnoreCase("release")) {
                                                       System.out.println("New release year");
                                                       String release = scanner.nextLine();
                                                       if (prod instanceof Movie) {
                                                           Movie movie = (Movie) prod;
                                                           movie.setReleaseYear(Long.valueOf(release));
                                                       } else if (prod instanceof Series) {
                                                           Series series = (Series) prod;
                                                           series.setReleaseYear(Long.valueOf(release));
                                                       }

                                                   } else if (option.equalsIgnoreCase("nr of seasons")) {
                                                       Series series = (Series) prod;
                                                       System.out.println("enter new nr of seasons");
                                                       int value = scanner.nextInt();
                                                       series.setNrOfSeasons(value);
                                                   }
                                                   System.out.println("Done!");
                                               }
                                               System.out.println("Couldn't update");
                                           }
                                       } else {
                                           System.out.println("You dont have access");

                                       }
                                       break;

                                   case 9:
                                       if(!(user instanceof Regular)) {
                                           System.out.println("What actor would you like to update");
                                           String actname = scanner.nextLine();
                                           for (Actor actor : imdb.getActorList()) {
                                               if (actor.getActorName().equals(actname)) {
                                                   System.out.println("What would you like to change?");
                                                   String cghoiceee = scanner.nextLine();
                                                   if (cghoiceee.equalsIgnoreCase("actor name")) {
                                                       System.out.println("New actor name:");

                                                       String actnam = scanner.nextLine();
                                                       actor.setActorName(actnam);
                                                   } else if (cghoiceee.equalsIgnoreCase("biography")) {
                                                       System.out.println("New biography");
                                                       String bio = scanner.nextLine();
                                                       actor.setBiografie(bio);
                                                   }
                                                   System.out.println("Done!");
                                               }
                                               System.out.println("Couldn't update ");
                                           }
                                       }else
                                       {
                                           System.out.println("You dont have access");
                                       }
                                       break;

                                   case 10:
                                         if(!(user instanceof Regular))
                                         {
                                            if(user instanceof Contributor)
                                            {
                                                Contributor contributor = (Contributor) user;
                                                  if(contributor.assignedRequests!=null) {
                                                      for (Request request : contributor.assignedRequests) {

                                                          System.out.println(request.displayInfo());
                                                      }
                                                      System.out.println("Solve/Deny?");
                                                      String action = scanner.nextLine();
                                                      if (action.equalsIgnoreCase("solve")) {
                                                          System.out.println("Which request ? (number)");
                                                          int nr = scanner.nextInt();
                                                          contributor.removeRequest(contributor.assignedRequests.get(nr));
                                                      } else if (action.equalsIgnoreCase("deny")) {
                                                          System.out.println("Which request ? (number)");
                                                          int nr = scanner.nextInt();
                                                          contributor.denyRequest(contributor.assignedRequests.get(nr));
                                                      }
                                                  }
                                            } else if (user instanceof Admin) {
                                                Admin contributor = (Admin) user;
                                                if(contributor.assignedRequests!=null) {
                                                    for (Request request : contributor.assignedRequests) {

                                                        System.out.println(request.displayInfo());
                                                    }
                                                    System.out.println("Solve/Deny?");
                                                    String action = scanner.nextLine();
                                                    if (action.equalsIgnoreCase("solve")) {
                                                        System.out.println("Which request ? (number)");
                                                        int nr = scanner.nextInt();
                                                        contributor.removeRequest(contributor.assignedRequests.get(nr));
                                                    } else if (action.equalsIgnoreCase("deny")) {
                                                        System.out.println("Which request ? (number)");
                                                        int nr = scanner.nextInt();
                                                        contributor.denyRequest(contributor.assignedRequests.get(nr));
                                                    }
                                                }
                                            }
                                         }
                                       break;
                                   case 11:
                                       loggedInFlag = false;
                                       break;

                                   case 12:
                                       for(Actor actor : user.actorFavorites)
                                       {
                                           System.out.println(actor);

                                       }
                                       break;
                                   case 13:
                                       for(Production production1 : user.productionFavorites)
                                       {
                                           System.out.println(production1.productionTitle);
                                           System.out.println(production1.proddirectorNames);
                                           System.out.println(production1.prodActorList);
                                           System.out.println(production1.prodGenreList);
                                           for (Rating rating : production1.prodRatingList) {
                                               System.out.print(rating.displayRating() + " ");
                                           }
                                           System.out.println(production1.plot);
                                           System.out.println(production1.ratingAvg);
                                           if (production1 instanceof Movie) {
                                               Movie movie = (Movie) production1;
                                               System.out.println(movie.releaseYear);
                                               System.out.println(movie.duration + "\n");
                                           } else if (production1 instanceof Series) {
                                               Series series = (Series) production1;
                                               System.out.println(series.nrOfSeasons);
                                               if (series.episoadePeSezoane != null && !series.episoadePeSezoane.isEmpty()) {
                                                   series.episoadePeSezoane.forEach((season, episodes) -> {

                                                       System.out.println(season + ": ");
                                                       for (Episode episode : episodes) {

                                                           System.out.println(episode.displayInfo() + "\n");
                                                       }
                                                       System.out.println("\n");
                                                   });
                                               }
                                               System.out.println("\n\n");
                                           }
                                       }
                                       break;

                               }
                           }
                       }
                   }

                    break;
                case 2:
                    System.out.println("You selected Option 2");
                    break;
                case 3:
                    System.out.println("Exiting the program. Goodbye!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    public static IMDB getInstance()
    {
        try {
            IMDB imdb = IMDB.getInstance();
            return imdb;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
    private static void printMenu() {
        System.out.println("-------- Menu --------");
        System.out.println("1. Login");
        System.out.println("2. Exit");
        System.out.print("Enter your choice: ");
    }
    private static void printLoggedInMenu()
    {
        System.out.println("1) View production details");
        System.out.println("2) View actor details");
        System.out.println("3) View notifications");
        System.out.println("4) Search for actor/movie/series");
        System.out.println("5) Add/Delete actor/movie/series to/from favorites");
        System.out.println("6) Add/Delete user");
        System.out.println("7) Add/Delete actor/movie/series");
        System.out.println("8) Update Production Details");
        System.out.println("9) Update Actor Details");
        System.out.println("10) Solve a request");
        System.out.println("11) Logout");
        System.out.println("12) Show actor favorites");
        System.out.println("13) Show Production favorites");

    }
    private static  void print5Menu()
    {
        System.out.println("1) Add actor to favorites");
        System.out.println("2) Add movie to favorites");
        System.out.println("3) Add series to favorites");
        System.out.println("4) Delete actor from favorites");
        System.out.println("5) Delete movie from favorites");
        System.out.println("6) Delete series from favorites");


    }
    private static void print7Menu()
    {
        System.out.println("1) Delete actor");
        System.out.println("2) Add actor");
        System.out.println("3) Delete production");
        System.out.println("4) Add production");


    }

}