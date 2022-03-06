package fr.gantoin.githubprojectlinescounter.support;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidURLException extends RuntimeException {

    public InvalidURLException() {
        super("The project url is not valid");
    }
}
