package edu.mongo10web.exception;

import com.mongodb.MongoCommandException;
import org.springframework.data.mongodb.UncategorizedMongoDbException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class MongoDatabaseExceptionHandler {
    @ExceptionHandler(UncategorizedMongoDbException.class)
    public String handleMongoException(UncategorizedMongoDbException e, RedirectAttributes redirectAttributes){
        Throwable rootCause = e.getMostSpecificCause();
        String displayMessage = "Database error. ";
        if (rootCause instanceof MongoCommandException) {
            MongoCommandException mongoEx = (MongoCommandException) rootCause;
            if(mongoEx.getErrorCode()==13) displayMessage += "Access denied. Error code: " + mongoEx.getErrorCode() + " " + mongoEx.getErrorCodeName();
            else displayMessage += "Unknown error. Error code: " + mongoEx.getErrorCode() + " " + mongoEx.getErrorCodeName() + " " + mongoEx.getErrorMessage();
        } else {
            displayMessage += "Unknown system error: " + e.getMessage();
        }
        redirectAttributes.addFlashAttribute("databaseError", displayMessage);
        return "redirect:/dashboard";
    }
}
