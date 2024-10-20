package GUI;

import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class CLIorGUI extends JFrame {
    public CLIorGUI() {
        JFrame choiceFrame = new JFrame();
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        JButton guiButton = new JButton("Open GUI Frame");
        JButton commandLineButton = new JButton("Open Command Line Frame");

        guiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openGuiFrame();
            }
        });

        commandLineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    openCommandLineFrame();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        setLayout(new GridLayout(2, 1));
        add(guiButton);
        add(commandLineButton);
    }

    private void openGuiFrame() {
        dispose();

        MainFrameClass guiFrame = new MainFrameClass();

    }

    private void openCommandLineFrame() throws IOException, ParseException {
        dispose();

      CommandLineInterface cli = new CommandLineInterface();
      cli.start();
    }


}

