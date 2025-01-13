package clients.login;
import clients.Main;
import middle.MiddleFactory;
import middle.LocalMiddleFactory;


import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoginController {
    private LoginModel model;
    private LoginView view;
    private Main mainapp;
    private final MiddleFactory mlf;

    public LoginController(LoginModel model, LoginView view, Main mainapp, MiddleFactory mlf) {
        this.model= model;
        this.view = view;
        this.mainapp = mainapp;
        this.mlf = mlf;

        view.addLoginListener(new LoginButtonListener());
        view.addGuestListener(new GuestButtonListener());

            }

    private class LoginButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = view.getUsername();
            String password = view.getPassword();

            if (model.validateCredentials(username, password)) {
                MiddleFactory mlf = new LocalMiddleFactory();
                mainapp.startCashierGUI_MVC(mlf);
                mainapp.startPackingGUI_MVC(mlf);
                mainapp.startBackDoorGUI_MVC(mlf);
                view.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(view, "Invalid credentials, please try again",
                        "Login Error",
                        JOptionPane.ERROR_MESSAGE);

            }

        }

    }

    private class GuestButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            mainapp.startCustomerGUI_MVC(mlf);
            view.setVisible(false);


        }
    }
}
