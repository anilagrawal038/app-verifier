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
public class ISTestCasesConfigTO {

	private List<ISTestCaseTO> testCases;

	public List<ISTestCaseTO> getTestCases() {
		return testCases;
	}

	public void setTestCases(List<ISTestCaseTO> testCases) {
		this.testCases = testCases;
	}

	@Override
	public String toString() {
		try {
			return "ISTestCasesConfigTO : " + CommonUtil.convertToJsonString(this);
		} catch (Exception e) {
			return "ISTestCasesConfigTO : " + hashCode();
		}
	}

}
