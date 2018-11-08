package example.infrastructure.datasource.worker;

import example.domain.model.worker.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WorkerMapper {

    Worker selectByIdentifier(@Param("identifier") WorkerIdentifier id);

    List<Worker> selectContracts();

    void insertWorker(@Param("workerIdentifier") WorkerIdentifier workerIdentifier);

    void deleteNameMapper(@Param("workerIdentifier") WorkerIdentifier workerIdentifier);

    void insertName(@Param("id") Long id, @Param("workerIdentifier") WorkerIdentifier workerIdentifier, @Param("name") Name name);

    void insertNameMapper(@Param("workerIdentifier") WorkerIdentifier workerIdentifier, @Param("nameId") Long nameId);

    void deletePhoneNumberMapper(@Param("workerIdentifier") WorkerIdentifier workerIdentifier);

    void insertPhoneNumber(@Param("id") Long id, @Param("workerIdentifier") WorkerIdentifier workerIdentifier, @Param("phoneNumber") PhoneNumber phoneNumber);

    void insertPhoneNumberMapper(@Param("workerIdentifier") WorkerIdentifier workerIdentifier, @Param("phoneNumberId") Long phoneNumberId);

    void deleteMailAddressMapper(@Param("workerIdentifier") WorkerIdentifier workerIdentifier);

    void insertMailAddress(@Param("id") Long id, @Param("workerIdentifier") WorkerIdentifier workerIdentifier, @Param("mailAddress") MailAddress mailAddress);

    void insertMailAddressMapper(@Param("workerIdentifier") WorkerIdentifier workerIdentifier, @Param("mailAddressId") Long mailAddressId);

    void insertExpireContract(@Param("worker") Worker worker);

    long newWorkerIdentifier();

    long newWorkerNameIdentifier();

    long newWorkerPhoneNumberIdentifier();

    long newWorkerMailAddressIdentifier();
}
