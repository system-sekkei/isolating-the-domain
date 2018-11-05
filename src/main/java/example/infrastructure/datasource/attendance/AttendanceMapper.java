package example.infrastructure.datasource.attendance;

import example.domain.model.attendance.AttendanceOfDay;
import example.domain.model.attendance.TimeRecord;
import example.domain.model.user.UserIdentifier;
import example.domain.type.date.Date;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AttendanceMapper {
    long newWorkTimeIdentifier();
    void registerWorkTime(@Param("id") Long id, @Param("userId") UserIdentifier userId, @Param("work") AttendanceOfDay work);
    void registerWorkTimeMapper(@Param("workTimeId") Long workTimeId,
                                @Param("userId") UserIdentifier userId, @Param("workDay") Date workDay);
    void deleteWorkTimeMapper(@Param("userId") UserIdentifier userId, @Param("workDay") Date workDay);

    AttendanceOfDay findBy(@Param("userId") UserIdentifier userId, @Param("workDay") Date workDay);
}
