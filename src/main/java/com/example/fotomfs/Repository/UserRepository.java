package com.example.fotomfs.Repository;

import com.example.fotomfs.Model.Role;
import com.example.fotomfs.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Null;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByLogin(String name);
    List<User> findAllByRolesContainsOrderById(Role role);
}
