package com.epam.esm.dao;

import com.epam.esm.exception.NotFoundException;
import com.epam.esm.model.Tag;

import java.util.List;

/**
 * CRD operations for {@code Tag} entity.
 */
public interface TagDAO extends GenericDAO<Tag, Integer> {

    /**
     * Finds {@code tag} of given parameter(s) value.
     *
     * @param page index of page
     * @param size max size of given page
     * @param id   requested int id value (optional)
     * @param name requested String tag's name (optional)
     * @return tags          list of tags of given id or/and name
     * @throws NotFoundException in case there's no tag in database that fits criteria
     */
    List<Tag> getTagsByParam(int page, int size, Integer id, String name) throws NotFoundException;

    /**
     * Finds {@code tag} of given name.
     *
     * @param name requested String tag's name
     * @return tag of given name
     * @throws NotFoundException in case there's no tag of given name in database
     */
    Tag getByName(String name) throws NotFoundException;
}
