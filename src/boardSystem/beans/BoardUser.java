package boardSystem.beans;

import java.io.Serializable;
import java.util.Date;

public class BoardUser implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private int userId;
	private String login_id;
	private String name;
	private int branch_id;
	private String branchName;
	private int department_id;
	private String departmentName;
	private String password;
	private int isWorking;
	private Date createDate;
	private Date updateDate;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getLoginId() {
		return login_id;
	}

	public void setLoginId(String login_id) {
		this.login_id = login_id;
	}

	public int getBranchId() {
		return branch_id;
	}

	public void setBranchId(int branch_id) {
		this.branch_id = branch_id;
	}

	public String getBranchName() {
		return branchName ;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public int getDepartmentId() {
		return department_id;
	}

	public void setDepartmentId(int department_id) {
		this.department_id = department_id;
	}

	public String getDepartmentName() {
		return departmentName ;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getIsWorking() {
		return isWorking;
	}

	public void setIsWorking(int isWorking) {
		this.isWorking = isWorking;
	}


	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}
