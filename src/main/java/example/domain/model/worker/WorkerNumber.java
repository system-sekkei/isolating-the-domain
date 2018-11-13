package example.domain.model.worker;

/**
 * 従業員番号
 */
public class WorkerNumber {

    Integer value;

    public WorkerNumber() {
    }

    public WorkerNumber(String value) {
        this.value = Integer.parseInt(value);
    }

    public WorkerNumber(Integer value) {
        this.value = value;
    }

    public Integer value() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
