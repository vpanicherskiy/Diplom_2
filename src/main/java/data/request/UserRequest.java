package data.request;

public class UserRequest {
    private String email;
    private String password;
    private String name;

    public UserRequest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public UserRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
