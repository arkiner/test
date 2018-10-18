package postOffice.login;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private LoginService loginServiceImp;
	
	@RequestMapping("/login")
	@ResponseBody
	public Map<String, Object> login(@RequestBody String userName,@RequestBody String password,HttpServletRequest request){
		Map<String, Object> user = (Map<String, Object>) this.loginServiceImp.login(userName, password);
		if(user != null){
			user.put("success", "1");
			request.getSession().setAttribute("userName", userName);
			request.getSession().setAttribute("branchName", user.get("branchName").toString());
			request.getSession().setAttribute("branchId", user.get("branchId").toString());
		}else{
			user = new HashMap<String, Object>();
			user.put("success", "0");
		}
		return user;
	}
}
