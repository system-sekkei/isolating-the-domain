package example.domain.model.contract;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 労働条件一覧
 */
public class ContractConditions {
    List<ContractCondition> list;

    public ContractConditions(List<ContractCondition> contractConditions) {
        this.list = contractConditions;
    }

    public List<ContractCondition> list() {
        return list.stream()
                .sorted((c1, c2) -> c2.effectiveDate().value().value.compareTo(c1.effectiveDate().value().value))
                .collect(Collectors.toList());
    }
}
