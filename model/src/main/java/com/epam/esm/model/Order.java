package com.epam.esm.model;

import com.epam.esm.audit.AuditListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Order entity.
 */
@EntityListeners(AuditListener.class)
@Entity
@Table(name = "shop_order")
public class Order implements Model{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "cost", nullable = false)
    private BigDecimal cost;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_user", nullable = false)
    private User shopUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gift_certificate", nullable = false)
    private GiftCertificate giftCertificate;

    public Order() {
    }

    public Order(Integer id, BigDecimal cost, LocalDateTime createDate,
                 LocalDateTime lastUpdateDate, User shopUser, GiftCertificate giftCertificate) {
        this.id = id;
        this.cost = cost;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.shopUser = shopUser;
        this.giftCertificate = giftCertificate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCreateDate() {
        this.createDate = LocalDateTime.now();
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLastUpdateDate() {
        this.lastUpdateDate = LocalDateTime.now();
    }

    public User getShopUser() {
        return shopUser;
    }

    public void setShopUser(User shopUser) {
        this.shopUser = shopUser;
    }

    public GiftCertificate getGiftCertificate() {
        return giftCertificate;
    }

    public void setGiftCertificate(GiftCertificate giftCertificate) {
        this.giftCertificate = giftCertificate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(cost, order.cost) && Objects.equals(shopUser, order.shopUser) && Objects.equals(giftCertificate, order.giftCertificate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cost, shopUser, giftCertificate);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", cost=" + cost +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                ", user=" + shopUser +
                ", giftCertificate=" + giftCertificate +
                '}';
    }
}
