package com.epam.esm.filter;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Filter for searching giftCertificates by params with sorting option.
 */
public class GiftCertificateFilter {

    private final SortParam sortParameter;
    private final SortDirection sortDirection;
    private final String keyWord;
    private final Set<String> tags;

    public GiftCertificateFilter(SortParam sortParameter, SortDirection sortDirection, String key, Set<String> tags) {
        this.sortParameter = sortParameter;
        this.sortDirection = sortDirection;
        this.keyWord = key;
        this.tags = tags;
    }

    /**
     * Builds criteria query for searching giftCertificates by given params and with sorting optionally.
     * @param criteriaBuilder to build the query
     * @return criteriaQuery
     */
    public CriteriaQuery<GiftCertificate> buildCriteriaQuery(CriteriaBuilder criteriaBuilder) {
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = criteriaQuery.from(GiftCertificate.class);
        List<Predicate> predicates = new ArrayList<>();
        if(hasTags()) {
            predicates = buildTagsPredicate(criteriaBuilder, root);
        }
        if(hasKeyWord()) {
            predicates.add(buildKeyWordPredicate(criteriaBuilder, root));
        }
        if(hasSortParameter()) {
            var order = buildSortOrder(criteriaBuilder, root);
            criteriaQuery.orderBy(order).select(root).where(predicates.toArray(new Predicate[]{}));
        } else {
            criteriaQuery.select(root).where(predicates.toArray(new Predicate[]{}));
        }
        return criteriaQuery;
    }

    private Predicate buildKeyWordPredicate(CriteriaBuilder criteriaBuilder, Root<GiftCertificate> root) {
        var key = buildKeyWord(keyWord);
        return criteriaBuilder.or(
                criteriaBuilder.like(criteriaBuilder.upper(root.get("name")), key),
                criteriaBuilder.like(criteriaBuilder.upper(root.get("description")), key));
    }

    private Order buildSortOrder(CriteriaBuilder criteriaBuilder, Root<GiftCertificate> root) {
        if (sortParameter.equals(SortParam.NAME)) {
            var name = "name";
            if (hasSortDirection() && sortDirection.equals(SortDirection.DESC)) {
                return criteriaBuilder.desc(root.get(name));
            } else {
                return criteriaBuilder.asc(root.get(name));
            }
        } else {
            var date = "createDate";
            if (hasSortDirection() && sortDirection.equals(SortDirection.DESC)) {
                return criteriaBuilder.desc(root.get(date));
            } else {
                return criteriaBuilder.asc(root.get(date));
            }
        }
    }

    private List<Predicate> buildTagsPredicate(CriteriaBuilder criteriaBuilder, Root<GiftCertificate> root) {
        List<Predicate> predicates = new ArrayList<>();
        for(String tag : tags) {
            SetJoin<GiftCertificate, Tag> tagsJoin = root.joinSet("tags");
            predicates.add(criteriaBuilder.equal(tagsJoin.get("name"), tag));
        }
        return predicates;
    }

    private String buildKeyWord(String key){
        return "%" + key.toUpperCase() + "%";
    }

    private boolean hasKeyWord() {
        return keyWord != null;
    }

    private boolean hasSortParameter() {
        return sortParameter != null;
    }

    private boolean hasSortDirection() {
        return sortDirection != null;
    }

    private boolean hasTags() {
        return tags != null && !tags.isEmpty();
    }
}
