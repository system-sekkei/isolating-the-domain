package example;

import jig.erd.JigErd;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

@SpringBootTest
public class BootUpTest {

    @Test
    void 起動確認(@Autowired DataSource dataSource) {
        JigErd.run(dataSource);
    }
}
