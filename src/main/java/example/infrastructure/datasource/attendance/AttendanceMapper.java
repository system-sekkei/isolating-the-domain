package example.infrastructure.datasource.attendance;

import example.domain.model.attendance.AttendanceOfDay;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.Date;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AttendanceMapper {
    long newWorkTimeIdentifier();

    void insertWorkTimeHistory(@Param("id") Long id, @Param("workerNumber") WorkerNumber workerNumber, @Param("work") AttendanceOfDay work);

    void insertWorkTime(@Param("workerNumber") WorkerNumber workerNumber, @Param("work") AttendanceOfDay work);

    void deleteWorkTime(@Param("workerNumber") WorkerNumber workerNumber, @Param("workDay") Date workDay);

    AttendanceOfDay select(@Param("workerNumber") WorkerNumber workerNumber, @Param("workDay") Date workDay);
}
