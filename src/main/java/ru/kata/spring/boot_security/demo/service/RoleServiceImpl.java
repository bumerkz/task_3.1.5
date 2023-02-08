package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDaoImpl;
import ru.kata.spring.boot_security.demo.entity.Role;

import java.util.List;
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDaoImpl roleDao;

    public RoleServiceImpl(RoleDaoImpl roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    @Transactional
    public void addRole(Role role) {
        Role userOne = roleDao.findByName(role.getRole());
        roleDao.add(role);
    }

    @Override
    @Transactional(readOnly = true)
    public Role findByNameRole(String email) {
        return roleDao.findByName(email);
    }
    @Override
    @Transactional(readOnly = true)
    public List<Role> getAllRoles() {
        return roleDao.getAllRoles();
    }

    @Override
    @Transactional(readOnly = true)
    public Role findByIdRole(Long id) {
        return roleDao.findByIdRole(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> listByRole(List<String> email) {
        return roleDao.listByName(email);
    }
}
