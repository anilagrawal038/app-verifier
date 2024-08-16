package com.san.util;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.san.to.ISProblemSolutionTO;
import com.san.to.ISProblemTO;
import com.san.to.ISProblemsConfigTO;
import com.san.to.ISTestCaseTO;
import com.san.to.ProcessStatusTO;

public class ISSolutionsUtil {

	static Logger logger = LoggerFactory.getLogger(ISSolutionsUtil.class);

	public static void findSolutions(ISProblemsConfigTO problemConfig) {
		List<String> supportedOSList = new ArrayList<>();
		supportedOSList.add("COMMON");
		String currentOS = "LINUX";
		if (CommonUtil.isWindowsOS()) {
			currentOS = "WINDOWS";
		}
		supportedOSList.add(currentOS);

		List<ISProblemTO> problems = problemConfig.getProblems();
		if (problems != null && !problems.isEmpty()) {
			for (ISProblemTO problem : problems) {
				logger.debug("Finding solutions for problem : " + problem.getId());
				boolean solutionsFound = false;
				File solutionsPath = new File("solutions/" + problem.getId());
				if (solutionsPath.exists() && solutionsPath.isDirectory()) {
					File[] osList = solutionsPath.listFiles();
					if (osList != null && osList.length > 0) {
						for (File os : osList) {
							if (os.isDirectory() && supportedOSList.contains(os.getName())) {
								File[] languageList = os.listFiles();
								if (languageList != null && languageList.length > 0) {
									for (File language : languageList) {
										if (language.isDirectory() && problemConfig.getSupportedLanguages().contains(language.getName())) {
											File[] userList = language.listFiles();
											if (userList != null && userList.length > 0) {
												for (File user : userList) {
													if (user.isDirectory()) {
														String[] parts = user.getName().split("_");
														String userName = null;
														String userId = null;
														if (parts.length > 0) {
															userName = parts[0];
														}
														if (parts.length > 1) {
															userId = parts[1];
														}

														int solutionCount = 0;
														File[] solutions = user.listFiles();
														if (solutions != null && solutions.length > 0) {
															for (File solution : solutions) {
																if (!solution.isDirectory() || !solution.getName().equals("Executable")) {
																	continue;
																}
																File[] executables = solution.listFiles();
																for (File executable : executables) {
																	if (executable.isFile()) { // && solution.getName().startsWith(problem.getExecutableNamePrefix())
																		if (solutionCount > 0) {
																			logger.info("Problem[" + problem.getId() + "] | Solution(Path: " + executable.getPath() + ") for User : " + user.getName() + " will not be analyzed. More than one solution found.");
																			continue;
																		} else {
																			ISProblemSolutionTO probSolution = new ISProblemSolutionTO(problem.getId(), os.getName(), language.getName(), userName, userId, executable.getPath());
																			problem.addSolution(probSolution);
																			logger.info("Problem[" + problem.getId() + "] | User[" + user.getName() + "] | Solution(Path: " + executable.getPath() + ") selected for analysis");
																		}
																		solutionCount++;
																		solutionsFound = true;
																	} else {
																		logger.debug("Problem[" + problem.getId() + "] | Solutions(Path: " + executable.getPath() + ") for User : " + user.getName() + " will not be analyzed. Invalid file or invalid executable name.");
																	}
																}
															}
														}
														if (solutionCount < 1) {
															logger.info("Problem[" + problem.getId() + "] | No solutions found for User : " + user.getName());
														}
													} else {
														logger.debug("Problem[" + problem.getId() + "] | Solutions(Path: " + user.getPath() + ") for User : " + user.getName() + " will not be analyzed. Invalid directory.");
													}
												}
											}
										} else {
											logger.debug("Problem[" + problem.getId() + "] | Solutions(Path: " + language.getPath() + ") for Language : " + language.getName() + " will not be analyzed. Either language is not supported or it is not a valid directory. ");
										}
									}
								}
							} else {
								logger.debug("Problem[" + problem.getId() + "] | Solutions(Path: " + os.getPath() + ") for OS : " + os.getName() + " will not be analyzed on " + currentOS);
							}
						}
					}
				}
				if (!solutionsFound) {
					logger.info("Problem : " + problem.getId() + ", No solutions found at path : " + solutionsPath.getPath());
				} else {
					logger.info("Problem : " + problem.getId() + ", Total Solutions : " + problem.getSolutions().size());
				}
			}
		}
	}

