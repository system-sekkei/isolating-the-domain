package example.infrastructure.datasource.contract;

import example.domain.model.contract.HourlyWageContract;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.Date;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ContractMapper {
    Integer newHourlyWageIdentifier();

    void registerHourlyWage(@Param("workerNumber") WorkerNumber workerNumber, @Param("id") Integer hourlyWageId,
                            @Param("applyDate") Date applyDate,
                            @Param("hourlyWageContract") HourlyWageContract hourlyWageContract);

    List<HourlyWageData> selectContracts(@Param("workerNumber") WorkerNumber workerNumber);

    HourlyWageData selectHourlyWageData(@Param("workerNumber") WorkerNumber workerNumber, @Param("workDay") Date workDay);

    void insertContract(@Param("workerNumber") WorkerNumber workerNumber,
                        @Param("startDate") Date applyDate, @Param("endDate") Date date,
                        @Param("hourlyWageContract") HourlyWageContract hourlyWageContract);

    void deleteContractData(@Param("workerNumber") WorkerNumber workerNumber,
                            @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    void deleteFeatureContract(@Param("workerNumber") WorkerNumber workerNumber, @Param("date") Date date);

}
