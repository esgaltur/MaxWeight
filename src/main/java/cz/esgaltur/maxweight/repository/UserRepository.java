package cz.esgaltur.maxweight.repository;

import cz.esgaltur.maxweight.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for User entity.
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {
    
    /**
     * Find user by username.
     *
     * @param username Username
     * @return Optional user
     */
    Optional<User> findByUsername(String username);
    
    /**
     * Find user by email.
     *
     * @param email Email
     * @return Optional user
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Check if username exists.
     *
     * @param username Username
     * @return true if exists, false otherwise
     */
    Boolean existsByUsername(String username);
    
    /**
     * Check if email exists.
     *
     * @param email Email
     * @return true if exists, false otherwise
     */
    Boolean existsByEmail(String email);
}