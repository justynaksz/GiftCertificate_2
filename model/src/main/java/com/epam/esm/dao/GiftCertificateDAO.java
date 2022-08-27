package com.epam.esm.dao;

import com.epam.esm.model.GiftCertificate;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

/**
 * CRUD operations for {@code GiftCertificate} entity.
 */
public interface GiftCertificateDAO {

    /**
     * Finds {@code giftCertificate} of given id value.
     * @param id                                int id value
     * @return giftCertificate                  giftCertificate of given id value
     * @throws EmptyResultDataAccessException   in case of gift certificate not found
     */
    GiftCertificate findById(int id) throws EmptyResultDataAccessException;

    /**
     * Finds {@code giftCertificate} assigned to given tagName value.
     * @param tagName                        String value of tag's name
     * @return giftCertificates lists        giftCertificates assigned to given tag's name
     */
    List<GiftCertificate> findByTag(String tagName);

    /**
     * Finds {@code giftCertificate} by part of name or description.
     * @param key                           String value of requested name/description word
     * @return giftCertificates lists       giftCertificates containing key word in their name or description
     */
    List<GiftCertificate> findByNameOrDescription(String key);

    /**
     * Finds all {@code giftCertificate}.
     * @return giftCertificates lists    all giftCertificates
     */
    List<GiftCertificate> findAll();

    /**
     * Sorts all {@code giftCertificate} by ascending order.
     * @return giftCertificates lists in ascending order
     */
    List<GiftCertificate> sortAscending();

    /**
     * Sorts all {@code giftCertificate} by descending order.
     * @return giftCertificates lists in descending order
     */
    List<GiftCertificate> sortDescending();

    /**
     * Sorts all {@code giftCertificate} by ascending order by date.
     * @return giftCertificates lists in ascending order by date
     */
    List<GiftCertificate> sortAscendingByDate() throws DataAccessException;

    /**
     * Sorts all {@code giftCertificate} by descending order by date.
     * @return giftCertificates lists in descending order by date
     */
    List<GiftCertificate> sortDescendingByDate();

    /**
     * Creates new {@code giftCertificate} entity.
     * @param giftCertificate     GiftCertificate instance to be inserted into database
     * @return giftCertificate    GiftCertificate instance with specified id value that has been inserted into database
     */
    GiftCertificate createGiftCertificate(GiftCertificate giftCertificate);

    /**
     * Updates {@code giftCertificate} contained in database.
     * @param giftCertificate                       GiftCertificate instance to be updated in database
     * @throws EmptyResultDataAccessException       in case of gift certificate not found
     */
    void updateGiftCertificate(GiftCertificate giftCertificate) throws EmptyResultDataAccessException;

    /**
     * Deletes {@code giftCertificate} of given id value.
     * @param  id                                   int id value of giftCertificate instance to be removed
     * @throws EmptyResultDataAccessException       in case of gift certificate not found
     */
    void deleteGiftCertificate(int id) throws EmptyResultDataAccessException;
}
