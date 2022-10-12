package com.epam.esm.dao;

import com.epam.esm.exception.NotFoundException;
import com.epam.esm.model.Order;
import com.epam.esm.model.User;

import java.util.List;

/**
 * CRUD operations for {@link Order} entity.
 */
public interface OrderDAO extends GenericDAO<Order, Integer> {

    /**
     * Finds {@link Order} of given {@link User} requested by user's id.
     *
     * @param page index of page
     * @param size max size of given page
     * @param userId id of given user
     * @return list of orders placed by given user
     */
    List<Order> getByUser(int page, int size, Integer userId);

    /**
     * Updates {@link Order} contained in database.
     *
     * @param order Order instance to be updated in database
     * @return updated order
     * @throws NotFoundException in case of order of given id has not been found
     */
    Order updateOrder(Order order) throws NotFoundException;

    /**
     * Returns total count of {@link Order} placed by single user.
     *
     * @param userId which orders are counted
     * @return total count of all user's order
     */
    int countOrdersFoundByUser(Integer userId);
}
