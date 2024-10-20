package ProductionTree;

public class Rating {

    public  String userName;
    public int nota;
    public String comments;

    public Rating(String username,int rating , String comment)
    {
        this.nota = rating;
        this.comments = comment;
        this.userName = username;
    }



    public String displayRating()
    {
        return this.userName + " " + this.nota + " " + this.comments;
    }
}
