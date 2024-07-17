package pojoClass;

import java.util.List;

public class pojoClassCourses {
	
	private List<pojoCLassForCourseWebAutomation> webAutomation;		
	public List<pojoCLassForCourseWebAutomation> getWebAutomation() {
		return webAutomation;
	}
	public void setWebAutomation(List<pojoCLassForCourseWebAutomation> webAutomation) {
		this.webAutomation = webAutomation;
	}
	private List<pojoCLassForCourseAPI> api;
	public List<pojoCLassForCourseAPI> getApi() {
		return api;
	}
	public void setApi(List<pojoCLassForCourseAPI> api) {
		this.api = api;
	}
	private List<pojoCLassForCourseMobile> mobile;
	public List<pojoCLassForCourseMobile> getMobile() {
		return mobile;
	}
	public void setMobile(List<pojoCLassForCourseMobile> mobile) {
		this.mobile = mobile;
	}

}
