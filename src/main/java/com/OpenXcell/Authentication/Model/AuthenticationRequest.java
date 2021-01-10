package com.OpenXcell.Authentication.Model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name ="Login")
public class AuthenticationRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;

	@Column(name = "userName")
	private String userName;

	@Column(name = "password")
	private String password;

	public AuthenticationRequest() { }

	public AuthenticationRequest(String username, String password) {
		this.userName = username;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return userName;
	}

	public void setUsername(String username) {
		this.userName = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "AuthenticationRequest{" +
				"id=" + id +
				", useName='" + userName + '\'' +
				", password='" + password + '\'' +
				'}';
	}

}
