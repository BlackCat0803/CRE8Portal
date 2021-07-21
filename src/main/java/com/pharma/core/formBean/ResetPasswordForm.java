package com.pharma.core.formBean;
/**
 * This class is a mcv form bean
 *
 */
public class ResetPasswordForm {

		private int userId;
	    private String userName;
	    private String userType;
	    private String userEmailId;
	    public String oldPassword;
		public String newPassword;
		public String reenterPassword;
		private Integer securityquestion;
	    private String securityanswer;
	    private Integer securityquestion2;
	    private String securityanswer2;
	    
	    private String username;
		private String password;
		private String type;
		
		// for session access purpose 
		private int userid;
		private String displayName;
		private String status;
		private String photoUrl;
		
		private String rememberMe;
	    
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getUserType() {
			return userType;
		}
		public void setUserType(String userType) {
			this.userType = userType;
		}
		public String getUserEmailId() {
			return userEmailId;
		}
		public void setUserEmailId(String userEmailId) {
			this.userEmailId = userEmailId;
		}
		public String getOldPassword() {
			return oldPassword;
		}
		public void setOldPassword(String oldPassword) {
			this.oldPassword = oldPassword;
		}
		public String getNewPassword() {
			return newPassword;
		}
		public void setNewPassword(String newPassword) {
			this.newPassword = newPassword;
		}
		public String getReenterPassword() {
			return reenterPassword;
		}
		public void setReenterPassword(String reenterPassword) {
			this.reenterPassword = reenterPassword;
		}
		public Integer getSecurityquestion() {
			return securityquestion;
		}
		public void setSecurityquestion(Integer securityquestion) {
			this.securityquestion = securityquestion;
		}
		public String getSecurityanswer() {
			return securityanswer;
		}
		public void setSecurityanswer(String securityanswer) {
			this.securityanswer = securityanswer;
		}
		public Integer getSecurityquestion2() {
			return securityquestion2;
		}
		public void setSecurityquestion2(Integer securityquestion2) {
			this.securityquestion2 = securityquestion2;
		}
		public String getSecurityanswer2() {
			return securityanswer2;
		}
		public void setSecurityanswer2(String securityanswer2) {
			this.securityanswer2 = securityanswer2;
		}
		public int getUserId() {
			return userId;
		}
		public void setUserId(int userId) {
			this.userId = userId;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public int getUserid() {
			return userid;
		}
		public void setUserid(int userid) {
			this.userid = userid;
		}
		public String getDisplayName() {
			return displayName;
		}
		public void setDisplayName(String displayName) {
			this.displayName = displayName;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getPhotoUrl() {
			return photoUrl;
		}
		public void setPhotoUrl(String photoUrl) {
			this.photoUrl = photoUrl;
		}
		public String getRememberMe() {
			return rememberMe;
		}
		public void setRememberMe(String rememberMe) {
			this.rememberMe = rememberMe;
		}
		
}
