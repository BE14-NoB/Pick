package com.nob.pick.common.config.typehandler;

import com.nob.pick.report.query.dto.enums.ReportStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportStatusEnumTypeHandler extends BaseTypeHandler<ReportStatus> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, ReportStatus parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getNUM());
    }

    @Override
    public ReportStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int value = rs.getInt(columnName);
        return ReportStatus.fromNum(value);
    }

    @Override
    public ReportStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int value = rs.getInt(columnIndex);
        return ReportStatus.fromNum(value);
    }

    @Override
    public ReportStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int value = cs.getInt(columnIndex);
        return ReportStatus.fromNum(value);
    }
}
