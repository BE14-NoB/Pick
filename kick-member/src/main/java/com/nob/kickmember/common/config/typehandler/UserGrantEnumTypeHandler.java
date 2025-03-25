package com.nob.kickmember.common.config.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import com.nob.kickmember.member.query.dto.UserGrant;

public class UserGrantEnumTypeHandler extends BaseTypeHandler<UserGrant> {
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, UserGrant parameter, JdbcType jdbcType) throws SQLException {
		ps.setInt(i, parameter.getValue());
	}

	@Override
	public UserGrant getNullableResult(ResultSet rs, String columnName) throws SQLException {
		int value = rs.getInt(columnName);
		return toUserGrant(value);
	}

	@Override
	public UserGrant getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		int value = rs.getInt(columnIndex);
		return toUserGrant(value);
	}

	@Override
	public UserGrant getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		int value = cs.getInt(columnIndex);
		return toUserGrant(value);
	}

	private UserGrant toUserGrant(int value) {
		for (UserGrant userGrant : UserGrant.values()) {
			if (userGrant.getValue() == value) {
				return userGrant;
			}
		}
		throw new IllegalArgumentException("Unknown UserGrant value: " + value);
	}
}