package com.san.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.san.to.ISProblemTO;
import com.san.to.ISProblemsConfigTO;
import com.san.to.ISTestCasesConfigTO;

public class ConfigUtil {

	private static Logger logger = LoggerFactory.getLogger(ConfigUtil.class);

	public static ISProblemsConfigTO populateISProblemConfig() {
		ISProblemsConfigTO conf = null;
		try {
			FileInputStream fis = new FileInputStream("conf/apps-config.json");
			String configData = CommonUtil.fetchStringFromInputStream(fis);
			logger.debug("Config-Data : \n" + configData);
			conf = CommonUtil.bindJSONToObject(configData, ISProblemsConfigTO.class);
			populateTestCases(conf);
		} catch (Exception e) {
			logger.error("Exception occurred in populateISProblemConfig", e);
		}
		return conf;
	}

	private static void populateTestCases(ISProblemsConfigTO problemConfig) {
		try {
			List<ISProblemTO> problems = problemConfig.getProblems();
			if (problems != null && !problems.isEmpty()) {
				for (ISProblemTO problem : problems) {
					boolean testCasesFound = false;
					try {
						if (problem.getTestCasesPath() != null && !(problem.getTestCasesPath().isEmpty())) {
							File file = new File(problem.getTestCasesPath());
							if (file.exists() && file.isFile()) {
								FileInputStream fis = new FileInputStream(file);
								String configData = CommonUtil.fetchStringFromInputStream(fis);
								logger.debug("Test-Case-Data [Problem : " + problem.getId() + "] : \n" + configData);
								ISTestCasesConfigTO testCases = CommonUtil.bindJSONToObject(configData, ISTestCasesConfigTO.class);
								if (testCases != null) {
									testCasesFound = true;
									problem.setTestCases(testCases);
								}
							}
						}
					} catch (Exception e) {
						logger.error("Exception occurred while fetching test-cases for Problem : " + problem.getId() + "", e);
					}
					if (!testCasesFound) {
						logger.error("No test-cases found for Problem : " + problem.getId());
					}
					logger.info("Problem : " + problem.getId() + ", Test Case Count : " + problem.getTestCases().getTestCases().size());
				}
			}
		} catch (Exception e) {
			logger.error("Exception occurred in populateTestCases()", e);
		}
	}

}
