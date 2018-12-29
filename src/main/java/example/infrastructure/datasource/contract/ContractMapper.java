package example.infrastructure.datasource.contract;

import example.domain.model.contract.WageCondition;
import example.domain.model.worker.WorkerNumber;
import example.domain.type.date.Date;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ContractMapper {
    Integer newHourlyWageIdentifier();

    void insertContractHistory(@Param("workerNumber") WorkerNumber workerNumber, @Param("id") Integer hourlyWageId,
                               @Param("applyDate") Date applyDate,
                               @Param("wageCondition") WageCondition wageCondition);

    List<HourlyWageData> selectContracts(@Param("workerNumber") WorkerNumber workerNumber);

    void insertContract(@Param("workerNumber") WorkerNumber workerNumber,
                        @Param("startDate") Date applyDate,
                        @Param("wageCondition") WageCondition wageCondition);

    void deleteContractData(@Param("workerNumber") WorkerNumber workerNumber, @Param("startDate") Date startDate);
}
