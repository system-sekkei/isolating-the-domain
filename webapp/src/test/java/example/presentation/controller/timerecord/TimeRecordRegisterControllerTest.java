package example.presentation.controller.timerecord;

import example.application.repository.EmployeeRepository;
import example.application.repository.TimeRecordRepository;
import example.domain.model.attendance.TimeRecords;
import example.domain.model.employee.ContractingEmployees;
import example.domain.model.employee.Employee;
import example.domain.model.employee.EmployeeNumber;
import example.domain.model.timerecord.evaluation.TimeRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;

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
        when(employeeRepository.findUnderContracts())
                .thenReturn(new ContractingEmployees(Arrays.asList(
                        new Employee(new EmployeeNumber(1), null, null, null))));
    }

    @Test
    void 登録画面が表示できる() throws Exception {
        mockMvc.perform(get("/timerecord"))
                .andExpect(status().isOk())
                .andExpect(view().name("timerecord/form"));
    }

    @Test
    void 登録できる() throws Exception {
        doNothing().when(timeRecordRepository).registerTimeRecord(any());

        mockMvc.perform(post("/timerecord")
                .param("employeeNumber", "1")
                .param("workDate", "2018-01-01")
                .param("startHour", "9")
                .param("startMinute", "00")
                .param("endHour", "17")
                .param("endMinute", "30")
                .param("daytimeBreakTime", "0")
                .param("nightBreakTime", "0")
        )
                .andExpect(redirectedUrlPattern("/attendances/1/*"));
    }

    @Test
    void バリデーションエラー() throws Exception {
        mockMvc.perform(post("/timerecord")
                .param("employeeNumber", "1")
                .param("workDate", "2018-01-01")
                .param("startHour", "20")
                .param("startMinute", "00")
                .param("endHour", "17")
                .param("endMinute", "30")
                .param("daytimeBreakTime", "0")
                .param("nightBreakTime", "0")
        )
                .andExpect(status().isOk())
                .andExpect(view().name("timerecord/form"));
    }

    @Test
    void 日跨ぎが登録できる() throws Exception {
        doNothing().when(timeRecordRepository).registerTimeRecord(any());

        mockMvc.perform(post("/timerecord")
                .param("employeeNumber", "1")
                .param("workDate", "2018-01-01")
                .param("startHour", "9")
                .param("startMinute", "00")
                .param("endHour", "26")
                .param("endMinute", "30")
                .param("daytimeBreakTime", "0")
                .param("nightBreakTime", "0")
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
                                AttendanceForm.toActualWorkDateTime("2011-11-11", "9:00", "28:00", "0", "0")))));

        MvcResult mvcResult = mockMvc.perform(
                get("/timerecord")
                        .param("employeeNumber", "1")
                        .param("workDate", "2011-11-11"))
                .andExpect(status().isOk())
                .andExpect(view().name("timerecord/form"))
                .andReturn();
        ModelAndView modelAndView = mvcResult.getModelAndView();
        AttendanceForm attendanceForm = (AttendanceForm) modelAndView.getModel().get("attendanceForm");

        assertEquals("28", attendanceForm.endHour);
    }
}