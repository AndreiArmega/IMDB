package GUI;

import Miscellaneous.RequestExperienceStrategy;
import RequestTree.Request;
import RequestTree.RequestTypes;
import Users.Admin;
import Users.Contributor;
import Users.User;
import main.IMDB;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDateTime;

import static Miscellaneous.NotificationService.notifyObserver;

public class CreateRequestFrame extends JFrame {

    private JComboBox<RequestTypes> requestTypeComboBox;
    private JTextField titleOrActorNameField;
    private JTextArea problemDescriptionArea;
    private JTextField requestingUserField;
    private JTextField assignedUserField;
    private User user;
    public CreateRequestFrame(User user) {
        this.user=user;
        initializeFrame();
        addComponents();
    }

    private void initializeFrame() {
        setTitle("Create New Request");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addComponents() {
        setLayout(new GridLayout(5, 2));

        add(new JLabel("Request Type:"));
        requestTypeComboBox = new JComboBox<>(RequestTypes.values());
        add(requestTypeComboBox);

        add(new JLabel("Title or Actor Name:"));
        titleOrActorNameField = new JTextField();
        add(titleOrActorNameField);

        add(new JLabel("Problem Description:"));
        problemDescriptionArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(problemDescriptionArea);
        add(scrollPane);



        add(new JLabel("assignedUser"));
        assignedUserField = new JTextField();
        add(assignedUserField);

        JButton createRequestButton = new JButton("Create Request");
        createRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    user.userExperience = user.userExperience + RequestExperienceStrategy.calcEXP();
                    createRequest();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        add(createRequestButton);
    }

    private void createRequest() throws IOException, ParseException {
        RequestTypes requestType = (RequestTypes) requestTypeComboBox.getSelectedItem();
        String titleOrActorName = titleOrActorNameField.getText();
        String problemDescription = problemDescriptionArea.getText();

        String assignedUser = assignedUserField.getText();
        LocalDateTime creationDate = LocalDateTime.now();
        Request newRequest = new Request(creationDate, problemDescription, user.getUsername(), assignedUser);
        newRequest.setType(requestType);
        newRequest.setTitleOrActorName(titleOrActorName);

        if(assignedUser.equalsIgnoreCase("ADMIN")){
        Request.RequestHolder.addRequest(newRequest);}else {
            for( User user : IMDB.getInstance().getUsers())
            {
                if(user.getUsername().equalsIgnoreCase(assignedUser))
                {
                    if(user instanceof Contributor)
                    {
                        Contributor contributor = (Contributor) user;
                        contributor.createRequest(newRequest);
                        notifyObserver(user.getUsername() + " has requested  "+ problemDescription ,contributor);
                    }else if(user instanceof Admin)
                    {
                        Admin admin= (Admin) user;
                        admin.createRequest(newRequest);
                        notifyObserver(user.getUsername() + " has requested  "+ problemDescription ,admin);

                    }
                }
            }
        }

        IMDB.getInstance().requestsList.add(newRequest);
        System.out.println("New Request Created:");
        System.out.println(newRequest);

        dispose();
    }


}
