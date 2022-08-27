package com.epam.esm.model;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * GiftCertificate entity with methods defined in Object class.
 */
@Component
public class GiftCertificate {

    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private Duration duration;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;

    public GiftCertificate() {
    }

    public GiftCertificate(int id, String name, String description, BigDecimal price,
                           Duration duration, LocalDateTime createDate, LocalDateTime lastUpdateDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price.doubleValue();
    }

    public void setPrice(double price) {
        this.price = BigDecimal.valueOf(price);
    }

    public long getDuration() {
        return duration.toDays();
    }

    public void setDuration(long duration) {
        this.duration = Duration.ofDays(duration);
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GiftCertificate that = (GiftCertificate) o;
        return id == that.id && name.equals(that.name) && description.equals(that.description) && price.equals(that.price) && duration.equals(that.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, duration);
    }

    @Override
    public String toString() {
        return "GiftCertificate[" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", price=" + getPrice() +
                ", duration=" + getDuration() +
                ", createDate=" + getCreateDate() +
                ", lastUpdateDate=" + getLastUpdateDate() +
                ']';
    }
}
