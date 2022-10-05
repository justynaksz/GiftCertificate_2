package com.epam.esm.dao;

import com.epam.esm.exception.NotFoundException;
import com.epam.esm.filter.GiftCertificateFilter;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;

import java.util.List;

/**
 * CRUD operations for {@link GiftCertificate} entity.
 */
public interface GiftCertificateDAO extends GenericDAO<GiftCertificate, Integer> {

    /**
     * Finds {@link GiftCertificate} with requested list of {@link Tag} and requested param optionally sorted by name or date.
     *
     * @param page index of page
     * @param size max size of given page
     * @param filter instance with searching and sorting params
     * @return giftCertificates lists        giftCertificates that fits criteria
     */
    List<GiftCertificate> findByParam(int page, int size, GiftCertificateFilter filter);

    /**
     * Returns total count of {@link GiftCertificate} found with specified filter.
     *
     * @param filter instance with searching and sorting params
     * @return count of found {@link GiftCertificate}
     */
    int countCertificatesFoundByParam(GiftCertificateFilter filter);

    /**
     * Updates {@link GiftCertificate} contained in database.
     *
     * @param giftCertificate GiftCertificate instance to be updated in database
     * @return updated giftCertificate
     * @throws NotFoundException in case of gift certificate of given id has not been found
     */
    GiftCertificate updateGiftCertificate(GiftCertificate giftCertificate) throws NotFoundException;
}
