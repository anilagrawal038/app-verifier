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
public class ISProblemsConfigTO {

	private List<String> supportedLanguages;
	private List<String> supportedPlatforms;
	private List<ISProblemTO> problems;

	public List<String> getSupportedLanguages() {
		return supportedLanguages;
	}

	public void setSupportedLanguages(List<String> supportedLanguages) {
		this.supportedLanguages = supportedLanguages;
	}

	public List<String> getSupportedPlatforms() {
		return supportedPlatforms;
	}

	public void setSupportedPlatforms(List<String> supportedPlatforms) {
		this.supportedPlatforms = supportedPlatforms;
	}

	public List<ISProblemTO> getProblems() {
		return problems;
	}

	public void setProblems(List<ISProblemTO> problems) {
		this.problems = problems;
	}

	@Override
	public String toString() {
		try {
			return "ISProblemsConfigTO : " + CommonUtil.convertToJsonString(this);
		} catch (Exception e) {
			return "ISProblemsConfigTO : " + hashCode();
		}
	}

}
