package example.presentation.controller.timerecord;

import example.application.service.timerecord.TimeRecordRecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TimeRecordRegisterControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TimeRecordRecordService timeRecordRecordService;

    @Test
    void 登録画面が表示できる() throws Exception {
        mockMvc.perform(get("/timerecord"))
                .andExpect(status().isOk())
                .andExpect(view().name("timerecord/form"));
    }

    @Test
    void 登録できる() throws Exception {
        doNothing().when(timeRecordRecordService).registerTimeRecord(any());

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
    void 登録バリデーションエラー() throws Exception {
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
    void 日跨ぎ() throws Exception {
        doNothing().when(timeRecordRecordService).registerTimeRecord(any());

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
}