package com.njwb.util;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Fly
 *
 */
public interface RowMapper {
	public abstract Object mapperObject(ResultSet rs) throws SQLException;
}
