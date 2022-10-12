import com.epam.esm.dao.UserDAO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exceptions.InvalidInputException;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.model.User;
import com.epam.esm.pagination.Page;
import com.epam.esm.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserDAO userDAO;

    @Mock
    private UserMapper userMapper;

    @Nested
    @DisplayName("find by id test")
    class findByIdTests {

        @Test
        @DisplayName("user is correctly found")
        void findByIdShouldReturnCorrectUser() throws NotFoundException, InvalidInputException {
            // GIVEN
            var id = 1;
            var nickname = "Walter";
            var createDate = "2022-03-18T12:24:47.241";
            var user = new User(id, nickname, LocalDateTime.parse(createDate));
            var userDTO = new UserDTO(id, nickname, createDate);
            // WHEN
            when(userDAO.findById(id)).thenReturn(user);
            when(userMapper.toDTO(user)).thenReturn(userDTO);
            // THEN
            assertEquals(userDTO, userService.getById(id));
        }

        @Test
        @DisplayName("user of given id doesn't exist")
        void findByIdShouldThrowExceptionIfUserDoesNotExist() throws NotFoundException {
            // GIVEN
            var id = 1;
            // WHEN
            when(userDAO.findById(id)).thenThrow(NotFoundException.class);
            // THEN
            assertThrows(NotFoundException.class, () -> userService.getById(id));
        }


        @Test
        @DisplayName("given id is invalid")
        void findByIdShouldThrowExceptionIfIdIsNegative() {
            // GIVEN
            var id = -1;
            // WHEN

            // THEN
            assertThrows(InvalidInputException.class, () -> userService.getById(id));
        }
    }

    @Nested
    @DisplayName("find all tests")
    class findAllTests {
        @Test
        @DisplayName("find all test")
        void findAllShouldReturnUsersFromDb() {
            // GIVEN
            int pageNumber = 1;
            int pageSize = 10;

            var id = 1;
            var nickname = "Walter";
            var createDate = "2022-03-18T12:24:47.241";
            var user = new User(id, nickname, LocalDateTime.parse(createDate));
            var userDTO = new UserDTO(id, nickname, createDate);

            var id2 = 2;
            var nickname2 = "Pinkman";
            var user2 = new User(id2, nickname2, LocalDateTime.parse(createDate));
            var userDTO2 = new UserDTO(id2, nickname2, createDate);

            List<User> users = new ArrayList<>();
            users.add(user);
            users.add(user2);

            List<UserDTO> userDTOS = new ArrayList<>();
            userDTOS.add(userDTO);
            userDTOS.add(userDTO2);

            Page<UserDTO> page = new Page<>(pageNumber, pageSize, userDTOS.size(), userDTOS);
            // WHEN
            when(userDAO.findAll(pageNumber, pageSize)).thenReturn(users);
            when(userMapper.toDTO(user)).thenReturn(userDTO);
            when(userMapper.toDTO(user2)).thenReturn(userDTO2);
            // THEN
            assertEquals(page, userService.getAll(pageNumber, pageSize));
        }

        @Test
        @DisplayName("find all when no record in db")
        void findAllWhenNoRecordsInDatabase() {
            // GIVEN
            int pageNumber = 1;
            int pageSize = 10;
            Page<UserDTO> page = new Page<>(pageNumber, pageSize, 0, new ArrayList<>());
            // WHEN
            when(userDAO.findAll(pageNumber, pageSize)).thenReturn(new ArrayList<>());
            // THEN
            assertEquals(page, userService.getAll(pageNumber, pageSize));
        }
    }

    @Nested
    @DisplayName("find by name tests")
    class findByNameTests {
        @Test
        @DisplayName("user correctly found by nickname")
        void findByNicknameGetCorrectUser() throws NotFoundException, InvalidInputException {
            // GIVEN
            var id = 1;
            var nickname = "Walter";
            var createDate = "2022-03-18T12:24:47.241";
            var user = new User(id, nickname, LocalDateTime.parse(createDate));
            var userDTO = new UserDTO(id, nickname, createDate);
            // WHEN
            when(userDAO.getByNickname(nickname)).thenReturn(user);
            when(userMapper.toDTO(user)).thenReturn(userDTO);
            // THEN
            assertEquals(userDTO, userService.getByName(nickname));
        }

        @Test
        @DisplayName("no user of given nickname found")
        void findByNicknameThrowsExceptionIfUserNotFound() throws NotFoundException {
            // GIVEN
            var nickname = "Walter";
            // WHEN
            when(userDAO.getByNickname(nickname)).thenThrow(NotFoundException.class);
            // THEN
            assertThrows(NotFoundException.class, () -> userService.getByName(nickname));
        }

        @Test
        @DisplayName("blank nickname")
        void findByNicknameThrowsExceptionIfBlankName() {
            // GIVEN
            var nickname = "     ";
            // WHEN

            // THEN
            assertThrows(InvalidInputException.class, () -> userService.getByName(nickname));
        }

        @Test
        @DisplayName("null nickname")
        void findByNicknameThrowsExceptionIfNullName() {
            // GIVEN

            // WHEN

            // THEN
            assertThrows(InvalidInputException.class, () -> userService.getByName(null));
        }
    }
}
