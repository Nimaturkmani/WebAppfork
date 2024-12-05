package edu.fra.uas.repository;

import java.util.HashMap;

import org.springframework.stereotype.Repository;

import edu.fra.uas.model.User;

@Repository // so gut wie ein Datenbankklasse
public class UserRepository extends HashMap<Long, User> {
    
}
