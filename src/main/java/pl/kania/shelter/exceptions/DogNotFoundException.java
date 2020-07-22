package pl.kania.shelter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class DogNotFoundException extends RuntimeException {

    public DogNotFoundException(String message) {
        super(message);
    }
}
