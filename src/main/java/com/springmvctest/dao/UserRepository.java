package com.springmvctest.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springmvctest.model.User;

/**
 * 
 * Spring Data JPA repository for the User entity.
 *
 */
@Repository
public interface UserRepository extends CrudRepository<User, String> {
}
