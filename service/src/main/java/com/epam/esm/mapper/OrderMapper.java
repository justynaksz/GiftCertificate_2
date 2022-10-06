package com.epam.esm.mapper;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Mapper to transform {@link Order} and {@link OrderDTO} types.
 */
@Component
public class OrderMapper extends Mapper<Order, OrderDTO> {

    private final UserMapper userMapper;
    private final GiftCertificateMapper giftCertificateMapper;

    @Autowired
    public OrderMapper(UserMapper userMapper, GiftCertificateMapper giftCertificateMapper) {
        this.userMapper = userMapper;
        this.giftCertificateMapper = giftCertificateMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Order toModel (OrderDTO orderDTO) {
        var id = 0;
        if (orderDTO.getId() != null) {
            id = orderDTO.getId();
        }
        var cost = orderDTO.getCost();
        LocalDateTime createDate = null;
        if (orderDTO.getCreateDate() != null) {
            createDate = LocalDateTime.parse(orderDTO.getCreateDate());
        }
        LocalDateTime lastUpdateDate = null;
        if (orderDTO.getLastUpdateDate() != null) {
            lastUpdateDate = LocalDateTime.parse(orderDTO.getLastUpdateDate());
        }
        var user = userMapper.toModel(orderDTO.getUser());
        var giftCertificate = giftCertificateMapper.toModel(orderDTO.getGiftCertificate());
        return new Order(id, cost, createDate, lastUpdateDate, user, giftCertificate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderDTO toDTO (Order order) {
        var id = 0;
        if (order.getId() != null) {
            id = order.getId();
        }
        var cost = order.getCost();
        String createDate = null;
        if (order.getCreateDate() != null) {
            createDate = constructISOFormatDate(order.getCreateDate());
        }
        String lastUpdateDate = null;
        if (order.getLastUpdateDate() != null) {
            lastUpdateDate = constructISOFormatDate(order.getLastUpdateDate());
        }
        UserDTO userDTO = userMapper.toDTO(order.getShopUser());
        var giftCertificateDTO = giftCertificateMapper.toDTO(order.getGiftCertificate());
        return new OrderDTO(id, cost, createDate, lastUpdateDate, userDTO, giftCertificateDTO);
    }
}
