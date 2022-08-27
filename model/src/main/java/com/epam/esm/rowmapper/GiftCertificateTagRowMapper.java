package com.epam.esm.rowmapper;

import com.epam.esm.model.GiftCertificateTag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation which maps each row to a result GiftCertificateTag object.
 */
public class GiftCertificateTagRowMapper implements RowMapper<GiftCertificateTag> {

    Logger logger = Logger.getLogger(getClass().getName());

    /**
     * Maps each row of data in the ResultSet to GiftCertificateTag object.
     * @param rs                    ResultSet to be mapped
     * @param rowNum                the number of current row
     * @return giftCertificateTag   GiftCertificateTag instance
     */
    @Override
    public GiftCertificateTag mapRow(ResultSet rs, int rowNum) {
        GiftCertificateTag giftCertificateTag = new GiftCertificateTag();
        try {
        giftCertificateTag.setId(rs.getInt("id"));
        giftCertificateTag.setGiftCertificateId(rs.getInt("gift_certificate_id"));
        giftCertificateTag.setTagId(rs.getInt("tag_id"));
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.toString(), e);
        }
        return giftCertificateTag;
    }
}
