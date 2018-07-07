package com.mangni.vegaplan.servletsrc.mercenary;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import com.mangni.vegaplan.datatable.MercenarytaskData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class GetMercenaryTask {

	public String getMercenary(){

		MyJdbcTemplate myJdbcTemplate=(MyJdbcTemplate) Bean.getCtx().getBean("myJdbcTemplate");

		String selsql="SELECT top 1 updatetime FROM znx_mercenarytask";

		String updatetime=myJdbcTemplate.queryForObject(selsql,String.class);

		String sql=null;

		String res=null;

		if(updatetime==null){

			sql="INSERT INTO znx_mercenarytask(expaward,goldaward,stoneaward,jewelid,chipid,goodsid,id) VALUES(?,?,?,?,?,?,?)";

		}else{

			Date dupdatedate=null;

			try {

				dupdatedate=Bean.getDateFormat().parse(updatetime);

			} catch (ParseException e) {

				e.printStackTrace();

			}
			Calendar updatedate=Calendar.getInstance();

			updatedate.setTime(dupdatedate);

			updatedate.add(Calendar.HOUR_OF_DAY, -3);

			Calendar calendar=Calendar.getInstance();

			calendar.add(Calendar.HOUR_OF_DAY, -3);

			if(updatedate.get(Calendar.YEAR)==calendar.get(Calendar.YEAR)){

				if(updatedate.get(Calendar.DAY_OF_YEAR)<calendar.get(Calendar.DAY_OF_YEAR))

					sql="UPDATE znx_mercenarytask SET expaward=?,goldaward=?,stoneaward=?,jewelid=?,chipid=?,goodsid=?,updatetime=getdate() WHERE id=?";

			}else{

				sql="UPDATE znx_mercenarytask SET expaward=?,goldaward=?,stoneaward=?,jewelid=?,chipid=?,goodsid=?,updatetime=getdate() WHERE id=?";

			}

		}

		if(sql!=null){

			List<Object[]> sqlparalist=getMercenarySql();

			for(int i=0;i<sqlparalist.size();i++){
				myJdbcTemplate.update(sql,sqlparalist.get(i));
			}

		}else{

			res="true";

		}

		return res;

	}

	private List<Object[]> getMercenarySql(){

		List<Object[]> sqlparalist=new ArrayList<Object[]>();

		HashMap<String,MercenarytaskData> taskmap = Bean.getMercenarytaskmap();

		Random random=new Random();

		for(int i=1;i<=taskmap.size();i++)
		{
			MercenarytaskData tasktdata=taskmap.get(String.valueOf(i));

			String[] sqlpara=new String[7];

			sqlpara[0]=tasktdata.getExp();

			int goldprob=(int)(Double.parseDouble(tasktdata.getGoldprob())*100);

			int goldran=random.nextInt(99);

			if(goldran<goldprob){//gold

				sqlpara[1]=tasktdata.getGold();

				sqlpara[2]="0";

			}else{//stone

				sqlpara[1]="0";

				sqlpara[2]=tasktdata.getStone();

			}

			int jewelprob=(int)(Double.parseDouble(tasktdata.getJewelprob())*100);

			int chipprob=(int)(Double.parseDouble(tasktdata.getChipprob())*100);

			int ranid=random.nextInt(99);

			if(ranid>jewelprob+chipprob){//goosd

				sqlpara[3]="0";

				sqlpara[4]="0";

				sqlpara[5]=tasktdata.getGoodsid();

			}else if(ranid>jewelprob){//chip

				sqlpara[3]="0";

				sqlpara[4]=tasktdata.getChipid();

				sqlpara[5]="0";

			}else{//jewel

				sqlpara[3]=tasktdata.getJewelid();

				sqlpara[4]="0";

				sqlpara[5]="0";

			}

			sqlpara[6]=tasktdata.getId();

			sqlparalist.add(sqlpara);

		}

		if(sqlparalist.size()!=taskmap.size()){

			try {
				throw new MercenaryTaskException("插入条目数不等于模版条目数！");
			} catch (MercenaryTaskException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return sqlparalist;

	}

}
