package example.domain.model.contract;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 契約の変遷
 */
public class ContractHistory {
    List<Contract> list;

    public ContractHistory(Contracts contracts) {
        this.list = contracts.list;
    }

    public List<Contract> list() {
        return list.stream()
                .sorted((c1, c2) -> c2.startDate().value().compareTo(c1.startDate().value()))
                .collect(Collectors.toList());
    }
}
