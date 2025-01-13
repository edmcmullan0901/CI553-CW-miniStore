package clients.login;

public class LoginModel {
    private final String Username = "admin123";
    private final String Password = "password123";

    public boolean validateCredentials(String username, String password) {
        return username.equals(Username) && password.equals(Password);
    }
}
