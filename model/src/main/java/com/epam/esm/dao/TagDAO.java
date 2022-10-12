package com.epam.esm.dao;

import com.epam.esm.exception.NotFoundException;
import com.epam.esm.model.Tag;

import java.util.List;

/**
 * CRD operations for {@link Tag} entity.
 */
public interface TagDAO extends GenericDAO<Tag, Integer> {

    /**
     * Finds {@link Tag} of given parameter(s) value.
     *
     * @param page index of page
     * @param size max size of given page
     * @param id   requested int id value (optional)
     * @param name requested String tag's name (optional)
     * @return tags          list of tags of given id or/and name
     */
    List<Tag> getTagsByParam(int page, int size, Integer id, String name);

    /**
     * Returns total count of {@link Tag} found with specified params.
     *
     * @param id   requested int id value (optional)
     * @param name requested String tag's name (optional)
     * @return count of found {@link Tag}
     */
    int countTagsFoundByParam(Integer id, String name);

    /**
     * Finds {@link Tag} of given name.
     *
     * @param name requested String tag's name
     * @return tag of given name
     * @throws NotFoundException in case there's no tag of given name in database
     */
    Tag getByName(String name) throws NotFoundException;


    /**
     * Finds the most popular {@link Tag} for user with the highest cost of all orders.
     *
     * @return the most popular tag
     */
    Tag getTheMostPopularTag();
}
