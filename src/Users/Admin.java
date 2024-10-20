package Users;

import Miscellaneous.Observer;
import ProductionTree.Actor;
import ProductionTree.Movie;
import ProductionTree.Production;
import ProductionTree.Series;
import RequestTree.Request;
import main.IMDB;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import static Users.UserFactory.createUser;

public class Admin extends Staff implements Observer {


    public Admin(AccountType accountType)
    {
        this.accountType=accountType;
    }

    public void displayInfoRegular()
    {
        System.out.println("Username: " + this.username + " " + this.notifications + " " + this.favActString + " ");
    }
    public void removeUser(User user) {

        try {
            IMDB imdb = IMDB.getInstance();
            for(User user1 : imdb.getUsers())
            {
                if(user1.getUsername().equals(user.getUsername()))
                {
                    if(user1 instanceof Regular)
                    {
                        Regular regular = (Regular) user1;
                        imdb.regulars.remove(regular);
                    } else if (user1 instanceof Contributor) {

                        Contributor contributor = (Contributor) user1;
                        imdb.contributors.remove(contributor);
                    } else if (user1 instanceof Admin) {
                        Admin admin = (Admin) user1;
                        imdb.admins.remove(admin);

                    }
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

      public void createRequest(Request request)
      {
          this.assignedRequests.add(request);
      }
    public void addUser() throws IOException, ParseException {

        System.out.println("Enter username");
        Scanner scanner = new Scanner(System.in);
        String usernamee = scanner.nextLine();
        System.out.println("Enter email");
        String emaill = scanner.nextLine();
        System.out.println("Enter password");
        String pass = scanner.nextLine();
        System.out.println("Enter country");
        String country = scanner.nextLine();

        boolean isUsernameTaken = false;
        boolean isEmailTaken = false;
        for (User user : IMDB.getInstance().getUsers()) {
            if (user.getUsername().equals(usernamee))
                isUsernameTaken = true;
            if (user.getCredentials().getEmail().equals(emaill))
                isEmailTaken = true;
        }

        if (isUsernameTaken || isEmailTaken) {
            System.out.println("Username or email already exists. Please choose a different one.");
        } else {
            Information.Builder userInfoBuilder = new Information.Builder(new Credentials.Builder(emaill, pass).build());
            userInfoBuilder.country(country);

            System.out.println("Enter name");
            String name = scanner.nextLine();
            userInfoBuilder.name(name);

            System.out.println("Enter age");
            int age = scanner.nextInt();
            scanner.nextLine();
            userInfoBuilder.age(age);

            System.out.println("Enter gender (M/F)");
            char gender = scanner.next().charAt(0);
            scanner.nextLine();
            userInfoBuilder.gender(gender);

            System.out.println("Enter date of birth (YYYY-MM-DD)");
            LocalDate dateOfBirth = LocalDate.parse(scanner.nextLine());
            userInfoBuilder.dateOfBirth(dateOfBirth);


            System.out.println("Enter account type");
            AccountType accountType1 = AccountType.valueOf(scanner.nextLine());
            User newUSer = createUser(AccountType.valueOf(String.valueOf(accountType1)));
            newUSer.setInfo(userInfoBuilder.build());
            newUSer.setUsername(usernamee);


            if(newUSer instanceof Regular)
            IMDB.getInstance().regulars.add((Regular) newUSer);
            else if(newUSer instanceof Contributor)
                IMDB.getInstance().contributors.add((Contributor) newUSer);
            else if(newUSer instanceof Admin)
                IMDB.getInstance().admins.add((Admin) newUSer);

            System.out.println("User added successfully!");
        }

    }


    @Override
    public void update(String notification) {

    }



}
