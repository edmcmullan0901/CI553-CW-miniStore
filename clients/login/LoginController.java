package clients.login;
import clients.EmployeeMenuView;
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
    private EmployeeMenuView employeeMenuView;

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
        @Override //validates user credentials and navigates to employee menu
        public void actionPerformed(ActionEvent e) {
            System.out.println("mainApp in LoginButtonListener: " + mainApp);
            String username = view.getUsername(); //retrieves user input
            String password = view.getPassword();

            if (mainApp == null) {
                System.out.println("Error: mainApp is null. Cannot proceed with login.");
                return;
            }

            if (model.validateCredentials(username, password)) { // validates credentials
                System.out.println("Login successful, closing login window.");
                view.setVisible(false);
                LoginClient.closeLoginView();

                employeeMenuView = new EmployeeMenuView();
                employeeMenuView.setVisible(true);

                employeeMenuView.addCashierButtonListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        startCashierGUI();
                    }
                });

                employeeMenuView.addPackingButtonListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        startPackingGUI();
                    }
                });

                employeeMenuView.addBackDoorListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        startBackDoorGUI();
                    }
                });

            } else {
                JOptionPane.showMessageDialog(view, "Invalid credentials, please try again",
                        "Login Error", JOptionPane.ERROR_MESSAGE);

            }





        }

    }

    private class GuestButtonListener implements ActionListener{
        @Override //listener for guest button, navigates to customer client
        public void actionPerformed(ActionEvent e) {
            if (mainApp != null) {
                System.out.println("Guest login, closing login window.");
                LoginClient.getInstance(mainApp, mlf).getView().setVisible(false);

                System.out.println("Starting customer client");
                mainApp.startCustomerGUI_MVC(mlf);
            }



        }
    }


    private void startCashierGUI() {
        if (!mainApp.isCashierStarted()) {
            mainApp.startCashierGUI_MVC(mlf);
            mainApp.setCashierStarted(true);
        }
    }
    private void startPackingGUI() {
        if (!mainApp.isPackingStarted()) {
            mainApp.startPackingGUI_MVC(mlf);
            mainApp.setPackingStarted(true);
        }
    }
    private void startBackDoorGUI() {
        if (!mainApp.isBackDoorStarted()) {
            mainApp.startBackDoorGUI_MVC(mlf);
            mainApp.setBackDoorStarted(true);
        }
    }
}
