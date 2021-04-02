package example.domain.model.daysoff;

import java.util.List;

/**
 * 休日一覧
 */
public class DaysOffRecords {
    List<DaysOff> list;

    public DaysOffRecords(List<DaysOff> list) {
        this.list = list;
    }
}
