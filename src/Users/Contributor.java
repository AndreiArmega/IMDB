package Users;

import Miscellaneous.Observer;

import RequestTree.Request;
import RequestTree.RequestManager;

import java.util.ArrayList;


public class Contributor extends Staff implements RequestManager, Observer {

 public Contributor(AccountType accountType)
 {

     this.accountType= accountType;

 }

    public void createRequest(Request request) {
     if(this.assignedRequests== null)
     {
         this.assignedRequests= new ArrayList<>();
     }
        this.assignedRequests.add(request);
    }

    @Override
    public void removeRequest(Request request) {
          this.assignedRequests.remove(request);
    }



    @Override
    public void update(String notification) {

    }
    public void displayInfoRegular()
    {
        System.out.println("Username: " + this.username + " " + this.notifications + " " + this.favActString + " ");
    }
}
