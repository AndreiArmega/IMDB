package Users;

import ProductionTree.*;
import RequestTree.Request;
import main.IMDB;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.IOException;
import java.util.*;

public class Staff extends User implements StaffInterface {


    public List<Request> assignedRequests;
    public ArrayList<String> prodContr;
    public ArrayList<String> actorContr;

    public ArrayList<Object> addedItems;



    public void addFavorite(Object item) {

    }
public void InitializeReq()
{
 this.assignedRequests = new ArrayList<>();
}
public List<Request> getAssignedRequests()
{
 return  this.assignedRequests;
}
 public  void addReq(Request r){
        this.assignedRequests.add(r);
   }
    @Override
    public void removeFavorite(Object item) {

    }

    @Override
    public void updateExperience(int experience) {

    }

    public void denyRequest(Request request)
    {
        this.assignedRequests.remove(request);

    }
    public void removeRequest(Request request) {
        this.assignedRequests.remove(request);
    }
    @Override
    public void addProductionSystem(String type) throws IOException, ParseException {


        Scanner scanner = new Scanner(System.in);
        int ok=0;
        System.out.println("Type production title:");
        String title = scanner.nextLine();
        System.out.println("Type director names (comma-separated):");
        String directorNamesInput = scanner.nextLine();
        ArrayList<String> directorNames = new ArrayList<>(Arrays.asList(directorNamesInput.split(",\\s*")));

        System.out.println("Type actor names (comma-separated):");
        String actorListInput = scanner.nextLine();
        ArrayList<String> actorList = new ArrayList<>(Arrays.asList(actorListInput.split(",\\s*")));

        System.out.println("Type genres (comma-separated):");
        String genresInput = scanner.nextLine();
        ArrayList<Genre> genres = new ArrayList<>();
        for (String genreName : genresInput.split(",\\s*")) {
            genres.add(Genre.valueOf(genreName.toUpperCase()));
        }

        System.out.println("Type plot:");
        String plot = scanner.nextLine();

        System.out.println("Type average rating:");
        double ratingAvg = scanner.nextDouble();
        Production newProduction = new Movie();

        newProduction.setProductionTitle(title);
        newProduction.setProddirectorNames(directorNames);
        newProduction.setProdActorList(actorList);
        newProduction.setGenres(genres);
        newProduction.setPlot(plot);
        newProduction.ratingAvg = ratingAvg;
        if(type.equalsIgnoreCase("Movie"))
        {
            Movie movie = (Movie) newProduction;
            System.out.println("Type release year");
            movie.setReleaseYear(scanner.nextLong());
            System.out.println("Type duration");
            movie.setDuration(scanner.nextLine());
            IMDB.getInstance().movieList.add(movie);
            System.out.println("Production added to the system.");
            ok=1;
        } else if (type.equalsIgnoreCase("Series")) {
            Series series = (Series) newProduction;
            System.out.println("Type release year");
            series.setReleaseYear(scanner.nextLong());
            System.out.println("Type nr of seasons");
            series.setNrOfSeasons(scanner.nextInt());
            IMDB.getInstance().seriesList.add(series);
            System.out.println("Production added to the system.");
            ok=1;

        }
         if(ok ==0) System.out.println("Couldn't add prod to system");

    }

    @Override
    public void addActorSystem() throws IOException, ParseException {
        Scanner scanner = new Scanner(System.in);
        ArrayList<FilmPairNameType> roles = new ArrayList<>();
        System.out.println("Type actor name");
        String actorName  = scanner.nextLine();
        System.out.println("Type biography");
        String biography = scanner.nextLine();
        while(true) {
            System.out.println("Type production name:");
            String prodName = scanner.nextLine();
            System.out.println("What is the type of the previous production?");
            String type = scanner.nextLine();
            FilmPairNameType pr = new FilmPairNameType(prodName, type);
            roles.add(pr);
            System.out.println("Add another role? (yes/no)");
            String ch = scanner.nextLine();
            if (ch.equalsIgnoreCase("no"))
                break ;

        }
        Actor newactor = new Actor(actorName,roles,biography);
        IMDB imdb = IMDB.getInstance();
        imdb.actorList.add(newactor);
    }

    @Override
    public void removeProductionSystem(String name, User user) throws IOException, ParseException {
        IMDB imdb = IMDB.getInstance();
        int ok=0;
        Contributor contributor = (Contributor) user;

        for(Object production : imdb.getProductionList())
        {
            Production production1 = (Production) production;
            if(production1.getProductionTitle().equalsIgnoreCase(name)&& contributor.prodContr!=null )
            {
                for(String prodContr : contributor.prodContr)
                {
                    if(production1.getProductionTitle().equalsIgnoreCase(prodContr))
                    {
                        ok=1;
                        if(production1 instanceof  Movie)
                            imdb.movieList.remove(production1);
                        else if(production1 instanceof Series)
                            imdb.seriesList.remove(production1);
                        System.out.println(name + " has been deleted");
                    }
                }
            }
        }
        if(ok ==0 )
        {
            System.out.println("Unable to delete");
        }
    }

    @Override
    public void removeActorSystem(String name, User user) throws IOException, ParseException {

        Contributor contributor = (Contributor) user;

        IMDB imdb = IMDB.getInstance();
        int ok=0;
        for(Actor actor : imdb.actorList)
        {
            if(actor.getActorName().equalsIgnoreCase(name) && contributor.actorContr!=null)
            {
                for(String actor1 : contributor.actorContr)
                {
                    if(actor.getActorName().equalsIgnoreCase(actor1))
                    {
                        imdb.actorList.remove(actor);
                        ok=1;
                    }
                }
            }
        }
        if(ok ==0 )
        {
            System.out.println("Unable to delete");
        }else
        {System.out.println(name + " has been deleted");}
    }

    @Override
    public void updateProduction(JButton solveMovieIssue) {


    }

    @Override
    public  void updateActor(Actor actor) {



    }



}
