package com.nob.pick.common.config.typehandler;

import com.nob.pick.report.query.dto.enums.ReportCategory;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportCategoryEnumTypeHandler extends BaseTypeHandler<ReportCategory> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, ReportCategory parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getNUM());
    }

    @Override
    public ReportCategory getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int value = rs.getInt(columnName);
        return ReportCategory.fromNum(value);
    }

    @Override
    public ReportCategory getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int value = rs.getInt(columnIndex);
        return ReportCategory.fromNum(value);
    }

    @Override
    public ReportCategory getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int value = cs.getInt(columnIndex);
        return ReportCategory.fromNum(value);
    }
}
