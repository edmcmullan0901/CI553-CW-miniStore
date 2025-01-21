package clients.login;
import clients.Main;
import middle.MiddleFactory;
import middle.LocalMiddleFactory;
import middle.StockException;


import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoginController {
    private LoginModel model;
    private LoginView view;
    private Main mainApp;
    private MiddleFactory mlf;

    public LoginController(LoginModel model, LoginView view, Main mainApp, MiddleFactory mlf) {
        this.model= model;
        this.view = view;
        this.mainApp = mainApp;
        this.mlf = mlf;

        view.addLoginListener(new LoginButtonListener());
        view.addGuestListener(new GuestButtonListener());


        System.out.println("Login controller initialised with mainapp " + this.mainApp);

            }

    private class LoginButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("mainApp in LoginButtonListener: " + mainApp);
            String username = view.getUsername();
            String password = view.getPassword();

            if (mainApp == null) {
                System.out.println("Error: mainApp is null. Cannot proceed with login.");
                return;
            }

            if (model.validateCredentials(username, password)) {
                System.out.println("Login successful, closing login window.");
                view.setVisible(false);
                LoginClient.closeLoginView();
                try {
                    MiddleFactory mlf = new LocalMiddleFactory();
                } catch (StockException ex) {
                    throw new RuntimeException(ex);
                }

                System.out.println("mainApp before checking if started: " + mainApp);
                if (mainApp != null) {
                    if (!mainApp.isCashierStarted()) {
                        mainApp.startCashierGUI_MVC(mlf);
                        mainApp.setCashierStarted(true);
                    }
                    if (!mainApp.isPackingStarted()) {
                        mainApp.startPackingGUI_MVC(mlf);
                        mainApp.setPackingStarted(true);
                    }
                    if (!mainApp.isBackDoorStarted()) {
                        mainApp.startBackDoorGUI_MVC(mlf);
                        mainApp.setBackDoorStarted(true);
                    }

                } else {
                    System.out.println("mainApp is null when attempting to start other clients.");
                }
            } else {
                JOptionPane.showMessageDialog(view, "Invalid credentials, " +
                                "please try again",
                        "Login Error",
                        JOptionPane.ERROR_MESSAGE);

            }

        }

    }

    private class GuestButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if (mainApp != null) {
                System.out.println("Guest login, closing login window.");
                view.setVisible(false);
                LoginClient.closeLoginView();
                mainApp.startCustomerGUI_MVC(mlf);
            }



        }
    }
}
