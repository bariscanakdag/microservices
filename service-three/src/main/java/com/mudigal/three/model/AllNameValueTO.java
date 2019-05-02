package com.mudigal.three.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Barış Can Akdağ
 *
 */
public class AllNameValueTO {

	private String originalName;
	private String originalValue;

	private Map<String, String> remainingNameValuePair = new HashMap<>();

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getOriginalValue() {
		return originalValue;
	}

	public void setOriginalValue(String originalValue) {
		this.originalValue = originalValue;
	}

	public Map<String, String> getRemainingNameValuePair() {
		return remainingNameValuePair;
	}

	public void setRemainingNameValuePair(Map<String, String> remainingNameValuePair) {
		this.remainingNameValuePair = remainingNameValuePair;
	}

	@Override
	public String toString() {
		return "AllNameValueTO{" +
				"originalName='" + originalName + '\'' +
				", originalValue='" + originalValue + '\'' +
				", remainingNameValuePair=" + remainingNameValuePair +
				'}';
	}
}
