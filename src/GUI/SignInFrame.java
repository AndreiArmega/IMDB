package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignInFrame extends FrameMethods{

    private MainFrameClass mainFrame;
    public SignInFrame(MainFrameClass mainFrame) {
        this.mainFrame = mainFrame;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1080, 580);
        setResizable(false);
        JButton signbut = createInvisibleButton();
        JButton err1 = createInvisibleButton();
        JButton err2 = createInvisibleButton();
        JButton err3 = createInvisibleButton();
        JButton err4 = createInvisibleButton();
        err4.setBounds(47,395,300,43);
        signbut.setBounds(47,83,300,43);
        signbut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                CredentialsWindow credentialsWindow = new CredentialsWindow(mainFrame);
            }
        });
        err1.setBounds(47,135,300,43);
        err2.setBounds(47,190,300,43);
        err3.setBounds( 47,245,300,43);
        ImageIcon signInImageIcon = new ImageIcon("C:\\Users\\Armega\\Desktop\\java\\imdbtema\\IMDB\\src\\Miscellaneous\\sib.png");
        JLabel signInImageLabel = new JLabel(signInImageIcon);
        signInImageLabel.setBounds(0, 0, signInImageIcon.getIconWidth(), signInImageIcon.getIconHeight());
        err1.setVisible(true);
        ErrButton(err1,"Option not available at the moment.");
        err2.setVisible(true);
        ErrButton(err2,"Option not available at the moment.");
        err3.setVisible(true);
        ErrButton(err3,"Option not available at the moment.");
        ErrButton(err4,"Ask a admin to create and account for you");
        getContentPane().setLayout(null);
        getContentPane().add(err4);
        getContentPane().add(signInImageLabel);
        getContentPane().add(signbut);
        getContentPane().add(err1);
        getContentPane().add(err2);
        getContentPane().add(err3);

        setLocationRelativeTo(null);
        setVisible(true);

    }

}
