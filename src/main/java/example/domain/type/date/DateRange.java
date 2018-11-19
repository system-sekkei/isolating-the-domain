package example.domain.type.date;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DateRange {
    private Date startDate;
    private Date endDate;

    public DateRange(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date startDate() {
        return startDate;
    }
    public Date endDate() {
        return endDate;
    }

    public List<Date> days() {
        List<Date> ret = new ArrayList<>();
        LocalDate d = startDate.value();
        while(d.compareTo(endDate.value()) <= 0) {
            ret.add(new Date(d));
            d = d.plusDays(1L);
        }
        return ret;
    }
}
