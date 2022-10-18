package com.jbdl.blogapp.payload;

// This DTO will be response of signin or login REST API
public class JwtAuthResponseDto {

    private String accessToken;
    private String tokenType = "Bearer";

    public JwtAuthResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
