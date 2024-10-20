package GUI;

import Users.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class NotificationsFrame extends JFrame {
    public NotificationsFrame(User user) {
        setTitle("Notifications");
        setSize(1000, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        ArrayList<String> notifications = (ArrayList<String>) user.getNotifications();

        JList<String> notificationsList = new JList<>();
        notificationsList.setFont(new Font("Arial", Font.PLAIN, 14));

        if (notifications != null && !notifications.isEmpty()) {
            DefaultListModel<String> listModel = new DefaultListModel<>();
            for (String notification : notifications) {
                listModel.addElement(notification);
                listModel.addElement("----------------------------------------------------------------------------------"); // Separator
            }
            notificationsList.setModel(listModel);
        } else {
            DefaultListModel<String> emptyModel = new DefaultListModel<>();
            emptyModel.addElement("No notifications available.");
            notificationsList.setModel(emptyModel);
        }

        mainPanel.add(new JScrollPane(notificationsList), BorderLayout.CENTER);

        getContentPane().add(mainPanel);

        setLocationRelativeTo(null);
        setVisible(true);
    }


}
