package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.filter.GiftCertificateFilter;
import com.epam.esm.model.GiftCertificate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Implements CRUD operations for {@code GiftCertificate} entity.
 */
@Repository
public class GiftCertificateDAOImpl extends GenericDAOImpl<GiftCertificate, Integer> implements GiftCertificateDAO {

    private final Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    public GiftCertificateDAOImpl() {
        super(GiftCertificate.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GiftCertificate> findByParam(int page, int size, GiftCertificateFilter filter) throws NotFoundException {
        var criteriaBuilder = getEntityManager().getCriteriaBuilder();
        List<GiftCertificate> giftCertificates = getEntityManager().createQuery(filter.buildCriteriaQuery(criteriaBuilder))
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();
        if (giftCertificates.isEmpty()) {
            throw new NotFoundException();
        }
        logger.debug(giftCertificates.size() + " giftCertificates meet given criteria.");
       return giftCertificates;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateGiftCertificate(GiftCertificate giftCertificate){
        getEntityManager().merge(giftCertificate);
        logger.debug("GiftCertificate successfully updated");
    }
}
