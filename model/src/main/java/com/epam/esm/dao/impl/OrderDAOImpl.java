package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDAO;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.model.Order;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Implementation of CRUD operations for {@link Order} entity.
 */
@Repository
public class OrderDAOImpl extends GenericDAOImpl<Order, Integer> implements OrderDAO {

    private final Logger logger = Logger.getLogger(getClass().getName());

    public OrderDAOImpl() {
        super(Order.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> getByUser(int page, int size, Integer shopUser) {
        var criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("shopUser"), shopUser));
        List<Order> orders = getEntityManager().createQuery(criteriaQuery)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();
        logger.debug(countOrdersFoundByUser(shopUser) + " placed by user with id - " + shopUser + " - has been found.");
        return orders;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Order updateOrder(Order order) throws NotFoundException {
        getEntityManager().merge(order);
        logger.debug("Order successfully updated");
        return findById(order.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int countOrdersFoundByUser(Integer userId) {
        return createGetByUserQuery(userId).getResultList().size();
    }

    private TypedQuery<Order> createGetByUserQuery(Integer userId) {
        var criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("shop_user"), userId));
        return getEntityManager().createQuery(criteriaQuery);
    }
}
