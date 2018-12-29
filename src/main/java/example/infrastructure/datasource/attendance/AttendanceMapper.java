package example.infrastructure.datasource.attendance;

import example.domain.model.workrecord.WorkRecord;
import example.domain.model.workrecord.WorkDate;
import example.domain.model.worker.WorkerNumber;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AttendanceMapper {
    Integer newWorkTimeIdentifier();

    void insertWorkTimeHistory(@Param("id") Integer id, @Param("workerNumber") WorkerNumber workerNumber, @Param("work") WorkRecord work);

    void insertWorkTime(@Param("workerNumber") WorkerNumber workerNumber, @Param("workTimeId") Integer workTimeId, @Param("work") WorkRecord work);

    void deleteWorkTime(@Param("workerNumber") WorkerNumber workerNumber, @Param("workDate") WorkDate workDate);

    List<WorkRecord> selectByMonth(@Param("workerNumber") WorkerNumber workerNumber, @Param("yearMonth") String yearMonth);
}
