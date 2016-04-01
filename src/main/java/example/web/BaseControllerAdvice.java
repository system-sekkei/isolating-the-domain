package example.web;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * Created by Masuda on 2016/04/01.
 */

@ControllerAdvice
public class BaseControllerAdvice {
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.initDirectFieldAccess();
        binder.setAllowedFields("to be specified");
        binder.setDisallowedFields("id", "identifier");
    }
}
