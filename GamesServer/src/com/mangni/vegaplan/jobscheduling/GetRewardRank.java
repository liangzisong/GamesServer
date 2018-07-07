package com.mangni.vegaplan.jobscheduling;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.mangni.vegaplan.toolshelper.SendMessageHelper;

public class GetRewardRank implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		Calendar calendar=Calendar.getInstance();
		if(calendar.get(Calendar.YEAR)==2017&&calendar.get(Calendar.MONTH)==6&&calendar.get(Calendar.DAY_OF_MONTH)==8){
			rewardRank();
		}
	}

	private void rewardRank(){
		
		List<Map<String,Object>> rank=new ArrayList<Map<String,Object>>();
		
		synchronized (new Object()) {

			List<Map<String,Object>> fightpowerrank=GetRank.getFightpowerrank();
			
			for(int i=0;i<20;i++){
				
				Map<String, Object> rankinfo=fightpowerrank.get(i);
				
				rank.add(rankinfo);
				
			}
			
		}
		
		// 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("战力表");  
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        HSSFRow row = sheet.createRow(0);  
        // 第四步，创建单元格，并设置值表头 设置表头居中  
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(CellStyle.ALIGN_CENTER); // 创建一个居中格式  
        
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("名次");  
        cell.setCellStyle(style);  
        cell = row.createCell(1);  
        cell.setCellValue("昵称");  
        cell.setCellStyle(style);  
        cell = row.createCell(2);  
        cell.setCellValue("战力");  
        cell.setCellStyle(style);  
        cell = row.createCell(3);  
        cell.setCellValue("playerid");  
        cell.setCellStyle(style);
        cell = row.createCell(4);  
        cell.setCellValue("fighterposition1");  
        cell.setCellStyle(style);  
        cell = row.createCell(5);  
        cell.setCellValue("fighterposition2");  
        cell.setCellStyle(style);  
        cell = row.createCell(6);  
        cell.setCellValue("fighterposition3");  
        cell.setCellStyle(style);  
        cell = row.createCell(7);  
        cell.setCellValue("SKINID1");  
        cell.setCellStyle(style);  
        cell = row.createCell(8);  
        cell.setCellValue("SKINID2");  
        cell.setCellStyle(style);  
        cell = row.createCell(9);  
        cell.setCellValue("SKINID3");  
        cell.setCellStyle(style);  
		
        for (int i = 0; i < rank.size(); i++)  
        {  
            row = sheet.createRow(i + 1);  
            Map<String,Object> rankmap = rank.get(i);  
            // 第四步，创建单元格，并设置值  
            row.createCell(0).setCellValue(String.valueOf(rankmap.get("num")));
            row.createCell(1).setCellValue(String.valueOf(rankmap.get("nickname")));
            row.createCell(2).setCellValue(String.valueOf(rankmap.get("fightpower")));
            row.createCell(3).setCellValue(String.valueOf(rankmap.get("playerid")));
            row.createCell(4).setCellValue(String.valueOf(rankmap.get("fighterposition1")));
            row.createCell(5).setCellValue(String.valueOf(rankmap.get("fighterposition2")));
            row.createCell(6).setCellValue(String.valueOf(rankmap.get("fighterposition3")));
            row.createCell(7).setCellValue(String.valueOf(rankmap.get("SKINID1")));
            row.createCell(8).setCellValue(String.valueOf(rankmap.get("SKINID2")));
            row.createCell(9).setCellValue(String.valueOf(rankmap.get("SKINID3")));
        }
        
        try  
        {  
            FileOutputStream fout = new FileOutputStream("d:/fightpowerrank.xls");  
            wb.write(fout);  
            fout.close();  
        }  
        catch (Exception e)  
        {  
            e.printStackTrace();  
        }
        
        for (int i = 0; i < rank.size(); i++)  
        {
        	Map<String, Object> rankmap = rank.get(i);
        	String sql="insert into player_mail(PLAYERID,MAILTYPE,MAILTITLE,mailcontent,showtime) values(?,?,?,?,?)";
        	String[] sqlpara={String.valueOf(rankmap.get("playerid")),"1","恭喜获奖！","恭喜您获得本次话费奖励活动战力榜第"+rankmap.get("num")+"名，请您于7月10日零时以前和我们联系，以便奖励尽快发放。客服QQ：1286885522。请在QQ留言中说明您中奖的名次与游戏呢称。谢谢合作","2017-07-10 23:59:59"};
        	SendMessageHelper.sendMail(sql, sqlpara, String.valueOf(rankmap.get("playerid")));
        }
	}

}
