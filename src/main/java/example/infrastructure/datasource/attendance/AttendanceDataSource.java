package example.infrastructure.datasource.attendance;

import example.domain.model.attendance.AttendanceRepository;
import example.domain.model.attendance.TimeRecord;
import example.domain.model.user.UserIdentifier;
import example.domain.type.date.Date;
import org.springframework.stereotype.Repository;

@Repository
public class AttendanceDataSource implements AttendanceRepository {
    AttendanceMapper mapper;
    @Override
    public void registerWorkTime(UserIdentifier userId, Date workDay, TimeRecord workTime) {
        Long identifier = mapper.newWorkTimeIdentifier();
        mapper.registerWorkTime(identifier, userId, workDay, workTime);
        mapper.deleteWorkTimeMapper(userId, workDay);
        mapper.registerWorkTimeMapper(identifier, userId, workDay);
    }

    @Override
    public TimeRecord findBy(UserIdentifier userId, Date workDay) {
        return mapper.findBy(userId, workDay);
    }

    AttendanceDataSource(AttendanceMapper mapper){this.mapper = mapper;}
}
