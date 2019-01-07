package example.infrastructure.datasource.workrecord;

import example.domain.model.timerecord.TimeRecord;
import example.domain.model.timerecord.WorkDate;
import example.domain.model.worker.WorkerNumber;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WorkRecordMapper {
    Integer newWorkTimeIdentifier();

    void insertWorkTimeHistory(@Param("id") Integer id, @Param("workerNumber") WorkerNumber workerNumber, @Param("work") TimeRecord work);

    void insertWorkTime(@Param("workerNumber") WorkerNumber workerNumber, @Param("workTimeId") Integer workTimeId, @Param("work") TimeRecord work);

    void deleteWorkTime(@Param("workerNumber") WorkerNumber workerNumber, @Param("workDate") WorkDate workDate);

    List<TimeRecord> selectByMonth(@Param("workerNumber") WorkerNumber workerNumber, @Param("yearMonth") String yearMonth);
}
