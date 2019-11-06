package example.infrastructure.datasource.timerecord;

import example.domain.model.timerecord.evaluation.TimeRecord;
import example.domain.model.employee.EmployeeNumber;
import example.domain.model.timerecord.timefact.StartDateTime;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TimeRecordMapper {
    Integer newWorkTimeIdentifier();

    void insertWorkTimeHistory(@Param("id") Integer id, @Param("employeeNumber") EmployeeNumber employeeNumber, @Param("work") TimeRecord work);

    void insertWorkTime(@Param("employeeNumber") EmployeeNumber employeeNumber, @Param("workTimeId") Integer workTimeId, @Param("work") TimeRecord work);

    void deleteWorkTime(@Param("employeeNumber") EmployeeNumber employeeNumber, @Param("startDateTime") StartDateTime startDateTime);

    List<TimeRecord> selectByMonth(@Param("employeeNumber") EmployeeNumber employeeNumber, @Param("yearMonth") String yearMonth);
}
