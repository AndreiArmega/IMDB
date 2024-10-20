package ProductionTree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Series extends Production{

 public Long releaseYear;
  public int nrOfSeasons;
   public Map<String, List<Episode>> episoadePeSezoane;



    @Override
        public void displayInfo() {
        System.out.println(this.productionTitle );
        System.out.println("Director names:" +this.proddirectorNames + "\n Actor names:" + this.prodActorList +"\n Genre list :"+
                this.prodGenreList);
        for(Rating rating : this.prodRatingList)
        {
            rating.displayRating();
        }

        System.out.println(this.releaseYear);
        System.out.println(this.nrOfSeasons);
        for (Map.Entry<String, List<Episode>> entry : this.episoadePeSezoane.entrySet()) {
            String season = entry.getKey();
            List<Episode> episodes = entry.getValue();

            System.out.println("Season: " + season);
            for (Episode episode : episodes) {
                episode.displayInfo();
            }
        }

        }
    public String buildProductionInfo() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(this.productionTitle).append("\n");
        stringBuilder.append("Director names:").append(this.proddirectorNames)
                .append("\n Actor names:").append(this.prodActorList)
                .append("\n Genre list:").append(this.prodGenreList).append("\n");

        for (Rating rating : this.prodRatingList) {
            stringBuilder.append(rating.displayRating()).append("\n");
        }

        stringBuilder.append("Release year:" + this.releaseYear).append("\n");

        if (this instanceof Series) {
            Series series = (Series) this;
            stringBuilder.append("Nr of seasons"+ series.nrOfSeasons).append("\n");
            for (Map.Entry<String, List<Episode>> entry : series.episoadePeSezoane.entrySet()) {
                String season = entry.getKey();
                List<Episode> episodes = entry.getValue();

                stringBuilder.append("Season: ").append(season).append("\n");
                for (Episode episode : episodes) {
                    stringBuilder.append(episode.displayInfo());
                }
                stringBuilder.append("\n");
            }
        }

        return stringBuilder.toString();
    }

    public void setNrOfSeasons(int nrOfSeasons) {
        this.nrOfSeasons = nrOfSeasons;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episoadePeSezoane = new HashMap<>();
        for (int i = 1; i <= nrOfSeasons; i++) {
            this.episoadePeSezoane.put("Season " + i, episodes);
        }
    }
    public void setEpisoadePeSezoane(Map<String, List<Episode>> episoadePeSezoane) {
        this.episoadePeSezoane = episoadePeSezoane;
    }
    public void setReleaseYear(Long releaseYear) {
        this.releaseYear = releaseYear;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
