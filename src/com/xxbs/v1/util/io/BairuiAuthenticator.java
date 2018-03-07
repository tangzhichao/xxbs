package com.xxbs.v1.util.io;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

public class BairuiAuthenticator extends Authenticator {
	private String username, password;

	public BairuiAuthenticator(String username, String password) {
		this.username = username;
		this.password = password;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(username, password == null ? null : password.toCharArray());
	}
}
