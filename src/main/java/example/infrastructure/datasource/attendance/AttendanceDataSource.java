package example.infrastructure.datasource.attendance;

import example.domain.model.attendance.AttendanceOfDay;
import example.domain.model.attendance.AttendanceRepository;
import example.domain.model.attendance.TimeRecord;
import example.domain.model.user.UserIdentifier;
import example.domain.type.date.Date;
import org.springframework.stereotype.Repository;

@Repository
public class AttendanceDataSource implements AttendanceRepository {
    AttendanceMapper mapper;
    @Override
    public void registerWorkTime(UserIdentifier userId, AttendanceOfDay work) {
        Long identifier = mapper.newWorkTimeIdentifier();
        mapper.registerWorkTime(identifier, userId, work);
        mapper.deleteWorkTimeMapper(userId, work.dayOfMonth());
        mapper.registerWorkTimeMapper(identifier, userId, work.dayOfMonth());
    }

    @Override
    public AttendanceOfDay findBy(UserIdentifier userId, Date workDay) {
        return mapper.findBy(userId, workDay);
    }

    AttendanceDataSource(AttendanceMapper mapper){this.mapper = mapper;}
}
