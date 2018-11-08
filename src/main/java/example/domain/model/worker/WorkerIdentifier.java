package example.domain.model.worker;

/**
 * 従業員識別子
 */
public class WorkerIdentifier {

    Long value;

    public WorkerIdentifier() {
    }

    public WorkerIdentifier(String value) {
        this.value = Long.parseLong(value);
    }

    public WorkerIdentifier(Long value) {
        this.value = value;
    }

    public Long value() { return value; }
    @Override
    public String toString() {
        return value.toString();
    }
}
