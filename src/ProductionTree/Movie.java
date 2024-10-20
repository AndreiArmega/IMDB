package ProductionTree;

public class Movie extends Production{
    public String duration;
    public Long releaseYear;
    @Override
    public void displayInfo() {
      System.out.println(this.productionTitle );
        System.out.println("Director names:" +this.proddirectorNames + "\n Actor names:" + this.prodActorList +"\n Genre list :"+
                this.prodGenreList);
        for(Rating rating : this.prodRatingList)
        {
            rating.displayRating();
        }
        System.out.println("Plot:" +this.plot + " Rating avg:" + this.ratingAvg +" Duration:"+ this.duration + " Year:" + this.releaseYear) ;

    }
    public String getMovieInfo()
    {
        StringBuilder builder = new StringBuilder();

        builder.append(this.productionTitle).append("\n")
                .append("Director names:").append(this.proddirectorNames).append("\n")
                .append("Actor names:").append(this.prodActorList).append("\n")
                .append("Genre list:").append(this.prodGenreList).append("\n");

        for (Rating rating : this.prodRatingList) {
            builder.append(rating.displayRating()).append("\n");
        }

        builder.append("Plot:").append(this.plot).append(" Rating avg:").append(this.ratingAvg)
                .append(" Duration:").append(this.duration).append(" Year:").append(this.releaseYear);
        return builder.toString();

    }
    public void setReleaseYear(Long releaseYear) {
        this.releaseYear = releaseYear;
    }
    public String  getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
