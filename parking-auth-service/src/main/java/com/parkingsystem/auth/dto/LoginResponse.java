package com.parkingsystem.auth.dto;

public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
    private Long expiresIn;
    private String role;
    private String username;
    
    // Constructors
    public LoginResponse() {}
    
    public LoginResponse(String accessToken, String refreshToken, Long expiresIn, String role, String username) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.role = role;
        this.username = username;
    }
    
    // Getters and setters
    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
    
    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
    
    public String getTokenType() { return tokenType; }
    public void setTokenType(String tokenType) { this.tokenType = tokenType; }
    
    public Long getExpiresIn() { return expiresIn; }
    public void setExpiresIn(Long expiresIn) { this.expiresIn = expiresIn; }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}