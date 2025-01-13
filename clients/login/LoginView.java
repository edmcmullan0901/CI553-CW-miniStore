package clients.login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView extends Component {
    private JFrame loginFrame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton guestButton;
    private JLabel messageLabel;


    public LoginView() {
        loginFrame = new JFrame("Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(800, 500);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        inputPanel.add(new JLabel("Username:"));
        usernameField = new JTextField(10);
        inputPanel.add(usernameField);

        inputPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField(10);
        inputPanel.add(passwordField);

        loginButton = new JButton("Login");
        inputPanel.add(loginButton);
        guestButton = new JButton("Guest");
        inputPanel.add(guestButton);

        messageLabel = new JLabel("", JLabel.CENTER);
        messageLabel.setForeground(Color.BLACK);

        loginFrame.add(inputPanel, BorderLayout.CENTER);
        loginFrame.add(inputPanel, BorderLayout.SOUTH);

        
    }

    public void setVisible(boolean visible) {
        loginFrame.setVisible(visible);
    }

    public String getUsername() {
        return usernameField.getText();
    }
    public String getPassword() {
        return new String(passwordField.getPassword());
        
    }

    public void setMessage(String message) {
        messageLabel.setText(message);
    }

    public void addLoginListener(ActionListener listener) {
        loginButton.addActionListener(listener);

    }

    public void addGuestListener(ActionListener listener) {
        guestButton.addActionListener(listener);
    }
    
}
