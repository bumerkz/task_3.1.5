package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.kata.spring.boot_security.demo.dao.UserDaoImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;

import java.util.Collection;
import java.util.List;

import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    private final UserDaoImpl userDao;

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }


    public UserServiceImpl(UserDaoImpl userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public void add(User user) {                                        // изменения метода для правильного сохранения в БД, иначе пароль не кодировался
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        userDao.add(user);

    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userDao.delete(id);
    }

    @Override
    @Transactional
    public void edit(User user) {
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        userDao.edit(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(Long id) {
        return userDao.getUser(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User userOne = findByEmail(email);
        if (userOne == null) {
            throw new UsernameNotFoundException(email + " не найден");
        }
        UserDetails user = new org.springframework.security.core.userdetails.User(userOne.getUsername(),
                userOne.getPassword(), authority(userOne.getRoles())); // не нужные действия
        return userOne;
    }

    private Collection<? extends GrantedAuthority> authority(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
    }
}
