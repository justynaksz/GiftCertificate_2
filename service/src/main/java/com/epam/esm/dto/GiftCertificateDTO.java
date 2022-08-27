package com.epam.esm.dto;

import com.epam.esm.model.Tag;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

/**
 * DTO class fo GiftCertificate.
 */
@Component
public class GiftCertificateDTO {

    private int id;
    private String name;
    private String description;
    private double price;
    private Duration duration;
    private String createDate;
    private String lastUpdateDate;
    private List<Tag> tags;

    public GiftCertificateDTO() {
    }

    public GiftCertificateDTO(int id, String name, String description, double price,
                              Duration duration, String createDate, String lastUpdateDate, List<Tag> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public long getDuration() {
        return duration.toDays();
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDuration(long duration) {
        this.duration = Duration.ofDays(duration);
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
