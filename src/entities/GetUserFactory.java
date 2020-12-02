package entities;

public class GetUserFactory {

	public User GetUserFactory(String type) {
		if(type == null) {
			return null;	
		}
		if(type == "Lecturer") {
			return new Lecturer();
		}
		else if(type =="Student") {
			return new Student();
		}
		return null;
	}

}
