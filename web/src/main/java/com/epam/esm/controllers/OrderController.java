package com.epam.esm.controllers;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exceptions.InvalidInputException;
import com.epam.esm.hateoas.assembler.OrderAssembler;
import com.epam.esm.hateoas.pagelinker.OrderPageLinker;
import com.epam.esm.model.Order;
import com.epam.esm.model.User;
import com.epam.esm.pagination.Page;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Spring REST controllers for processing requests {@link Order} resource.
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final GiftCertificateService giftCertificateService;
    private final OrderAssembler assembler;
    private final OrderPageLinker pageLinker;

    @Autowired
    public OrderController(OrderService orderService, UserService userService, GiftCertificateService
            giftCertificateService, OrderAssembler assembler, OrderPageLinker pageLinker) {
        this.orderService = orderService;
        this.userService = userService;
        this.giftCertificateService = giftCertificateService;
        this.assembler = assembler;
        this.pageLinker = pageLinker;
    }

    /**
     * Gets {@link Order} with requested id.
     * Handles GET http-request.
     *
     * @param id required id value
     * @return order
     */
    @GetMapping("id/{id}")
    public OrderDTO getById(@PathVariable Integer id) throws InvalidInputException, NotFoundException {
        return assembler.addLink(orderService.getById(id));
    }

    /**
     * Gets all {@link Order}.
     * Handles GET http-request.
     *
     * @return orders   list of all orders in database
     */
    @GetMapping()
    public CollectionModel<OrderDTO> getAll(@RequestParam(defaultValue = "1") int page,
                                            @RequestParam(defaultValue = "5") int size) {
        Page<OrderDTO> pageOfResults = orderService.getAll(page, size);
        List<OrderDTO> orders = assembler.addLinks(pageOfResults.getContent());
        List<Link> links = new ArrayList<>();
        links.add(assembler.getLinkToCollection());
        pageLinker.addPagesLinksInGetAllMethod(pageOfResults, links);
        return CollectionModel.of(orders, links);
    }

    /**
     * Gets {@link Order} by {@link User}.
     * Handles GET http-request.
     *
     * @param userid id of requested user
     * @return orders   list of all orders in database
     */
    @GetMapping("user")
    public CollectionModel<OrderDTO> getByUser(@RequestParam(defaultValue = "1") int page,
                                               @RequestParam(defaultValue = "5") int size,
                                               @RequestParam int userid) throws InvalidInputException, NotFoundException {
        Page<OrderDTO> pageOfResults = orderService.getByUser(page, size, userid);
        List<OrderDTO> orders = assembler.addLinks(pageOfResults.getContent());
        List<Link> links = new ArrayList<>();
        links.add(assembler.getLinkToCollection());
        pageLinker.addPagesLinksInGetAllMethod(pageOfResults, links);
        return CollectionModel.of(orders, links);
    }

    /**
     * Creates new {@link Order}.
     * Handles POST http-request.
     *
     * @param userid id of user who's placing an order
     * @param certificateid id of ordered certificate
     * @return order that has been inserted into database
     * @throws InvalidInputException in case of negative id value
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO placeAnOrder(@RequestParam Integer userid,
                                 @RequestParam Integer certificateid) throws InvalidInputException, NotFoundException {
        UserDTO userDTO = userService.getById(userid);
        GiftCertificateDTO giftCertificateDTO = giftCertificateService.getById(certificateid);
        OrderDTO orderDTO = new OrderDTO(null, giftCertificateDTO.getPrice(), null, null, userDTO, giftCertificateDTO);
        return assembler.addLink(orderService.placeAnOrder(orderDTO));
    }

    /**
     * Updates {@link Order} in database.
     * Handles PUT http-request.
     *
     * @param id of order to update
     * @param userid id of new user to update in order
     * @param certificateid id of new gift certificate to update in order
     * @return updated order
     * @throws InvalidInputException in case of negative price or duration input
     * @throws NotFoundException     in case of order to be updated is not present in database
     */
    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public OrderDTO updateOrder(@PathVariable Integer id,
                                @RequestParam Integer userid,
                                @RequestParam Integer certificateid) throws InvalidInputException, NotFoundException {
        UserDTO userDTO = userService.getById(userid);
        GiftCertificateDTO giftCertificateDTO = giftCertificateService.getById(certificateid);
        OrderDTO orderDTO = new OrderDTO(id, giftCertificateDTO.getPrice(), null, null, userDTO, giftCertificateDTO);
        return orderService.updateOrder(orderDTO, id);
    }

    /**
     * Removes {@link Order} from database.
     * Handles DELETE http-request.
     *
     * @param id int id of order to delete from database
     * @throws NotFoundException in case of order to be deleted is not present in database
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGiftCertificate(@PathVariable Integer id) throws NotFoundException, InvalidInputException {
        orderService.delete(id);
    }
}
