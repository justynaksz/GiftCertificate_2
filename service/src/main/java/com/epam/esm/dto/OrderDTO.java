package com.epam.esm.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * DTO class for Order.
 */
@Relation(itemRelation = "order", collectionRelation = "orders")
public class OrderDTO extends RepresentationModel<OrderDTO> implements DTO {

    private final Integer id;
    private final BigDecimal cost;
    private final String createDate;
    private final String lastUpdateDate;
    private final UserDTO user;
    private final GiftCertificateDTO giftCertificate;

    public OrderDTO(Integer id, BigDecimal cost, String createDate,
                    String lastUpdateDate, UserDTO user, GiftCertificateDTO giftCertificate) {
        this.id = id;
        this.cost = cost;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.user = user;
        this.giftCertificate = giftCertificate;
    }

    public Integer getId() {
        return id;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public UserDTO getUser() {
        return user;
    }

    public GiftCertificateDTO getGiftCertificate() {
        return giftCertificate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OrderDTO orderDTO = (OrderDTO) o;
        return Objects.equals(id, orderDTO.id) && Objects.equals(cost, orderDTO.cost) && Objects.equals(user, orderDTO.user) && Objects.equals(giftCertificate, orderDTO.giftCertificate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, cost, user, giftCertificate);
    }
}
