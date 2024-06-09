package jpasecurity.jpasecurity.handler;

import jpasecurity.jpasecurity.expcetion.EmailAddressIsTakenException;
import jpasecurity.jpasecurity.expcetion.NoteNotFoundException;
import jpasecurity.jpasecurity.expcetion.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(EmailAddressIsTakenException.class)
    public ProblemDetail handleEmailAddressIsTakenException(EmailAddressIsTakenException e) {
        ProblemDetail problemDetail = ProblemDetail
                .forStatusAndDetail(HttpStatus.CONFLICT,
                        String.format("This Email address is already in use: %s", e.getUsername()));
        problemDetail.setTitle("Email address is already taken");
        problemDetail.setType(URI.create("email-address-is-taken"));
        return problemDetail;
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ProblemDetail handleUserNotFoundException(UserNotFoundException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                String.format("User not found with id: %d", e.getId()));
        problemDetail.setTitle("User not found");
        problemDetail.setType(URI.create("user-not-found"));
        return problemDetail;
    }

    @ExceptionHandler(NoteNotFoundException.class)
    public ProblemDetail handleExampleNotFoundException(NoteNotFoundException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                String.format("Example not found with id: %d", e.getId())
        );
        problemDetail.setTitle("Example not found");
        problemDetail.setType(URI.create("example-not-found"));
        return problemDetail;
    }
}
