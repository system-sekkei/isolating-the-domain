package example.domain.model.contract;

import java.time.LocalDate;
import java.util.*;

public class ContractHistory {
    List<Contract> contracts;

    public ContractHistory(List<Contract> contracts) {
        this.contracts = sort(contracts);
    }

    private List<Contract> sort(List<Contract> contracts) {
        SortedMap<LocalDate, Contract> map = new TreeMap<>();
        contracts.forEach(contract -> map.put(contract.startDate().value(), contract));
        ArrayList<Contract> ret = new ArrayList<>(map.values());
        Collections.reverse(ret);
        return ret;
    }

    public List<Contract> history() {
        return contracts;
    }
}
