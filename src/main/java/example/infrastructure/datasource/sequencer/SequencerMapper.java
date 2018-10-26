package example.infrastructure.datasource.sequencer;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SequencerMapper {
    Long nextVal();
}
