package com.prodapt.capstoneproject.security.payload.response;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JwtResponse {
	private String accessToken;
	private String tokenType = "Bearer";
	private Long id;
	private String username;
	private List<String> role;

	public JwtResponse(String accessToken, Long id, String username,List<String> roles) {
		this.accessToken = accessToken;
		this.id = id;
		this.username = username;
		this.role = roles;
		
	}
}
