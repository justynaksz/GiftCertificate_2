package com.epam.esm.service;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exceptions.InvalidInputException;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Order;
import com.epam.esm.model.User;
import com.epam.esm.pagination.Page;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Presents access to service operations with {@link Order}.
 */
@Service
public class OrderService {

    private final Logger logger = Logger.getLogger(getClass().getName());

    private final OrderDAO orderDAO;
    private final UserDAO userDAO;
    private final GiftCertificateDAO giftCertificateDAO;
    private final OrderMapper orderMapper;

    private static final String INVALID_ID_MESSAGE = "Given user's id is invalid.";
    private static final String INVALID_INPUT_MESSAGE = "At least one of given parameters is invalid.";
    private static final String USER_NOT_EXISTS_MESSAGE = "Requested user doesn't exists in db.";

    public OrderService(OrderDAO orderDAO, UserDAO userDao, GiftCertificateDAO giftCertificateDAO,
                        OrderMapper orderMapper) {
        this.orderDAO = orderDAO;
        this.userDAO = userDao;
        this.giftCertificateDAO = giftCertificateDAO;
        this.orderMapper = orderMapper;
    }

    /**
     * Finds all {@link Order}.
     *
     * @return orders    list of all orders on given page
     */
    public Page<OrderDTO> getAll(int page, int size) {
        List<Order> orders = orderDAO.findAll(page, size);
        List<OrderDTO> orderDTOs = new ArrayList<>();
        orders.forEach(user -> orderDTOs.add(orderMapper.toDTO(user)));
        return new Page<>(page, size, orderDAO.findAll().size(), orderDTOs);
    }

    /**
     * Finds {@link Order} of given id value.
     *
     * @param id int id value
     * @return order of given id value
     * @throws InvalidInputException in case of negative id value
     * @throws NotFoundException     in case of no result in database
     */
    public OrderDTO getById(Integer id) throws InvalidInputException, NotFoundException {
        if (isIdInvalid(id)) {
            throw new InvalidInputException(INVALID_ID_MESSAGE);
        }
        var order = orderDAO.findById(id);
        return orderMapper.toDTO(order);
    }

    /**
     * Finds {@link Order} of given user value.
     *
     * @param userId int user's id value
     * @return orders list of orders og requested user
     * @throws InvalidInputException in case of negative id value
     */
    public Page<OrderDTO> getByUser(int page, int size, Integer userId) throws InvalidInputException, NotFoundException {
        if (isIdInvalid(userId)) {
            throw new InvalidInputException(INVALID_ID_MESSAGE);
        }
        if (userDAO.findById(userId) == null) {
            logger.debug(USER_NOT_EXISTS_MESSAGE);
        }
        List<Order> orders = orderDAO.getByUser(page, size, userId);
        List<OrderDTO> orderDTOs = new ArrayList<>();
        orders.forEach(order -> orderDTOs.add(orderMapper.toDTO(order)));
        return new Page<>(page, size, orderDAO.countOrdersFoundByUser(userId), orderDTOs);
    }

    /**
     * Creates new {@link Order} entity.
     *
     * @param orderDTO instance to be inserted into database
     * @return order instance with specified id value that has been inserted into database
     * @throws InvalidInputException in case of invalid input
     */
    @Transactional
    public OrderDTO placeAnOrder(OrderDTO orderDTO) throws InvalidInputException {
        if (!isInputInvalid(orderDTO)) {
            logger.debug("Validation of placed order succeeded.");
        } else {
            throw new InvalidInputException(INVALID_INPUT_MESSAGE);
        }
        var order = orderMapper.toModel(orderDTO);
        order.setId(null);
        var orderInserted = orderDAO.create(order);
        return orderMapper.toDTO(orderInserted);
    }

    /**
     * Updates {@link Order} contained in database.
     *
     * @param orderDTO instance to be updated in database
     * @return updated order
     * @throws InvalidInputException in case of invalid input
     * @throws NotFoundException     in case of order to be updated is not present in database
     */
    @Transactional
    public OrderDTO updateOrder(OrderDTO orderDTO, Integer id) throws InvalidInputException, NotFoundException {
        var orderInDb = orderDAO.findById(id);
        if (orderInDb == null) {
            throw new NotFoundException("Order of requested id = " + id + " not found.");
        }
        prepareOrderToUpdate(orderInDb, orderDTO);
        return orderMapper.toDTO(orderDAO.updateOrder(orderInDb));
    }

    /**
     * Deletes {@link Order} of given id value.
     *
     * @param id int id value of order instance to be removed
     * @throws NotFoundException in case of order to be deleted is not present in database
     */
    public void delete(Integer id) throws NotFoundException, InvalidInputException {
        if (isIdInvalid(id)) {
            throw new InvalidInputException(INVALID_ID_MESSAGE);
        }
        orderDAO.delete(id);
    }

    private boolean isInputInvalid(OrderDTO orderDTO) {
        return isCostInvalid(orderDTO.getCost()) || isUserInvalid(orderDTO.getUser())
                || isGiftCertificateInvalid(orderDTO.getGiftCertificate());
    }

    private boolean isIdInvalid(Integer id) {
        return id == null || id <= 0;
    }

    private boolean isCostInvalid(BigDecimal cost) {
        return cost.intValue() <= 0;
    }

    private boolean isUserInvalid(UserDTO userDTO) {
        return userDTO == null;
    }

    private boolean isGiftCertificateInvalid(GiftCertificateDTO giftCertificateDTO) {
        return giftCertificateDTO == null;
    }

    private void prepareOrderToUpdate(Order orderInDb, OrderDTO orderDTO) throws InvalidInputException, NotFoundException {
        prepareCostToUpdate(orderInDb, orderDTO);
        prepareUserToUpdate(orderInDb, orderDTO);
        prepareGiftCertificateToUpdate(orderInDb, orderDTO);
    }

    private void prepareCostToUpdate(Order updatedOrder, OrderDTO orderDTO) throws InvalidInputException {
        if (orderDTO.getCost() != null) {
            if (!isCostInvalid(orderDTO.getCost())) {
                updatedOrder.setCost(orderDTO.getCost());
            } else {
                throw new InvalidInputException(constructExceptionMessage("cost"));
            }
        }
    }

    private void prepareUserToUpdate(Order updatedOrder, OrderDTO orderDTO) throws NotFoundException, InvalidInputException {
        UserDTO updatedUserDTO = orderDTO.getUser();
        if (updatedUserDTO == null) {
            throw new InvalidInputException(constructExceptionMessage("user"));
        }
        User userWithId = userDAO.getByNickname(updatedUserDTO.getName());
        updatedOrder.setShopUser(userWithId);
    }

    private void prepareGiftCertificateToUpdate(Order updatedOrder, OrderDTO orderDTO) throws NotFoundException, InvalidInputException {
        GiftCertificateDTO updatedGiftCertificateDTO = orderDTO.getGiftCertificate();
        if (updatedGiftCertificateDTO == null) {
            throw new InvalidInputException(constructExceptionMessage("gift certificate"));
        }
        GiftCertificate giftCertificateWithId = giftCertificateDAO.findById(updatedGiftCertificateDTO.getId());
        updatedOrder.setGiftCertificate(giftCertificateWithId);
    }

    private String constructExceptionMessage(String parameter) {
        return "Invalid " + parameter + " in update order method.";
    }
}
