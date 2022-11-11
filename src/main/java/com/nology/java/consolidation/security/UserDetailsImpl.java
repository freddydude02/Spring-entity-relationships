package com.nology.java.consolidation.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nology.java.consolidation.entities.User;

public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private final Long id;

	private final String username;
	
	@JsonIgnore
	private final String password;

	private Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl(Long id, String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}
	
	public static UserDetailsImpl build(User user) {
		
		List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRole()));
		
		return new UserDetailsImpl(
				user.getId(), 
				user.getUsername(), 
				user.getPassword(),
				authorities);
	}


	public Long getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}
	
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}




	
}
