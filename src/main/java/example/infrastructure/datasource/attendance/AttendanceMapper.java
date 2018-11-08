package example.infrastructure.datasource.attendance;

import example.domain.model.attendance.AttendanceOfDay;
import example.domain.model.worker.WorkerIdentifier;
import example.domain.type.date.Date;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AttendanceMapper {
    long newWorkTimeIdentifier();

    void insertWorkTime(@Param("id") Long id, @Param("workerIdentifier") WorkerIdentifier workerIdentifier, @Param("work") AttendanceOfDay work);

    void insertWorkTimeMapper(@Param("workTimeId") Long workTimeId,
                              @Param("workerIdentifier") WorkerIdentifier workerIdentifier, @Param("workDay") Date workDay);

    void deleteWorkTimeMapper(@Param("workerIdentifier") WorkerIdentifier workerIdentifier, @Param("workDay") Date workDay);

    AttendanceOfDay select(@Param("workerIdentifier") WorkerIdentifier workerIdentifier, @Param("workDay") Date workDay);
}
