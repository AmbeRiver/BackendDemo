package com.windstream.demo.response;

import java.io.Serializable;

public class JwtAuthenticationResponse implements Serializable {
    private static final long serialVersionUID = 1250166508152483573L;

    private final String token;
    private String expiredTime;

    public JwtAuthenticationResponse(String token,String expiredTime) {
        this.token = token;
        this.expiredTime = expiredTime;
    }

    public String getToken() {
        return this.token;
    }

	public String getExpiredTime() {
		return expiredTime;
	}
    
    
}
