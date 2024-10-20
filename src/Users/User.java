package Users;

import GUI.MainFrameClass;
import ProductionTree.Actor;
import ProductionTree.Production;

import javax.swing.*;
import java.time.LocalDate;
import java.util.*;

public abstract class User implements Comparable<User>{
   protected   Information userInfo;
    public AccountType accountType;
    public String username;
    public  int userExperience;
    public List<String> notifications;

    public ArrayList<String> favActString;
    public ArrayList<String> favProdsString;

    public ArrayList<Production> productionFavorites;

    public  SortedSet<Actor> actorFavorites;

    public void setInfo(Information information)
    {
        this.userInfo = information;
    }

    public void addFavoriteActor(Actor actor){
            this.actorFavorites.add(actor);
    }

  public void addProductionFavorite(Production production) {this.productionFavorites.add(production);
    this.favProdsString.add(production.getProductionTitle());}
    public abstract void removeFavorite(Object item);
    public void removeActorFavorite(Actor actor)
    {
        this.actorFavorites.remove(actor);
    }
     public void removeProductionFavorite(Production production)
     {
         this.productionFavorites.remove(production);
         this.favProdsString.remove(production.getProductionTitle());
     }
     public void setUsername(String username)
     {
         this.username=username;
     }
     public int getUserExperience()
     {
         return  this.userExperience;
     }
    public abstract void updateExperience(int experience);
    public List<String> getNotifications()
    {
        return this.notifications;
    }
    public  void logout(JFrame frame){
        frame.dispose();
        MainFrameClass mainFrame = new MainFrameClass();
    }

    public int compareTo(User o)
    {
        return 0;
    }
    public String getUsername()
    {
        return this.username ;
    }

    public  Information getUserInfo()
    {
        return this.userInfo;}
    public static class Information {
          Credentials credentials;
         String name;
          String country;
         int age;
       char gender;
          LocalDate dateOfBirth;


         private Information(Credentials credentials, String name, String country, int age, char gender, LocalDate dateOfBirth) {
             this.credentials = credentials;
             this.name = name;
             this.country = country;
             this.age = age;
             this.gender = gender;
             this.dateOfBirth = dateOfBirth;
         }

         public Information(Builder builder) {
             this.credentials= builder.credentials;
             this.age=builder.age;
             this.name=builder.name;
             this.country= builder.country;
             this.gender= builder.gender;
             this.dateOfBirth=builder.dateOfBirth;
         }


         public  Credentials getCredentials() {
             return credentials;
         }

         public String getName() {
             return name;
         }

         public String getCountry() {
             return country;
         }

         public int getAge() {
             return age;
         }

         public char getGender() {
             return gender;
         }

         public LocalDate getDateOfBirth() {
             return dateOfBirth;
         }

         //}
         public static class Builder {
             private Credentials credentials;
             private String name;
             private String country;
             private int age;
             private char gender;
             private LocalDate dateOfBirth;

             public Builder(Credentials credentials)
             {
                 this.credentials= credentials;
                 this.age=age;
                 this.name=name;
                 this.country= country;
                 this.gender= gender;
                 this.dateOfBirth=dateOfBirth;
             }
             public  Builder(Credentials credentials, String name) {
                 this.credentials = credentials;
                 this.name = name;
             }

             public Builder country(String country) {
                 this.country = country;
                 return this;
             }
            public Builder name(String name){
                 this.name = name;
                 return  this;
            }
             public Builder age(int age) {
                 this.age = age;
                 return this;
             }

             public Builder gender(char gender) {
                 this.gender = gender;
                 return this;
             }

             public Builder dateOfBirth(LocalDate dateOfBirth) {
                 this.dateOfBirth = dateOfBirth;
                 return this;
             }
             public Information build() {
                 return new Information(this);
             }

         }
     }
    public  Credentials getCredentials() {
        return this.userInfo.credentials;
    }

}
