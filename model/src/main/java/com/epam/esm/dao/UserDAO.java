package com.epam.esm.dao;


import com.epam.esm.exception.NotFoundException;
import com.epam.esm.model.User;

/**
 * CRUD operations for {@link User} entity.
 */
public interface UserDAO extends GenericDAO<User, Integer> {

    /**
     * Finds {@link User} of given nickname.
     *
     * @param nickname requested String user's nickname
     * @return user of given nickname
     * @throws NotFoundException in case there's no user of given nickname in database
     */
    User getByNickname(String nickname) throws NotFoundException;
}
