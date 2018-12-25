package example.presentation.controller.worker;

import example.application.service.contract.ContractQueryService;
import example.application.service.worker.WorkerQueryService;
import example.domain.model.contract.WorkerContracts;
import example.domain.model.worker.ContractingWorkers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 従業員の一覧
 */
@Controller
@RequestMapping("workers")
public class WorkersController {

    WorkerQueryService workerQueryService;
    ContractQueryService contractQueryService;

    @GetMapping("")
    String workers(Model model) {
        ContractingWorkers contractingWorkers = workerQueryService.contractingWorkers();
        WorkerContracts workerContracts = contractQueryService.findWorkerContracts(contractingWorkers);

        model.addAttribute("workerContracts", workerContracts);
        return "worker/list";
    }

    WorkersController(WorkerQueryService workerQueryService, ContractQueryService contractQueryService) {
        this.workerQueryService = workerQueryService;
        this.contractQueryService = contractQueryService;
    }
}
