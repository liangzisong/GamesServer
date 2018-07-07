package com.mangni.vegaplan.servletsrc.selectdata;

import java.util.HashMap;

import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.jobscheduling.GetRank;

public class GetAllRank implements IReceiveMessage{
	@Override
	public HashMap<String, Object> dopost(HashMap<String, Object> request) {
		HashMap<String,Object> response=new HashMap<String,Object>();
		String gettype=(String)request.get("gettype");
		switch(gettype)
		{
		default:
			break;
		case "militaryrank":
			response.put("rank", GetRank.getMilitaryrank());
			break;
		case "fightpowerrank":
			response.put("rank", GetRank.getFightpowerrank());
			break;
//		case "trialranklv1":
//			response.put("rank", GetRank.getTrialranklv1());
//			break;
//		case "trialranklv2":
//			response.put("rank", GetRank.getTrialranklv2());
//			break;
//		case "trialranklv3":
//			response.put("rank", GetRank.getTrialranklv3());
//			break;
//		case "trialranklv4":
//			response.put("rank", GetRank.getTrialranklv4());
//			break;
//		case "trialranklv5":
//			response.put("rank", GetRank.getTrialranklv5());
//			break;
		}
		return response;
	}
}
