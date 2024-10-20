package GUI;

import Users.*;
import main.IMDB;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.event.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static GUI.FrameMethods.createInvisibleButton;

public class CredentialsWindow extends JFrame {
    private MainFrameClass mainFram;
    public CredentialsWindow(MainFrameClass mainFram) {
        this.mainFram= mainFram;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(510, 540);
        setResizable(false);

        setFocusable(true);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                requestFocusInWindow();
            }
        });
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = createInvisibleButton();

        usernameField.setBounds(97, 169, 304, 32);
        passwordField.setBounds(97, 238, 304, 31);
        loginButton.setBounds(95, 292, 305, 35);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    IMDB imdb = IMDB.getInstance();
                     ArrayList<User> userList = imdb.getUsers();
                    String username = usernameField.getText();
                    char[] password = passwordField.getPassword();
                    int OK=0;
                    for (int i = 0 ; i <userList.toArray().length;i++) {

                          Credentials credentials = userList.get(i).getCredentials();

                        if(credentials.getEmail().equals(usernameField.getText()))
                         {

                           if(Arrays.equals(credentials.getPassword().toCharArray(), passwordField.getPassword()))
                           {
                               AccountType type = userList.get(i).accountType;
                               switch (type) {
                                   case REGULAR:
                                     mainFram.dispose();
                                     OK=1;
                                     dispose();
                                     RegularFrame regframe = new RegularFrame(userList.get(i));
                                   case CONTRIBUTOR:
                                       mainFram.dispose();
                                       OK=1;
                                       dispose();
                                       ContributorFrame conframe = new ContributorFrame(userList.get(i));
                                   case ADMIN:
                                       mainFram.dispose();
                                       OK=1;
                                       dispose();
                                       AdminFrame adminFrame = new AdminFrame(userList.get(i));
                                   default:
                               }

                           }

                         }

                    }
                    if(OK==0){
                    JOptionPane.showMessageDialog(null, "No matching email or password",
                            "Information", JOptionPane.INFORMATION_MESSAGE);}
                }catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        ImageIcon backgroundImageIcon = new ImageIcon("C:\\Users\\Armega\\Desktop\\java\\imdbtema\\IMDB\\src\\Miscellaneous\\Screenshot 2023-12-26 234306.png");
        JLabel backgroundLabel = new JLabel(backgroundImageIcon);
        backgroundLabel.setBounds(0, 0, backgroundImageIcon.getIconWidth(), backgroundImageIcon.getIconHeight());

        JPanel panel = new JPanel(null);
        panel.add(usernameField);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(backgroundLabel);

        setContentPane(panel);

        setLayout(null);
        setVisible(true);
        setLocationRelativeTo(null);
    }
}
