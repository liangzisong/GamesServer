package product;

import org.junit.Test;

import com.yhmp.project.entity.ProjectPlan;
import com.yhmp.project.service.PlanServer;

public class PlanTest extends TestBean {

	@Test
	public void Test(){
		PlanServer declareServer = ctx.getBean("planServerImpl", PlanServer.class);
		ProjectPlan projectPlan = new ProjectPlan();
		projectPlan.setId(3);
		projectPlan.setDeclareProject("项目名称");
		projectPlan.setResponsibilityName("负责人姓名");
		projectPlan.setPeriodicPlan("1^111^2018-06-01^111|2^222^2018-06-02^222|3^222^2018-06-02^222");
		declareServer.addPrjectPlan(projectPlan);
	}
}
