package example.domain.model.attendance;

import example.domain.model.labour_standards_law.Midnight;
import example.domain.type.time.ClockTime;
import example.domain.type.time.ClockTimeRange;
import example.domain.type.time.Minute;

import java.time.Clock;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * 業務時刻の範囲
 */
public class WorkTimeRange {

    private final WorkStartTime startTime;
    private final WorkEndTime endTime;

    public WorkTimeRange(WorkStartTime startTime, WorkEndTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public WorkStartTime start() {
        return startTime;
    }

    public WorkEndTime end() {
        return endTime;
    }

    public ClockTimeRange toTimeRange() {
        return new ClockTimeRange(startTime.value, endTime.value);
    }

    public static WorkTimeRange of(ClockTimeRange timeRange) {
        return new WorkTimeRange(new WorkStartTime(timeRange.begin()), new WorkEndTime(timeRange.end()));
    }

    public Minute totalWorkMinute() {
        return startTime.normalizedHourTime().until(endTime.normalizedHourTime());
    }

    public Minute workMinute() {
        return totalWorkMinute().subtract(midnightWorkMinute());
    }

    public Minute midnightWorkMinute() {
        ClockTimeRange clockTimeRange = toTimeRange();
        List<ClockTime> midnightTickes = new ArrayList<>();
        midnightTickIterator().forEachRemaining(midnightTickes::add);
        List<ClockTime> includeMidnidhtTickes = midnightTickes.stream()
                .filter(ct -> clockTimeRange.include(ct)).collect(Collectors.toList());
        return new Minute((includeMidnidhtTickes.size() - rangeStartCount(includeMidnidhtTickes)) * ClockTime.tickPeriod().value());
    }

    private int rangeStartCount(List<ClockTime> ticks) {
        if(ticks.size() == 0) {
            return 0;
        }
        for(int i = 1 ; i < ticks.size() ; i++) {
            LocalTime last = ticks.get(i - 1).value();
            LocalTime current = ticks.get(i).value();
            if(!last.plusMinutes(ClockTime.tickPeriod().value()).equals(current)) {
                return 2;
            }
        }
        return 1;
    }

    static Iterator<ClockTime> midnightTickIterator() {
        return new Iterator<ClockTime>() {
            private ClockTime next = Midnight.legal().range().begin();

            @Override
            public boolean hasNext() {
                return (Midnight.legal().range().include(next));
            }

            @Override
            public ClockTime next() {
                if(!hasNext()) throw new NoSuchElementException();
                ClockTime ret = next;
                next = new ClockTime(next.value().plusMinutes(ClockTime.tickPeriod().value()));
                return ret;
            }
        };
    }
}
