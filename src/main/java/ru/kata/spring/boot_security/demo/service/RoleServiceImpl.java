package ru.kata.spring.boot_security.demo.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleDao roleDao;
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Role> getAll() {
        return roleDao.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Override
    public Set<Role> getByNames(List<String> names) {
        return roleDao.findRolesByNameIn(names, Sort.by(Sort.Direction.ASC, "name"));
    }
}
