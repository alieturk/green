package app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;



@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class GroupNotFound extends RuntimeException{
    public GroupNotFound(String message) {
        super(message);
    }

}
