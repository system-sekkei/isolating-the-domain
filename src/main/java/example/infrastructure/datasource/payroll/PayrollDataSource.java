package example.infrastructure.datasource.payroll;

import example.domain.model.payroll.PayrollRepository;
import example.domain.model.payroll.TimeRecord;
import example.domain.model.user.UserIdentifier;
import example.domain.type.date.DayOfMonth;
import org.springframework.stereotype.Repository;

@Repository
public class PayrollDataSource implements PayrollRepository {
    PayrollMapper mapper;
    @Override
    public void registerWorkTime(UserIdentifier userId, DayOfMonth workDay, TimeRecord workTime) {
        Long identifier = mapper.newWorkTimeIdentifier();
        mapper.registerWorkTime(identifier, userId, workDay, workTime);
        mapper.deleteWorkTimeMapper(userId, workDay);
        mapper.registerWorkTimeMapper(identifier, userId, workDay);
    }

    PayrollDataSource(PayrollMapper mapper){this.mapper = mapper;}
}
