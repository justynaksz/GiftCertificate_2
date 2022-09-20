package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.model.Tag;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Implementation of CRD operations for {@code Tag} entity.
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
    public List<Tag> getTagsByParam(int page, int size, Integer id, String name) throws NotFoundException {
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
        List<Tag> tags = getEntityManager().createQuery(criteriaQuery)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();
        if (tags.isEmpty()) {
            throw new NotFoundException();
        }
        logger.debug(tags.size() + " tags meet given criteria.");
        return tags;
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
            throw new NotFoundException();
        }
        logger.debug("Tag of name " + name + " has been found");
        return tag;
    }

    private String buildKeyName(String key) {
        return "%" + key.toUpperCase() + "%";
    }
}
