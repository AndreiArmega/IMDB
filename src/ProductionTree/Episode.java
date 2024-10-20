package ProductionTree;

public class Episode {
    String episodeName;
    String duration;
    public  Episode(String episodeName, String duration)
    {
        this.duration = duration;
        this.episodeName = episodeName;
    }
    public String displayInfo()
    {
        return this.episodeName + " " + this.duration;
    }
}
