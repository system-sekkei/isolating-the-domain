package example.application.service.daysoff;

import example.application.repository.DaysOffRepository;
import example.domain.model.daysoff.DaysOff;
import org.springframework.stereotype.Service;

/**
 * 休日登録サービス
 */
@Service
public class DaysOffRecordService {
    DaysOffRepository daysOffRepository;

    DaysOffRecordService(DaysOffRepository daysOffRepository) {
        this.daysOffRepository = daysOffRepository;
    }

    public void registerDaysOffRecord(DaysOff daysOff, boolean isDaysOff) {
        if (isDaysOff) {
            daysOffRepository.registerDaysOff(daysOff);
        } else {
            daysOffRepository.deleteDaysOff(daysOff);
        }
    }
}
