package Miscellaneous;

public class AddProductionExperienceStrategy implements ExperienceStrategy {
    @Override
    public int calculateExperience() {
        // Calculate experience for adding a new movie or actor
        return 15;
    }
}