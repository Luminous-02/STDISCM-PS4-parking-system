package com.parkingsystem.auth.service;

import com.parkingsystem.auth.config.JwtUtil;
import com.parkingsystem.auth.dto.LoginRequest;
import com.parkingsystem.auth.dto.LoginResponse;
import com.parkingsystem.auth.model.RefreshToken;
import com.parkingsystem.auth.model.User;
import com.parkingsystem.auth.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    
    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        
        User user = userService.findByUsername(loginRequest.getUsername());
        String sessionId = UUID.randomUUID().toString();
        
        // Generate tokens
        String accessToken = jwtUtil.generateAccessToken(user.getUsername(), user.getRole().name(), sessionId);
        String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());
        
        // Save refresh token
        RefreshToken refreshTokenEntity = new RefreshToken(
            refreshToken, 
            user, 
            LocalDateTime.now().plusSeconds(jwtUtil.getRefreshExpiration() / 1000)
        );
        refreshTokenRepository.save(refreshTokenEntity);
        
        return new LoginResponse(
            accessToken,
            refreshToken,
            jwtUtil.getExpiration() / 1000, // Convert to seconds
            user.getRole().name(),
            user.getUsername()
        );
    }
    
    public LoginResponse refreshToken(String refreshToken) {
        RefreshToken storedToken = refreshTokenRepository.findByToken(refreshToken)
            .orElseThrow(() -> new RuntimeException("Invalid refresh token"));
        
        if (storedToken.getRevoked() || storedToken.isExpired()) {
            throw new RuntimeException("Refresh token expired or revoked");
        }
        
        User user = storedToken.getUser();
        String newSessionId = UUID.randomUUID().toString();
        String newAccessToken = jwtUtil.generateAccessToken(user.getUsername(), user.getRole().name(), newSessionId);
        String newRefreshToken = jwtUtil.generateRefreshToken(user.getUsername());
        
        // Revoke old token and save new one
        storedToken.setRevoked(true);
        refreshTokenRepository.save(storedToken);
        
        RefreshToken newRefreshTokenEntity = new RefreshToken(
            newRefreshToken,
            user,
            LocalDateTime.now().plusSeconds(jwtUtil.getRefreshExpiration() / 1000)
        );
        refreshTokenRepository.save(newRefreshTokenEntity);
        
        return new LoginResponse(
            newAccessToken,
            newRefreshToken,
            jwtUtil.getExpiration() / 1000,
            user.getRole().name(),
            user.getUsername()
        );
    }
    
    public void logout(String refreshToken) {
        RefreshToken storedToken = refreshTokenRepository.findByToken(refreshToken)
            .orElse(null);
        
        if (storedToken != null) {
            storedToken.setRevoked(true);
            refreshTokenRepository.save(storedToken);
        }
    }
    
    public void logoutAllUserSessions(String username) {
        User user = userService.findByUsername(username);
        refreshTokenRepository.revokeAllUserTokens(user);
    }
}