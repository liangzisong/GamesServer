package com.mangni.vegaplan.servletsrc.customsystem.servletsrc;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.jetty.websocket.api.Session;
import com.mangni.vegaplan.datatable.Znx_GmData;
import com.mangni.vegaplan.servletsrc.customsystem.tools.Initialise;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class GmLogin{
	private MyJdbcTemplate myJdbcTemplate;
	public MyJdbcTemplate getMyJdbcTemplate() {
		return myJdbcTemplate;
	}
	public void setMyJdbcTemplate(MyJdbcTemplate myJdbcTemplate) {
		this.myJdbcTemplate = myJdbcTemplate;
	}
	
	public HashMap<String, Object> dopost(HashMap<String, Object> request,Session session) {
		// TODO Auto-generated method stub
		HashMap<String,Object> response=new HashMap<String,Object>();
		String username=(String)request.get("username");
		String pwd=(String)request.get("pwd");
		String res="false";
		String sql="SELECT top 1 * FROM gm_manager WHERE username=? and pwd=?";
		Object[] sqlpara={username,pwd};
		
		List<Map<String,Object>> mainres=myJdbcTemplate.queryForList(sql, sqlpara);
		if(mainres!=null&&mainres.size()>0){
			String lastip=session.getRemoteAddress().getAddress().getHostAddress();
			myJdbcTemplate.update("update gm_manager set lastip='"+lastip+"',updatetime=getdate() where id="+mainres.get(0).get("id"));
			
			Znx_GmData znx_gmdata=new Znx_GmData();
			znx_gmdata.setId(String.valueOf(mainres.get(0).get("id")));
			znx_gmdata.setUsername(String.valueOf(mainres.get(0).get("username")));
			znx_gmdata.setRealname(String.valueOf(mainres.get(0).get("realname")));
			znx_gmdata.setAuthority(String.valueOf(mainres.get(0).get("authority")));
			Initialise.getZnx_gmmap().put(znx_gmdata.getId(), znx_gmdata);
			res="true";
		}
		response.put("res", res);
		response.put("data", mainres);
		return response;
	}

}
