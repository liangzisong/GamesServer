package com.mangni.vegaplan.toolshelper;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
public class SqlHelper1 
{

	/**
	 * 打开链接
	 */

	/**
	public static Connection contection()
	{

		Connection ct=null;
		String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String url="jdbc:sqlserver://"+Bean.getIp()+";databaseName="+Bean.getDatabaseName()+"";
		try 
		{
			Class.forName(driver);
			ct=DriverManager.getConnection(url,Bean.getUser(),Bean.getPasswd());
		} 
		catch (Exception e){}
		return ct;
	}
	 */
	private static DruidDataSource dataSource;

	public SqlHelper1(){
		//String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
		//String url="jdbc:sqlserver://"+Bean.getIp()+";databaseName="+Bean.getDatabaseName();
		String url="jdbc:sqlserver://localhost;integratedSecurity=true;DatabaseName="+Bean.getDatabaseName();
		//String username=Bean.getUser();
		//String password=Bean.getPasswd();


		dataSource = new DruidDataSource();
		//dataSource.setDriverClassName(driver); 
		//dataSource.setUsername("root"); 
		//dataSource.setPassword("11111111");
		dataSource.setUrl(url); 
		dataSource.setInitialSize(10); 
		dataSource.setMinIdle(10);
		dataSource.setMaxActive(500); 

		dataSource.setTimeBetweenEvictionRunsMillis(1000*60*5);
		dataSource.setTestWhileIdle(true);
		dataSource.setTestOnBorrow(false);
		dataSource.setTestOnReturn(false);
		dataSource.setValidationQuery("SELECT 1");
		try {
			dataSource.setFilters("stat");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		/*
	        dataSource = new ComboPooledDataSource();

	        dataSource.setUser(username);
	        dataSource.setPassword(password);
	        dataSource.setJdbcUrl(url);
	        dataSource.setDriverClass(driver);
	        //初始化时获取N个连接
	        dataSource.setInitialPoolSize(50);

	        dataSource.setMinPoolSize(50);
	        dataSource.setMaxPoolSize(1500);
	        //设置连接池最大statements对象容量
	        dataSource.setMaxStatements(0);

	        //最大空闲时间,60秒内未使用则连接被丢弃。若为0则永不丢弃。
	        dataSource.setMaxIdleTime(600);
		 */
	}

	public static DruidDataSource getDruidDataSource()
	{
		return dataSource;
	}
	/**
	 * 读取一个值
	 * 参数sql
	 * select ? from db.table where ?=? and ?=?; 
	 * @throws SQLException 
	 */
	public static String getOneRead(String sql,String []paras)
	{
		DruidPooledConnection ct=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String s=null;

		try {
			ct=dataSource.getConnection();
			ps=ct.prepareStatement(sql);

			for(int i=0;i<paras.length;i++)
			{
				ps.setString(i+1, paras[i]);
			}
			rs=ps.executeQuery();
			while(rs.next())
			{
				s=rs.getString(1);
			}
		} catch (SQLException e) {
			ErrlogHelper.Errlog("0", sql+"&&"+paras, e);
			e.printStackTrace();
		}
		finally{

			try{
				if(rs!=null)
				{
					rs.close();
				}
				if(ps!=null)
				{
					ps.close();
				}
				if(ct!=null)
				{
					ct.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return s;
	}
	public static String getOneRead(String sql)
	{
		DruidPooledConnection ct=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String s=null;

		try {
			ct=dataSource.getConnection();
			ps=ct.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next())
			{
				s=rs.getString(1);
			}
		} catch (SQLException e) {
			ErrlogHelper.Errlog("0", sql, e);
			e.printStackTrace();
		}
		finally{
			try{
				if(rs!=null)
				{
					rs.close();
				}
				if(ps!=null)
				{
					ps.close();
				}
				if(ct!=null)
				{
					ct.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return s;
	}

	/**
	 * 获取多行数据
	 * 参数sql fieldsum获取字段个数
	 * select xxxx from xx where id=xx
	 * @throws SQLException 
	 */

	public static List<HashMap<String,String>> getData(String sql)
	{
		PreparedStatement ps=null;
		ResultSet rs=null;
		DruidPooledConnection ct=null;
		ArrayList<HashMap<String,String>> mainlist=null;
		try {
			ct=dataSource.getConnection();
			ps=ct.prepareStatement(sql);
			rs=ps.executeQuery();
			ResultSetMetaData metadata=rs.getMetaData();
			int rscount=metadata.getColumnCount();
			int rowcount=rs.getRow();
			mainlist=new ArrayList<HashMap<String,String>>(rowcount*2);
			while(rs.next())
			{
				HashMap<String,String> mainmap=new HashMap<String,String>(rscount*2);
				for(int i=1;i<=rscount;i++)
				{
					mainmap.put(metadata.getColumnName(i),rs.getString(i));	
				}
				mainlist.add(mainmap);
			}
		} catch (SQLException e) {
			ErrlogHelper.Errlog("0", sql, e);
			e.printStackTrace();
		}
		finally{
			try{
				if(rs!=null)
				{
					rs.close();
				}
				if(ps!=null)
				{
					ps.close();
				}
				if(ct!=null)
				{
					ct.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return mainlist;  
	}

	public static List<HashMap<String,String>> getData(String sql,String[] paras)
	{
		PreparedStatement ps=null;
		ResultSet rs=null;
		DruidPooledConnection ct=null;
		ArrayList<HashMap<String,String>> mainlist=null;
		try {
			ct=dataSource.getConnection();
			ps=ct.prepareStatement(sql);
			for(int i=0;i<paras.length;i++)
			{
				ps.setString(i+1, paras[i]);
			}
			rs=ps.executeQuery();
			ResultSetMetaData metadata=rs.getMetaData();
			int rscount=metadata.getColumnCount();
			int rowcount=rs.getRow();
			mainlist=new ArrayList<HashMap<String,String>>(rowcount*2);
			while(rs.next())
			{
				HashMap<String,String> mainmap=new HashMap<String,String>(rscount*2);
				for(int i=1;i<=rscount;i++)
				{
					mainmap.put(metadata.getColumnName(i),rs.getString(i));	
				}
				mainlist.add(mainmap);
			}
		} catch (SQLException e) {
			ErrlogHelper.Errlog("0", sql, e);
			e.printStackTrace();
		}
		finally{
			try{
				if(rs!=null)
				{
					rs.close();
				}
				if(ps!=null)
				{
					ps.close();
				}
				if(ct!=null)
				{
					ct.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return mainlist;  
	}
	/**
	 * 获取一行
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public static List<String> getMyData(String sql)
	{
		PreparedStatement ps=null;
		ResultSet rs=null;
		DruidPooledConnection ct=null;
		List<String> list=null;
		try {
			ct=dataSource.getConnection();
			ps=ct.prepareStatement(sql);
			rs=ps.executeQuery();
			ResultSetMetaData metadata=rs.getMetaData();
			int colcount=metadata.getColumnCount();
			int rowcount=rs.getRow();
			list=new ArrayList<String>(rowcount*2);
			while(rs.next())
			{	
				for(int i=1;i<=colcount;i++)
				{
					list.add(rs.getString(i));	
				}
			}
		}catch (SQLException e) {
			ErrlogHelper.Errlog("0", sql, e);
			e.printStackTrace();
		}
		finally{
			try{
				if(rs!=null)
				{
					rs.close();
				}
				if(ps!=null)
				{
					ps.close();
				}
				if(ct!=null)
				{
					ct.close();
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return list;
	}


	/**
	 * 插入或更新
	 * 参数sql数组paras
	 * update(insert)  ???? from db.table
	 * @throws SQLException 
	 */
	public static int Updatedb(String sql,String []paras)
	{
		PreparedStatement ps=null;
		DruidPooledConnection ct=null;
		ResultSet rs=null;
		int id=-1;
		try {
			ct=dataSource.getConnection();
			ps=ct.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			for(int i=0;i<paras.length;i++)
			{
				ps.setString(i+1, paras[i]);
			}
			ps.executeUpdate();
			rs=ps.getGeneratedKeys();
			if(rs.next())
			{
				id = rs.getInt(1);
			}
			return id;
		}catch(Exception e){
			ErrlogHelper.Errlog("0", sql+"&&"+paras, e);
			e.printStackTrace();
			return id;
		}
		finally{
			try{
				if(ps!=null)
				{
					ps.close();
				}
				if(ct!=null)
				{
					ct.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	public static int Updatedb(String sql)
	{
		PreparedStatement ps=null;
		DruidPooledConnection ct=null;
		ResultSet rs=null;
		int id=-1;
		try {
			ct=dataSource.getConnection();
			ps=ct.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.executeUpdate();
			rs=ps.getGeneratedKeys();
			if(rs.next())
			{
				id = rs.getInt(1);
			}
			return id;
		} catch (SQLException e) {
			ErrlogHelper.Errlog("0", sql, e);
			e.printStackTrace();
			return id;
		}
		finally{
			try{
				if(rs!=null)
				{
					rs.close();
				}
				if(ps!=null)
				{
					ps.close();
				}
				if(ct!=null)
				{
					ct.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			} 
		}
	}


	public static String UpdatedbGetid(String sql)
	{
		PreparedStatement ps=null;
		DruidPooledConnection ct=null;
		ResultSet rs=null;
		String restr=null;
		try {
			ct=dataSource.getConnection();
			ps=ct.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.executeUpdate();
			rs=ps.getGeneratedKeys();
			if(rs.next())
			{
				restr=rs.getString(1);
			}
		} catch (SQLException e) {
			ErrlogHelper.Errlog("0", sql, e);
			e.printStackTrace();
		}
		finally{
			try{
				if(ps!=null)
				{
					ps.close();
				}
				if(ct!=null)
				{
					ct.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			} 
		}
		return restr;
	}

	/**
	 * 调用不带返回值存储过程
	 * @throws SQLException 
	 */
	public static void DbExecute(String proname,String[] para)
	{
		String sql="{call "+proname+"}";
		DruidPooledConnection ct=null;
		CallableStatement proc = null;
		try {
			ct=dataSource.getConnection();
			proc=ct.prepareCall(sql);

			for(int i=0;i<para.length;i++)
			{
				proc.setString(i+1, para[i]);
			}
			proc.execute();
		} catch (SQLException e) {
			ErrlogHelper.Errlog("0||"+para[0], sql, e);
			e.printStackTrace();
		}
		finally{

			try{
				if(ct!=null)
				{
					ct.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	} 

	/**
	 * 带1个返回参数的存储过程
	 * @param proname
	 * @param para
	 * @throws SQLException
	 */
	public static String DbExecute(String proname,String[] para,boolean b)
	{
		String res="false";
		String sql="{call "+proname+"}";
		DruidPooledConnection ct=null;
		CallableStatement proc = null;
		try {
			ct=dataSource.getConnection();
			proc=ct.prepareCall(sql);

			int i=0;
			while(i<para.length)
			{
				proc.setString(i+1, para[i]);
				i++;
			}
			proc.registerOutParameter(i+1,Types.VARCHAR);
			proc.execute();
			res=proc.getString(i+1);
		} catch (SQLException e) {
			ErrlogHelper.Errlog("0||"+para[0], sql, e);
			e.printStackTrace();
		}
		finally{
			try{
				if(ct!=null)
				{
					ct.close();
				}
				if(proc!=null)
				{
					proc.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return res;
	}

	public static List<String> DbExecute(String proname,String[] para,int output)
	{
		List<String> outlist=new ArrayList<String>(output*2);
		String res="false";
		String sql="{call "+proname+"}";
		DruidPooledConnection ct=null;
		CallableStatement proc = null;
		try {
			ct=dataSource.getConnection();
			proc=ct.prepareCall(sql);

			for(int i=0;i<para.length;i++){
				proc.setString(i+1, para[i]);						
			}
			for(int i=1;i<=output;i++){
				proc.registerOutParameter(para.length+i,Types.VARCHAR);
			}
			proc.execute();
			for(int i=1;i<=output;i++){
				res=proc.getString(para.length+i);
				outlist.add(res);
			}
		} catch (SQLException e) {
			ErrlogHelper.Errlog("0", sql+"&&"+para, e);
			e.printStackTrace();
		}
		finally{
			try{
				if(ct!=null)
				{
					ct.close();
				}
				if(proc!=null)
				{
					proc.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return outlist;
	}


	/**
	 * 获取总排行
	 */
	public static List<HashMap<String,String>> getTop(String sql,String [] cum)
	{
		PreparedStatement ps=null;
		ResultSet rs=null;
		DruidPooledConnection ct=null;
		ArrayList<HashMap<String,String>> mainlist=new ArrayList<HashMap<String,String>>(cum.length*2);
		try {
			ct=dataSource.getConnection();
			ps=ct.prepareStatement(sql);
			rs=ps.executeQuery();
			int colcount=rs.getMetaData().getColumnCount()*2;
			while(rs.next())
			{
				HashMap<String,String> mainmap=new HashMap<String,String>(colcount);
				for(int i=1;i<=cum.length;i++)
				{
					mainmap.put(cum[i-1],rs.getString(i));	
				}
				mainlist.add(mainmap);
			}
		} catch (SQLException e) {
			ErrlogHelper.Errlog("0", sql+"&&"+cum, e);
			e.printStackTrace();
		}
		finally{
			try{

				if(rs!=null)
				{
					rs.close();
				}
				if(ps!=null)
				{
					ps.close();
				}
				if(ct!=null)
				{
					ct.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}

		return mainlist;  
	}


	/**
	 * 判断是数据库否存在记录
	 * select count(id) from db.table where ?=? and ?=?
	 * @throws SQLException 
	 */
	public static String isChecked(String sql,String []paras)
	{
		DruidPooledConnection ct=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String s=null;
		int sum=0;
		try {
			ct=dataSource.getConnection();
			ps=ct.prepareStatement(sql);

			for(int i=0;i<paras.length;i++)
			{
				ps.setString(i+1, paras[i]);
			}
			rs=ps.executeQuery();
			while(rs.next())
			{
				s=rs.getString(1);
				sum++;
			}
		} catch (SQLException e) {
			ErrlogHelper.Errlog("0", sql+"&&"+paras, e);
			e.printStackTrace();
		}
		finally{
			try{
				if(rs!=null)
				{
					rs.close();
				}
				if(ps!=null)
				{
					ps.close();
				}
				if(ct!=null)
				{
					ct.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}

		//if(Integer.parseInt(str)==1){
		if(sum!=1)
		{
			return "false";
		}else
		{
			return s;
		}
	}

	public static String isChecked(String sql)
	{
		DruidPooledConnection ct=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String s=null;
		int sum=0;
		try {
			ct=dataSource.getConnection();
			ps=ct.prepareStatement(sql);

			rs=ps.executeQuery();
			while(rs.next())
			{
				s=rs.getString(1);
				sum++;
			}
		} catch (SQLException e) {
			ErrlogHelper.Errlog("0", sql, e);
			e.printStackTrace();
		}
		finally{
			try{
				if(rs!=null)
				{
					rs.close();
				}
				if(ps!=null)
				{
					ps.close();
				}
				if(ct!=null)
				{
					ct.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}

		//if(Integer.parseInt(str)==1){
		if(sum!=1)
		{
			return "false";
		}else
		{
			return s;
		}
	}

	public static String execTransaction(List<String> sqllist)
	{
		DruidPooledConnection con=null;

		PreparedStatement ps=null;

		Savepoint sp=null;

		String res="false";

		try {

			con=dataSource.getConnection();

			con.setAutoCommit(false);

			//con.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
			sp=con.setSavepoint();

			int length=sqllist.size();

			for(int i=0;i<length;i++)
			{

				String str=sqllist.get(i);

				ps=con.prepareStatement(str);

				ps.executeUpdate();

			}

			con.commit();

			res="true";

		}catch(SQLException e){

			try {

				con.rollback(sp);

				con.commit();

				ErrlogHelper.Errlog("0", sqllist.toString(), e);

				e.printStackTrace();

			} catch (SQLException e1) {

				// TODO Auto-generated catch block

				e1.printStackTrace();

			}

		}finally{

			try {

				con.setAutoCommit(true);

				if(ps!=null)
				{
					ps.close();
				}
				if(con!=null)
				{
					con.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return res;
	}

	public static String execTransaction(String sql,List<String[]> sqlparalist)
	{
		DruidPooledConnection con=null;

		PreparedStatement ps=null;

		Savepoint sp=null;

		String res="false";

		try {

			con=dataSource.getConnection();

			con.setAutoCommit(false);

			//con.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
			sp=con.setSavepoint();

			int length=sqlparalist.size();

			for(int i=0;i<length;i++)
			{

				ps=con.prepareStatement(sql);

				String[] sqlpara=sqlparalist.get(i);

				for(int j=0;j<sqlpara.length;j++)
				{

					ps.setString(j+1, sqlpara[j]);

				}

				ps.executeUpdate();

			}

			con.commit();

			res="true";

		}catch(SQLException e){

			try {

				con.rollback(sp);

				con.commit();

				ErrlogHelper.Errlog("0", sql, e);

				e.printStackTrace();

			} catch (SQLException e1) {

				// TODO Auto-generated catch block

				e1.printStackTrace();

			}

		}finally{

			try {

				con.setAutoCommit(true);

				if(ps!=null)
				{
					ps.close();
				}
				if(con!=null)
				{
					con.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return res;
	}

	public static String execTransaction(List<String> sqllist,List<String[]> sqlparalist)
	{
		DruidPooledConnection con=null;

		PreparedStatement ps=null;

		Savepoint sp=null;

		String res="false";

		try {

			con=dataSource.getConnection();

			con.setAutoCommit(false);

			//con.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
			sp=con.setSavepoint();

			int length=sqllist.size();

			for(int i=0;i<length;i++)
			{

				String sql=sqllist.get(i);

				ps=con.prepareStatement(sql);

				String[] sqlpara=sqlparalist.get(i);

				for(int j=0;j<sqlpara.length;j++)
				{

					ps.setString(j+1, sqlpara[j]);

				}

				ps.executeUpdate();

			}

			con.commit();

			res="true";

		}catch(SQLException e){

			try {

				con.rollback(sp);

				con.commit();

				ErrlogHelper.Errlog("0", sqllist.toString(), e);

				e.printStackTrace();

			} catch (SQLException e1) {

				// TODO Auto-generated catch block

				e1.printStackTrace();

			}

		}finally{

			try {

				con.setAutoCommit(true);

				if(ps!=null)
				{
					ps.close();
				}
				if(con!=null)
				{
					con.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return res;
	}

	/*
	public static void main(String [] args) throws FileNotFoundException
	{
		new Bean();
		String [] str={"znx_player","nickname='aaa',fightpower='100'","id='67'"};
		try 
		{
			Updatedb("update "+str[0]+" set "+str[1]+" where "+str[2]);
			System.out.println(123);
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
}
