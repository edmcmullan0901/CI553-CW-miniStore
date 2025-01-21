package clients.login;

import clients.UIStyler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton guestButton;
    private JLabel messageLabel;


    public LoginView() {

        UIStyler.applyStyling();
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 20));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 25)); // Font for username label
        inputPanel.add(usernameLabel);

        usernameField = new JTextField(10);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14)); // Font for text field
        inputPanel.add(usernameField);


        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 25)); // Font for password label
        inputPanel.add(passwordLabel);


        passwordField = new JPasswordField(10);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14)); // Font for text field (hidden)
        inputPanel.add(passwordField);




        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        loginButton.setPreferredSize(new Dimension(75, 25));
        inputPanel.add(loginButton);

        guestButton = new JButton("Guest");
        guestButton.setFont(new Font("Arial", Font.BOLD, 18));
        guestButton.setPreferredSize(new Dimension(100, 50));
        inputPanel.add(guestButton);

        add(inputPanel, BorderLayout.CENTER);


        System.out.println("Adding listeners");

        loginButton.addActionListener(listener -> {
            System.out.println("Login button clicked");
        });
    }

    public String getUsername() {

        return usernameField.getText();
    }
    public String getPassword() {
        return new String(passwordField.getPassword());
        
    }

    public void clearListeners() {
        for (ActionListener listener : loginButton.getActionListeners()) {
            loginButton.removeActionListener(listener);
        }
        for (ActionListener listener : guestButton.getActionListeners()) {
            guestButton.removeActionListener(listener);
        }
    }

    public void setMessage(String message) {
        messageLabel.setText(message);
    }

    public void addLoginListener(ActionListener listener) {
        clearListeners();
        loginButton.addActionListener(listener);

    }

    public void addGuestListener(ActionListener listener) {

        clearListeners();
        guestButton.addActionListener(listener);
    }



    
}
