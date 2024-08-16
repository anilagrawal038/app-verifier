package com.san.to;

import java.util.ArrayList;
import java.util.List;

import com.san.util.CommonUtil;

public class ProcessStatusTO {

	String command;
	List<String> stdOut = new ArrayList<>();
	List<String> errOut = new ArrayList<>();
	long startTime;
	long endTime;
	int exitCode = -1;
	boolean isTimeoutOccurred;

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public List<String> getStdOut() {
		return stdOut;
	}

	public void setStdOut(List<String> stdOut) {
		this.stdOut = stdOut;
	}

	public void addStdOut(String line) {
		stdOut.add(line);
	}

	public List<String> getErrOut() {
		return errOut;
	}

	public void setErrOut(List<String> errOut) {
		this.errOut = errOut;
	}

	public void addErrOut(String line) {
		errOut.add(line);
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public int getExitCode() {
		return exitCode;
	}

	public void setExitCode(int exitCode) {
		this.exitCode = exitCode;
	}

	@Override
	public String toString() {
		try {
			return CommonUtil.convertToJsonString(this);
		} catch (Exception e) {
		}
		return "ProcessStatusTO [stdOut=" + stdOut + ", errOut=" + errOut + ", startTime=" + startTime + ", endTime=" + endTime + ", exitCode=" + exitCode + ", getCommand()=" + getCommand() + ", getStdOut()=" + getStdOut() + ", getErrOut()=" + getErrOut() + ", getStartTime()=" + getStartTime() + ", getEndTime()=" + getEndTime() + ", getExitCode()=" + getExitCode() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	public boolean isTimeoutOccurred() {
		return isTimeoutOccurred;
	}

	public void setTimeoutOccurred(boolean isTimeoutOccurred) {
		this.isTimeoutOccurred = isTimeoutOccurred;
	}

}
