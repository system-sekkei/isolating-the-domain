package example.application.service;

import example.Application;
import example.application.service.contract.ContractQueryService;
import example.domain.model.contract.ContractHistory;
import example.domain.model.worker.WorkerNumber;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
public class ContractQueryServiceTest {
    @Autowired
    ContractQueryService sutQuery;

    @DisplayName(("時給変遷が取得できる事"))
    @Test
    void contractHistory() {
        ContractHistory contractHistory = sutQuery.getContractHistory(new WorkerNumber(6));
        assertEquals(2, contractHistory.history().size());
    }
}
