package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateTagDAO;
import com.epam.esm.model.GiftCertificateTag;
import com.epam.esm.rowmapper.GiftCertificateTagRowMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Implements CRUD operations for GiftCertificateTag entity.
 */
@Repository
public class GiftCertificateTagDAOImpl implements GiftCertificateTagDAO {

    private static final String EXCEPTION_MESSAGE = "No record of requested gift certificate id and tag id has been found.";
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public GiftCertificateTagDAOImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GiftCertificateTag findGiftCertificateTagById(int id) {
        String query = "SELECT id, gift_certificate_id, tag_id FROM gift_certificate_tag WHERE id = ?";
        GiftCertificateTag giftCertificateTag = new GiftCertificateTag();
        try {
            giftCertificateTag = jdbcTemplate.queryForObject(query, new GiftCertificateTagRowMapper(), id);
        } catch (EmptyResultDataAccessException exception) {
            logger.error(EXCEPTION_MESSAGE);
            throw new EmptyResultDataAccessException(EXCEPTION_MESSAGE, 0);
        } catch (DataAccessException exception) {
            logger.error("Selecting giftCertificateTag has failed.");
        }
        return giftCertificateTag;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GiftCertificateTag findGiftCertificateTagByIds(int giftCertificateId, int tagId) {
        String query = "SELECT id, gift_certificate_id, tag_id FROM gift_certificate_tag " +
                "WHERE gift_certificate_id = ? AND tag_id = ?";
        GiftCertificateTag giftCertificateTag = new GiftCertificateTag();
        try {
            giftCertificateTag = jdbcTemplate.queryForObject(query, new GiftCertificateTagRowMapper(), giftCertificateId, tagId);
        } catch (EmptyResultDataAccessException exception) {
            logger.error(EXCEPTION_MESSAGE);
            throw new EmptyResultDataAccessException(EXCEPTION_MESSAGE, 0);
        } catch (DataAccessException exception) {
            logger.error("Selecting giftCertificateTag has failed.");
        }
        return giftCertificateTag;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GiftCertificateTag createGiftCertificateTag(GiftCertificateTag giftCertificateTag) {
        String query = "INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id) " +
                "VALUES (:gift_certificate_id, :tag_id)";
        Map<String, Object> map = new HashMap<>();
        try {
            if (!isInDataBase(giftCertificateTag)) {
                map.put("gift_certificate_id", giftCertificateTag.getGiftCertificateId());
                map.put("tag_id", giftCertificateTag.getTagId());
                KeyHolder keyHolder = new GeneratedKeyHolder();
                SqlParameterSource parameterSource = new MapSqlParameterSource(map);
                namedParameterJdbcTemplate.update(query, parameterSource, keyHolder, new String[]{"id"});
                int id = Objects.requireNonNull(keyHolder.getKey()).intValue();
                giftCertificateTag.setId(id);
            }
        } catch (DataAccessException exception) {
            logger.error("Creating giftCertificateTag has failed.");
        }
        return giftCertificateTag;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteGiftCertificateTag(int id) {
        String query = "DELETE FROM gift_certificate_tag WHERE id = ?";
        try {
            findGiftCertificateTagById(id);
            jdbcTemplate.update(query, id);
        } catch (EmptyResultDataAccessException exception) {
            throw new EmptyResultDataAccessException(EXCEPTION_MESSAGE, 0);
        } catch (DataAccessException exception) {
            logger.error("Deleting giftCertificateTag has failed.");
        }
    }

    private boolean isInDataBase(GiftCertificateTag giftCertificateTag) {
        boolean givenGiftCertificateTagAlreadyExists = true;
        try {
            findGiftCertificateTagByIds(giftCertificateTag.getGiftCertificateId(), giftCertificateTag.getTagId());
        } catch (EmptyResultDataAccessException exception) {
            givenGiftCertificateTagAlreadyExists = false;
        }
        return givenGiftCertificateTagAlreadyExists;
    }
}
