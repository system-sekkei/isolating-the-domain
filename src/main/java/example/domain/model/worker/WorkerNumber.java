package example.domain.model.worker;

/**
 * 従業員番号
 */
public class WorkerNumber {

    Long value;

    public WorkerNumber() {
    }

    public WorkerNumber(String value) {
        this.value = Long.parseLong(value);
    }

    public WorkerNumber(Long value) {
        this.value = value;
    }

    public Long value() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
