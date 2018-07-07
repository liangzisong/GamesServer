package com.mangni.vegaplan.servletsrc.gang;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class UpdateGang implements IReceiveMessage {
	private MyJdbcTemplate myJdbcTemplate;
	public MyJdbcTemplate getMyJdbcTemplate() {
		return myJdbcTemplate;
	}
	public void setMyJdbcTemplate(MyJdbcTemplate myJdbcTemplate) {
		this.myJdbcTemplate = myJdbcTemplate;
	}
	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Object> dopost(HashMap<String, Object> request) {
		HashMap<String,Object> response=new HashMap<String,Object>();
		String playerid=(String)request.get("playerid");
		List<String> cums=(List<String>) request.get("cum");
		List<String> values=(List<String>) request.get("values");
		String res="false";
		Map<String, Object> ganginfo=myJdbcTemplate.queryForMap("SELECT GANGID,DUTIES FROM PLAYER_GANGINFO WHERE PLAYERID="+playerid);
		String gangid=String.valueOf(ganginfo.get("gangid"));
		int duites=Integer.parseInt(String.valueOf(ganginfo.get("duties")));
		if(gangid!=null){
			String sql="UPDATE PLAYER_GANG SET ";//"+cum+" WHERE ID="+gangid;
			int sum=0;
			for(int i=0;i<cums.size();i++){
				switch(cums.get(i)){
				case "gangname":
					if(duites==1){
						sql+=cums.get(i)+"='"+values.get(i)+"',";
						sum++;
					}
					break;
				case "declaration":
					if(duites<=2){
						sql+=cums.get(i)+"='"+values.get(i)+"',";
						sum++;
					}
					break;
				case "limitlv":
					if(duites==1){
						sql+=cums.get(i)+"='"+values.get(i)+"',";
						sum++;
					}
					break;
				case "icon":
					if(duites==1){
						sql+=cums.get(i)+"='"+values.get(i)+"',";
						sum++;
					}
					break;
				case "gangpwd":
					if(duites==1){
						String pwd=myJdbcTemplate.queryForObject("select gangpwd from player_gang where id="+gangid,String.class);
						String oldpwd=(String)request.get("oldpwd");
						if(oldpwd.equals(pwd)){
							sql+=cums.get(i)+"='"+values.get(i)+"',";
							sum++;
						}
					}
				}
			}
			if(sum>0){
				sql=sql.substring(0, sql.length()-1);
				sql+=" WHERE ID="+gangid;
				myJdbcTemplate.update(sql);
				res="true";
			}
				
		}
		response.put("res", res);
		return response;
	}

}
