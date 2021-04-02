package example.application.repository;

import example.domain.model.daysoff.DaysOff;

/**
 * 休日リポジトリ
 */
public interface DaysOffRepository {

    void registerDaysOff(DaysOff daysOff);

    void deleteDaysOff(DaysOff daysOff);

}
