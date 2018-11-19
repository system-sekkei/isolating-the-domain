package example.domain.model.contract;

import java.util.List;

public class Contracts {
    List<Contract> value;
    public Contracts(List<Contract> contracts) {
        this.value = contracts;
    }
    public List<Contract> value() { return value; }
}
