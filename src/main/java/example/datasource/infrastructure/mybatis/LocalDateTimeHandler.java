package example.datasource.infrastructure.mybatis;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * Created by kazuya.fujioka on 2014/11/21.
 */
@MappedTypes(LocalDateTime.class)
public class LocalDateTimeHandler extends BaseTypeHandler<LocalDateTime> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, LocalDateTime parameter, JdbcType jdbcType) throws SQLException {
        ps.setTimestamp(i, Timestamp.valueOf(parameter));
    }
    @Override
    public LocalDateTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
        final Timestamp timestamp = rs.getTimestamp(columnName);
        if (rs.wasNull()) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
    @Override
    public LocalDateTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        final Timestamp timestamp = rs.getTimestamp(columnIndex);
        if (rs.wasNull()) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
    @Override
    public LocalDateTime getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        final Timestamp timestamp = cs.getTimestamp(columnIndex);
        if (cs.wasNull()) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}