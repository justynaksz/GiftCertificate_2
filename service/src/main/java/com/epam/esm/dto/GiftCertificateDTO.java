package com.epam.esm.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

/**
 * DTO class for GiftCertificate.
 */
@Relation(itemRelation = "gift certificate", collectionRelation = "gift certificates")
public class GiftCertificateDTO extends RepresentationModel<GiftCertificateDTO> implements DTO {

    private final Integer id;
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final Integer duration;
    private final String createDate;
    private final String lastUpdateDate;
    private final Set<TagDTO> tags;


    public GiftCertificateDTO(Integer id, String name, String description, BigDecimal price,
                              Integer duration, String createDate, String lastUpdateDate, Set<TagDTO> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.tags = tags;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getDuration() {
        return duration;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public Set<TagDTO> getTags() {
        return tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var that = (GiftCertificateDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name)
                && Objects.equals(description, that.description) && Objects.equals(price, that.price)
                && Objects.equals(duration, that.duration) && Objects.equals(createDate, that.createDate)
                && Objects.equals(lastUpdateDate, that.lastUpdateDate) && Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, duration, createDate, lastUpdateDate, tags);
    }
}
