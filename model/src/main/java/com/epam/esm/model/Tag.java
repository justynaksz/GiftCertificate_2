package com.epam.esm.model;

import com.epam.esm.audit.AuditListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

/**
 * Tag entity.
 */
@EntityListeners(AuditListener.class)
@Entity
@Table(name = "tag")
public class Tag implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private Set<GiftCertificate> giftCertificates;

    public Tag(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Tag() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setCreateDate() {
        this.createDate = LocalDateTime.now();
    }

    @Override
    public void setLastUpdateDate() {
        // Because there is no possibility to update tag but the method needs to be implemented - it stays empty.
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(id, tag.id) && name.equals(tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Tag[" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ']';
    }
}
