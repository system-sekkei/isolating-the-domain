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

    void deleteWorkerName(@Param("workerNumber") WorkerNumber workerNumber);

    void insertWorkerNameHistory(@Param("id") Integer id, @Param("workerNumber") WorkerNumber workerNumber, @Param("name") Name name);

    void insertWorkerName(@Param("workerNumber") WorkerNumber workerNumber, @Param("nameId") Integer nameId, @Param("name") Name workerName);

    void deleteWorkerPhoneNumber(@Param("workerNumber") WorkerNumber workerNumber);

    void insertWorkerPhoneNumberHistory(@Param("id") Integer id, @Param("workerNumber") WorkerNumber workerNumber, @Param("phoneNumber") PhoneNumber phoneNumber);

    void insertWorkerPhoneNumber(@Param("workerNumber") WorkerNumber workerNumber, @Param("phoneNumberId") Integer phoneId, @Param("phoneNumber") PhoneNumber phoneNumber);

    void deleteWorkerMailAddress(@Param("workerNumber") WorkerNumber workerNumber);

    void insertWorkerMailAddressHistory(@Param("id") Integer id, @Param("workerNumber") WorkerNumber workerNumber, @Param("mailAddress") MailAddress mailAddress);

    void insertWorkerMailAddress(@Param("workerNumber") WorkerNumber workerNumber, @Param("mailAddress") MailAddress mailAddress);

    void insertInspireContract(@Param("workerNumber") WorkerNumber workerNumber);

    void deleteInspireContract(@Param("workerNumber") WorkerNumber workerNumber);

    void insertExpireContract(@Param("workerNumber") WorkerNumber workerNumber);

    Integer newWorkerNumber();

    Integer newWorkerNameIdentifier();

    Integer newWorkerPhoneNumberIdentifier();

    Integer newWorkerMailAddressIdentifier();
}
