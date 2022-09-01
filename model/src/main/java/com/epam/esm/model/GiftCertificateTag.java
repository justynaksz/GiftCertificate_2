package com.epam.esm.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * GiftCertificateTag entity with methods defined in Object class.
 */
@Entity
@Table(name = "gift_certificate_tag")
public class GiftCertificateTag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "gift_certificate_id", nullable = false)
    private int giftCertificateId;

    @Column(name = "tag_id", nullable = false)
    private int tagId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGiftCertificateId() {
        return giftCertificateId;
    }

    public void setGiftCertificateId(int giftCertificateId) {
        this.giftCertificateId = giftCertificateId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public GiftCertificateTag(int giftCertificateId, int tagId) {
        this.giftCertificateId = giftCertificateId;
        this.tagId = tagId;
    }

    public GiftCertificateTag() {
    }

    public GiftCertificateTag(int id, int giftCertificateId, int tagId) {
        this.id = id;
        this.giftCertificateId = giftCertificateId;
        this.tagId = tagId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GiftCertificateTag that = (GiftCertificateTag) o;
        return id == that.id && giftCertificateId == that.giftCertificateId && tagId == that.tagId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, giftCertificateId, tagId);
    }

    @Override
    public String toString() {
        return "GiftCertificate_Tag[" +
                "id=" + getId() +
                ", giftCertificateId=" + getGiftCertificateId() +
                ", tagId=" + getTagId() +
                ']';
    }
}
