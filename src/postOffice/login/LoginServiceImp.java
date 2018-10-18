package postOffice.login;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImp implements LoginService {
	
	@Autowired
	private JdbcTemplate jabcTemplate;

	@Override
	public Map<String, Object> login(String userName,String password) {
		String sql = "SELECT s.staffName,s.password,b.branchId,b.branchName,b.fatherId t_staff s INNER JOIN t_branch b ON s.branchId = b.branchId WHERE s.lanchId = '"+userName+"'";
		List<Map<String, Object>> user = this.jabcTemplate.queryForList(sql);
		if(user.size() > 0){
			if(user.get(0).get("password").toString().equals(password)){
				return user.get(0);
			}else{
				return null;
			}
		}
		return null;
	}

}
