import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exceptions.InvalidInputException;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Order;
import com.epam.esm.model.User;
import com.epam.esm.pagination.Page;
import com.epam.esm.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;
    @Mock
    private OrderDAO orderDAO;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private GiftCertificateDAO giftCertificateDAO;
    @Mock
    private UserDAO userDAO;

    private GiftCertificate paintballCertificate;
    private GiftCertificate movieCertificate;
    private GiftCertificateDTO paintballCertificateDTO;
    private GiftCertificateDTO movieCertificateDTO;

    private User walter;
    private User saul;
    private UserDTO walterDTO;
    private UserDTO saulDTO;

    private Order walterMovie;
    private Order pinkmanPaintBall;
    private Order saulAqua;
    private OrderDTO walterMovieDTO;
    private OrderDTO pinkmanPaintBallDTO;
    private OrderDTO saulAquaDTO;


    @BeforeEach
    void init() {
        paintballCertificate = new GiftCertificate(1, "Paintball voucher",
                "2 hours of paintball match in Paintball-World", BigDecimal.valueOf(49.99),
                180, LocalDateTime.parse("2022-03-18T12:24:47.241"),
                LocalDateTime.parse("2022-06-28T09:28:57.241"), new HashSet<>());
        GiftCertificate aquaparkCertificate = new GiftCertificate(2, "Aquapark Fun",
                "3 hours in the biggest auqapark in PL", BigDecimal.valueOf(30.00),
                90, LocalDateTime.parse("2022-03-18T12:24:47.241"),
                null, new HashSet<>());
        movieCertificate = new GiftCertificate(0, "Movie night",
                "Movie session in Cinema City", BigDecimal.valueOf(15.00), 200,
                LocalDateTime.parse("2022-03-18T12:24:47.241"),
                LocalDateTime.parse("2022-06-18T12:24:47.241"), new HashSet<>());
        paintballCertificateDTO = new GiftCertificateDTO(1, "Paintball voucher",
                "2 hours of paintball match in Paintball-World", BigDecimal.valueOf(49.99),
                180, "2022-03-18T12:24:47.241", "2022-06-28T09:28:57.241", new HashSet<>());
        GiftCertificateDTO aquaparkCertificateDTO = new GiftCertificateDTO(2, "Aquapark Fun",
                "3 hours in the biggest auqapark in PL", BigDecimal.valueOf(30.00), 90,
                "2022-03-18T12:24:47.241", null, new HashSet<>());
        movieCertificateDTO = new GiftCertificateDTO(0, "Movie night",
                "Movie session in Cinema City", BigDecimal.valueOf(15.00), 200,
                "2022-03-18T12:24:47.241", "2022-06-18T12:24:47.241", new HashSet<>());

        walter = new User(1, "Walter", LocalDateTime.parse("2022-03-18T12:24:47.241"));
        User pinkman = new User(2, "Pinkman", LocalDateTime.parse("2020-02-18T12:24:47.241"));
        saul = new User(3, "Saul", LocalDateTime.parse("2022-06-18T12:24:47.241"));
        walterDTO = new UserDTO(1, "Walter", "2022-03-18T12:24:47.241");
        UserDTO pinkmanDTO = new UserDTO(2, "Pinkman", "2020-02-18T12:24:47.241");
        saulDTO = new UserDTO(3, "Saul", "2022-06-18T12:24:47.241");

        walterMovie = new Order(1, BigDecimal.valueOf(100.00), LocalDateTime.parse("2022-03-18T12:24:47.241"),
                null, walter, movieCertificate);
        pinkmanPaintBall = new Order(2, BigDecimal.valueOf(250.00), LocalDateTime.parse("2022-03-18T12:24:47.241"),
                LocalDateTime.parse("2022-05-18T12:24:47.241"), pinkman, paintballCertificate);
        saulAqua = new Order(3, BigDecimal.valueOf(74.99), LocalDateTime.parse("2021-03-18T12:24:47.241"),
                null, saul, aquaparkCertificate);
        walterMovieDTO = new OrderDTO(1, BigDecimal.valueOf(100.00), "2022-03-18T12:24:47.241",
                null, walterDTO, movieCertificateDTO);
        pinkmanPaintBallDTO = new OrderDTO(2, BigDecimal.valueOf(250.00), "2022-03-18T12:24:47.241",
                "2022-05-18T12:24:47.241", pinkmanDTO, paintballCertificateDTO);
        saulAquaDTO = new OrderDTO(3, BigDecimal.valueOf(74.99), "2021-03-18T12:24:47.241",
                null, saulDTO, aquaparkCertificateDTO);
    }

    @Nested
    @DisplayName("get all tests")
    class getAllTests {
        @Test
        @DisplayName("correct get all test")
        void getAllShouldReturnListOfAllOrdersInDb() {
            // GIVEN
            int pageNumber = 1;
            int pageSize = 10;
            List<Order> orders = new ArrayList<>(List.of(walterMovie, pinkmanPaintBall, saulAqua));
            List<OrderDTO> orderDTOs = new ArrayList<>(List.of(walterMovieDTO, pinkmanPaintBallDTO, saulAquaDTO));
            Page<OrderDTO> page = new Page<>(pageNumber, pageSize, orderDTOs.size(), orderDTOs);
            // WHEN
            when(orderDAO.findAll(pageNumber, pageSize)).thenReturn(orders);
            when(orderMapper.toDTO(walterMovie)).thenReturn(walterMovieDTO);
            when(orderMapper.toDTO(pinkmanPaintBall)).thenReturn(pinkmanPaintBallDTO);
            when(orderMapper.toDTO(saulAqua)).thenReturn(saulAquaDTO);
            // THEN
            assertEquals(page, orderService.getAll(pageNumber, pageSize));
        }

        @Test
        @DisplayName("find all when no record in db")
        void getAllShouldReturnEmptyListWhenNoRecordInDb() {
            // GIVEN
            int pageNumber = 1;
            int pageSize = 10;
            Page<OrderDTO> page = new Page<>(pageNumber, pageSize, 0, new ArrayList<>());
            // WHEN
            when(orderDAO.findAll(pageNumber, pageSize)).thenReturn(new ArrayList<>());
            // THEN
            assertEquals(page, orderService.getAll(pageNumber, pageSize));
        }
    }

    @Nested
    @DisplayName("get by id tests")
    class getByIdTests {
        @Test
        @DisplayName("get by id correct order")
        void getByIdShouldReturnCorrectOrder() throws NotFoundException, InvalidInputException {
            // GIVEN
            var id = 2;
            // WHEN
            when(orderDAO.findById(id)).thenReturn(pinkmanPaintBall);
            when(orderMapper.toDTO(pinkmanPaintBall)).thenReturn(pinkmanPaintBallDTO);
            // THEN
            assertEquals(pinkmanPaintBallDTO, orderService.getById(id));
        }

        @Test
        @DisplayName("no order of given id found in db")
        void getByIdShouldThrowExceptionIfNoUserOfGivenIdFound() throws NotFoundException {
            // GIVEN
            var id = 2;
            // WHEN
            when(orderDAO.findById(id)).thenThrow(NotFoundException.class);
            // THEN
            assertThrows(NotFoundException.class, () -> orderService.getById(id));
        }

        @Test
        @DisplayName("invalid id")
        void getByIdShouldThrowExceptionIfInvalidId() {
            // GIVEN
            var id = -2;
            // WHEN

            // THEN
            assertThrows(InvalidInputException.class, () -> orderService.getById(id));
        }
    }

    @Nested
    @DisplayName("get by user tests")
    class getByUserTests {
        @Test
        @DisplayName("get by user correct orders")
        void getByUserShouldReturnCorrectOrders() throws NotFoundException, InvalidInputException {
            // GIVEN
            int pageNumber = 1;
            int pageSize = 10;
            var userId = 3;
            List<OrderDTO> orderDTOs = new ArrayList<>(List.of(saulAquaDTO));
            Page<OrderDTO> page = new Page<>(pageNumber, pageSize, orderDTOs.size(), orderDTOs);
            // WHEN
            when(userDAO.findById(userId)).thenReturn(saul);
            when(orderDAO.getByUser(pageNumber, pageSize, userId)).thenReturn(new ArrayList<>(List.of(saulAqua)));
            when(orderMapper.toDTO(saulAqua)).thenReturn(saulAquaDTO);
            // THEN
            assertEquals(page, orderService.getByUser(pageNumber, pageSize, userId));
        }

        @Test
        @DisplayName("get by user no orders in db")
        void getByUserShouldReturnEmptyList() throws NotFoundException, InvalidInputException {
            // GIVEN
            int pageNumber = 1;
            int pageSize = 10;
            var userId = 3;
            Page<OrderDTO> page = new Page<>(pageNumber, pageSize, 0, new ArrayList<>());
            // WHEN
            when(userDAO.findById(userId)).thenReturn(saul);
            when(orderDAO.getByUser(pageNumber, pageSize, userId)).thenReturn(new ArrayList<>());
            // THEN
            assertEquals(page, orderService.getByUser(pageNumber, pageSize, userId));
        }

        @Test
        @DisplayName("get by invalid user's id")
        void getByUserShouldThrowExceptionIfInvalidId() {
            // GIVEN
            int pageNumber = 1;
            int pageSize = 10;
            var userId = -3;
            // WHEN

            // THEN
            assertThrows(InvalidInputException.class, () -> orderService.getByUser(pageNumber, pageSize, userId));
        }

        @Test
        @DisplayName("get by user who does not exists in db")
        void getByUserShouldThrowExceptionWhenUserNotFoundInDb() throws NotFoundException {
            // GIVEN
            int pageNumber = 1;
            int pageSize = 10;
            var userId = 3;
            // WHEN
            when(userDAO.findById(userId)).thenThrow(NotFoundException.class);
            // THEN
            assertThrows(NotFoundException.class, () -> orderService.getByUser(pageNumber, pageSize, userId));
        }
    }

    @Nested
    @DisplayName("place an order tests")
    class placeAnOrderTests {
        @Test
        @DisplayName("place an order should correctly create new order")
        void placeAnOrderShouldCorrectlyCreateNewOrderInDb() throws InvalidInputException {
            // GIVEN
            var orderToPlace = new Order(0, BigDecimal.valueOf(100.00), LocalDateTime.parse("2022-03-18T12:24:47.241"),
                    null, walter, movieCertificate);
            var orderToPlaceDTO = new OrderDTO(0, BigDecimal.valueOf(100.00), "2022-03-18T12:24:47.241",
                    null, walterDTO, movieCertificateDTO);
            // WHEN
            when(orderMapper.toModel(orderToPlaceDTO)).thenReturn(orderToPlace);
            when(orderDAO.create(orderToPlace)).thenReturn(walterMovie);
            when(orderMapper.toDTO(walterMovie)).thenReturn(walterMovieDTO);
            // THEN
            assertEquals(walterMovieDTO, orderService.placeAnOrder(orderToPlaceDTO));
        }

        @Test
        @DisplayName("invalid cost test")
        void placeAnOrderWithInvalidCostShouldThrowException() {
            // GIVEN
            var orderToPlaceDTO = new OrderDTO(0, BigDecimal.valueOf(-100.00), "2022-03-18T12:24:47.241",
                    null, walterDTO, movieCertificateDTO);
            // WHEN

            // THEN
            assertThrows(InvalidInputException.class, () -> orderService.placeAnOrder(orderToPlaceDTO));
        }

        @Test
        @DisplayName("invalid user test")
        void placeAnOrderWithInvalidUserShouldThrowException() {
            // GIVEN
            var orderToPlaceDTO = new OrderDTO(0, BigDecimal.valueOf(100.00), "2022-03-18T12:24:47.241",
                    null, null, movieCertificateDTO);
            // WHEN

            // THEN
            assertThrows(InvalidInputException.class, () -> orderService.placeAnOrder(orderToPlaceDTO));
        }

        @Test
        @DisplayName("invalid gift certificate test")
        void placeAnOrderWithInvalidGiftCertificateShouldThrowException() {
            // GIVEN
            var orderToPlaceDTO = new OrderDTO(0, BigDecimal.valueOf(100.00), "2022-03-18T12:24:47.241",
                    null, walterDTO, null);
            // WHEN

            // THEN
            assertThrows(InvalidInputException.class, () -> orderService.placeAnOrder(orderToPlaceDTO));
        }
    }

    @Nested
    @DisplayName("update order tests")
    class updateOrderTests {
        @Test
        @DisplayName("update order should correctly update order")
        void updateOrderShouldCorrectlyUpdateOrderInDb() throws InvalidInputException, NotFoundException {
            // GIVEN
            int id = 1;
            var orderInDb = new Order(id, BigDecimal.valueOf(100.00), LocalDateTime.parse("2022-03-18T12:24:47.241"),
                    null, walter, movieCertificate);
            var givenOrderDTO = new OrderDTO(id, null, null,
                    null, saulDTO, paintballCertificateDTO);
            var updatedOrder = new Order(id, BigDecimal.valueOf(100.00), LocalDateTime.parse("2022-03-18T12:24:47.241"),
                    null, saul, paintballCertificate);
            var updatedOrderDTO = new OrderDTO(id, BigDecimal.valueOf(100.00), "2022-03-18T12:24:47.241",
                    null, saulDTO, paintballCertificateDTO);
            // WHEN
            when(orderDAO.findById(id)).thenReturn(orderInDb);
            when(userDAO.getByNickname(saul.getNickname())).thenReturn(saul);
            when(giftCertificateDAO.findById(paintballCertificate.getId())).thenReturn(paintballCertificate);
            when(orderDAO.updateOrder(orderInDb)).thenReturn(updatedOrder);
            when(orderMapper.toDTO(updatedOrder)).thenReturn(updatedOrderDTO);
            // THEN
            assertEquals(updatedOrderDTO, orderService.updateOrder(givenOrderDTO, id));
        }

        @Test
        @DisplayName("update order which does not exist")
        void updateOrderShouldThrowExceptionIfOrderDoesNotExistInDb() throws NotFoundException {
            // GIVEN
            int id = 1;
            var orderToUpdateDTO = new OrderDTO(id, BigDecimal.valueOf(100.00), "2022-03-18T12:24:47.241",
                    null, walterDTO, movieCertificateDTO);
            // WHEN
            when(orderDAO.findById(id)).thenThrow(NotFoundException.class);

            // THEN
            assertThrows(NotFoundException.class, () -> orderService.updateOrder(orderToUpdateDTO, id));
        }

        @Test
        @DisplayName("invalid cost test")
        void updateOrderWithInvalidCostShouldThrowException() throws NotFoundException {
            // GIVEN
            var orderToUpdateDTO = new OrderDTO(1, BigDecimal.valueOf(-100.00), "2022-03-18T12:24:47.241",
                    null, walterDTO, movieCertificateDTO);
            var orderInDb = new Order(1, BigDecimal.valueOf(100.00), LocalDateTime.parse("2022-03-18T12:24:47.241"),
                    null, walter, movieCertificate);
            // WHEN
            when(orderDAO.findById(1)).thenReturn(orderInDb);
            // THEN
            assertThrows(InvalidInputException.class, () -> orderService.updateOrder(orderToUpdateDTO, 1));
        }

        @Test
        @DisplayName("invalid user test")
        void updateOrderWithInvalidUserShouldThrowException() throws NotFoundException {
            // GIVEN
            var orderToUpdateDTO = new OrderDTO(1, BigDecimal.valueOf(100.00), "2022-03-18T12:24:47.241",
                    null, null, movieCertificateDTO);
            var orderInDb = new Order(1, BigDecimal.valueOf(100.00), LocalDateTime.parse("2022-03-18T12:24:47.241"),
                    null, walter, movieCertificate);
            // WHEN
            when(orderDAO.findById(1)).thenReturn(orderInDb);
            // THEN
            assertThrows(InvalidInputException.class, () -> orderService.updateOrder(orderToUpdateDTO, 1));
        }

        @Test
        @DisplayName("invalid gift certificate test")
        void placeAnOrderWithInvalidGiftCertificateShouldThrowException() throws NotFoundException {
            // GIVEN
            var orderToUpdateDTO = new OrderDTO(1, BigDecimal.valueOf(100.00), "2022-03-18T12:24:47.241",
                    null, walterDTO, null);
            var orderInDb = new Order(1, BigDecimal.valueOf(100.00), LocalDateTime.parse("2022-03-18T12:24:47.241"),
                    null, walter, movieCertificate);
            // WHEN
            when(orderDAO.findById(1)).thenReturn(orderInDb);
            // THEN
            assertThrows(InvalidInputException.class, () -> orderService.updateOrder(orderToUpdateDTO, 1));
        }
    }

    @Nested
    @DisplayName("delete order tests")
    class deleteOrderTests {
        @Test
        @DisplayName("correctly delete order")
        void deleteOrderShouldCorrectlyRemoveOrderFromDb() throws InvalidInputException, NotFoundException {
            // GIVEN
            int id = 1;
            // WHEN
            orderService.delete(id);
            // THEN
            verify(orderDAO, times(1)).delete(id);
        }

        @Test
        @DisplayName("delete non existing order")
        void deleteNonExistingOrderShouldThrowException() throws NotFoundException {
            // GIVEN
            int id = 1;
            // WHEN
            doThrow(new NotFoundException("Item of id = " + id + " not found.")).when(orderDAO).delete(id);
            // THEN
            assertThrows(NotFoundException.class, () -> orderService.delete(id));
        }

        @Test
        @DisplayName("delete negative id order")
        void deleteNegativeIdOrderShouldThrowException() {
            // GIVEN
            int id = -1;
            // WHEN

            // THEN
            assertThrows(InvalidInputException.class, () -> orderService.delete(id));
        }
    }





    }

