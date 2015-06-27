package example.datasource.infrastructure.mybatis;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.*;
import java.time.LocalDate;

/**
 * Created by kazuya.fujioka on 2014/11/21.
 */
@MappedTypes(LocalDate.class)
public class LocalDateHandler extends BaseTypeHandler<LocalDate> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, LocalDate parameter, JdbcType jdbcType) throws SQLException {
        ps.setDate(i, Date.valueOf(parameter));
    }
    @Override
    public LocalDate getNullableResult(ResultSet rs, String columnName) throws SQLException {
        final Date date = rs.getDate(columnName);
        if (rs.wasNull()) {
            return null;
        }
        return date.toLocalDate();
    }
    @Override
    public LocalDate getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        final Date date = rs.getDate(columnIndex);
        if (rs.wasNull()) {
            return null;
        }
        return date.toLocalDate();
    }
    @Override
    public LocalDate getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        final Date date = cs.getDate(columnIndex);
        if (cs.wasNull()) {
            return null;
        }
        return date.toLocalDate();
    }
}