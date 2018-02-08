public class RegisterMessage extends AbstractMessage {
    private String login;
    private String password;
    private String email;

    public RegisterMessage(String login, String password, String email) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() { return login; }

    public String getPassword() { return password;
    }

    public String getEmail() { return email;
    }
}

