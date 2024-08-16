package com.san.to;

import com.san.util.CommonUtil;

public class ISProblemSolutionTO {

	private String problemId;
	private String userName;
	private String userId;
	private String solutionFilePath;
	private String resultFilePath;
	private boolean isOSCommandRequierd;
	private String osCommandName;
	private String osName;
	private String language;

	public ISProblemSolutionTO(String problemId, String osName, String language, String userName, String userId, String solutionFilePath) {
		this.problemId = problemId;
		this.osName = osName;
		this.language = language;
		this.userName = userName;
		this.userId = userId;
		this.solutionFilePath = solutionFilePath;
		this.resultFilePath = "results/" + problemId + "/" + userName + "_" + userId + "/results_" + osName + "_" + language + "_" + userName + "_" + userId + "_" + CommonUtil.dateTimeStringForFolderName(null) + ".csv";
	}

	public ISProblemSolutionTO() {
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSolutionFilePath() {
		return solutionFilePath;
	}

	public void setSolutionFilePath(String solutionFilePath) {
		this.solutionFilePath = solutionFilePath;
	}

	public String getResultFilePath() {
		return resultFilePath;
	}

	public void setResultFilePath(String resultFilePath) {
		this.resultFilePath = resultFilePath;
	}

	public boolean isOSCommandRequierd() {
		return isOSCommandRequierd;
	}

	public void setOSCommandRequierd(boolean isOSCommandRequierd) {
		this.isOSCommandRequierd = isOSCommandRequierd;
	}

	public String getOsCommandName() {
		return osCommandName;
	}

	public void setOsCommandName(String osCommandName) {
		this.osCommandName = osCommandName;
	}

	public String getOsName() {
		return osName;
	}

	public void setOsName(String osName) {
		this.osName = osName;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getProblemId() {
		return problemId;
	}

	public void setProblemId(String problemId) {
		this.problemId = problemId;
	}

	private String _fetchUniqueStr_() {
		String str = problemId + "|" + userId + "|" + userName;
		return str;
	}

	@Override
	public int hashCode() {
		return _fetchUniqueStr_().hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof ISProblemSolutionTO)) {
			return false;
		}
		ISProblemSolutionTO other = (ISProblemSolutionTO) o;
		return other._fetchUniqueStr_().equals(_fetchUniqueStr_());
	}

	@Override
	public String toString() {
		try {
			return "ISProblemSolutionTO : " + CommonUtil.convertToJsonString(this);
		} catch (Exception e) {
			return "ISProblemSolutionTO : " + _fetchUniqueStr_();
		}
	}

}
