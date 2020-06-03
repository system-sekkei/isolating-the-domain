package example.infrastructure.datasource.timerecord;

import example.domain.model.employee.EmployeeNumber;
import example.domain.model.timerecord.evaluation.TimeRecord;
import example.domain.model.timerecord.evaluation.WorkDate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TimeRecordMapper {
    Integer newWorkTimeIdentifier();

    void insertWorkTimeHistory(@Param("id") Integer id, @Param("employeeNumber") EmployeeNumber employeeNumber, @Param("work") TimeRecord work, @Param("workDate") WorkDate workDate);

    void insertWorkTime(@Param("employeeNumber") EmployeeNumber employeeNumber, @Param("workTimeId") Integer workTimeId, @Param("work") TimeRecord work, @Param("workDate") WorkDate workDate);

    void deleteWorkTime(@Param("employeeNumber") EmployeeNumber employeeNumber, @Param("workDate") WorkDate workDate);

    List<TimeRecord> selectByMonths(@Param("employeeNumber") EmployeeNumber employeeNumber, @Param("yearMonths") List<String> yearMonths);
}
