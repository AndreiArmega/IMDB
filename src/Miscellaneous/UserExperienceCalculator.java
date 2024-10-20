package Miscellaneous;

public class UserExperienceCalculator {
    private ExperienceStrategy experienceStrategy; // This is the field

    public void setExperienceStrategy(ExperienceStrategy strategy) {
        this.experienceStrategy = strategy;
    }

    public int calculateExperience() {
        if (experienceStrategy == null) {
            throw new IllegalStateException("Experience strategy not set");
        }
        return experienceStrategy.calculateExperience();
    }
}