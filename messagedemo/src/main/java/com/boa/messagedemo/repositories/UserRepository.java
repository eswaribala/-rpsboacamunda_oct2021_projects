package com.boa.messagedemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boa.messagedemo.models.User;

public interface UserRepository extends JpaRepository<User,String>{

}
