package example.infrastructure.datasource.contract;

import example.domain.model.contract.ContractWage;
import example.domain.model.employee.EmployeeNumber;
import example.domain.model.contract.WageCondition;
import example.domain.type.date.Date;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ContractMapper {
    Integer newHourlyWageIdentifier();

    void insertContractHistory(@Param("employeeNumber") EmployeeNumber employeeNumber, @Param("id") Integer hourlyWageId,
                               @Param("effectiveDate") Date effectiveDate,
                               @Param("wageCondition") WageCondition wageCondition);

    List<ContractWage> selectContracts(@Param("employeeNumber") EmployeeNumber employeeNumber);

    void insertContract(@Param("employeeNumber") EmployeeNumber employeeNumber,
                        @Param("effectiveDate") Date effectiveDate,
                        @Param("wageCondition") WageCondition wageCondition);

    void deleteContractData(@Param("employeeNumber") EmployeeNumber employeeNumber, @Param("effectiveDate") Date effectiveDate);
}
