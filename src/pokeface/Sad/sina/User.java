package pokeface.Sad.sina;

import java.util.List;

public class User {
	String username;
	String url;
	String uid;
	List<User> followers; //��ע���б�
	List<User> fans; //��˿�б�
	Boolean isExploded  = false;
	String sex;
	String location;
	String birthday;
	@Override
	public String toString() {
		return "User [username=" + username + ", url=" + url + ", uid=" + uid
				+ "]";
	}
}
