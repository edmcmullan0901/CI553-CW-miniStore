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
        } else {
            view.setVisible(true);
        }
    }


    public LoginView getView() {
        return this.view;
    }

    public static LoginClient getInstance(Main mainApp, MiddleFactory mlf) {
        if (instance == null) {
            if (mainApp == null) {
                throw new IllegalArgumentException("mainApp is null");
            }
            if (mlf == null) {
                throw new IllegalArgumentException("mlf is null");
            }
            System.out.println("Creating new loginclient instance");
            instance = new LoginClient(mainApp, mlf);
        } else {
            if (instance.view != null && instance.view.isVisible()) {
                instance.view.setVisible(true);
            }
        }
        return instance;
    }


    public static void closeLoginView() {
        if (instance != null) {
            if (instance.view != null) {
                instance.view.setVisible(false);
            }
            isLoginViewOpen = false;
            instance.view.setVisible(false);
        }

    }
}
