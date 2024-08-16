package com.san;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.san.to.ISProblemTO;
import com.san.to.ISProblemsConfigTO;
import com.san.to.ISTestCaseTO;
import com.san.util.ConfigUtil;
import com.san.util.ISSolutionsUtil;

public class App {
	static Logger logger = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		ISProblemsConfigTO probConfig = ConfigUtil.populateISProblemConfig();
		// logger.info(probConfig.toString());
		ISSolutionsUtil.findSolutions(probConfig);
		ISSolutionsUtil.processSolutions(probConfig.getProblems());
	}

	// Program Arguments => ProblemID, PASS/FAIL, Inputs
	public static void main1(String[] args) {
		String problemId = "test";
		try {
			File file = new File(App.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			System.out.println("Executable Name : " + file.getName());
			String[] parts = file.getName().split("-");
			problemId = parts[1];
			System.out.println("Problem ID : " + problemId);
		} catch (Exception e) {
		}

		if (args.length > 0) {
			ISProblemsConfigTO probConfig = ConfigUtil.populateISProblemConfig();
			List<ISProblemTO> problems = probConfig.getProblems();
			ISProblemTO problem = null;
			for (ISProblemTO prob : problems) {
				if (prob.getId().equals(problemId)) {
					problem = prob;
					break;
				}
			}
			if (problem != null) {
				List<ISTestCaseTO> testCases = problem.getTestCases().getTestCases();
				if (testCases != null) {
					int randomFailedIndex = ((int) (Math.random() * 10000)) % (testCases.size());
					int testCaseCounter = 0;
					for (ISTestCaseTO testCase : testCases) {
						if (testCaseCounter == randomFailedIndex) {
							System.out.println("Failing test case as per random logic");
							break;
						}
						if (args.length >= testCase.getInputs().size()) {
							int counter = 0;
							boolean argumentsMatched = true;
							for (String input : testCase.getInputs()) {
								if (!input.equals(args[counter].trim())) {
									argumentsMatched = false;
									break;
								}
								counter++;
							}
							if (argumentsMatched) {
								System.out.println(testCase.getExpectedOutput());
								return;
							}
						}
						testCaseCounter++;
					}
				}
			}
		}
		System.out.println(((int) (Math.random() * 10000)));
	}
}
