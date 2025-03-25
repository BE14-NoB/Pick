package com.nob.pick.common.config.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.nob.pick.post.command.application.dto.PostCategory;

public class PostCategoryEnumTypeHandler extends BaseTypeHandler<PostCategory> {
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, PostCategory parameter, JdbcType jdbcType)
		throws SQLException {
		ps.setInt(i, parameter.getValue());
	}
	
	@Override
	public PostCategory getNullableResult(ResultSet rs, String columnName) throws SQLException {
		int value = rs.getInt(columnName);
		return toPostCategory(value);
	}
	
	@Override
	public PostCategory getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		int value = rs.getInt(columnIndex);
		return toPostCategory(value);
	}
	
	@Override
	public PostCategory getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		int value = cs.getInt(columnIndex);
		return toPostCategory(value);
	}
	
	private PostCategory toPostCategory(int value) {
		for (PostCategory postCategory : PostCategory.values()) {
			if (postCategory.getValue() == value) {
				return postCategory;
			}
		}
		throw new IllegalArgumentException("Unknown PostCategory value: " + value);
	}
}
