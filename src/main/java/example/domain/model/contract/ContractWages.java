package example.domain.model.contract;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 契約給与一覧
 */
public class ContractWages {
    List<ContractWage> list;

    public ContractWages(List<ContractWage> contractWages) {
        this.list = contractWages;
    }

    public List<ContractWage> list() {
        return list.stream()
                .sorted((c1, c2) -> c2.startDate().value().compareTo(c1.startDate().value()))
                .collect(Collectors.toList());
    }
}
