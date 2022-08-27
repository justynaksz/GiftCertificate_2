package com.epam.esm.dao;

import com.epam.esm.model.GiftCertificateTag;
import org.springframework.dao.EmptyResultDataAccessException;

public interface GiftCertificateTagDAO {

    /**
     * Finds giftCertificateTag of given id value.
     * @param  id                                   int id value
     * @return giftCertificateTag                   giftCertificateTag of given id value
     * @throws EmptyResultDataAccessException       in case of giftCertificateTag of given id not found
     */
    GiftCertificateTag findGiftCertificateTagById(int id) throws EmptyResultDataAccessException;

    /**
     * Finds giftCertificateTag of given gift certificate id and tag id.
     * @param giftCertificateId                 id of requested gift certificate
     * @param tagId                             id of requested tag
     * @return giftCertificateTag               of requested gift certificate and tag
     * @throws EmptyResultDataAccessException   in case of giftCertificateTag not found
     */
    GiftCertificateTag findGiftCertificateTagByIds(int giftCertificateId, int tagId) throws EmptyResultDataAccessException;

    /**
     * Creates new giftCertificateTag entity.
     * @param  giftCertificateTag     GiftCertificateTag instance to be inserted into database
     * @return giftCertificateTag     GiftCertificateTag instance with specified id value that has been inserted into database
     */
    GiftCertificateTag createGiftCertificateTag(GiftCertificateTag giftCertificateTag);

    /**
     * Deletes giftCertificateTag of given id value.
     * @param id                                    int id value of giftCertificateTag instance to be removed
     * @throws EmptyResultDataAccessException       in case of giftCertificateTag of given id not found
     */
    void deleteGiftCertificateTag(int id) throws EmptyResultDataAccessException;
}
