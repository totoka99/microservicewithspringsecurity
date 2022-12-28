package jpasecurity.jpasecurity.expcetion;

public class UserNotFoundException extends RuntimeException {
    private final Long id;

    public UserNotFoundException(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}