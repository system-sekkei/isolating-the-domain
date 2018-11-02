package example.application.service;

import example.domain.model.attendance.PayrollRepository;
import example.domain.model.attendance.TimeRecord;
import example.domain.model.user.UserIdentifier;
import example.domain.type.date.DayOfMonth;
import org.springframework.stereotype.Service;

@Service
public class PayrollService {
    PayrollRepository payrollRepository;

    public void registerWorkTime(UserIdentifier userId, DayOfMonth workDay, TimeRecord workTime) {
        payrollRepository.registerWorkTime(userId, workDay, workTime);
    }

    PayrollService(PayrollRepository payrollRepository) {
        this.payrollRepository = payrollRepository;
    }
}
