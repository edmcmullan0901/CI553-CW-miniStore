package clients.login;

import clients.Main;
import middle.MiddleFactory;

public class LoginClient {
    private MiddleFactory mlf;
    private LoginModel model;
    private LoginView view;
    private LoginController controller;

    public LoginClient(Main mainApp, MiddleFactory mlf) {
        model = new LoginModel();
        view = new LoginView();
        controller = new LoginController(model, view, mainApp, mlf);
        view.setVisible(true);
    }
}
