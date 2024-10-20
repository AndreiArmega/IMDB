package GUI;

import ProductionTree.Actor;
import ProductionTree.Production;
import Users.*;
import main.IMDB;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static GUI.FrameMethods.createInvisibleButton;

public class MenuWindow extends JFrame {
    public MenuWindow(User user) {
        // Set up the menu frame
        setTitle("Menu Window");
        setSize(1520, 900);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JButton showProd = createColoredTextButton("Browse productions", "#1F1F1F", Color.WHITE);
        JButton addAReview =createColoredTextButton("Add a review", "#1F1F1F", Color.WHITE);
        JButton showActors = createColoredTextButton("Browse actors","#1F1F1F",Color.WHITE);
        showActors.setBounds(230,270,150,30);
        showActors.setBorderPainted(false);

        addAReview.setBounds(215,300,170,30);
        showProd.setBorderPainted(false);
        addAReview.setBorderPainted(false);
        Font font2= new Font("Helvetica Neue",Font.BOLD,16);
        showProd.setFont(font2);
        showActors.setFont(font2);
        addAReview.setFont(font2);
        showProd.setBounds(218,240,220,30);
        JLabel productions = new JLabel("<html><font color='" + toHexString(Color.WHITE) + "'>" + "Productions" + "</font></html>");
        productions.setBounds(210,150,200,60);
        productions.setFont(new Font("Veranda",Font.BOLD,20));
        ImageIcon img = new ImageIcon("C:\\Users\\Armega\\Desktop\\java\\imdbtema\\IMDB\\src\\Miscellaneous\\menubun.png");
        showProd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              DataBaseFrame frame = new DataBaseFrame(user,1);
            }
        });
        showActors.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DataBaseFrame frame = new DataBaseFrame(user,2);
            }
        });
        addAReview.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String reviewText = JOptionPane.showInputDialog("Enter your production");

                try {
                    for(Object product : IMDB.getInstance().getProductionList())
                    {
                        Production production = (Production) product;
                        if(production.getProductionTitle().equalsIgnoreCase(reviewText))
                        {
                            RevFrame revframe = new RevFrame(user.getUsername(),production);
                        }
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        JLabel imglbl = new JLabel(img);
        imglbl.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());



            if (user instanceof Admin || user instanceof  Contributor) {
                JLabel requests1 = new JLabel("Requests");
                requests1.setBounds(520,160,140,40);
                requests1.setFont(new Font("Veranda",Font.BOLD,20));
                requests1.setForeground(Color.WHITE);
                JButton solveMovieIssue = createInvisibleButton();
                solveMovieIssue.setText("Solve Movie Issue");
                solveMovieIssue.setFont(font2);
                solveMovieIssue.setBounds(550, 238, 180, 30);
                   solveMovieIssue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Movie Modification");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.getContentPane().add(new MovieModificationPanel());
                frame.setSize(700, 350);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
                JButton solveActorIssue = createInvisibleButton();
                solveActorIssue.setText("Solve Actor Issue");
                solveActorIssue.setFont(font2);
                solveActorIssue.setBounds(548, 268, 180, 30);
               solveActorIssue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Actor Modification");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.getContentPane().add(new ActorModificationPanel());
                frame.setSize(700, 350);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });

                JButton deleteAccount = createInvisibleButton();
                deleteAccount.setText("Delete Account");
                deleteAccount.setFont(font2);
                deleteAccount.setBounds(540, 298, 180, 30);
                deleteAccount.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                       DeleteAccountFrame frame = new DeleteAccountFrame(new Admin(AccountType.ADMIN));
                    }
                });

                JButton Other = createInvisibleButton();
                Other.setText("Other");
                Other.setFont(font2);
                Other.setBounds(532, 328, 120, 30);
            Other.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    IMDB imdb = IMDB.getInstance();

                    // Get the username from user input (you can use a dialog or any other method)
                    String usernameToSearch = JOptionPane.showInputDialog("Enter username to search:");

                    if (usernameToSearch != null && !usernameToSearch.isEmpty()) {
                        User foundUser = null;

                        // Search for the user with the specified username
                        for (User user2 : imdb.getUsers()) {
                            if (user2.getUsername().equals(usernameToSearch)) {
                                foundUser = user2;
                                break;
                            }
                        }

                        if (foundUser != null) {
                            UpdateUserInformationFrame updateUserInformationFrame = new UpdateUserInformationFrame(foundUser);
                        } else {
                            JOptionPane.showMessageDialog(null, "User not found!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid username!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException | ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

              JButton label = createInvisibleButton();
              label.setText("Add a production");
              label.setForeground(Color.WHITE);
              label.setBounds(225,300,180,30);
              label.setFont(font2);
              label.addActionListener(new ActionListener() {
                  @Override
                  public void actionPerformed(ActionEvent e) {
                    NewProductionFrame newProductionFrame = new NewProductionFrame();
                  }
              });
              JButton actAdd = createInvisibleButton();
              actAdd.setText("Add an Actor");
              actAdd.setForeground(Color.WHITE);
              actAdd.setBounds(209,330,180,30);
              actAdd.setFont(font2);
              actAdd.addActionListener(new ActionListener() {
                  @Override
                  public void actionPerformed(ActionEvent e) {
                   NewActorFrame newActorFrame = new NewActorFrame();
                  }
              });
              add(actAdd);
                add(label);
                add(requests1);
                add(solveActorIssue);
                add(deleteAccount);
                add(Other);
                add(solveMovieIssue);
           }




        JButton xbutton = createInvisibleButton();
        JButton xbutton2 = createInvisibleButton();
        xbutton.setBounds(197, 40, 117, 57);
        xbutton2.setBounds(1252, 45, 56, 53);
        exitButton(xbutton);
        exitButton(xbutton2);
        if(user instanceof Regular){
            add(addAReview);
            JButton createRequestButton = createInvisibleButton();
            createRequestButton.setText("Create Request");
            createRequestButton.setFont(font2);
            createRequestButton.setBounds(199,327,220,30);
            createRequestButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CreateRequestFrame createRequestFrame = new CreateRequestFrame(user);
                }
            });
            add(createRequestButton);

            JButton reviewACtor = createInvisibleButton();
            reviewACtor.setFont(font2);
            reviewACtor.setText("Review an actor");
            reviewACtor.setBounds(199,355,220,30);
            reviewACtor.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String actorname = JOptionPane.showInputDialog("Enter the actor's name to search:");
                    try {
                        for(Actor actor : IMDB.getInstance().getActorList())
                        {
                            if(actor.getActorName().equalsIgnoreCase(actorname))
                            {
                                ActorReviewFrame actorReviewFrame = new ActorReviewFrame(actor);

                            }
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            });
            add(reviewACtor);


        }
        if(user instanceof Contributor){
            JButton createRequestButton = createInvisibleButton();
            createRequestButton.setText("Create Request");
            createRequestButton.setFont(font2);
            createRequestButton.setBounds(199,357,220,30);
            createRequestButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CreateRequestFrame createRequestFrame = new CreateRequestFrame(user);
                }
            });
            add(createRequestButton);
        }




        add(showProd);
        add(showActors);
        add(xbutton);
        add(xbutton2);
        add(productions);
        add(imglbl);

        setLocationRelativeTo(null);
        setVisible(true);
    }
      public void exitButton(JButton button)
      {
          button.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                  dispose();
              }
          });
      }
    private static JButton createColoredTextButton(String text, String backgroundColorHex, Color textColor) {
        JButton button = new JButton("<html><font color='" + toHexString(textColor) + "'>" + text + "</font></html>");
        button.setBackground(Color.decode(backgroundColorHex));
        button.setFocusPainted(false); // Remove the focus border
        return button;
    }

    private static String toHexString(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }
}