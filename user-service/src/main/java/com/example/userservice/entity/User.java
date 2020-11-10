package com.example.userservice.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModelProperty;



@Entity
@Table(name = "user")
public class User implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "username")
	@Pattern(regexp="^[a-zA-Z]{1,30}+$", message="Invalid FirstName")
	@NotNull
	private String username;

	@Column(name = "password")
	@NotNull
	@Pattern(regexp = "\\A(?=\\S*?[0-9])(?=\\S*?[a-z])(?=\\S*?[A-Z])(?=\\S*?[!@#$%^&*])\\S{8,}\\z",message="Invalid Password")
	@ApiModelProperty(notes="must contain 1 small and caps letters,number and special characters",example="Dummy@11")
	private String password;

	@Column(name = "email")
	@NotNull
	@Pattern(regexp= "([a-zA-Z]{1}[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+[\\.][a-z]+)",
	message="Invalid Email")
	private String email;

	@Column(name = "phone_number")	
	private String phoneNumber;

	@Column(name = "role")
	@ApiModelProperty(example = "ROLE_admin,ROLE_user")
	private String role;

	public User() {
		super();
	}
	
	public User(User user){
		this.setEmail(user.getEmail());
		this.setId(user.getId());
		this.setPassword(user.getPassword());
		this.setPhoneNumber(user.getPhoneNumber());
		this.setRole(user.getRole());
		this.setUsername(user.getUsername());
	}

	

	public User(Integer id,
			@Pattern(regexp = "^[a-zA-Z]{1,30}+$", message = "Invalid FirstName") @NotNull String username,
			@NotNull @Pattern(regexp = "\\A(?=\\S*?[0-9])(?=\\S*?[a-z])(?=\\S*?[A-Z])(?=\\S*?[!@#$%^&*])\\S{8,}\\z", message = "Invalid Password") String password,
			@NotNull @Pattern(regexp = "([a-zA-Z]{1}[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+[\\.][a-z]+)", message = "Invalid Email") String email,
			String phoneNumber, String role) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.role = role;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", phoneNumber=" + phoneNumber + ", role=" + role + "]";
	}
	
	



}