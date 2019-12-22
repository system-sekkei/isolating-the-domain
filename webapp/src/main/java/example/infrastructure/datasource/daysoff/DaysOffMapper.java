package example.infrastructure.datasource.daysoff;

import example.domain.model.employee.EmployeeNumber;
import example.domain.type.date.Date;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DaysOffMapper {
    void insertDaysOffHistory(@Param("employeeNumber") EmployeeNumber employeeNumber, @Param("daysOff") Date daysOff);

    void insertDaysOff(@Param("employeeNumber") EmployeeNumber employeeNumber, @Param("daysOff") Date daysOff);

    void deleteDaysOff(@Param("employeeNumber") EmployeeNumber employeeNumber, @Param("daysOff") Date daysOff);
}
