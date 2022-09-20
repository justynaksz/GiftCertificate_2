package com.epam.esm.dao.impl;

import com.epam.esm.dao.GenericDAO;
import com.epam.esm.exception.NotFoundException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

/**
 * Implementation of CRD operations for entities.
 */
@Repository
@Transactional
public abstract class GenericDAOImpl <T, I extends Serializable> implements GenericDAO<T, I> {

    @PersistenceContext
    private EntityManager entityManager;

    private final Class<T> entityClass;

    private final Logger logger = Logger.getLogger(getClass().getName());

    protected GenericDAOImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T findById(I id) throws NotFoundException {
        T entity = entityManager.find(entityClass, id);
        if (entity == null) {
            throw new NotFoundException();
        }
        logger.debug("Item of requested id = " + id + " has been found.");
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> findAll(int page, int size) throws NotFoundException {
        List<T> entities = entityManager.createQuery("from " + getEntityClass().getName())
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();
        if (entities.isEmpty()) {
            throw new NotFoundException();
        }
        logger.debug(entities.size() + " items has been found.");
        return entities;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> findAll() throws NotFoundException {
        List<T> entities = entityManager.createQuery("from " + getEntityClass().getName()).getResultList();
        if (entities.isEmpty()) {
            throw new NotFoundException();
        }
        logger.debug(entities.size() + " items has been found.");
        return entities;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public T create(T entity) {
        entityManager.persist(entity);
        logger.debug("Item created.");
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void delete(I id) throws NotFoundException {
        T entity = findById(id);
        if (entity != null) {
            entityManager.remove(entity);
        } else {
            throw new NotFoundException();
        }
        logger.debug("Item of id = " + id + " has been deleted.");
    }
}
