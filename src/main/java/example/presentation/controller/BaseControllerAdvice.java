package example.presentation.controller;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * Created by Masuda on 2016/04/01.
 */

@ControllerAdvice
public class BaseControllerAdvice {

    private static final String[] allowFields ;
    private static final String[] disallowFields;

    static {
        allowFields = new String[] {
                "to be specified",
        };

        disallowFields = new String[] {
                "protected*",
        };
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.initDirectFieldAccess();
        binder.setAllowedFields(allowFields);
        binder.setDisallowedFields(disallowFields);

        binder.registerCustomEditor(Object.class, new StringTrimmerEditor(false));
    }
}
