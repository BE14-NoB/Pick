package com.nob.pick.common.config.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeTypeHandler extends BaseTypeHandler<Date>{

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Date parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, FORMATTER.format(parameter));  // Date → String 변환
    }

    @Override
    public Date getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return parseDate(rs.getString(columnName));
    }

    @Override
    public Date getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return parseDate(rs.getString(columnIndex));
    }

    @Override
    public Date getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return parseDate(cs.getString(columnIndex));
    }

    private Date parseDate(String dateString) {
        try {
            return (dateString != null) ? FORMATTER.parse(dateString) : null;
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse date: " + dateString, e);
        }
    }
}