	public static void processSolutions(List<ISProblemTO> problems) {
		if (problems == null || problems.size() < 0) {
			logger.info("No problems provided to process the solutions");
			return;
		}

		File resultFile = new File("results/results_" + CommonUtil.dateTimeStringForFolderName(null) + ".csv");
		resultFile.getParentFile().mkdirs();
		FileWriter resultWriter = null;
		try {
			resultWriter = new FileWriter("results/results_" + CommonUtil.dateTimeStringForFolderName(null) + ".csv");
			resultWriter.write("ProblemID, UserID, UserName, Platform, Language, TotalTestCases, PassedTestCases, FailedTestCases, TotalPoints");
		} catch (Exception e) {
			logger.error("Unable to open result file : " + resultFile.getPath());
		}

		for (ISProblemTO problem : problems) {
			List<ISTestCaseTO> testCases = problem.getTestCases().getTestCases();
			if (testCases == null || testCases.size() < 1) {
				logger.info("No test cases provided for Problem : " + problem.getId() + ". Solutions couldn't be analyzed for the Problem : " + problem.getId());
				continue;
			}
			List<ISProblemSolutionTO> solutions = problem.getSolutions();
			if (solutions != null && solutions.size() > 0) {
				for (ISProblemSolutionTO solution : solutions) {
					logger.debug("Problem : " + problem.getId() + ", Processing solution for User [" + solution.getUserId() + "] : " + solution.getUserName() + "");
					List<String> command = new ArrayList<>();
					if (solution.getLanguage().equals("JAVA")) {
						solution.setOSCommandRequierd(true);
						if (solution.getSolutionFilePath().endsWith(".class")) {
							File clazzFile = new File(solution.getSolutionFilePath());
							command.add("cd");
							command.add(clazzFile.getParent());
							command.add("&&");
							command.add("java");
							// command.add("D:\\Setups\\jdk-14.0.2\\bin\\java");
							String clazz = clazzFile.getName();
							command.add(clazz.substring(0, (clazz.length() - 6)));
						} else if (solution.getSolutionFilePath().endsWith(".jar")) {
							command.add("java");
							command.add("-jar");
							command.add("\"" + solution.getSolutionFilePath() + "\"");
						}
					} else if (solution.getLanguage().equals("PYTHON3")) {
						solution.setOSCommandRequierd(true);
						command.add("python3");
						command.add("\"" + solution.getSolutionFilePath() + "\"");
					} else {
						command.add("cd");
						File exeFile = new File(solution.getSolutionFilePath());
						command.add(exeFile.getParent());
						command.add("&&");
						command.add("" + exeFile.getName() + "");
					}
					String statusString = null;
					try {
						statusString = processSolution(solution, testCases, command, (problem.getTimeoutInSeconds()));
						if (resultWriter != null) {
							resultWriter.append(System.lineSeparator());
							resultWriter.append(statusString);
						}
					} catch (Exception e) {
						logger.debug("Problem : " + solution.getProblemId() + " | Data couldn't be appended to result file (" + resultFile.getName() + ") => " + statusString);
					}
					if (statusString != null) {
						logger.info("Problem : " + problem.getId() + ", Solution analyzed for User [" + solution.getUserId() + "] : " + solution.getUserName() + "");
					} else {
						logger.info("Problem : " + problem.getId() + ", Solution couldn't be analyzed for User [" + solution.getUserId() + "] : " + solution.getUserName() + "");
					}
				}
			} else {
				logger.debug("No solutions provided for Problem : " + problem.getId());
			}
		}
		try {
			if (resultWriter != null) {
				logger.info("All solutions processed. Check results in file : " + resultFile.getPath());
				resultWriter.close();
			}
		} catch (Exception e) {
			logger.trace("Unable to close result file (" + resultFile.getName() + ")");
		}
	}

