package example.presentation.controller.employee;

import example.application.coordinator.employee.EmployeeRecordCoordinator;
import example.domain.model.employee.EmployeeNumber;
import example.domain.model.employee.EmployeeToRegister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 従業員の登録
 */
@Controller
@RequestMapping("employees/register")
@SessionAttributes({"employeeToRegister"})
class EmployeeRegisterController {

    private static final String[] allowFields =
            {
                    "name.value",
                    "mailAddress.value",
                    "phoneNumber.value",
            };

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields(allowFields);
    }

    EmployeeRecordCoordinator employeeRecordCoordinator;

    @GetMapping
    String clearSessionAtStart(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "forward:/employees/register/input";
    }

    @GetMapping("input")
    String showForm(Model model) {
        model.addAttribute("employeeToRegister", EmployeeToRegister.blank());
        return "employee/register/form";
    }

    @GetMapping("input/again")
    String showFormToModify() {
        return "employee/register/form";
    }

    @PostMapping("confirm")
    String validate(@Validated @ModelAttribute("employeeToRegister") EmployeeToRegister employeeToRegister,
                    BindingResult result) {
        if (result.hasErrors()) return "employee/register/form";

        return "employee/register/confirm";
    }

    @GetMapping("register")
    String registerThenRedirectAndClearSession(
            @ModelAttribute("employeeToRegister") EmployeeToRegister employeeToRegister,
            SessionStatus status, RedirectAttributes attributes) {
        EmployeeNumber employeeNumber = employeeRecordCoordinator.register(employeeToRegister);
        status.setComplete();

        attributes.addAttribute("name", employeeToRegister.name());
        attributes.addAttribute("employeeNumber", employeeNumber);

        return "redirect:/employees/register/completed";
    }

    @GetMapping("completed")
    String showResult(Model model,
                      @RequestParam("name") String name,
                      @RequestParam("employeeNumber") String employeeNumber) {
        model.addAttribute("name", name);
        model.addAttribute("employeeNumber", employeeNumber);
        return "employee/register/result";
    }

    public EmployeeRegisterController(EmployeeRecordCoordinator employeeRecordCoordinator) {
        this.employeeRecordCoordinator = employeeRecordCoordinator;
    }
}
