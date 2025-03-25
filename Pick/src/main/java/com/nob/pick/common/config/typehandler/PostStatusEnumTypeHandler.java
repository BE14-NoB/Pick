package com.nob.pick.common.config.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.nob.pick.post.command.application.dto.PostStatus;

public class PostStatusEnumTypeHandler extends BaseTypeHandler<PostStatus> {
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, PostStatus parameter, JdbcType jdbcType)
		throws SQLException {
		ps.setInt(i, parameter.getValue());
	}
	
	@Override
	public PostStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
		int value = rs.getInt(columnName);
		return toPostStatus(value);
	}
	
	@Override
	public PostStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		int value = rs.getInt(columnIndex);
		return toPostStatus(value);
	}
	
	@Override
	public PostStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		int value = cs.getInt(columnIndex);
		return toPostStatus(value);
	}
	
	private PostStatus toPostStatus(int value) {
		for (PostStatus postStatus : PostStatus.values()) {
			if (postStatus.getValue() == value) {
				return postStatus;
			}
		}
		throw new IllegalArgumentException("Unknown PostStatus value: " + value);
	}
}
