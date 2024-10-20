package Users;

import Miscellaneous.Observer;
import ProductionTree.Production;
import ProductionTree.Rating;
import RequestTree.Request;
import RequestTree.RequestManager;
import Users.User;

public class Regular extends User implements RequestManager, Observer {


 public Regular(AccountType accountType)
 {
  this.accountType = accountType;

 }
   public void displayInfoRegular()
   {
    System.out.println("Username: " + this.username + " " + this.notifications + " " + this.favActString + " ");
   }


    @Override
    public void removeFavorite(Object item) {

    }

    @Override
    public void updateExperience(int experience) {

    }


    public void createRequest(Request request) {


    }

    @Override
    public void removeRequest(Request request) {

    }



    @Override
    public void update(String notification) {

    }

}
