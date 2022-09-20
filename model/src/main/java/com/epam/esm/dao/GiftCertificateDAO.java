package com.epam.esm.dao;

import com.epam.esm.exception.NotFoundException;
import com.epam.esm.filter.GiftCertificateFilter;
import com.epam.esm.model.GiftCertificate;

import java.util.List;

/**
 * CRUD operations for {@code GiftCertificate} entity.
 */
public interface GiftCertificateDAO extends GenericDAO<GiftCertificate, Integer> {

    /**
     * Finds {@code giftCertificate} with requested list of {@code tag} and requested param optionally sorted by name or date.
     *
     * @param page index of page
     * @param size max size of given page
     * @param filter instance with searching and sorting params
     * @return giftCertificates lists        giftCertificates that fits criteria
     * @throws NotFoundException in case of no giftCertificate that fits criteria has been found in database
     */
    List<GiftCertificate> findByParam(int page, int size, GiftCertificateFilter filter) throws NotFoundException;

    /**
     * Updates {@code giftCertificate} contained in database.
     *
     * @param giftCertificate GiftCertificate instance to be updated in database
     */
    void updateGiftCertificate(GiftCertificate giftCertificate);
}
