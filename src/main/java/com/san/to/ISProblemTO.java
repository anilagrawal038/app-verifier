package com.san.to;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.san.util.CommonUtil;

@JsonSerialize
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ISProblemTO {

	private String id;
	private String name;
	private String problemStatement;
	private String description;
	private String testCasesPath;
	private String resultsPath;
	private String solutionsPath;
	private String executableNamePrefix;
	private int inputCount;
	private int outputCount;
	private int timeoutInSeconds;
	private String expectedOutput;
	transient private ISTestCasesConfigTO testCases;
	transient private List<ISProblemSolutionTO> solutions = new ArrayList<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProblemStatement() {
		return problemStatement;
	}

	public void setProblemStatement(String problemStatement) {
		this.problemStatement = problemStatement;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTestCasesPath() {
		return testCasesPath;
	}

	public void setTestCasesPath(String testCasesPath) {
		this.testCasesPath = testCasesPath;
	}

	public String getResultsPath() {
		return resultsPath;
	}

	public void setResultsPath(String resultsPath) {
		this.resultsPath = resultsPath;
	}

	public String getSolutionsPath() {
		return solutionsPath;
	}

	public void setSolutionsPath(String solutionsPath) {
		this.solutionsPath = solutionsPath;
	}

	public String getExecutableNamePrefix() {
		return executableNamePrefix;
	}

	public void setExecutableNamePrefix(String executableNamePrefix) {
		this.executableNamePrefix = executableNamePrefix;
	}

	public int getInputCount() {
		return inputCount;
	}

	public void setInputCount(int inputCount) {
		this.inputCount = inputCount;
	}

	public int getOutputCount() {
		return outputCount;
	}

	public void setOutputCount(int outputCount) {
		this.outputCount = outputCount;
	}

	public String getExpectedOutput() {
		return expectedOutput;
	}

	public void setExpectedOutput(String expectedOutput) {
		this.expectedOutput = expectedOutput;
	}

	public ISTestCasesConfigTO getTestCases() {
		return testCases;
	}

	public void setTestCases(ISTestCasesConfigTO testCases) {
		this.testCases = testCases;
	}

	public List<ISProblemSolutionTO> getSolutions() {
		return solutions;
	}

	public void setSolutions(List<ISProblemSolutionTO> solutions) {
		this.solutions = solutions;
	}

	public void addSolution(ISProblemSolutionTO solution) {
		solutions.add(solution);
	}

	@Override
	public int hashCode() {
		if (id != null) {
			return id.hashCode();
		}
		return (int) (Math.random() * 100);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof ISProblemTO)) {
			return false;
		}
		ISProblemTO other = (ISProblemTO) o;
		if (other.getId() == null && getId() == null) {
			return true;
		} else if (other.getId() == null || getId() == null) {
			return false;
		} else {
			return getId().equals(other.getId());
		}
	}

	public int getTimeoutInSeconds() {
		return timeoutInSeconds;
	}

	public void setTimeoutInSeconds(int timeoutInSeconds) {
		this.timeoutInSeconds = timeoutInSeconds;
	}

	@Override
	public String toString() {
		try {
			return "ISProblemTO : " + CommonUtil.convertToJsonString(this);
		} catch (Exception e) {
			return "ISProblemTO : " + getId();
		}
	}

}
