package example.application.service.contract;

import example.application.repository.ContractRepository;
import example.domain.model.contract.Contract;
import example.domain.model.contract.ContractHistory;
import example.domain.model.contract.Contracts;
import example.domain.model.contract.HourlyWage;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.Date;
import org.springframework.stereotype.Service;

/**
 * 契約参照サービス
 */
@Service
public class ContractQueryService {
    ContractRepository contractRepository;

    /**
     * 時給取得
     */
    public HourlyWage getHourlyWage(WorkerNumber workerNumber, Date workDay) {
        return contractRepository.getHourlyWage(workerNumber, workDay);
    }

    /**
     *　雇用契約変遷
     */
    public Contracts getContracts(WorkerNumber workerNumber, Date startDate, Date endDate) {
        return contractRepository.getContracts(workerNumber, startDate, endDate);
    }

    public Contracts getContracts2(WorkerNumber workerNumber, Date startDate, Date endDate) {
        return contractRepository.getContracts2(workerNumber, startDate, endDate);
    }

    public Contract getContract(WorkerNumber workerNumber, Date date) {
        return contractRepository.getContract(workerNumber, date);
    }

    public ContractHistory getContractHistory(WorkerNumber workerNumber) {
        return contractRepository.getContractHistory(workerNumber);
    }

    ContractQueryService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

}
