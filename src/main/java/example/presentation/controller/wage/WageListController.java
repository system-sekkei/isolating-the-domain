package example.presentation.controller.wage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 従業員ごとの時給の一覧
 */
@Controller
@RequestMapping("wages/{workerNumber}")
public class WageListController {

    @GetMapping("")
    public String list(Model model) {
        return "wage/list";
    }

}
