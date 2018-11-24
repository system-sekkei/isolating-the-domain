package example.infrastructure.datasource.attendance;

import example.domain.model.attendance.Attendance;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.Date;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AttendanceMapper {
    Integer newWorkTimeIdentifier();

    void insertWorkTimeHistory(@Param("id") Integer id, @Param("workerNumber") WorkerNumber workerNumber, @Param("work") Attendance work);

    void insertWorkTime(@Param("workerNumber") WorkerNumber workerNumber, @Param("workTimeId") Integer workTimeId, @Param("work") Attendance work);

    void deleteWorkTime(@Param("workerNumber") WorkerNumber workerNumber, @Param("workDay") Date workDay);

    Attendance select(@Param("workerNumber") WorkerNumber workerNumber, @Param("workDay") Date workDay);
}
