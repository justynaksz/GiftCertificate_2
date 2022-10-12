import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Order;
import com.epam.esm.model.User;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;

class OrderMapperTest {

    private final SoftAssertions softAssertions = new SoftAssertions();

    private final Integer giftCertId = 1;
    private final String name = "Aquapark Fun";
    private final String description = "2 hours of paintball match in Paintball-World";
    private final BigDecimal price = BigDecimal.valueOf(49.99);
    private final Integer duration = 180;
    private final String giftCertCreateDate = "2022-03-18T12:24:47.241";
    private final String giftCertLastUpdateDate = "2022-06-28T09:28:57.241";


    @Test
    @DisplayName("to DTO test")
    void toDTOTest() {
        var userMapper = new UserMapper();
        var tagMapper = new TagMapper();
        var giftCertificateMapper = new GiftCertificateMapper(tagMapper);
        var orderMapper = new OrderMapper(userMapper, giftCertificateMapper);
        var id = 1;
        var cost = BigDecimal.valueOf(100.00);
        var createDate = "2022-03-18T12:24:47.241";
        var user = createUser();
        var userDTO = createUserDTO();
        var giftCertificate = createGiftCertificate();
        var giftCertificateDTO = createGiftCertificateDTO();
        // WHEN
        var order = new Order(id, cost, LocalDateTime.parse(createDate), null, user, giftCertificate);
        var orderDTO = new OrderDTO(id, cost, createDate, null, userDTO, giftCertificateDTO);
        // THEN
        softAssertions.assertThat(orderMapper.toDTO(order).getId()).isEqualTo(orderDTO.getId());
        softAssertions.assertThat(orderMapper.toDTO(order).getCost()).isEqualTo(orderDTO.getCost());
        softAssertions.assertThat(orderMapper.toDTO(order).getCreateDate()).isEqualTo(orderDTO.getCreateDate());
        softAssertions.assertThat(orderMapper.toDTO(order).getLastUpdateDate()).isEqualTo(orderDTO.getLastUpdateDate());
        softAssertions.assertThat(orderMapper.toDTO(order).getUser()).isEqualTo(orderDTO.getUser());
        softAssertions.assertThat(orderMapper.toDTO(order).getGiftCertificate()).isEqualTo(orderDTO.getGiftCertificate());
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("to Model test")
    void toModelTest() {
        var userMapper = new UserMapper();
        var tagMapper = new TagMapper();
        var giftCertificateMapper = new GiftCertificateMapper(tagMapper);
        var orderMapper = new OrderMapper(userMapper, giftCertificateMapper);
        var id = 1;
        var cost = BigDecimal.valueOf(100.00);
        var createDate = "2022-03-18T12:24:47.241";
        var user = createUser();
        var userDTO = createUserDTO();
        var giftCertificate = createGiftCertificate();
        var giftCertificateDTO = createGiftCertificateDTO();
        // WHEN
        var order = new Order(id, cost, LocalDateTime.parse(createDate), null, user, giftCertificate);
        var orderDTO = new OrderDTO(id, cost, createDate, null, userDTO, giftCertificateDTO);
        // THEN
        softAssertions.assertThat(orderMapper.toModel(orderDTO).getId()).isEqualTo(order.getId());
        softAssertions.assertThat(orderMapper.toModel(orderDTO).getCost()).isEqualTo(order.getCost());
        softAssertions.assertThat(orderMapper.toModel(orderDTO).getCreateDate()).isEqualTo(order.getCreateDate());
        softAssertions.assertThat(orderMapper.toModel(orderDTO).getLastUpdateDate()).isEqualTo(order.getLastUpdateDate());
        softAssertions.assertThat(orderMapper.toModel(orderDTO).getShopUser()).isEqualTo(order.getShopUser());
        softAssertions.assertThat(orderMapper.toModel(orderDTO).getGiftCertificate()).isEqualTo(order.getGiftCertificate());
        softAssertions.assertAll();
    }

    private GiftCertificate createGiftCertificate() {
        return new GiftCertificate(giftCertId, name, description, price, duration, LocalDateTime.parse(giftCertCreateDate), LocalDateTime.parse(giftCertLastUpdateDate), new HashSet<>());
    }

    private GiftCertificateDTO createGiftCertificateDTO() {
        return new GiftCertificateDTO(giftCertId, name, description, price, duration, giftCertCreateDate, giftCertLastUpdateDate, new HashSet<>());
    }

    private User createUser() {
        return new User(5, "Ben", LocalDateTime.parse("2022-03-18T12:24:47.241"));
    }

    private UserDTO createUserDTO() {
        return new UserDTO(5, "Ben", "2022-03-18T12:24:47.241");
    }
}
