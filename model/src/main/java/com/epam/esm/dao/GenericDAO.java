package com.epam.esm.dao;

import com.epam.esm.exception.NotFoundException;

import java.util.List;

/**
 * CRD operations for entities.
 */
public interface GenericDAO<T, I> {

    /**
     * Finds record of requested id from given table.
     *
     * @param id requested id
     * @return record   of requested id
     * @throws NotFoundException in case of no record of given id found in database
     */
    T findById(I id) throws NotFoundException;

    /**
     * Finds all records from given table on page.
     *
     * @param page index of page
     * @param size max size of given page
     * @return list of all records in chosen table one single page
     */
    List<T> findAll(int page, int size);

    /**
     * Finds all records from given table.
     *
     * @return list of all records in chosen table
     */
    List<T> findAll();

    /**
     * Creates new record in given table.
     *
     * @param entity record to be inserted into table
     * @return insertedEntity   record that has been inserted into table
     */
    T create(T entity);

    /**
     * Deletes record of requested id from given entity.
     *
     * @param id requested id
     * @throws NotFoundException in case of no record of given id found in database
     */
    void delete(I id) throws NotFoundException;
}