	public static String processSolution(ISProblemSolutionTO solution, List<ISTestCaseTO> testCases, List<String> commands, long timeout) {
		int totalPoints = 0;
		File resultFile = new File(solution.getResultFilePath());
		resultFile.getParentFile().mkdirs();
		FileWriter resultWriter = null;
		try {
			resultWriter = new FileWriter(resultFile);
			// resultWriter.write("ProblemID, TestCaseName, Inputs, ExpectedOutput, ActualOutput, TestStatus, Point");
			resultWriter.write("ProblemID, TestCaseName, ExpectedOutput, ActualOutput, ExecutionTime, TestStatus, Point, ExitCode, TimeoutOccurred");
		} catch (Exception e) {
			logger.error("Problem : " + solution.getProblemId() + " | Unable to open result file : " + resultFile.getPath());
		}
		int passCounter = 0;
		int failCounter = 0;
		for (ISTestCaseTO testCase : testCases) {
			logger.trace("Problem : " + solution.getProblemId() + ", User [" + solution.getUserId() + "] : " + solution.getUserName() + " | Executing test case : " + testCase.getName());
			try {
				String args = "\"" + String.join("\" \"", testCase.getInputs()) + "\"";
				String command = String.join(" ", commands) + " " + args;
				logger.trace("Problem : " + solution.getProblemId() + ", User [" + solution.getUserId() + "] : " + solution.getUserName() + ", Test-Case : " + testCase.getName() + " | Executing command : " + command);
				ProcessStatusTO processStatus = ProcessHelperUtil.executeCommandWithOutput(command, timeout);
				List<String> outputs = processStatus.getStdOut();
				String output = "";
				if (outputs.isEmpty()) {
					List<String> err = processStatus.getErrOut();
					if (!err.isEmpty()) {
						output = err.get(err.size() - 1);
					}
				} else {
					for (int i = (outputs.size() - 1); i >= 0; i--) {
						output = outputs.get(i);
						output = output.trim();
						if (output.length() > 0) {
							break;
						}
					}
				}
				if (output.startsWith("Result:")) {
					output = output.substring("Result:".length());
				}
				int point = testCase.getFailurePoint();
				boolean testStatus = false;
				if (testCase.getExpectedOutput() != null && !testCase.getExpectedOutput().isEmpty()) {
					Set<String> expectedOutputs = new HashSet<>();
					if (testCase.getExpectedOutput().indexOf("|") > -1) {
						String[] parts = testCase.getExpectedOutput().split("\\|");
						for (String part : parts) {
							expectedOutputs.add(part);
						}
					} else {
						expectedOutputs.add(testCase.getExpectedOutput());
					}
					if (expectedOutputs.contains("NOCRASH") && processStatus.getExitCode() == 0) {
						point = testCase.getSuccessPoint();
						testStatus = true;
						passCounter++;
					} else if (expectedOutputs.contains(output.trim())) {
						point = testCase.getSuccessPoint();
						testStatus = true;
						passCounter++;
					} else {
						failCounter++;
					}
				}
				totalPoints += point;
				if (resultWriter != null) {
					// String record = "" + solution.getProblemId() + ", " + testCase.getName() + ", " + String.join(" ", testCase.getInputs()) + ", " + testCase.getExpectedOutput() + ", " + output + ", " + (testStatus ? "Pass" : "Fail") + " , " +
					// point
					// + "";
					String record = "" + solution.getProblemId() + ", " + testCase.getName() + ", " + testCase.getExpectedOutput() + ", " + output + ", " + (processStatus.getEndTime() - processStatus.getStartTime()) + ", " + (testStatus ? "Pass" : "Fail") + " , " + point + ", " + processStatus.getExitCode() + ", " + processStatus.isTimeoutOccurred();
					try {
						resultWriter.append(System.lineSeparator());
						resultWriter.append(record);
					} catch (Exception e) {
						logger.trace("Problem : " + solution.getProblemId() + " | Data couldn't be written to result file (" + resultFile.getName() + ") => " + record);
					}
				}
				logger.debug("Problem : " + solution.getProblemId() + ", User [" + solution.getUserId() + "] : " + solution.getUserName() + ", Test-Case : " + testCase.getName() + " | Expected-Output : " + testCase.getExpectedOutput() + ", Actual-Output : " + output + ", Point : " + point);
			} catch (Exception e) {
				logger.debug("Exception occurred while executing test case Problem : " + solution.getProblemId() + ", User [" + solution.getUserId() + "] : " + solution.getUserName() + " | Executing test case : " + testCase.getName());
			}
		}
		if (resultWriter != null) {
			try {
				resultWriter.close();
			} catch (Exception e) {
				logger.trace("Problem : " + solution.getProblemId() + " | Unable to close result file (" + resultFile.getName() + ")");
			}
		}
		return "" + solution.getProblemId() + ", " + solution.getUserId() + ", " + solution.getUserName() + ", " + solution.getOsName() + ", " + solution.getLanguage() + ", " + testCases.size() + ", " + passCounter + ", " + failCounter + ", " + totalPoints + "";
	}

}
