package example.domain.model.timerecord.evaluation;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WorkTimesTest {
    @Test
    void 一日８時間を超える労働時間の週の集計を計算できる() {
        WorkTimes weeklyWorkTimes = new WorkTimes(List.of(
                new WorkTime(new DaytimeWorkTime(400), new NightWorkTime(0)),
                new WorkTime(new DaytimeWorkTime(500), new NightWorkTime(0)),
                new WorkTime(new DaytimeWorkTime(480), new NightWorkTime(0)),
                new WorkTime(new DaytimeWorkTime(480), new NightWorkTime(60)),
                new WorkTime(new DaytimeWorkTime(600), new NightWorkTime(30)),
                new WorkTime(new DaytimeWorkTime(600), new NightWorkTime(0))));

        OverLegalHoursWorkTime overLegalHoursWorkTime = weeklyWorkTimes.dailyOverLegalHoursWorkTimePerWeek();
        assertEquals("350", overLegalHoursWorkTime.quarterHour().minute().toString());
    }

    @Test
    void 週４０時間を超える労働時間を計算できる() {
        WorkTimes weeklyWorkTimes = new WorkTimes(List.of(
                new WorkTime(new DaytimeWorkTime(400), new NightWorkTime(0)),
                new WorkTime(new DaytimeWorkTime(500), new NightWorkTime(0)),
                new WorkTime(new DaytimeWorkTime(480), new NightWorkTime(0)),
                new WorkTime(new DaytimeWorkTime(480), new NightWorkTime(60)),
                new WorkTime(new DaytimeWorkTime(600), new NightWorkTime(30)),
                new WorkTime(new DaytimeWorkTime(600), new NightWorkTime(0))));

        OverLegalHoursWorkTime overLegalHoursWorkTime = weeklyWorkTimes.weeklyOverLegalHoursWorkTime();
        assertEquals("750", overLegalHoursWorkTime.quarterHour().minute().toString());
    }
}