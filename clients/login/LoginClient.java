package clients.login;

import clients.Main;
import middle.MiddleFactory;

import javax.sound.midi.MidiDevice;

public class LoginClient {


    private static LoginClient instance;
    private Main mainApp;

    private MiddleFactory mlf;
    private LoginModel model;
    private LoginView view;
    private LoginController controller;

    private static boolean isLoginViewOpen = false;


    private LoginClient(Main mainApp, MiddleFactory mlf) {

        if (mainApp == null) {
            throw new IllegalArgumentException("mainApp cannot be null");
        }

        this.mainApp = mainApp;
        System.out.println("loginclient intiialised with mainApp " + this.mainApp);
        this.mlf = mlf;

        if (view == null || !isLoginViewOpen) {


            model = new LoginModel();
            view = new LoginView();
            controller = new LoginController(model, view, mainApp, mlf);

            view.setVisible(true);
            isLoginViewOpen = true;

        }
    }


    public LoginView getView() {
        return this.view;
    }

    public static LoginClient getInstance(Main mainApp, MiddleFactory mlf) {
        System.out.println("Creating and showing LoginView");

        // If an instance already exists, just show the view
        if (instance != null) {
            if (instance.view != null && !instance.view.isVisible()) {
                System.out.println("Reusing existing LoginView instance and making it visible");
                instance.view.setVisible(true);  // Make the existing view visible again
                return instance;
            } else {
                System.out.println("Reusing existing LoginView instance and view is visible");
                return instance;
            }
        }


        // If instance is null, create a new one
        if (instance == null) {
            if (mainApp == null || mlf == null) {
                throw new IllegalArgumentException("mainApp or mlf is null");
            }

            System.out.println("Creating new loginclient instance");
            instance = new LoginClient(mainApp, mlf);
        }

        return instance;
    }



    public static void closeLoginView() {
        if (instance != null) {
            if (instance.view != null) {
                System.out.println("Closing login view");

                instance.view.setVisible(false);
            }
            instance = null;
        }

    }
}
