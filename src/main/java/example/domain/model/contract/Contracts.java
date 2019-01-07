package example.domain.model.contract;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 雇用契約一覧
 */
public class Contracts {
    List<Contract> list;

    public Contracts(List<Contract> contracts) {
        this.list = contracts;
    }

    public List<Contract> list() {
        return list.stream()
                .sorted((c1, c2) -> c2.startDate().value().compareTo(c1.startDate().value()))
                .collect(Collectors.toList());
    }
}
