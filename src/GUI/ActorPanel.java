package GUI;

import ProductionTree.Actor;
import Users.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActorPanel extends JPanel {
    private JTextArea textArea;
    private JLabel imageLabel;
    private JButton removeButton;

    public ActorPanel(String actorInfo, ImageIcon imageIcon , JPanel mainPanel, User user , Actor actor) {
        setLayout(new BorderLayout());


        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setText(actorInfo);
        textArea.setForeground(Color.WHITE);
        textArea.setBackground(Color.BLACK);
        removeButton = new JButton("Remove");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                user.removeActorFavorite(actor);
                mainPanel.remove(ActorPanel.this);
                mainPanel.revalidate();
                mainPanel.repaint();
            }
        });
        imageLabel = new JLabel(imageIcon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.TOP);

        add(imageLabel, BorderLayout.WEST);
        add(textArea, BorderLayout.CENTER);
        add(removeButton, BorderLayout.EAST);
    }
    public ActorPanel (String actorInfo , ImageIcon actorImage){
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setText(actorInfo);
        textArea.setForeground(Color.WHITE);
        textArea.setBackground(Color.BLACK);
        imageLabel = new JLabel(actorImage);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.TOP);
        add(imageLabel, BorderLayout.WEST);
        add(textArea, BorderLayout.CENTER);
    }
}