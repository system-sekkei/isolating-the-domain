package example.presentation.controller.timerecord;

import example.application.repository.EmployeeRepository;
import example.application.repository.TimeRecordRepository;
import example.domain.model.attendance.TimeRecords;
import example.domain.model.employee.ContractingEmployees;
import example.domain.model.employee.Employee;
import example.domain.model.employee.EmployeeNumber;
import example.domain.model.timerecord.evaluation.ActualWorkDateTime;
import example.domain.model.timerecord.evaluation.TimeRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TimeRecordRegisterControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TimeRecordRepository timeRecordRepository;
    @MockBean
    EmployeeRepository employeeRepository;

    @BeforeEach
    void setup() {
        Employee employee = new Employee(new EmployeeNumber(1), null, null, null);
        when(employeeRepository.findUnderContracts())
                .thenReturn(new ContractingEmployees(Arrays.asList(employee)));

        when(employeeRepository.choose(new EmployeeNumber(1)))
                .thenReturn(employee);
    }

    @Test
    void 登録画面が表示できる() throws Exception {
        mockMvc.perform(get("/timerecord"))
                .andExpect(status().isOk())
                .andExpect(view().name("timerecord/form"));
    }

    @Test
    void 登録できる() throws Exception {
        when(timeRecordRepository.findTimeRecords(any(), any()))
                .thenReturn(new TimeRecords(Collections.emptyList()));
        doNothing().when(timeRecordRepository).registerTimeRecord(any());

        mockMvc.perform(post("/timerecord")
                .param("employeeNumber", "1")
                .param("workDate.value", "2018-01-01")
                .param("startTime.hour.value", "9")
                .param("startTime.minute.value", "00")
                .param("endTime.hour.value", "17")
                .param("endTime.minute.value", "30")
                .param("daytimeBreakTime.value.value", "0")
                .param("nightBreakTime.value.value", "0")
        )
                .andExpect(redirectedUrlPattern("/attendances/1/*"));
    }

    @CsvSource(value = {
            "        '', 10,00, 17,30,  0,  0, workDate.value",
            "xxxx-01-01, 10,00, 17,30,  0,  0, workDate.value",
            "2019-01-01, '',00, 17,30,  0,  0, startTime.hour.value",
            "2019-01-01, 10,'', 17,30,  0,  0, startTime.minute.value",
            "2019-01-01,  x,00, 17,30,  0,  0, startTime.valid",
            "2019-01-01, 10, x, 17,30,  0,  0, startTime.valid",
            "2019-01-01, 10,00, '',30,  0,  0, endTime.hour.value",
            "2019-01-01, 10,00, 17,'',  0,  0, endTime.minute.value",
            "2019-01-01, 10,00,  x,30,  0,  0, endTime.valid",
            "2019-01-01, 10,00, 17, x,  0,  0, endTime.valid",
            "2019-01-01, 20,00, 17,30,  0,  0, timeRecord.actualWorkDateTime.workRange.workTimeValid", // 開始 > 終了
            "2019-01-01, 10,00, 17,30,  x,  0, daytimeBreakTime.value.value",
            "2019-01-01, 10,00, 10,30, 90,  0, timeRecord.actualWorkDateTime.daytimeBreakTimeValid", // over
            "2019-01-01, 10,00, 23,30,  0,  x, nightBreakTime.value.value",
            "2019-01-01, 10,00, 13,30,  0, 90, timeRecord.actualWorkDateTime.nightBreakTimeValid", // over
            "2019-01-02, 8,59, 21,00,  0, 0, startTime.valid",
            "2019-01-02, 10,00, 33,01,  0, 0, endTime.valid",
    })
    @ParameterizedTest
    void validation(String workDate, String startHour, String startMinute, String endHour, String endMinute, String daytimeBreakTime, String nightBreakTime, String errorField) throws Exception {
        when(timeRecordRepository.findTimeRecords(any(), any()))
                .thenReturn(new TimeRecords(Arrays.asList(
                        new TimeRecord(
                                new EmployeeNumber(1),
                                ActualWorkDateTime.toActualWorkDateTime("2019-01-01", "9:00", "33:00", "0", "0")),
                        new TimeRecord(
                                new EmployeeNumber(1),
                                ActualWorkDateTime.toActualWorkDateTime("2019-01-03", "9:00", "21:00", "0", "0")))));

        mockMvc.perform(post("/timerecord")
                .param("employeeNumber", "1")
                .param("workDate.value", workDate)
                .param("startTime.hour.value", startHour)
                .param("startTime.minute.value", startMinute)
                .param("endTime.hour.value", endHour)
                .param("endTime.minute.value", endMinute)
                .param("daytimeBreakTime.value.value", daytimeBreakTime)
                .param("nightBreakTime.value.value", nightBreakTime)
        )
                .andExpect(status().isOk())
                .andExpect(view().name("timerecord/form"))
                .andExpect(model().attributeHasFieldErrors("attendanceForm", errorField));
    }

    @Test
    void 日跨ぎが登録できる() throws Exception {
        doNothing().when(timeRecordRepository).registerTimeRecord(any());
        when(timeRecordRepository.findTimeRecords(any(), any()))
                .thenReturn(new TimeRecords(Collections.emptyList()));

        mockMvc.perform(post("/timerecord")
                .param("employeeNumber", "1")
                .param("workDate.value", "2018-01-01")
                .param("startTime.hour.value", "9")
                .param("startTime.minute.value", "00")
                .param("endTime.hour.value", "26")
                .param("endTime.minute.value", "30")
                .param("daytimeBreakTime.value.value", "0")
                .param("nightBreakTime.value.value", "0")
        )
                .andExpect(redirectedUrlPattern("/attendances/1/*"));
    }

    @Test
    void 日跨ぎを表示すると24時を超える() throws Exception {
        when(employeeRepository.choose(any())).thenReturn(new Employee(new EmployeeNumber(1), null, null, null));
        when(timeRecordRepository.findTimeRecords(any(), any()))
                .thenReturn(new TimeRecords(Arrays.asList(
                        new TimeRecord(
                                new EmployeeNumber(1),
                                ActualWorkDateTime.toActualWorkDateTime("2011-11-11", "9:00", "28:00", "0", "0")))));

        MvcResult mvcResult = mockMvc.perform(
                get("/timerecord")
                        .param("employeeNumber", "1")
                        .param("workDate", "2011-11-11"))
                .andExpect(status().isOk())
                .andExpect(view().name("timerecord/form"))
                .andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        AttendanceForm attendanceForm = (AttendanceForm) modelAndView.getModel().get("attendanceForm");

        assertEquals("28", attendanceForm.endTime.hour.toString());
    }
}