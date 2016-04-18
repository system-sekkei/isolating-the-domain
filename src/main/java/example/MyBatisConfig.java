package example;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.*;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

/**
 * Created by haljik on 15/06/04.
 */
@Configuration
@MapperScan(basePackages = "example.datasource")
public class MyBatisConfig {

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setTypeHandlers(new TypeHandler[]{
                new InstantTypeHandler(),
                new LocalDateTimeTypeHandler(),
                new LocalDateTypeHandler(),
                new OffsetDateTimeTypeHandler(),
                new OffsetTimeTypeHandler(),
                new ZonedDateTimeTypeHandler()
        });
        sessionFactory.setConfigLocation(new ClassPathResource("mybatis.xml"));
        return sessionFactory.getObject();
    }
}
