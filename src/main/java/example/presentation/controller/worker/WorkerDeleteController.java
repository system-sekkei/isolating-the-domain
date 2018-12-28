package example.presentation.controller.worker;

import example.application.service.worker.WorkerQueryService;
import example.application.service.worker.WorkerRecordService;
import example.domain.model.worker.Worker;
import example.domain.model.worker.WorkerNumber;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 従業員の削除
 */
@Controller
@RequestMapping("workers/{workerNumber}/delete")
public class WorkerDeleteController {

    WorkerRecordService workerRecordService;
    WorkerQueryService workerQueryService;

    @GetMapping(value = "")
    String deleteThenRedirect(@PathVariable(value = "workerNumber") WorkerNumber workerNumber) {
        Worker worker = workerQueryService.choose(workerNumber);
        workerRecordService.expireContract(worker);
        return "redirect:/workers";
    }

    WorkerDeleteController(WorkerRecordService workerRecordService, WorkerQueryService workerQueryService) {
        this.workerRecordService = workerRecordService;
        this.workerQueryService = workerQueryService;
    }
}
