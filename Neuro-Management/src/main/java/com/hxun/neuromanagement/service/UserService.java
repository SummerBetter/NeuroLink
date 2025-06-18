package com.hxun.neuromanagement.service;

import com.hxun.neuromanagement.dto.AuthResponseDto;
import com.hxun.neuromanagement.dto.UserLoginDto;
import com.hxun.neuromanagement.dto.UserRegistrationDto;
import com.hxun.neuromanagement.entity.User;
import com.hxun.neuromanagement.repository.UserRepository;
import com.hxun.neuromanagement.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    
    public AuthResponseDto registerUser(UserRegistrationDto registrationDto) {
        // 检查用户名是否已存在
        if (userRepository.existsByUsername(registrationDto.getUsername())) {
            return new AuthResponseDto(null, "用户名已存在", false, null);
        }
        
        // 检查邮箱是否已存在
        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            return new AuthResponseDto(null, "邮箱已被注册", false, null);
        }
        
        // 创建新用户
        User user = new User();
        user.setUsername(registrationDto.getUsername());
        user.setEmail(registrationDto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(registrationDto.getPassword()));
        user.setStatus(User.UserStatus.ACTIVE);
        
        User savedUser = userRepository.save(user);
        
        // 生成JWT令牌
        String token = jwtUtil.generateToken(savedUser.getUsername(), savedUser.getUserId());
        
        AuthResponseDto.UserInfo userInfo = new AuthResponseDto.UserInfo(
                savedUser.getUserId(),
                savedUser.getUsername(),
                savedUser.getEmail()
        );
        
        return new AuthResponseDto(token, "注册成功", true, userInfo);
    }
    
    public AuthResponseDto loginUser(UserLoginDto loginDto) {
        // 查找用户（支持用户名或邮箱登录）
        Optional<User> userOpt = userRepository.findByUsernameOrEmail(
                loginDto.getUsernameOrEmail(), 
                loginDto.getUsernameOrEmail()
        );
        
        if (userOpt.isEmpty()) {
            return new AuthResponseDto(null, "用户名或密码错误", false, null);
        }
        
        User user = userOpt.get();
        
        // 验证密码
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPasswordHash())) {
            return new AuthResponseDto(null, "用户名或密码错误", false, null);
        }
        
        // 检查用户状态
        if (user.getStatus() != User.UserStatus.ACTIVE) {
            return new AuthResponseDto(null, "账户已被禁用", false, null);
        }
        
        // 生成JWT令牌
        String token = jwtUtil.generateToken(user.getUsername(), user.getUserId());
        
        AuthResponseDto.UserInfo userInfo = new AuthResponseDto.UserInfo(
                user.getUserId(),
                user.getUsername(),
                user.getEmail()
        );
        
        return new AuthResponseDto(token, "登录成功", true, userInfo);
    }
} 