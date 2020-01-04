package yan.ibbie.TYcrowdfunding.vo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import yan.ibbie.TYcrowdfunding.bean.MemberCert;
import yan.ibbie.TYcrowdfunding.bean.Role;
import yan.ibbie.TYcrowdfunding.bean.User;

public class Data {
	private List<User> userList = new ArrayList<User>();
	private List<Role> roleList = new ArrayList<Role>();
	private List<Integer> idList = new ArrayList<Integer>();
	private List<MemberCert> memberCerts = new ArrayList<MemberCert>();

	public List<MemberCert> getMemberCerts() {
		return memberCerts;
	}

	public void setMemberCerts(List<MemberCert> memberCerts) {
		this.memberCerts = memberCerts;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public List<Integer> getIdList() {
		return idList;
	}

	public void setIdList(List<Integer> idList) {
		this.idList = idList;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	
	
}
