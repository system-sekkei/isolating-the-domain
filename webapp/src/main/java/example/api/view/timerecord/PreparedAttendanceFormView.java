package example.api.view.timerecord;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import example.domain.model.employee.ContractingEmployees;
import example.domain.model.employee.Employee;
import example.presentation.controller.timerecord.AttendanceForm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PreparedAttendanceFormView {
    @JsonIgnore
    ContractingEmployees contractingEmployees;
    @JsonIgnore
    AttendanceForm preparedForm;

    public PreparedAttendanceFormView(ContractingEmployees contractingEmployees, AttendanceForm preparedForm) {
        this.contractingEmployees = contractingEmployees;
        this.preparedForm = preparedForm;
    }

    @JsonProperty("contractingEmployees")
    List<Map<String, String>> contractingEmployees() {
        List<Map<String, String>> list = new ArrayList<>();
        for (Employee employee : contractingEmployees.list()) {
            Map<String, String> data = new HashMap<>();
            data.put("employeeNumber", employee.employeeNumber().toString());
            data.put("employeeName", employee.name().toString());
            list.add(data);
        }
        return list;
    }

    @JsonProperty("preparedForm")
    AttendanceForm preparedForm() {
        return preparedForm;
    }
}
