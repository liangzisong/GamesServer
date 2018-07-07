package com.mangni.vegaplan.toolshelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;


public class MyJdbcTemplate extends JdbcTemplate{
	@Override
	public <T> T queryForObject(String sql, Object[] args, RowMapper<T> rowMapper) throws DataAccessException { 
		List<T> results = query(sql, args, new RowMapperResultSetExtractor<T>(rowMapper, 1)); 
		return requiredSingleResult(results); 
	}
	@Override
	public <T> T queryForObject(String sql, RowMapper<T> rowMapper) throws DataAccessException {
		List<T> results = query(sql, new RowMapperResultSetExtractor<T>(rowMapper, 1)); 
		return requiredSingleResult(results); 
	}


	public static <T> T requiredSingleResult(Collection<T> results) throws IncorrectResultSizeDataAccessException { 
		int size = (results != null ? results.size() : 0); if (size == 0) { 
			return null; 
		} 
		if (results.size() > 1) { 
			throw new IncorrectResultSizeDataAccessException(1, size); 
		} 
		return results.iterator().next(); 
	}

	public Long insertAndGetKey(String sql,String[] param){
		KeyHolder keyHolder = new GeneratedKeyHolder();  
		update(new PreparedStatementCreator() {  
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {  
				PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
				if(param!=null){
					for(int i=0;i<param.length;i++){
						ps.setString(i+1, param[i]);
					}
				}
				return ps;
			}
		},keyHolder);
		Long generatedId = keyHolder.getKey().longValue();   
	    return generatedId;
	}
}


