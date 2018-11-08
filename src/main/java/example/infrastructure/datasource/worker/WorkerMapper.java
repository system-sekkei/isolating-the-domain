package example.infrastructure.datasource.worker;

import example.domain.model.worker.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WorkerMapper {

    Worker findBy(@Param("identifier") WorkerIdentifier id);

    List<Worker> list();

    void registerWorker(@Param("workerIdentifier") WorkerIdentifier workerIdentifier);

    void deleteNameMapper(@Param("workerIdentifier") WorkerIdentifier workerIdentifier);

    void registerName(@Param("id") Long id, @Param("workerIdentifier") WorkerIdentifier workerIdentifier, @Param("name") Name name);

    void registerNameMapper(@Param("workerIdentifier") WorkerIdentifier workerIdentifier, @Param("nameId") Long nameId);

    void deletePhoneNumberMapper(@Param("workerIdentifier") WorkerIdentifier workerIdentifier);

    void registerPhoneNumber(@Param("id") Long id, @Param("workerIdentifier") WorkerIdentifier workerIdentifier, @Param("phoneNumber") PhoneNumber phoneNumber);

    void registerPhoneNumberMapper(@Param("workerIdentifier") WorkerIdentifier workerIdentifier, @Param("phoneNumberId") Long phoneNumberId);

    void deleteMailAddressMapper(@Param("workerIdentifier") WorkerIdentifier workerIdentifier);

    void registerMailAddress(@Param("id") Long id, @Param("workerIdentifier") WorkerIdentifier workerIdentifier, @Param("mailAddress") MailAddress mailAddress);

    void registerMailAddressMapper(@Param("workerIdentifier") WorkerIdentifier workerIdentifier, @Param("mailAddressId") Long mailAddressId);

    void delete(@Param("worker") Worker worker);

    long newWorkerIdentifier();

    long newWorkerNameIdentifier();

    long newWorkerPhoneNumberIdentifier();

    long newWorkerMailAddressIdentifier();
}
