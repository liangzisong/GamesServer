package com.mangni.vegaplan.jobscheduling;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.mangni.vegaplan.datatable.DailytaskData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;
import com.mangni.vegaplan.toolshelper.XmlHelper;

public class RefreshDailytask implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		refresh();
	}

	public synchronized void refresh(){
		MyJdbcTemplate myJdbcTemplate=(MyJdbcTemplate) Bean.getCtx().getBean("myJdbcTemplate");
		String updatetime=myJdbcTemplate.queryForObject("select top 1 updatetime from znx_dailytask",String.class);
		Calendar nowc=Calendar.getInstance();
		Date updatedate=null;
		if(updatetime!=null){
			try {
				updatedate=Bean.getDateFormat().parse(updatetime);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			nowc.set(Calendar.HOUR_OF_DAY, 3);
			nowc.set(Calendar.MINUTE,0);
			nowc.set(Calendar.SECOND,0);
		}

		HashMap<Integer,List<DailytaskData>> dailytasklistmap=new HashMap<Integer,List<DailytaskData>>();
		SAXBuilder builder = new SAXBuilder();
		String curDir=System.getProperty("user.dir");
		String dir=curDir+"\\xmllib\\";

		List<String> pathlist=XmlHelper.fileList(dir,"xml");

		for(String filepath:pathlist)
		{
			String filename=filepath.substring(filepath.lastIndexOf("\\")+1,filepath.lastIndexOf("."));

			Document read_doc = null;
			try {
				read_doc = builder.build(filepath.toString());
			} catch (JDOMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Element root = read_doc.getRootElement();
			List list = root.getChildren("data");
			switch(filename)
			{
			case "dailytask":
				Bean.getDailytaskmap().clear();
				for(int i=0; i<list.size(); i++)
				{
					DailytaskData dailytaskdata=new DailytaskData();

					Element stu = (Element)list.get(i);

					Element request = stu.getChild("Id");
					dailytaskdata.setId(request.getAttributeValue("Value"));

					request = stu.getChild("Dot");
					dailytaskdata.setDot(Integer.parseInt(request.getAttributeValue("Value")));

					request = stu.getChild("TaskCount");
					dailytaskdata.setTaskcount(Integer.parseInt(request.getAttributeValue("Value")));

					request = stu.getChild("RewardStone");
					dailytaskdata.setRewardstone(Integer.parseInt(request.getAttributeValue("Value")));

					request = stu.getChild("RewardGold");
					dailytaskdata.setRewardgold(Integer.parseInt(request.getAttributeValue("Value")));

					request = stu.getChild("RewardExp");
					dailytaskdata.setRewardexp(Integer.parseInt(request.getAttributeValue("Value")));

					request = stu.getChild("Type");
					dailytaskdata.setType(Integer.parseInt(request.getAttributeValue("Value")));

					request = stu.getChild("Name");
					if(request!=null)
						dailytaskdata.setName(request.getAttributeValue("Value"));

					request = stu.getChild("Description");
					if(request!=null)
						dailytaskdata.setDesc(request.getAttributeValue("Value"));

					if(dailytasklistmap.containsKey(dailytaskdata.getType())){
						dailytasklistmap.get(dailytaskdata.getType()).add(dailytaskdata);
					}else{
						List<DailytaskData> dl=new ArrayList<DailytaskData>();
						dl.add(dailytaskdata);
						dailytasklistmap.put(dailytaskdata.getType(), dl);
					}
				}
				break;

			default :
				break;
			}
		}

		if(updatedate==null||(nowc.getTimeInMillis()-updatedate.getTime())>(1000*3600*24)){
			myJdbcTemplate.update("delete from znx_dailytask");
			String sql=null;
			Random random=new Random();
			Iterator<Entry<Integer, List<DailytaskData>>> iterator=dailytasklistmap.entrySet().iterator();
			while(iterator.hasNext()){
				Entry<Integer,List<DailytaskData>> entry=iterator.next();
				List<DailytaskData> dailytasklist=entry.getValue();
				DailytaskData dailytaskdata=dailytasklist.get(random.nextInt(dailytasklist.size()));
				sql="insert into znx_dailytask(taskid,updatetime) values("+dailytaskdata.getId()+",getdate())";
				myJdbcTemplate.update(sql);
				Bean.getDailytaskmap().put(dailytaskdata.getId(), dailytaskdata);
			}
		}else{	
			List<String> list=myJdbcTemplate.queryForList("select taskid from znx_dailytask",String.class);
			Iterator<Entry<Integer, List<DailytaskData>>> iterator=dailytasklistmap.entrySet().iterator();
			while(iterator.hasNext()){
				Entry<Integer,List<DailytaskData>> entry=iterator.next();
				List<DailytaskData> dailytasklist=entry.getValue();
				for(DailytaskData d:dailytasklist){
					if(list.contains(d.getId())){
						Bean.getDailytaskmap().put(d.getId(), d);
					}
				}
			}
		}
	}
}
