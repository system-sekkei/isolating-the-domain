package example.domain.type.date;

/**
 * 年
 */
public class Year {
    Integer year;

    public Year(Integer year) {
        this.year = year;
    }

    public Year(String year) {
        this(Integer.parseInt(year));
    }

    public Integer value() {
        return year;
    }

    public String toString() {
        return year.toString();
    }
}
