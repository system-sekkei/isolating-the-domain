package example.domain.model.attendance;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 勤務日
 */
public class WorkDay {
    LocalDate value;

    public WorkDay() {
        this(LocalDate.now());
    }

    public WorkDay(LocalDate value){
        this.value = value;
    }

    public LocalDate value(){
        return value;
    }

    @Override
    public String toString() {
        return value.format(DateTimeFormatter.ISO_DATE);
    }
}
