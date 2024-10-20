package GUI;

import RequestTree.Request;
import Users.Admin;
import Users.Contributor;
import Users.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RequestFrame extends JFrame {
    private static List<Request> requestList = Request.RequestHolder.getRequestsList();

    public RequestFrame(User user) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        if(user instanceof Admin) {
            for (Request request : requestList) {
                listModel.addElement(request.toString());
            }
        }
        if(user instanceof Contributor)
        {Contributor contributor = (Contributor) user;
            if(contributor.assignedRequests!=null)
            for(Request request: contributor.assignedRequests){
                listModel.addElement(request.toString());
            }
        } else if (user instanceof Admin) {
            Admin admin = (Admin) user;
            if(admin.assignedRequests!=null)
            for(Request request: admin.assignedRequests)
            {
                listModel.addElement(request.toString());
            }
        }

        JList<String> requestJList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(requestJList);

        JButton completeButton = new JButton("Complete");
        JButton denyButton = new JButton("Deny");

        completeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = requestJList.getSelectedIndex();
                if (selectedIndex != -1) {
                    listModel.remove(selectedIndex);
                }
            }
        });

        denyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = requestJList.getSelectedIndex();
                if (selectedIndex != -1) {
                    listModel.remove(selectedIndex);
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(completeButton);
        buttonPanel.add(denyButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        setSize(500, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }


}
