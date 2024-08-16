package com.san.to;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.san.util.CommonUtil;

@JsonSerialize
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ISTestCaseTO {

	private String name;
	private List<String> inputs;
	private String expectedOutput;
	private int successPoint;
	private int failurePoint;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getInputs() {
		return inputs;
	}

	public void setInputs(List<String> inputs) {
		this.inputs = inputs;
	}

	public String getExpectedOutput() {
		return expectedOutput;
	}

	public void setExpectedOutput(String expectedOutput) {
		this.expectedOutput = expectedOutput;
	}

	public int getSuccessPoint() {
		return successPoint;
	}

	public void setSuccessPoint(int successPoint) {
		this.successPoint = successPoint;
	}

	public int getFailurePoint() {
		return failurePoint;
	}

	public void setFailurePoint(int failurePoint) {
		this.failurePoint = failurePoint;
	}

	@Override
	public int hashCode() {
		if (name != null) {
			return name.hashCode();
		}
		return (int) (Math.random() * 100);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof ISTestCaseTO)) {
			return false;
		}
		ISTestCaseTO other = (ISTestCaseTO) o;
		if (other.getName() == null && getName() == null) {
			return true;
		} else if (other.getName() == null || getName() == null) {
			return false;
		} else {
			return getName().equals(other.getName());
		}
	}

	@Override
	public String toString() {
		try {
			return "ISTestCaseTO : " + CommonUtil.convertToJsonString(this);
		} catch (Exception e) {
			return "ISTestCaseTO : " + hashCode();
		}
	}

}
