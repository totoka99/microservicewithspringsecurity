package jpasecurity.jpasecurity.expcetion;

public class UsernameIsTakenException extends RuntimeException {
    private final String username;

    public UsernameIsTakenException(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
