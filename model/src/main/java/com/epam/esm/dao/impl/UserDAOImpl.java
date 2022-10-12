package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.model.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Implements CRUD operations for {@link User} entity.
 */
@Repository
public class UserDAOImpl extends GenericDAOImpl<User, Integer> implements UserDAO {

    private final Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    public UserDAOImpl() {
        super(User.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getByNickname(String nickname) throws NotFoundException {
        var criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("nickname"), nickname));
        var user = getEntityManager().createQuery(criteriaQuery).getSingleResult();
        if (user == null) {
            throw new NotFoundException("User of requested name - " + nickname + " - not found.");
        }
        logger.debug("User of requested name - " + nickname + " - has been found.");
        return user;
    }
}
