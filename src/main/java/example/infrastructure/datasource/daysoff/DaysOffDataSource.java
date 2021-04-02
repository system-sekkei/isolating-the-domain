package example.infrastructure.datasource.daysoff;

import example.application.repository.DaysOffRepository;
import example.domain.model.daysoff.DaysOff;
import org.springframework.stereotype.Repository;

@Repository
public class DaysOffDataSource implements DaysOffRepository {
    DaysOffMapper mapper;

    @Override
    public void registerDaysOff(DaysOff daysOff) {
        mapper.deleteDaysOff(daysOff.employeeNumber(), daysOff.date());
        mapper.insertDaysOffHistory(daysOff.employeeNumber(), daysOff.date());
        mapper.insertDaysOff(daysOff.employeeNumber(), daysOff.date());
    }

    @Override
    public void deleteDaysOff(DaysOff daysOff) {
        mapper.deleteDaysOff(daysOff.employeeNumber(), daysOff.date());
    }

    DaysOffDataSource(DaysOffMapper mapper) {
        this.mapper = mapper;
    }
}
