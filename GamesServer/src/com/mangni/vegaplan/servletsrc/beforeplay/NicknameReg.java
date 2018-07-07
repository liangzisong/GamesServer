package com.mangni.vegaplan.servletsrc.beforeplay;

import java.util.HashMap;
import org.eclipse.jetty.websocket.api.Session;
import org.springframework.transaction.annotation.Transactional;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;
import com.mangni.vegaplan.toolshelper.SendHttpRequest;
/**
 * 客户端获取用户信息，客户端发送 nickname&username&password&sex&headimageid,服务器返回用户信息或false
 */
@Transactional
public class NicknameReg{
	private MyJdbcTemplate myJdbcTemplate;
	public MyJdbcTemplate getMyJdbcTemplate() {
		return myJdbcTemplate;
	}
	public void setMyJdbcTemplate(MyJdbcTemplate myJdbcTemplate) {
		this.myJdbcTemplate = myJdbcTemplate;
	}
	public NicknameReg(){
		myJdbcTemplate=(MyJdbcTemplate) Bean.getCtx().getBean("myJdbcTemplate");
	}
	
	public HashMap<String,Object> method(HashMap<String,Object> request,Session session)
	{
		HashMap<String,Object> response=new HashMap<String,Object>();
		String nickname=(String) request.get("nickname");
		if(nickname.length()<2)
			return null;
		String username=(String) request.get("username");
		String password=(String) request.get("password");
		String sex=(String) request.get("sex");
		String headimageid=(String) request.get("headimageid");
		String uid=(String) request.get("uid");
		String sdkid=(String) request.get("sdk");
		String res1=myJdbcTemplate.queryForObject("select id from znx_player where nickname='"+nickname+"'",String.class);
		String res2=null;
		//String regtime=null;
		if(res1==null)
		{
			if(uid!=null&&sdkid!=null&&uid!=""&&sdkid!=""){
				Object [] str={nickname,uid,sdkid,sex,headimageid,session.getRemoteAddress().getAddress().getHostAddress()};
				res2=myJdbcTemplate.queryForObject("select id from znx_player where uid=? and sdkid=?",new String[]{uid,sdkid},String.class);
				if(res2==null)
				{
					myJdbcTemplate.update("insert into znx_player(nickname,uid,sdkid,sex,headimageid,lastip) values(?,?,?,?,?,?)", str);
				}
				else
				{
					myJdbcTemplate.update("update znx_player set nickname=?,uid=?,sdkid=?,sex=?,headimageid=?,lastip=? where id="+res2, str);
				}
			}else{
				Object [] str={nickname,username,password,sex,headimageid,session.getRemoteAddress().getAddress().getHostAddress()};
				int chr=username.charAt(0);
				if(chr<48||chr>57)
				{
					res2=myJdbcTemplate.queryForObject("select id from znx_player where username='"+username+"'",String.class);
					if(res2==null)
					{
						myJdbcTemplate.update("insert into znx_player(nickname,username,pwd,sex,headimageid,lastip) values(?,?,?,?,?,?)", str);
					}
					else
					{
						myJdbcTemplate.update("update znx_player set nickname=?,username=?,pwd=?,sex=?,headimageid=?,lastip=? where id="+res2, str);
					}
				}
				else
				{
					res2=myJdbcTemplate.queryForObject("select id from znx_player where phonenumber='"+username+"'",String.class);
					if(res2==null)
					{
						String serverid=Bean.getServerid();
						String httpres=SendHttpRequest.sendrequest("adduserserver","phonenumber="+username+"&serverid="+serverid);
						String[] respara=httpres.split(",");
						String addres=respara[0];
						//regtime=respara[1];

						if(addres.equals("true")||addres.equals("have"))
							myJdbcTemplate.update("insert into znx_player(nickname,phonenumber,pwd,sex,headimageid,lastip) values(?,?,?,?,?,?)", str);
					}
					else
					{
						myJdbcTemplate.update("update znx_player set nickname=?,phonenumber=?,pwd=?,sex=?,headimageid=?,lastip=? where id="+res2, str);
					}
				}
			}
			
			UserLogin userlogin=new UserLogin();
			response=userlogin.login(request, session);
		}else{
			response.put("res", "false");
		}
		return response;
	}

}