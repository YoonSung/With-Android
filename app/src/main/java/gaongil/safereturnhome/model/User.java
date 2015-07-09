package gaongil.safereturnhome.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class User extends BaseDaoEnabled<User, Integer> {
	
	@DatabaseField(generatedId=true)
	private int id;
	
	@DatabaseField
	private String name;
	
	@DatabaseField
	private String imageName;
	
	@DatabaseField
	private String nickname;

	@DatabaseField
	private String phoneNumber;
	
	//It's for ORMLite
	public User(){}
	
	public User(String name, String imageNmae, String nickname) {
		this.name = name;
		this.imageName = imageNmae;
		this.nickname = nickname;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	};

	public String getPhoneNumber() {
		return phoneNumber;
	}
}
