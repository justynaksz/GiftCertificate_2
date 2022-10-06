package com.epam.esm.service;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exceptions.InvalidInputException;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.model.User;
import com.epam.esm.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Presents access to service operations with {@link User}.
 */
@Service
public class UserService {

    private final UserDAO userDAO;
    private final UserMapper userMapper;
    private static final String INVALID_ID_MESSAGE = "Given user's id is invalid.";
    private static final String INVALID_NAME_MESSAGE = "Given user's name is invalid.";

    @Autowired
    public UserService(UserDAO userDAO, UserMapper userMapper) {
        this.userDAO = userDAO;
        this.userMapper = userMapper;
    }

    /**
     * Finds {@link User} of given id value.
     *
     * @param id int id value
     * @return user             user of given id value
     * @throws InvalidInputException in case of negative id value
     * @throws NotFoundException     in case of no result in database
     */
    public UserDTO getById(Integer id) throws InvalidInputException, NotFoundException {
        if (isInvalidId(id)) {
            throw new InvalidInputException(INVALID_ID_MESSAGE);
        }
        var user = userDAO.findById(id);
        return userMapper.toDTO(user);
    }

    /**
     * Finds all {@link User}.
     *
     * @return users    list of all users on given page
     */
    public Page<UserDTO> getAll(int page, int size) {
        List<User> users = userDAO.findAll(page, size);
        List<UserDTO> userDTOs = new ArrayList<>();
        users.forEach(user -> userDTOs.add(userMapper.toDTO(user)));
        return new Page<>(page, size, userDAO.findAll().size(), userDTOs);
    }

    /**
     * Finds {@link User} of given name.
     *
     * @param nickname user's nickname
     * @return user             user of given nickname
     * @throws InvalidInputException in case of blank name
     * @throws NotFoundException     in case of no result in database
     */
    public UserDTO getByName(String nickname) throws InvalidInputException, NotFoundException {
        if (isInvalidName(nickname)) {
            throw new InvalidInputException(INVALID_NAME_MESSAGE);
        }
        var user = userDAO.getByNickname(nickname);
        return userMapper.toDTO(user);
    }

    private boolean isInvalidId(Integer id) {
        return id == null || id <= 0;
    }

    private boolean isInvalidName(String name) {
        return name == null || name.isBlank();
    }
}
