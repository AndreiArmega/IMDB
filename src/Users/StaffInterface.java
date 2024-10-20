package Users;

import ProductionTree.Actor;
import ProductionTree.Production;
import RequestTree.Request;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

public interface StaffInterface {
    void addProductionSystem(String type) throws IOException, ParseException;

    void addActorSystem() throws IOException, ParseException;

    void removeProductionSystem(String name,User user) throws IOException, ParseException;

    void removeActorSystem(String name, User user) throws IOException, ParseException;

    void updateProduction(JButton solveMovieIssue);

    void updateActor(Actor actor);


}
