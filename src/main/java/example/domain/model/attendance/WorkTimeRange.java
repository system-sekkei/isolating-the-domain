package example.domain.model.attendance;

import example.domain.model.labour_standards_law.Midnight;
import example.domain.type.time.ClockTime;
import example.domain.type.time.ClockTimeRange;
import example.domain.type.time.Minute;

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

    WorkStartTime startTime;
    WorkEndTime endTime;

    @Deprecated
    WorkTimeRange() {
    }

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

    public BindingTime bindingTime() {
        return new BindingTime(startTime.normalizedHourTime().until(endTime.normalizedHourTime()));
    }

    public Minute normalBindingTime() {
        return bindingTime().minute().subtract(midnightWorkMinute());
    }

    public Minute midnightWorkMinute() {
        ClockTimeRange clockTimeRange = toTimeRange();
        List<ClockTime> midnightTicks = getMidnightTicks();
        List<ClockTime> includeMidnightTicks = midnightTicks.stream()
                .filter(ct -> clockTimeRange.include(ct)).collect(Collectors.toList());
        return new Minute((includeMidnightTicks.size() - rangeStartCount(includeMidnightTicks)) * ClockTime.tickPeriod().value());
    }

    private static List<ClockTime> midnightTicks;

    static {
        midnightTicks = new ArrayList<>();
        midnightTickIterator().forEachRemaining(midnightTicks::add);
    }

    private List<ClockTime> getMidnightTicks() {
        return midnightTicks;
    }

    private int rangeStartCount(List<ClockTime> ticks) {
        if (ticks.size() == 0) {
            return 0;
        }
        LocalTime last = ticks.get(0).value();
        for (int i = 1; i < ticks.size(); i++) {
            LocalTime current = ticks.get(i).value();
            if (!last.plusMinutes(ClockTime.tickPeriod().value()).equals(current)) {
                return 2;
            }
            last = current;
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
                if (!hasNext()) throw new NoSuchElementException();
                ClockTime ret = next;
                next = new ClockTime(next.value().plusMinutes(ClockTime.tickPeriod().value()));
                return ret;
            }
        };
    }
}
