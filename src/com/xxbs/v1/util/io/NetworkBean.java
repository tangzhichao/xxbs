package com.xxbs.v1.util.io;

import java.io.Serializable;
import java.net.Proxy;
import java.net.Proxy.Type;

import com.xxbs.v1.util.Utils;


/**
 * @author tang
 * @date 2014/11/4
 */
public class NetworkBean implements Serializable {

	private static final long serialVersionUID = 1L;
	// private static sun.misc.BASE64Encoder base64Encoder = new sun.misc.BASE64Encoder();

	private Proxy.Type type;//
	private String address;
	private String port;
	private String username;
	private String password;
	private String domain;
	private String typeText;

	public NetworkBean() {
	}

	public NetworkBean(Type type, String address, String port, String username, String password) {
		this.type = type;
		this.address = address;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	public NetworkBean(Type type, String address, String port, String username, String password, String domain) {
		super();
		this.type = type;
		this.address = address;
		this.port = port;
		this.username = username;
		this.password = password;
		this.domain = domain;
	}

	public Proxy.Type getType() {
		return type;
	}

	public void setType(Proxy.Type type) {
		this.type = type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getTypeText() {
		return typeText;
	}

	public void setTypeText(String typeText) {
		this.typeText = typeText;
	}

	/**
	 * return domain + "\" + username
	 */
	public String getDomainAndUsername() {
		return (Utils.toString(domain).trim().isEmpty()) ? username : domain.trim() + "\\" + username;
	}

	/**
	 * return domain + "\" + username + ":" + password
	 */
	public String getDomainAndUsernameAndPassword() {
		return getDomainAndUsername() + ":" + password;
	}

	/**
	 * return username + ":" + password
	 */
	public String getUsernameAndPassword() {
		return username + ":" + password;
	}

	/**
	 * return (domain + "\" + username + ":" + password) to 64 bit
	 */
	public String getDomainAndUsernameAndPassword64() {
		return org.apache.commons.codec.binary.Base64.encodeBase64String(getDomainAndUsernameAndPassword().getBytes());
	}

	@Override
	public String toString() {
		return "NetworkBean [type=" + type + ", typeText=" + typeText + ", address=" + address + ", port=" + port + ", username=" + username + ", password="
				+ password + ", domain=" + domain + "]";
	}
}
