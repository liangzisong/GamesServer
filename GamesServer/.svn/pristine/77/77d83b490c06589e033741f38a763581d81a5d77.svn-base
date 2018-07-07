package com.mangni.vegaplan.toolshelper;
import java.sql.Connection;  
import java.sql.SQLException;
import org.apache.log4j.spi.ErrorCode;  
/** 
 * Log4j的 Appender, 通过连接池获取数据库连接 
 * @author Carl He 
 */  
public class MyJDBCAppender extends org.apache.log4j.jdbc.JDBCAppender {  
    /**通过 连接池 获取数据库连接对象的 jndiName 属性*/  
    protected String jndiName;  
    /**数据库连接对象*/  
    protected Connection connection;  
    public MyJDBCAppender() {  
        super();  
    }  
    @Override  
    protected void closeConnection(Connection connection) {  
        try {  
            if (connection != null && !connection.isClosed())  
                connection.close();  
        } catch (SQLException e) {  
            errorHandler.error("Error closing connection", e, ErrorCode.GENERIC_FAILURE);  
        }  
    }  
    @Override
    protected Connection getConnection() throws SQLException {  
        try {  
            //通过 PoolMan 获取数据库连接对象(http://nchc.dl.sourceforge.net/project/poolman/PoolMan/poolman-2.1-b1/poolman-2.1-b1.zip)  
            Class.forName("com.mangni.vegaplan.toolshelper.SqlHelper");  
            connection= ((MyJdbcTemplate)Bean.getCtx().getBean("myJdbcTemplate")).getDataSource().getConnection();
        } catch (Exception e) {  
            System.out.println(e.getMessage());  
        }  
        return connection;
    }  
    /** 
     * @return the jndiName 
     */  
    public String getJndiName() {  
        return jndiName;  
    }  
    /** 
     * @param jndiName the jndiName to set 
     */  
    public void setJndiName(String jndiName) {  
        this.jndiName = jndiName;  
    }  
}  