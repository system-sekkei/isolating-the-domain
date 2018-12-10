package example.presentation.controller.worker;

import example.application.service.worker.WorkerQueryService;
import example.application.service.worker.WorkerRecordService;
import example.domain.model.worker.Worker;
import example.domain.model.worker.WorkerNumber;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("workers/{workerNumber}/delete")
public class DeleteController {

    WorkerRecordService workerRecordService;
    WorkerQueryService workerQueryService;

    @GetMapping(value = "view")
    String show(@PathVariable(value = "workerNumber") WorkerNumber workerNumber, Model model) {
        Worker worker = workerQueryService.choose(workerNumber);
        model.addAttribute("worker", worker);

        return "worker/delete/confirm";
    }

    @GetMapping(value = "")
    String deleteThenRedirect(@PathVariable(value = "workerNumber") WorkerNumber workerNumber,
                              Model model, RedirectAttributes attributes) {
        Worker worker = workerQueryService.choose(workerNumber);
        workerRecordService.expireContract(worker);

        attributes.addAttribute("name", worker.name().toString());

        return "redirect:/workers/someone/delete/completed";
    }

    @GetMapping(value = "/completed")
    String showResult(Model model,
                      @RequestParam("name") String name) {
        model.addAttribute("name", name);
        return "worker/delete/result";
    }

    DeleteController(WorkerRecordService workerRecordService, WorkerQueryService workerQueryService) {
        this.workerRecordService = workerRecordService;
        this.workerQueryService = workerQueryService;
    }
}
