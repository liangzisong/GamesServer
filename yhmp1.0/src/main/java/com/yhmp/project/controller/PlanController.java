package com.yhmp.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yhmp.common.web.JsonResult;
import com.yhmp.project.entity.ProjectPlan;
import com.yhmp.project.service.PlanServer;
/**
 * 项目计划控制器
 * @author liang
 *
 */
@Controller
@RequestMapping("/plan/")
public class PlanController {

	@Autowired
	public PlanServer planServer;
	@ResponseBody
	@RequestMapping("addPrjectPlan")
	public JsonResult addPrjectPlan(ProjectPlan projectPlan) {
		System.out.println("PlanController.addPrjectPlan()");
		System.out.println(projectPlan);
		planServer.addPrjectPlan(projectPlan);
		return new JsonResult("完成");
	}
	@ResponseBody
	@RequestMapping("deletePrjectPlan")
	public JsonResult deletePrjectPlan(ProjectPlan projectPlan) {
		System.out.println("PlanController.deletePrjectPlan()");
		System.out.println(projectPlan);
		planServer.deletePrjectPlan(projectPlan);
		return new JsonResult("完成");
	}
	@ResponseBody
	@RequestMapping("selectPrjectPlan")
	public JsonResult selectPrjectPlan(ProjectPlan projectPlan) {
		System.out.println("PlanController.addPrjectPlan()");
		System.out.println(projectPlan);
		List<ProjectPlan> list = planServer.selectPrjectPlan(projectPlan);
		return new JsonResult(list);
	}
//	private ProjectTypeService projectTypeService;
//	//进入工作计划表主页面
//	@RequestMapping("listUI")
//	public String listUI() {
//		System.out.println("进入plan.listUI");
//		return "project/Plan";
//	}
//	//处理点击提交之后的请求
//	@RequestMapping("disposeSubmit")
//	@ResponseBody
//	public JsonResult disposeSubmit(WorkProgram workProgram) {
//		System.out.println(workProgram);
//		System.out.println("进入/plan/disposeSubmit");
//		String periodicPlan = workProgram.getPeriodicPlan();
//		periodicPlan = periodicPlan.substring(0, periodicPlan.length()-3);
//		String plan[] = periodicPlan.split("##");
//		for(int i=0;i<plan.length;i++) {
//			String[] periodicPlans = plan[i].split("&&");
//			Plan p = new WorkProgram().getPlan();
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			p.setStage(Integer.valueOf(periodicPlans[0]));
//			p.setContent(periodicPlans[1]);
//			try {
//				p.setTime(sdf.parse(periodicPlans[2]));
//			} catch (ParseException e) {
//				e.printStackTrace();
//				return new JsonResult("请输入正确的日期");
//			}
//			p.setMember(periodicPlans[3]);
//			System.out.println(p);
//		}
//		
//		return new JsonResult("提交完成");
//	}
}
