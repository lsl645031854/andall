package com.andall.sally.supply.utils;

public class SFTPConstants {

	private String host;

	private String port;

	private String userName;

	private String privateKey;

	private String password;

	public SFTPConstants() {
		super();
	}

	public SFTPConstants(String host, String port, String userName,
                         String privateKey, String password) {
		this.host = host;
		this.port = port;
		this.userName = userName;
		this.privateKey = privateKey;
		this.password = password;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
