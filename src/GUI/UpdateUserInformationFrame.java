package GUI;

import Users.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class UpdateUserInformationFrame extends JFrame {
    private JTextField nameField;
    private JTextField countryField;
    private JTextField ageField;
    private JTextField genderField;

    private User user;

    public UpdateUserInformationFrame(User user) {
        this.user = user;

        setTitle("Update User Information");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        final User.Information userInfo = user.getUserInfo();

        nameField = new JTextField(userInfo.getName());
        countryField = new JTextField(userInfo.getCountry());
        ageField = new JTextField(String.valueOf(userInfo.getAge()));
        genderField = new JTextField(String.valueOf(userInfo.getGender()));

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User.Information updatedInfo = new User.Information.Builder(userInfo.getCredentials(), nameField.getText())
                        .country(countryField.getText())
                        .age(Integer.parseInt(ageField.getText()))
                        .gender(genderField.getText().charAt(0))
                        .dateOfBirth(userInfo.getDateOfBirth())
                        .build();

                user.setInfo(updatedInfo);

                JOptionPane.showMessageDialog(null, "User information updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                dispose();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Country:"));
        panel.add(countryField);
        panel.add(new JLabel("Age:"));
        panel.add(ageField);
        panel.add(new JLabel("Gender:"));
        panel.add(genderField);
        panel.add(new JLabel(""));
        panel.add(updateButton);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
