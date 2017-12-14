package com.yf.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.*;
import java.util.Date;

@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(Date.class)
public class MyDateTypeHandler extends BaseTypeHandler<Date> {

    public void setNonNullParameter(PreparedStatement ps, int i, Date parameter, JdbcType jdbcType) throws SQLException {
       ps.setString(i,String.valueOf(parameter.getTime()));
    }

    public Date getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return new Date(rs.getLong(columnName));
    }

    public Date getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return new Date(rs.getLong(columnIndex));
    }

    public Date getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return new Date(cs.getLong(columnIndex));
    }
}
