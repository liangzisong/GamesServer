package product;

import org.junit.Test;

import com.yhmp.project.entity.PorjectDeclare;
import com.yhmp.project.service.DeclareServer;

public class TestDeclare extends TestBean {

	@Test
	public void testInsertPorjectDeclare() {
		DeclareServer declareServer = ctx.getBean("declareServerImpl", DeclareServer.class);
		PorjectDeclare porjectDeclare=new PorjectDeclare();
		porjectDeclare.setDeclareProject("基础测绘项目0");
		porjectDeclare.setDeclareNumber("项目编号");
		porjectDeclare.setDeclareFraction("项目标准分");
		porjectDeclare.setDeclareScale("项目规模");
		porjectDeclare.setDeclareArrange("项目类型：整理");
		porjectDeclare.setDeclareAssess("项目类型 ：评估");
		porjectDeclare.setDeclareSurveying("项目类型：测绘");
		porjectDeclare.setDeclarePlan("项目类型：规划");
		porjectDeclare.setContract(1);
		porjectDeclare.setDeclareCompany("单位名称");
		porjectDeclare.setDeclareName("联系人姓名");
		porjectDeclare.setDeclarePhone("联系人电话");
		porjectDeclare.setFinishTime("项目完成时间");
		porjectDeclare.setQuality(2);
		porjectDeclare.setBid(2);
		porjectDeclare.setResponsibilityName("负责人姓名");
		porjectDeclare.setResponsibilityPhone("负责人电话");
		
		System.out.println(declareServer.insertPorjectDeclare(porjectDeclare));
	}
}
