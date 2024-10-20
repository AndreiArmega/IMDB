package ProductionTree;

import RequestTree.Request;
import RequestTree.RequestTypes;
import Users.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileReader;
import java.io.IOException;
import Users.AccountType;
public class JsonReader {
    @SuppressWarnings("unchecked")
    public List<Actor> parseActors(String jsonFilePath) throws IOException, ParseException {
        List<Actor> actors = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(jsonFilePath)) {

            Object obj = jsonParser.parse(reader);
            JSONArray actorlist =(JSONArray) obj;
            //ala mare
          for(Object actorObj :  actorlist)
            {
                JSONObject actorJson = (JSONObject) actorObj;
                String actorName = (String) actorJson.get("name");
                String picture = null;
                if(actorJson.get("picture")!=null)
                {
                    picture = (String) actorJson.get("picture");
                }
                JSONArray ActorPerf = (JSONArray) actorJson.get("performances");
                ArrayList<FilmPairNameType> actorPerformances = new ArrayList<FilmPairNameType>();
               for(Object performanceObj: ActorPerf)
               {
                   JSONObject actorPerformance = (JSONObject) performanceObj;
                   String prodName  = (String) actorPerformance.get("title");
                   String prodType = (String) actorPerformance.get("type");
                   FilmPairNameType pair = new FilmPairNameType(prodName,prodType);
                   actorPerformances.add(pair);

               }
               String biografie = (String)  actorJson.get("biography");
               Actor actor = new Actor(actorName,actorPerformances,biografie);
              actor.setPicture(picture);
                actors.add(actor);
            }


        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return actors;
    }
    public ArrayList<ArrayList> productionReader (String jsonFilePath)throws IOException, ParseException{
       // ArrayList<Production> productions = new ArrayList<Production>();
        JSONParser jsonParser = new JSONParser();
        ArrayList<ArrayList> lists = new ArrayList<ArrayList>();
        try (FileReader reader = new FileReader(jsonFilePath)) {
            ArrayList<Movie> movieList = new ArrayList<>();
            ArrayList<Series> seriesList = new ArrayList<>();
            Object obj = jsonParser.parse(reader);
            JSONArray productionList = (JSONArray) obj;
            for(Object prodobj : productionList)
            {
                JSONObject prodJson = (JSONObject) prodobj;
                String productionName = (String) prodJson.get("title");
                String prodType = (String) prodJson.get("type");

                ArrayList<String> directorsList = new ArrayList<>();
                ArrayList<String> actorList = new ArrayList<>();
                ArrayList<Genre> genreList = new ArrayList<>();
                ArrayList<Rating> ratingList = new ArrayList<>();
                 int avg=0;
                JSONArray directors = (JSONArray) prodJson.get("directors");
                  directors.toArray();
                for(int i =0 ; i<directors.toArray().length;i++)
                {
                   String dirname = (String) directors.get(i);
                   directorsList.add(dirname);
                }

                JSONArray actors = (JSONArray) prodJson.get("actors");
                 actors.toArray();
                for(int i=0; i<actors.toArray().length;i++)
                {
                    String actorName = (String) actors.get(i);
                    actorList.add(actorName);
                }
                JSONArray genres = (JSONArray) prodJson.get("genres");
                genres.toArray();

                for(int i =0 ; i < genres.toArray().length;i++)
                {
                    String aux = (String) genres.get(i);
                    for(Genre genre : Genre.values() )
                    {
                        if(aux.toUpperCase().equals(genre.toString()))
                        {

                            genreList.add(genre);
                        }
                    }

                }
                JSONArray ratings = (JSONArray) prodJson.get("ratings");
                int nrRatings = ratings.toArray().length;
                for (Object rating : ratings)
                {
                        JSONObject rat1 = (JSONObject) rating;

                        String userName = (String) rat1.get("username");
                        long number = (Long) rat1.get("rating");
                        int nr = (int) number;

                        avg+=number;
                        String comment = (String)  rat1.get("comment");
                        Rating newRating = new Rating(userName,nr,comment);
                        ratingList.add(newRating);
                }
                double averageRating = avg/nrRatings;
                String plot = (String) prodJson.get("plot"); //***
                Long releaseYear;
               if(prodJson.get("releaseYear") == null) {
                    releaseYear = null;
               }else
               {
                 releaseYear = (Long) prodJson.get("releaseYear");
               }
                Long nrSez;


                if(prodJson.get("type").equals("Movie"))
                {
                  String duration = (String) prodJson.get("duration");
                  Movie movie = new Movie();
                  movie.productionTitle = productionName;
                  movie.proddirectorNames= directorsList;
                  movie.prodActorList=actorList;
                  movie.prodGenreList= genreList;
                  movie.prodRatingList=ratingList;
                  movie.plot=plot;
                  movie.ratingAvg=averageRating;
                  movie.duration=duration;
                  movie.releaseYear=releaseYear;

                  movieList.add(movie);
                } else
                if(prodJson.get("type").equals("Series"))
                {
                    Map<String, List<Episode>> episodesBySeason = new HashMap<>();
                         nrSez = (Long) prodJson.get("numSeasons");
                         int numSeasons =  nrSez.intValue();
                     JSONObject seasons =(JSONObject) prodJson.get("seasons");
                    for(int i=1 ; i <= numSeasons ; i++)
                    {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Season ");
                        String sesnr = Integer.toString(i);
                        sb.append(sesnr);

                        JSONArray oneSeason = (JSONArray) seasons.get(sb.toString());

                        ArrayList<Episode> episodeForOneSeason = new ArrayList<>();


                        for(Object episod : oneSeason)
                        {
                            JSONObject episode = (JSONObject) episod;
                            String epDur = (String) episode.get("duration");
                            String epName = (String) episode.get("episodeName");

                            Episode newEpisode = new Episode(epName,epDur);
                            episodeForOneSeason.add(newEpisode);


                        }

                        episodesBySeason.put(sb.toString(),episodeForOneSeason);

                    }
                   Series series = new Series();
                    series.productionTitle = productionName;
                    series.proddirectorNames= directorsList;
                    series.prodActorList=actorList;
                    series.prodGenreList= genreList;
                    series.prodRatingList=ratingList;
                    series.plot=plot;
                    series.ratingAvg=averageRating;
                    series.releaseYear=releaseYear;
                    series.nrOfSeasons=numSeasons;
                    series.episoadePeSezoane=episodesBySeason;

                    seriesList.add(series);
                }

            }

            lists.add(movieList);
            lists.add(seriesList);
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lists;
    }
  public ArrayList<ArrayList> parseAccounts(String jsonFilePath)
  {
      JSONParser parser = new JSONParser();
      ArrayList<ArrayList> info = new ArrayList<ArrayList>();
      ArrayList<Regular> regulars = new ArrayList<>();
      ArrayList<Admin> admins = new ArrayList<>();
      ArrayList<Contributor> contributors = new ArrayList<>();
      try (FileReader reader = new FileReader(jsonFilePath)) {

          Object obj = parser.parse(reader);

          JSONArray userList = (JSONArray) obj;
          for (Object object : userList)
          {

          JSONObject userJson = (JSONObject) object;

          String userName = (String) userJson.get("username");
          String experience = (String) userJson.get("experience");
          Integer exp;
          if(experience != null) {exp = Integer.parseInt(experience);}
          else {exp = 0;
          }
          JSONObject infoArray = (JSONObject) userJson.get("information");


          String realname = (String) infoArray.get("name");

          String country = (String) infoArray.get("country");
          Long age = (Long) infoArray.get("age");
          int Age = age.intValue();
          String gender = (String) infoArray.get("gender");
          char letter = gender.charAt(0);

          String birth = (String) infoArray.get("birthDate");
              DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
              LocalDate dateTime = LocalDate.parse(birth, formatter);

          JSONObject credent = (JSONObject) infoArray.get("credentials");

          String email = (String) credent.get("email");
          String pass = (String) credent.get("password");
          String type = (String) userJson.get("userType");
          ArrayList<String> productionComntributions = new ArrayList<>();
          ArrayList<String> actorContributions = new ArrayList<>();
              ArrayList<String> favoriteProductions= new ArrayList<>()  ;
              ArrayList<String> favoriteActors = new ArrayList<>();
              ArrayList<String> notifications = new ArrayList<>();
              if(userJson.get("favoriteProductions")!= null)
              {
                  JSONArray favProd = (JSONArray) userJson.get("favoriteProductions");
                  favProd.toArray();
                  for(int i =0 ; i< favProd.toArray().length;i++)
                  {

                      favoriteProductions.add( (String) favProd.get(i));
                  }

              }
              else {favoriteProductions = null;}
              if(userJson.get("favoriteActors")!=null)
              {
                  JSONArray favActors = (JSONArray) userJson.get("favoriteActors");
                  favActors.toArray();
                  for(int i=0 ; i < favActors.toArray().length;i++)
                  {
                      favoriteActors.add((String) favActors.get(i));
                  }

              }else
              {
                  favoriteActors = null;
              }
              if(userJson.get("notifications")!=null) {
                  JSONArray notif = (JSONArray) userJson.get("notifications");

                  notif.toArray();
                  for (int i = 0; i < notif.toArray().length; i++) {
                      notifications.add((String) notif.get(i));
                  }
              } else {notifications = null;}

              Credentials.Builder builder= new Credentials.Builder(email,pass);
              Credentials credentials= builder.build();
              User.Information.Builder builder1 = new User.Information.Builder(credentials);
              User.Information information = new User.Information(builder1);
          if(type.toUpperCase().equals("REGULAR"))
          {
             Regular regular = (Regular) UserFactory.createUser(AccountType.REGULAR);
             regular.setInfo(information);
              regular.username = userName;
              regular.userExperience=exp;
              regular.favActString =  favoriteActors;
              regular.favProdsString=  favoriteProductions;
              regular.notifications=notifications;
              regulars.add(regular);
          }else
              if(type.toUpperCase().equals("CONTRIBUTOR")){
                  if(userJson.get("productionsContribution")!=null) {
                      JSONArray pcontrib = (JSONArray) userJson.get("productionsContribution");
                      pcontrib.toArray();
                      for (int i = 0; i < pcontrib.toArray().length; i++) {
                          productionComntributions.add((String) pcontrib.get(i));
                      }
                  } else {productionComntributions=null;}

               if(userJson.get("actorsContribution") != null)
               {
                   JSONArray actcontrib = (JSONArray) userJson.get("actorsContribution");
                   actcontrib.toArray();
                   for (int i = 0; i < actcontrib.toArray().length; i++) {
                       actorContributions.add((String) actcontrib.get(i));
                   }

               }else { actorContributions = null;}

              Contributor contributor =(Contributor)  UserFactory.createUser(AccountType.CONTRIBUTOR);
              contributor.setInfo(information);
              contributor.username = userName;
              contributor.userExperience=exp;
              contributor.prodContr= productionComntributions;
              contributor.actorContr = actorContributions;

              contributor.favActString =  favoriteActors;
              contributor.notifications=notifications;
              contributor.favProdsString=  favoriteProductions;
           contributors.add(contributor);
          }else if(type.toUpperCase().equals("ADMIN"))
          {

              if(userJson.get("productionsContribution")!=null) {
              JSONArray pcontrib = (JSONArray) userJson.get("productionsContribution");
              pcontrib.toArray();
              for (int i = 0; i < pcontrib.toArray().length; i++) {

                  productionComntributions.add((String) pcontrib.get(i));
              }
          } else {

                  productionComntributions=null;}

              if(userJson.get("actorsContribution") != null)
              {
                  JSONArray actcontrib = (JSONArray) userJson.get("actorsContribution");
                  actcontrib.toArray();
                  for (int i = 0; i < actcontrib.toArray().length; i++) {
                      actorContributions.add((String) actcontrib.get(i));
                  }

              }else { actorContributions = null;}
              Admin admin= (Admin)  UserFactory.createUser(AccountType.ADMIN);
             admin.setInfo(information);
              admin.username = userName;
              admin.userExperience=exp;
              admin.prodContr = productionComntributions;
              admin.actorContr = actorContributions;
              admin.favActString =  favoriteActors;
             admin.notifications=notifications;
              admin.favProdsString=  favoriteProductions;
              admins.add(admin);
          }

      }


      }catch (FileNotFoundException e) {
          e.printStackTrace();
      } catch (IOException e) {
          e.printStackTrace();
      } catch (ParseException e) {
          throw new RuntimeException(e);
      }
    info.add(regulars);
      info.add(admins);
      info.add(contributors);
      return info;
  }
  public ArrayList<Request> requestParser (String jsonFilePath) throws IOException, ParseException
  {
       ArrayList<Request> requestList = new ArrayList<>();
       JSONParser parser = new JSONParser();
      try (FileReader reader = new FileReader(jsonFilePath)) {

          Object obj = parser.parse(reader);
          JSONArray reqList = (JSONArray) obj;

          for(Object object : reqList)
          {
              JSONObject requestJson = (JSONObject) object;
              String date = (String) requestJson.get("createdDate");
              DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
              LocalDateTime createdDate = LocalDateTime.parse(date, formatter);

              String description = (String) requestJson.get("description");
              String requestingUser = (String) requestJson.get("username");
              String assignedUser = (String) requestJson.get("to");

              String type = (String)requestJson.get("type");
              RequestTypes requestType;
              Request request = new Request(createdDate,description,requestingUser,assignedUser);

              if(type.equals("ACTOR_ISSUE"))
              {
                  requestType = RequestTypes.ACTOR_ISSUE;
               String actorName = (String) requestJson.get("actorName");
               request.setType(RequestTypes.ACTOR_ISSUE);
               request.setTitleOrActorName(actorName);


              }else if(type.equals("MOVIE_ISSUE"))
              {
                   requestType = RequestTypes.MOVIE_ISSUE;
                  String movieName = (String) requestJson.get("movieName");
                  request.setType(RequestTypes.MOVIE_ISSUE);
                  request.setTitleOrActorName(movieName);

              }
              else if(type.equals("DELETE_ACCOUNT"))
              {
                  requestType = RequestTypes.DELETE_ACCOUNT;
                  request.setType(RequestTypes.DELETE_ACCOUNT);


              }
              else if(type.equals("OTHERS"))
              {
                  requestType = RequestTypes.OTHERS;
                  request.setType(RequestTypes.OTHERS);

              }

             // request.displayInfo();
              requestList.add(request);
          }

      }catch (FileNotFoundException e) {
          e.printStackTrace();
      } catch (IOException e) {
          e.printStackTrace();
      }
       return requestList;
  }


}