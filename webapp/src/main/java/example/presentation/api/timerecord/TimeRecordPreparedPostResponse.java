package example.presentation.api.timerecord;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import example.domain.model.employee.ContractingEmployees;
import example.domain.model.employee.Employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 勤務時間の登録準備レスポンス
 */
class TimeRecordPreparedPostResponse {
    @JsonIgnore
    ContractingEmployees contractingEmployees;
    @JsonIgnore
    TimeRecordPostRequest preparedRequest;

    TimeRecordPreparedPostResponse(ContractingEmployees contractingEmployees, TimeRecordPostRequest preparedRequest) {
        this.contractingEmployees = contractingEmployees;
        this.preparedRequest = preparedRequest;
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

    @JsonProperty("preparedRequest")
    TimeRecordPostRequest preparedRequest() {
        return preparedRequest;
    }
}
