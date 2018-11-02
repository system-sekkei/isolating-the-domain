package example.infrastructure.datasource.payroll;

import example.domain.model.payroll.PayrollRepository;
import example.domain.model.payroll.TimeRecord;
import example.domain.model.user.UserIdentifier;
import example.domain.type.date.DayOfMonth;
import org.springframework.stereotype.Repository;

@Repository
public class PayrollDataSource implements PayrollRepository {
    @Override
    public void registerWorkTime(UserIdentifier userId, DayOfMonth workDay, TimeRecord workTime) {

    }
}
