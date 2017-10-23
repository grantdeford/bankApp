package com.userFront.domain.security;

import org.springframework.security.core.GrantedAuthority;

//Defining an authority with a String to represent the authority content...       Represents the token for an authenticated principal

public class Authority implements GrantedAuthority {
		
	private final String authority;
		
	public Authority(String authority) {
		this.authority = authority;
		}
		
	@Override
	public String getAuthority() {
		return authority;
		}
}
