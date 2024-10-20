package RequestTree;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Request {


    public RequestTypes requestType;
    private LocalDateTime creationDate;
    public String titleOrActorName;  // Depending on the request type
    private String problemDescription;
    private String requestingUser;
    private String assignedUser;
 public Request(LocalDateTime creationDate , String problemDescription , String requestingUser ,String assignedUser)
 {
     this.creationDate = creationDate;
     this.problemDescription = problemDescription;
     this.requestingUser=requestingUser;
     this.assignedUser=assignedUser;
 }
 public String getRequestingUser ()
 {
     return this.assignedUser;
 }
 public  void setType(RequestTypes type)
 {
     this.requestType = type;
 }

    public void setTitleOrActorName(String titleOrActorName) {
        this.titleOrActorName = titleOrActorName;
    }
  public String displayInfo ()
  {
      System.out.println(this.assignedUser + " " + this.requestingUser + " " +
              this.requestType + " " + this.problemDescription + " " + this.creationDate + " " + this.titleOrActorName);
    return  null;
  }
    public static class RequestHolder{
        private static List<Request> requestsList = new ArrayList<>();

        public static void addRequest(Request request) {
            requestsList.add(request);
        }

        public static void removeRequest(Request request) {
            requestsList.remove(request);
        }

        public static List<Request> getRequestsList() {
            return new ArrayList<>(requestsList);
        }

        public static void clearRequests() {
            requestsList.clear();
        }
    }


    public String toString() {
        StringBuilder builder = new StringBuilder();
        appendIfNotNull(builder, " \n", requestingUser);
        appendIfNotNull(builder, " \n", requestType);
        appendIfNotNull(builder, " \n", problemDescription);
        appendIfNotNull(builder, " \n", creationDate);
        appendIfNotNull(builder, " \n", titleOrActorName);

        return builder.toString();
    }

    private void appendIfNotNull(StringBuilder builder, String separator, Object value) {
        if (value != null) {
            builder.append(separator).append(value);
        }
    }




}
