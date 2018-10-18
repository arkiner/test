package postOffice.login;

import java.util.Map;

public interface LoginService {

	Map<String, Object> login(String userName,String password);
}
