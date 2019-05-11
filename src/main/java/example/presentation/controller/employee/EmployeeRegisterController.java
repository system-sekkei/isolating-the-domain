package example.presentation.controller.employee;

import example.application.coordinator.employee.EmployeeRecordCoordinator;
import example.domain.model.employee.EmployeeNumber;
import example.domain.model.employee.Name;
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
@SessionAttributes({"newEmployee"})
class EmployeeRegisterController {

    private static final String[] accept =
            {
                    "name.value",
                    "mailAddress.value",
                    "phoneNumber.value",
            };

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields(accept);
    }

    EmployeeRecordCoordinator employeeRecordCoordinator;

    @GetMapping(value = "")
    String clearSessionAtStart(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "forward:/employees/register/input";
    }

    @GetMapping(value = "input")
    String showForm(Model model) {
        NewEmployee newEmployee = new NewEmployee();
        model.addAttribute("newEmployee", newEmployee);
        return "employee/register/form";
    }

    @GetMapping(value = "input/again")
    String showFormToModify() {
        return "employee/register/form";
    }

    @PostMapping(value = "confirm")
    String validate(@Validated @ModelAttribute("newEmployee") NewEmployee newEmployee,
                    BindingResult result) {
        if (result.hasErrors()) return "employee/register/form";

        return "employee/register/confirm";
    }

    @GetMapping(value = "register")
    String registerThenRedirectAndClearSession(
            @ModelAttribute("newEmployee") NewEmployee newEmployee,
            SessionStatus status, RedirectAttributes attributes) {
        Name name = newEmployee.name();
        EmployeeNumber employeeNumber = employeeRecordCoordinator.register(newEmployee.profile());
        status.setComplete();

        attributes.addAttribute("name", name);
        attributes.addAttribute("employeeNumber", employeeNumber);

        return "redirect:/employees/register/completed";
    }

    @GetMapping(value = "completed")
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
