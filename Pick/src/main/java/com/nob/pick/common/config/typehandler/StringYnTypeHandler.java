package com.nob.pick.common.config.typehandler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StringYnTypeHandler implements TypeHandler<Boolean> {

    @Override
    public void setParameter(PreparedStatement ps, int i, Boolean parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter ? "Y" : "N");
    }

    @Override
    public Boolean getResult(ResultSet rs, String columnName) throws SQLException {
        String s = rs.getString(columnName);
        return "Y".equalsIgnoreCase(s);
    }

    @Override
    public Boolean getResult(ResultSet rs, int columnIndex) throws SQLException {
        String s = rs.getString(columnIndex);
        return "Y".equalsIgnoreCase(s);
    }

    @Override
    public Boolean getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String s = cs.getString(columnIndex);
        return "Y".equalsIgnoreCase(s);
    }
}
