package ru.kata.spring.boot_security.demo.dao;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Collection;
import java.util.Set;

public interface RoleDao extends JpaRepository<Role, Integer> {
    Set<Role> findRolesByNameIn(Collection<String> names, Sort sort);
}
