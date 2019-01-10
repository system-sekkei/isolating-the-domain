package example.infrastructure.datasource.employee;

import example.domain.model.employee.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    Employee selectByEmployeeNumber(@Param("employeeNumber") EmployeeNumber employeeNumber);

    List<Employee> selectContracts();

    void insertEmployee(@Param("employeeNumber") EmployeeNumber employeeNumber);

    void deleteEmployeeName(@Param("employeeNumber") EmployeeNumber employeeNumber);

    void insertEmployeeNameHistory(@Param("id") Integer id, @Param("employeeNumber") EmployeeNumber employeeNumber, @Param("name") Name name);

    void insertEmployeeName(@Param("employeeNumber") EmployeeNumber employeeNumber, @Param("nameId") Integer nameId, @Param("name") Name employeeName);

    void deleteEmployeePhoneNumber(@Param("employeeNumber") EmployeeNumber employeeNumber);

    void insertEmployeePhoneNumberHistory(@Param("id") Integer id, @Param("employeeNumber") EmployeeNumber employeeNumber, @Param("phoneNumber") PhoneNumber phoneNumber);

    void insertEmployeePhoneNumber(@Param("employeeNumber") EmployeeNumber employeeNumber, @Param("phoneNumberId") Integer phoneId, @Param("phoneNumber") PhoneNumber phoneNumber);

    void deleteEmployeeMailAddress(@Param("employeeNumber") EmployeeNumber employeeNumber);

    void insertEmployeeMailAddressHistory(@Param("id") Integer id, @Param("employeeNumber") EmployeeNumber employeeNumber, @Param("mailAddress") MailAddress mailAddress);

    void insertEmployeeMailAddress(@Param("employeeNumber") EmployeeNumber employeeNumber, @Param("mailAddressId") Integer mailAddressId, @Param("mailAddress") MailAddress mailAddress);

    void insertInspireContract(@Param("employeeNumber") EmployeeNumber employeeNumber);

    void deleteInspireContract(@Param("employeeNumber") EmployeeNumber employeeNumber);

    void insertExpireContract(@Param("employeeNumber") EmployeeNumber employeeNumber);

    Integer newEmployeeNumber();

    Integer newEmployeeNameIdentifier();

    Integer newEmployeePhoneNumberIdentifier();

    Integer newEmployeeMailAddressIdentifier();
}
