package example.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by haljik on 15/06/02.
 */
@Controller
@RequestMapping("/")
class TopController {

    @RequestMapping
    String show() {
        return "top";
    }

}
