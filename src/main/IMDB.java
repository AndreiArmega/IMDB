package main;

import GUI.CLIorGUI;
import ProductionTree.*;
import RequestTree.RequestTypes;
import Users.Admin;
import Users.Contributor;
import Users.Regular;
import RequestTree.Request;
import Users.User;
import org.json.simple.parser.ParseException;


import java.io.IOException;
import java.util.*;


public class IMDB {

    private static IMDB instance;
    ArrayList<Object> userType ;
    public ArrayList<Actor> actorList ;
    public ArrayList<Request> requestsList ;
    public ArrayList<Movie> movieList;
    public  ArrayList<Series> seriesList;
    public ArrayList<Regular> regulars;
    public ArrayList<Contributor> contributors;
    public ArrayList<Admin> admins;
    CLIorGUI frame ;
    public ArrayList<Actor> getActorList()
    {
        return this.actorList;
    }
    public ArrayList<Object> getProductionList()
    {
        ArrayList<Object> mergedlist = new ArrayList<>();
        mergedlist.addAll(this.movieList);
        mergedlist.addAll(this.seriesList);
        return mergedlist;
    }
    private IMDB() throws IOException, ParseException {
        setActors();
        setMoviesAndSeriesList();
        setUsers();
        setRequestsList();
        ArrayList<Object> usersmerged = new ArrayList<>();
        usersmerged.addAll(this.regulars);
        usersmerged.addAll(this.contributors);
        usersmerged.addAll(this.admins);
        this.userType = usersmerged;
        for(Object o : usersmerged)
        {
            if(o instanceof Regular)
            {
                Regular regular = (Regular) o;

                if(regular.favActString != null) {
                    SortedSet<Actor> actorFavorites = new TreeSet<>();

                    ArrayList<Actor> actorList = this.getActorList();
                    for (Actor actor : actorList) {
                        for (String actname : regular.favActString) {
                            if (actor.getActorName().equals(actname)) {
                                  actorFavorites.add(actor);
                            }}}
                    regular.actorFavorites = actorFavorites;
                }
                if(regular.favProdsString != null)
                {
                    ArrayList<Object> prodList = this.getProductionList();
                    ArrayList<Production> auxList = new ArrayList<>();
                    ArrayList<String> str = regular.favProdsString;
                    Collections.sort(str);
                    regular.favProdsString=str;


                    for(Object production : prodList)
                    {
                        Production production1;

                        for(String prodname: regular.favProdsString)
                        {

                            production1 = (Production) production;
                            if(production1.getProductionTitle().equals(prodname)){
                                auxList.add(production1);
                            }
                        }
                    }
                    regular.productionFavorites = auxList;
                }
            } else if (o instanceof Contributor)
            {
                Contributor contributor = (Contributor) o;
                if(contributor.favActString != null) {
                    SortedSet<Actor> actorFavorites = new TreeSet<>();
                    ArrayList<Actor> actorList = this.getActorList();
                    for (Actor actor : actorList) {
                        for (String actname : contributor.favActString) {
                            if (actor.getActorName().equals(actname)) {
                                actorFavorites.add(actor);
                            }}}contributor.actorFavorites = actorFavorites;}
                //------------------
                if(contributor.favProdsString != null)
                {
                    ArrayList<Object> prodList = this.getProductionList();
                    ArrayList<Production> auxList = new ArrayList<>();
                    ArrayList<String> str = contributor.favProdsString;
                    Collections.sort(str);
                    contributor.favProdsString=str;


                    for(Object production : prodList)
                    {
                        Production production1;

                        for(String prodname: contributor.favProdsString)
                        {

                            production1 = (Production) production;
                            if(production1.getProductionTitle().equals(prodname)){
                                auxList.add(production1);
                            }
                        }
                    }
                    contributor.productionFavorites = auxList;
                }
                //---------------
                ArrayList<Object> addedItems = new ArrayList<>();
                if(contributor.prodContr!=null) {
                    SortedSet<Production> productionSortedSet = new TreeSet<>();
                    ArrayList<Object> prodList = this.getProductionList();
                    for (Object production : prodList) {
                        for (String prodContrib : contributor.prodContr)
                        {if (production instanceof Movie) {
                                Movie movie = (Movie) production;
                                if (movie.getProductionTitle().equals(prodContrib)) {
                                    productionSortedSet.add(movie);
                                }} else if (production instanceof Series) {
                                Series series = (Series) production;
                                if (series.getProductionTitle().equals(prodContrib)) {
                                    productionSortedSet.add(series);
                                }}}}addedItems.addAll(productionSortedSet);}
                //--------------
                if(contributor.actorContr != null)
                {   SortedSet<Actor> actorsSortedSet = new TreeSet<>();
                    ArrayList<Actor> actorArrayList = this.getActorList();
                    for(Actor actor : actorArrayList)
                    {for(String actorContr : contributor.actorContr)
                        {if(actor.getActorName().equals(actorContr))
                            {actorsSortedSet.add(actor);
                            }}}addedItems.addAll(actorsSortedSet);
                }contributor.addedItems = addedItems;
                //---------------------------------------------------
            }else if (o instanceof Admin)
            {

                Admin admin = (Admin) o;
                if(admin.favActString != null) {
                    SortedSet<Actor> actorFavorites = new TreeSet<>();
                    ArrayList<Actor> actorList = this.getActorList();
                    for (Actor actor : actorList) {
                        for (String actname : admin.favActString) {
                            if (actor.getActorName().equals(actname)) {
                                actorFavorites.add(actor);
                            }}}admin.actorFavorites = actorFavorites;}
                //------------------
                if(admin.favProdsString != null)
                {ArrayList<Object> prodList = this.getProductionList();
                    ArrayList<Production> auxList = new ArrayList<>();
                    ArrayList<String> str = admin.favProdsString;
                    Collections.sort(str);
                    admin.favProdsString=str;


                    for(Object production : prodList)
                    {
                        Production production1;

                        for(String prodname: admin.favProdsString)
                        {

                            production1 = (Production) production;
                            if(production1.getProductionTitle().equals(prodname)){
                                auxList.add(production1);
                            }
                        }
                    }
                    admin.productionFavorites = auxList;
                }
                //---------------
                ArrayList<Object> addedItems = new ArrayList<>();
                if(admin.prodContr!=null) {
                    SortedSet<Production> productionSortedSet = new TreeSet<>();
                    ArrayList<Object> prodList = this.getProductionList();

                    for (Object production : prodList) {
                        for (String prodContrib : admin.prodContr) {
                            if (production instanceof Movie) {
                                Movie movie = (Movie) production;
                                if (movie.getProductionTitle().equals(prodContrib)) {

                                    addedItems.add(movie);
                                }
                            } else if (production instanceof Series) {
                                Series series = (Series) production;
                                if (series.getProductionTitle().equals(prodContrib)) {

                                    addedItems.add(series);
                                }
                            }
                        }
                    }
                }

                //--------------
                if(admin.actorContr != null)
                {   SortedSet<Actor> actorsSortedSet = new TreeSet<>();
                    ArrayList<Object> auxList = new ArrayList<>();

                    ArrayList<Actor> actorArrayList = this.getActorList();

                    for(Actor actor : actorArrayList)

                    {
                        for(String actorContr : admin.actorContr)

                    {
                        if(actor.getActorName().equals(actorContr))
                                          {addedItems.add(actor);}
                    }

                    }
                }admin.addedItems = addedItems;

            }
        }
    }

