package com.epam.esm.rowmapper;

import com.epam.esm.model.GiftCertificate;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Maps each row to a result {@code GiftCertificate} object.
 */
@Component
public class GiftCertificateRowMapper implements RowMapper<GiftCertificate> {

    private final Logger logger = Logger.getLogger(getClass().getName());

    /**
     * Maps each row of data in the ResultSet to {@code GiftCertificate} object.
     * @param rs                 ResultSet to be mapped
     * @param rowNum             the number of current row
     * @return giftCertificate   GiftCertificate instance
     */
    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) {
        GiftCertificate giftCertificate = new GiftCertificate();
        try {
            giftCertificate.setId(rs.getInt("id"));
            giftCertificate.setName(rs.getString("name"));
            giftCertificate.setDescription(rs.getString("description"));
            giftCertificate.setPrice(rs.getDouble("price"));
            giftCertificate.setDuration(rs.getLong("duration"));
            giftCertificate.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime());
            if (rs.getTimestamp("last_update_date") != null) {
                giftCertificate.setLastUpdateDate(rs.getTimestamp("last_update_date").toLocalDateTime());
            } else {
                giftCertificate.setLastUpdateDate(null);
            }
        } catch (SQLException exception) {
            logger.error(exception.getMessage());
        }
        return giftCertificate;
    }
}
