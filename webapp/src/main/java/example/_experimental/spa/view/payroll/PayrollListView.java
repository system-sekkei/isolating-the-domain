package example._experimental.spa.view.payroll;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import example.domain.model.payroll.Payroll;
import example.domain.model.payroll.Payrolls;
import org.springframework.context.MessageSource;

import java.util.*;

public class PayrollListView {
    @JsonIgnore
    Payrolls payrolls;
    @JsonIgnore
    MessageSource messageSource;

    public PayrollListView(Payrolls payrolls, MessageSource messageSource) {
        this.payrolls = payrolls;
        this.messageSource = messageSource;
    }

    @JsonProperty("list")
    List<Map<String, String>> list() {
        List<Map<String, String>> list = new ArrayList<>();
        for (Payroll payroll : payrolls.list()) {
            Map<String, String> data = new HashMap<>();
            data.put("employeeNumber", payroll.employeeNumber().toString());
            data.put("employeeName", payroll.employeeName().toString());
            if (payroll.payrollStatus().available())
                data.put("totalPayment", payroll.totalPayment().toString());
            else
                data.put("message", messageSource.getMessage(payroll.payrollStatus().messageKey(), new String[]{}, Locale.JAPAN));
            list.add(data);
        }
        return list;
    }
}