    public static synchronized IMDB getInstance() throws IOException, ParseException {
        if (instance == null) {
            instance = new IMDB();
        }
        return instance;
    }
    public ArrayList<User> getUsers()
    {
        ArrayList<User> users = new ArrayList<>();
        users.addAll(this.regulars);
        users.addAll(this.admins);
        users.addAll(this.contributors);
        return users;
    }
    public void run(){
        frame = new CLIorGUI();
    }

    private void setActors() throws IOException, ParseException {
        JsonReader obj = new JsonReader();
        String jsonFilePath = "C:\\Users\\Armega\\Desktop\\java\\imdbtema\\IMDB\\src\\inputjson\\actors.json";
        List<Actor> actors = obj.parseActors(jsonFilePath);
        this.actorList = (ArrayList<Actor>) actors;
    }
    private void setMoviesAndSeriesList() throws IOException,ParseException{
        JsonReader obj = new JsonReader();
        String jsonFilePath = "C:\\Users\\Armega\\Desktop\\java\\imdbtema\\IMDB\\src\\inputjson\\production.json";
        ArrayList<ArrayList> info = obj.productionReader(jsonFilePath);
        ArrayList<Movie> movieList = info.get(0);
        ArrayList<Series> seriesList = info.get(1);
        this.movieList = movieList;
        this.seriesList = seriesList;

    }
    private void setUsers()throws IOException,ParseException {
        JsonReader obj = new JsonReader();
        String jsonFilePath = "C:\\Users\\Armega\\Desktop\\java\\imdbtema\\IMDB\\src\\inputjson\\accounts.json";
          ArrayList<ArrayList> info = obj.parseAccounts(jsonFilePath);
           this.regulars = (ArrayList<Regular>) info.get(0);
           this.admins = (ArrayList<Admin>) info.get(1);
           this.contributors= (ArrayList<Contributor>) info.get(2);

    }
    private void setRequestsList() throws IOException,ParseException {
        JsonReader obj = new JsonReader();
        String jsonFilePath = "C:\\Users\\Armega\\Desktop\\java\\imdbtema\\IMDB\\src\\inputjson\\requests.json";
        ArrayList<Request> reqList = obj.requestParser(jsonFilePath);
        this.requestsList = reqList;

        for(Request req : reqList)
        {
            if(req.getRequestingUser().equals("ADMIN"))
            {
                Request.RequestHolder.addRequest(req);
            }else {
                ArrayList<User> users = this.getUsers();
                for(User user : users)
                {

                    if(req.getRequestingUser().equals(user.username))
                    {
                        if(user instanceof Contributor)
                        {

                            Contributor contributor = (Contributor) user;
                            if(contributor.assignedRequests == null)
                                contributor.assignedRequests = new ArrayList<>();
                            contributor.addReq(req);
                        }
                        else if(user instanceof  Admin)
                        {
                            Admin admin = (Admin) user;
                            if(admin.assignedRequests ==null)
                                admin.assignedRequests= new ArrayList<>();
                            admin.addReq(req);
                        }
                    }
                }

            }
        }

    }

}
