package example.infrastructure.datasource.worker;

import example.domain.model.worker.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WorkerMapper {

    Worker selectByWorkerNumber(@Param("workerNumber") WorkerNumber workerNumber);

    List<Worker> selectContracts();

    void insertWorker(@Param("workerNumber") WorkerNumber workerNumber);

    void deleteNameMapper(@Param("workerNumber") WorkerNumber workerNumber);

    void insertName(@Param("id") Long id, @Param("workerNumber") WorkerNumber workerNumber, @Param("name") Name name);

    void insertNameMapper(@Param("workerNumber") WorkerNumber workerNumber, @Param("name") Name workerName);

    void deletePhoneNumberMapper(@Param("workerNumber") WorkerNumber workerNumber);

    void insertPhoneNumber(@Param("id") Long id, @Param("workerNumber") WorkerNumber workerNumber, @Param("phoneNumber") PhoneNumber phoneNumber);

    void insertPhoneNumberMapper(@Param("workerNumber") WorkerNumber workerNumber, @Param("phoneNumber") PhoneNumber phoneNumber);

    void deleteMailAddressMapper(@Param("workerNumber") WorkerNumber workerNumber);

    void insertMailAddress(@Param("id") Long id, @Param("workerNumber") WorkerNumber workerNumber, @Param("mailAddress") MailAddress mailAddress);

    void insertMailAddressMapper(@Param("workerNumber") WorkerNumber workerNumber, @Param("mailAddress") MailAddress mailAddress);

    void insertInspireContract(@Param("workerNumber") WorkerNumber workerNumber);

    void deleteInspireContract(@Param("worker") Worker worker);

    void insertExpireContract(@Param("worker") Worker worker);

    long newWorkerNumber();

    long newWorkerNameIdentifier();

    long newWorkerPhoneNumberIdentifier();

    long newWorkerMailAddressIdentifier();
}
