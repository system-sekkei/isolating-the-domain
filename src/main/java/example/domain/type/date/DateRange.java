package example.domain.type.date;

/**
 * 日付範囲
 */
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
}
