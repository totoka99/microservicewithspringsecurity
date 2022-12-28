package jpasecurity.jpasecurity.expcetion;

public class ExampleNotFoundException extends RuntimeException {
    private final Long id;

    public ExampleNotFoundException(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
