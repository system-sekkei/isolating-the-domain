package example.infrastructure.datasource.contract;

import example.domain.model.contract.HourlyWage;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.Date;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ContractMapper {
    Integer newHourlyWageIdentifier();
    void registerHourlyWage(@Param("workerNumber") WorkerNumber workerNumber, @Param("id") Integer hourlyWageId,
                            @Param("applyDate") Date applyDate, @Param("hourlyWage") HourlyWage hourlyWage);

    List<ContractHistoryData> getContractData(@Param("workerNumber") WorkerNumber workerNumber, @Param("workDay") Date workDay);

    ContractData2 getContractData2(@Param("workerNumber") WorkerNumber workerNumber, @Param("workDay") Date workDay);

    void insertContract(@Param("workerNumber") WorkerNumber workerNumber,
                        @Param("startDate")Date applyDate, @Param("endDate") Date date, @Param("hourlyWage") HourlyWage hourlyWage);

    void deleteContractData(@Param("workerNumber") WorkerNumber workerNumber,
                            @Param("startDate")Date startDate, @Param("endDate")Date endDate);
}
