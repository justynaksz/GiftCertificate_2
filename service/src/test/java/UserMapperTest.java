import com.epam.esm.dto.UserDTO;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.model.User;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class UserMapperTest {

    private final SoftAssertions softAssertions = new SoftAssertions();

    @Test
    @DisplayName("to DTO test")
    void toDTOTest() {
        // GIVEN
        var userMapper = new UserMapper();
        var id = 1;
        var nickname = "Walter";
        var createDate = "2022-03-18T12:24:47.241";
        // WHEN
        var user = new User(id, nickname, LocalDateTime.parse(createDate));
        var userDTO = new UserDTO(id, nickname, createDate);
        // THEN
        softAssertions.assertThat(userMapper.toDTO(user).getId()).isEqualTo(userDTO.getId());
        softAssertions.assertThat(userMapper.toDTO(user).getName()).isEqualTo(userDTO.getName());
        softAssertions.assertThat(userMapper.toDTO(user).getCreateDate()).isEqualTo(userDTO.getCreateDate());
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("to Model test")
    void toModelTest() {
        // GIVEN
        var userMapper = new UserMapper();
        var id = 1;
        var nickname = "Walter";
        var createDate = "2022-03-18T12:24:47.241";
        // WHEN
        var user = new User(id, nickname, LocalDateTime.parse(createDate));
        var userDTO = new UserDTO(id, nickname, createDate);
        // THEN
        softAssertions.assertThat(userMapper.toModel(userDTO).getId()).isEqualTo(user.getId());
        softAssertions.assertThat(userMapper.toModel(userDTO).getNickname()).isEqualTo(user.getNickname());
        softAssertions.assertThat(userMapper.toModel(userDTO).getCreateDate()).isEqualTo(user.getCreateDate());
        softAssertions.assertAll();
    }
}
