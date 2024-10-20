package ProductionTree;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public abstract class Production implements Comparable{
   public  String productionTitle;
   public  ArrayList<String> proddirectorNames ;
   public ArrayList<String> prodActorList;
   public ArrayList<Genre> prodGenreList;
   public ArrayList<Rating> prodRatingList;
   public String plot;
      public Double ratingAvg;

    public abstract void displayInfo();

    public String getProductionTitle()
    {
        return  this.productionTitle;
    }
    public void addReview(Rating rating)

    {
        this.prodRatingList.add(rating);
    }
    public void setProductionTitle(String title) {
        this.productionTitle = title;
    }

    public void setProddirectorNames(ArrayList<String> directorNames) {
        this.proddirectorNames = directorNames;
    }
    public void setGenres(List<Genre> genres) {
        this.prodGenreList = (ArrayList<Genre>) genres;
    }

    public void setProdActorList(ArrayList<String> actorList) {
        this.prodActorList = actorList;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }



    public int compareTo(Object o) {

        if (o instanceof Production) {
            Production otherProduction =( Production) o;
            return this.productionTitle.compareTo(otherProduction.productionTitle);
        }else
            return 0;
    }

}

