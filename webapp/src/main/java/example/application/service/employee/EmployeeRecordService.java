package example.application.service.employee;

import example.application.repository.EmployeeRepository;
import example.domain.model.employee.*;
import org.springframework.stereotype.Service;

/**
 * 従業員登録更新サービス
 */
@Service
public class EmployeeRecordService {

    EmployeeRepository employeeRepository;

    /**
     * 従業員契約準備
     */
    public EmployeeNumber prepareNewContract() {
        return employeeRepository.registerNew();
    }

    /**
     * 従業員名登録
     */
    public void registerName(EmployeeName employeeName) {
        employeeRepository.registerName(employeeName);
    }

    /**
     * 従業員メールアドレス登録
     */
    public void registerMailAddress(EmployeeMailAddress employeeMailAddress) {
        employeeRepository.registerMailAddress(employeeMailAddress);
    }

    /**
     * 従業員電話番号登録
     */
    public void registerPhoneNumber(EmployeePhoneNumber employeePhoneNumber) {
        employeeRepository.registerPhoneNumber(employeePhoneNumber);
    }

    /**
     * 従業員契約開始
     */
    public void inspireContract(EmployeeNumber employeeNumber) {
        employeeRepository.registerInspireContract(employeeNumber);
    }

    /**
     * 従業員契約終了
     */
    public void expireContract(EmployeeNumber employeeNumber) {
        employeeRepository.registerExpireContract(employeeNumber);
    }

    EmployeeRecordService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
}
