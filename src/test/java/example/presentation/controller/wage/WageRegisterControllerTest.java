package example.presentation.controller.wage;

import example.application.repository.ContractRepository;
import example.application.repository.EmployeeRepository;
import example.domain.model.employee.Employee;
import example.domain.model.employee.EmployeeNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class WageRegisterControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    EmployeeRepository employeeRepository;

    @MockBean
    ContractRepository contractRepository;

    @BeforeEach
    void setup() {
        when(employeeRepository.choose(any())).thenReturn(new Employee(new EmployeeNumber(1), null, null, null));
    }

    @Test
    void 登録画面が開ける() throws Exception {
        mockMvc.perform(get("/wages/1/register"))
                .andExpect(view().name("wage/form"));
    }

    @Test
    void 登録で確認画面に遷移する() throws Exception {
        mockMvc.perform(
                post("/wages/1/register/confirm")
                        .param("effectiveDate", "2011-11-11")
                        .param("baseHourlyWage", "1234"))
                .andExpect(view().name("wage/confirm"));
    }

    @Test
    void 戻るで登録画面に遷移する() throws Exception {
        mockMvc.perform(
                post("/wages/1/register/again")
                        .param("effectiveDate", "2011-11-11")
                        .param("baseHourlyWage", "1234"))
                .andExpect(view().name("wage/form"));
    }

    @Test
    void 確認OKで完了画面に遷移する() throws Exception {
        mockMvc.perform(
                post("/wages/1/register/register")
                        .param("effectiveDate", "2011-11-11")
                        .param("baseHourlyWage", "1234"))
                .andExpect(redirectedUrl("/wages/1/register/completed"));
    }
}