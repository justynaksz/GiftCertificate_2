package com.epam.esm.rowmapper;

import com.epam.esm.model.Tag;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
* Maps each row to a result {@code Tag} object.
*/
@Component
public class TagRowMapper implements RowMapper<Tag> {

    private final Logger logger = Logger.getLogger(getClass().getName());

    /**
     * Maps each row of data in the ResultSet to {@code Tag} object.
     * @param rs                ResultSet to be mapped
     * @param rowNum            the number of current row
     * @return tag              Tag instance
     */
    @Override
    public Tag mapRow(ResultSet rs, int rowNum) {
        Tag tag = new Tag();
        try {
            tag.setId(rs.getInt("id"));
            tag.setName(rs.getString("name"));
        } catch (SQLException exception) {
            logger.error(exception.getMessage());
        }
        return tag;
    }
}
