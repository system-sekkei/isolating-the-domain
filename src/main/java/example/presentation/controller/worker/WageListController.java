package example.presentation.controller.worker;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("worker/wage/list")
public class WageListController {

    @GetMapping("")
    public String list(Model model) {
        return "worker/wage/list";
    }

}
