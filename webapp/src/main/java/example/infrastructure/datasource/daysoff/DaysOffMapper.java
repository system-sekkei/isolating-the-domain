package example.infrastructure.datasource.daysoff;

import example.domain.model.employee.EmployeeNumber;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;

@Mapper
public interface DaysOffMapper {
    void insertDaysOffHistory(@Param("employeeNumber") EmployeeNumber employeeNumber, @Param("daysOff") LocalDate daysOff);

    void insertDaysOff(@Param("employeeNumber") EmployeeNumber employeeNumber, @Param("daysOff") LocalDate daysOff);

    void deleteDaysOff(@Param("employeeNumber") EmployeeNumber employeeNumber, @Param("daysOff") LocalDate daysOff);
}
