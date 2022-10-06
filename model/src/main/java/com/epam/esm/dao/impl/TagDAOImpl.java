package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.model.Tag;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Implementation of CRD operations for {@link Tag} entity.
 */
@Repository
public class TagDAOImpl extends GenericDAOImpl<Tag, Integer> implements TagDAO {

    private final Logger logger = Logger.getLogger(getClass().getName());

    public TagDAOImpl() {
        super(Tag.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Tag> getTagsByParam(int page, int size, Integer id, String name) {
        logger.debug(countTagsFoundByParam(id, name) + " tags meet given criteria.");
        return createFoundByParamQuery(id, name)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int countTagsFoundByParam(Integer id, String name) {
        return createFoundByParamQuery(id, name).getResultList().size();
    }

    private TypedQuery<Tag> createFoundByParamQuery(Integer id, String name) {
        var criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);
        criteriaQuery.select(root);
        if (id != null) {
            criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));
            logger.debug("Finds tag with id = " + id);
        }
        if (name != null && !name.trim().isEmpty()) {
            criteriaQuery.where(criteriaBuilder.like(criteriaBuilder.upper(root.get("name")), buildKeyName(name)));
            logger.debug("Finds tag with name contains \"" + name + "\"");
        }
        return getEntityManager().createQuery(criteriaQuery);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Tag getByName(String name) throws NotFoundException {
        var criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("name"), name));
        var tag = getEntityManager().createQuery(criteriaQuery).getSingleResult();
        if (tag == null) {
            throw new NotFoundException("Tag of requested name - " + name + " - not found.");
        }
        logger.debug("Tag of name - " + name + " - has been found.");
        return tag;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Tag getTheMostPopularTag() {
        String popularQuery = "SELECT t.id, t.name, t.create_date FROM tag AS t\n" +
                "JOIN gift_certificate_tag AS gt \n" +
                "ON gt.tag_id = t.id\n" +
                "AND gt.gift_certificate_id IN (\n" +
                "\tSELECT g.id FROM gift_certificate AS g\n" +
                "\tJOIN shop_order AS o\n" +
                "\tON o.gift_certificate = g.id\n" +
                "\tAND o.shop_user = (\n" +
                "\tSELECT u.id FROM shop_user AS u\n" +
                "\tJOIN shop_order AS o\n" +
                "\tON u.id = o.shop_user\n" +
                "\tGROUP BY u.id\n" +
                "\tORDER BY SUM(o.cost) DESC\n" +
                "\tLIMIT 1)\n" +
                ")\n" +
                "LIMIT 1;";
        Query query = getEntityManager().createNativeQuery(popularQuery, Tag.class);
        return (Tag) query.getSingleResult();
    }

    private String buildKeyName(String key) {
        return "%" + key.toUpperCase() + "%";
    }
}
