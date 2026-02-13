package cz.esgaltur.maxweight.service;

import cz.esgaltur.maxweight.api.model.UpdateProfileRequest;
import cz.esgaltur.maxweight.api.model.UserProfile;
import cz.esgaltur.maxweight.model.User;
import cz.esgaltur.maxweight.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneId;

/**
 * Service for user management operations.
 */
@Service
public class UserService {
    
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    /**
     * Get current user profile.
     *
     * @return User profile
     * @throws UsernameNotFoundException if user not found
     */
    public UserProfile getCurrentUserProfile() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("Getting profile for user: {}", username);
        
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        
        return mapToUserProfile(user);
    }
    
    /**
     * Update current user profile.
     *
     * @param request Update profile request
     * @return Updated user profile
     * @throws UsernameNotFoundException if user not found
     * @throws IllegalArgumentException if email already exists or password is invalid
     */
    public UserProfile updateCurrentUserProfile(UpdateProfileRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("Updating profile for user: {}", username);
        
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        
        // Update email if provided and different
        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new IllegalArgumentException("Email is already in use");
            }
            user.setEmail(request.getEmail());
        }
        
        // Update max weight if provided
        if (request.getMaxWeight() != null) {
            user.setMaxWeight(request.getMaxWeight());
        }
        
        // Update password if provided
        if (request.getNewPassword() != null && !request.getNewPassword().isEmpty()) {
            if (request.getCurrentPassword() == null || request.getCurrentPassword().isEmpty()) {
                throw new IllegalArgumentException("Current password is required to change password");
            }
            
            if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
                throw new IllegalArgumentException("Current password is incorrect");
            }
            
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        }
        
        userRepository.save(user);
        
        logger.info("Profile updated successfully for user: {}", username);
        
        return mapToUserProfile(user);
    }
    
    /**
     * Get user by username.
     *
     * @param username Username
     * @return User
     * @throws UsernameNotFoundException if user not found
     */
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
    
    /**
     * Map User entity to UserProfile DTO.
     *
     * @param user User entity
     * @return UserProfile DTO
     */
    private UserProfile mapToUserProfile(User user) {
        UserProfile profile = new UserProfile();
        profile.setId(user.getId() != null ? Long.parseLong(user.getId()) : null);
        profile.setUsername(user.getUsername());
        profile.setEmail(user.getEmail());
        profile.setMaxWeight(user.getMaxWeight());
        if (user.getCreatedAt() != null) {
            profile.setCreatedAt(OffsetDateTime.ofInstant(
                    user.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant(),
                    ZoneId.systemDefault()
            ));
        }
        return profile;
    }
}