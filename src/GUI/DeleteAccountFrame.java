package GUI;

import Users.Admin;
import Users.User;
import main.IMDB;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class DeleteAccountFrame extends JFrame {
    private JTextField usernameField;

    public DeleteAccountFrame(Admin admin) {
        setTitle("Delete Account");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        usernameField = new JTextField();
        try{
            IMDB imdb = IMDB.getInstance();

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                int ok = 0;
                for( User user : imdb.getUsers())
                {
                    if(user.username.equals(username))
                    { ok=1;
                        int option = JOptionPane.showConfirmDialog(null,
                                "Are you sure you want to delete the account for username: " + username,
                                "Confirmation", JOptionPane.YES_NO_OPTION);

                        if (option == JOptionPane.YES_OPTION) {
                         admin.removeUser(user);
                            dispose();
                        }
                    }

                }
                if(ok == 0) {JOptionPane.showMessageDialog(null, "No matching user",
                        "Information", JOptionPane.INFORMATION_MESSAGE);}

            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.add(new JLabel("Enter Username:"));
        panel.add(usernameField);
        panel.add(new JLabel(""));
        panel.add(confirmButton);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }



}
