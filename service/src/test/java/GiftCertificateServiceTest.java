import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.impl.GiftCertificateDAOImpl;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exceptions.InvalidInputException;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.service.GiftCertificateService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceTest {

    @InjectMocks
    private GiftCertificateService giftCertificateService;

    @Mock
    private GiftCertificateDAOImpl giftCertificateDAO;

    @Mock
    private GiftCertificateMapper giftCertificateMapper;

    @Mock
    private TagDAO tagDAO;

    @Nested
    @DisplayName("find by id tests")
    class findByIdTest {

        @Test
        @DisplayName("gift certificate is correctly found")
        void findByIdShouldReturnCorrectGiftCertificate() throws NotFoundException, InvalidInputException {
            // GIVEN
            Integer id = 1;
            var giftCertificate = new GiftCertificate(id, "Paintball voucher",
                    "2 hours of paintball match in Paintball-World", BigDecimal.valueOf(49.99),
                    180, LocalDateTime.parse("2022-03-18T12:24:47.241"),
                    LocalDateTime.parse("2022-06-28T09:28:57.241"), new HashSet<>());
            var giftCertificateDTO = new GiftCertificateDTO(id, "Paintball voucher",
                    "2 hours of paintball match in Paintball-World", BigDecimal.valueOf(49.99),
                    180, null, null, new HashSet<>());
            // WHEN
            when(giftCertificateDAO.findById(id)).thenReturn(giftCertificate);
            when(giftCertificateMapper.toDTO(giftCertificate)).thenReturn(giftCertificateDTO);
            // THEN
            assertEquals(giftCertificateDTO, giftCertificateService.getById(id));
        }

        @Test
        @DisplayName("gift certificate of given id doesn't exist")
        void findByIdShouldThrowExceptionIfGiftCertificateDoesNotExist() throws NotFoundException {
            // GIVEN
            Integer id = 1;
            // WHEN
            when(giftCertificateDAO.findById(id)).thenThrow(EmptyResultDataAccessException.class);
            // THEN
            assertThrows(EmptyResultDataAccessException.class, () -> giftCertificateService.getById(id));
        }

        @Test
        @DisplayName("given id is invalid")
        void findByIdShouldThrowExceptionIfIdIsNegative() {
            // GIVEN
            Integer id = -2;
            // WHEN

            // THEN
            assertThrows(InvalidInputException.class, () -> giftCertificateService.getById(id));
        }
    }

    @Nested
    @DisplayName("find all tests")
    class findAll {
        @Test
        @DisplayName("correctly find all")
        void findAllShouldReturnListOfAllGiftCertificatesInDb() throws NotFoundException {
            // GIVEN
            var paintballCertificate = new GiftCertificate(1, "Paintball voucher",
                    "2 hours of paintball match in Paintball-World", BigDecimal.valueOf(49.99),
                    180,LocalDateTime.parse("2022-03-18T12:24:47.241"),
                    LocalDateTime.parse("2022-06-28T09:28:57.241"), new HashSet<>());
            var aquaparkCertificate = new GiftCertificate(2, "Aquapark Fun",
                    "3 hours in the biggest auqapark in PL", BigDecimal.valueOf(30.00),
                    90, LocalDateTime.parse("2022-03-18T12:24:47.241"),
                    null, new HashSet<>());
            List<GiftCertificate> giftCertificates = new ArrayList<>();
            giftCertificates.add(paintballCertificate);
            giftCertificates.add(aquaparkCertificate);
            GiftCertificateDTO paintballCertificateDTO = new GiftCertificateDTO(1, "Paintball voucher",
                    "2 hours of paintball match in Paintball-World", BigDecimal.valueOf(49.99),
                    180, "2022-03-18T12:24:47.241", "2022-06-28T09:28:57.241",new HashSet<>());
            GiftCertificateDTO aquaparkCertificateDTO = new GiftCertificateDTO(2, "Aquapark Fun",
                    "3 hours in the biggest auqapark in PL", BigDecimal.valueOf(30.00), 90,
                    "2022-03-18T12:24:47.241", null, new HashSet<>());
            List<GiftCertificateDTO> giftCertificatesDTO = new ArrayList<>();
            giftCertificatesDTO.add(paintballCertificateDTO);
            giftCertificatesDTO.add(aquaparkCertificateDTO);
            // WHEN
            when(giftCertificateDAO.findAll()).thenReturn(giftCertificates);
            when(giftCertificateMapper.toDTO(paintballCertificate)).thenReturn(paintballCertificateDTO);
            when(giftCertificateMapper.toDTO(aquaparkCertificate)).thenReturn(aquaparkCertificateDTO);
            // THEN
            assertEquals(giftCertificatesDTO, giftCertificateService.getAll(1,10));
        }

        @Test
        @DisplayName("find all when no records in db")
        void findAllShouldThrowExceptionWhenNoDataInDatabase() throws NotFoundException {
            // GIVEN

            // WHEN
            when(giftCertificateDAO.findAll()).thenThrow(NotFoundException.class);
            // THEN
            assertThrows(NotFoundException.class, () -> giftCertificateService.getAll(1, 10));
        }
    }

    @Nested
    @DisplayName("create gift certificate tests")
    class createGiftCertificateTests {

        @Test
        @DisplayName("gift certificate with non existing tags correctly inserted")
        void createGiftCertificateShouldInsertNewGiftCertificateWithNewTagsAndReturnInsertedGiftCertificate() throws InvalidInputException, NotFoundException {
            // GIVEN
            Set<Tag> tags = setUpTags(Arrays.asList("cinema", "date", "movie"), false);
            Set<TagDTO> tagsDTO = setUpTagsDTO(Arrays.asList("cinema", "date", "movie"), false);
            List<Tag> tagsInDb = new ArrayList<>();
            tagsInDb.add(new Tag(1, "cinema"));
            tagsInDb.add(new Tag(2, "date"));
            tagsInDb.add(new Tag(3, "movie"));
            var giftToBeInserted = new GiftCertificate(0, "Movie night",
                    "Movie session in Cinema City", BigDecimal.valueOf(15.00), 200,
                    LocalDateTime.parse("2022-03-18T12:24:47.241"),
                    LocalDateTime.parse("2022-06-18T12:24:47.241"), tags);
            var giftToBeInsertedDTO = new GiftCertificateDTO(0, "Movie night",
                    "Movie session in Cinema City", BigDecimal.valueOf(15.00), 200,
                    "2022-03-18T12:24:47.241", "2022-06-18T12:24:47.241", tagsDTO);
            Set<Tag> tagsInserted = setUpTags(Arrays.asList("cinema", "date", "movie"), true);
            Set<TagDTO> tagsInsertedDTO = setUpTagsDTO(Arrays.asList("cinema", "date", "movie"), true);
            var giftInserted = new GiftCertificate(3, "Movie night",
                    "Movie session in Cinema City", BigDecimal.valueOf(15.00), 200,
                    LocalDateTime.parse("2022-03-18T12:24:47.241"), LocalDateTime.parse("2022-06-18T12:24:47.241"), tagsInserted);
            var giftInsertedDTO = new GiftCertificateDTO(3, "Movie night",
                    "Movie session in Cinema City", BigDecimal.valueOf(15.00), 200,
                    "2022-03-18T12:24:47.241", "2022-06-18T12:24:47.241", tagsInsertedDTO);
            // WHEN
            when(giftCertificateMapper.toModel(giftToBeInsertedDTO)).thenReturn(giftToBeInserted);
            when(tagDAO.findAll()).thenReturn(tagsInDb);
            when(tagDAO.getByName("cinema")).thenReturn(new Tag(1, "cinema"));
            when(tagDAO.getByName("date")).thenReturn(new Tag(2, "date"));
            when(tagDAO.getByName("movie")).thenReturn(new Tag(3, "movie"));
            when(giftCertificateDAO.create(giftToBeInserted)).thenReturn(giftInserted);
            when(giftCertificateMapper.toDTO(giftInserted)).thenReturn(giftInsertedDTO);
            // THEN
            assertEquals(giftInsertedDTO, giftCertificateService.addGiftCertificate(giftToBeInsertedDTO));
        }

        @Test
        @DisplayName("gift certificate with existing tags correctly inserted")
        void createGiftCertificateShouldInsertNewGiftCertificateWithExistingTagsAndReturnInsertedGiftCertificate() throws NotFoundException, InvalidInputException {
            // GIVEN
            Set<Tag> tags = setUpTags(Arrays.asList("cinema", "date", "movie"), false);
            Set<TagDTO> tagsDTO = setUpTagsDTO(Arrays.asList("cinema", "date", "movie"), false);
            var giftToBeInserted = new GiftCertificate(0, "Movie night",
                    "Movie session in Cinema City", BigDecimal.valueOf(15.00), 200,
                    LocalDateTime.parse("2022-03-18T12:24:47.241"),
                    LocalDateTime.parse("2022-06-18T12:24:47.241"), tags);
            var giftToBeInsertedDTO = new GiftCertificateDTO(0, "Movie night",
                    "Movie session in Cinema City", BigDecimal.valueOf(15.00), 200,
                    "2022-03-18T12:24:47.241", "2022-06-18T12:24:47.241", tagsDTO);
            var existingInDbMovie = new Tag(29, "movie");
            var existingInDbDate = new Tag(7, "date");
            Set<Tag> tagsInserted = new HashSet<>();
            tagsInserted.add(new Tag(45, "cinema"));
            tagsInserted.add(new Tag(29, "movie"));
            tagsInserted.add(new Tag(7, "date"));
            Set<TagDTO> tagsInsertedDTO = new HashSet<>();
            tagsInsertedDTO.add(new TagDTO(45, "cinema"));
            tagsInsertedDTO.add(new TagDTO(29, "movie"));
            tagsInsertedDTO.add(new TagDTO(7, "date"));
            var giftInserted = new GiftCertificate(3, "Movie night",
                    "Movie session in Cinema City", BigDecimal.valueOf(15.00), 200,
                    LocalDateTime.parse("2022-03-18T12:24:47.241"), LocalDateTime.parse("2022-06-18T12:24:47.241"), tagsInserted);
            var giftInsertedDTO = new GiftCertificateDTO(3, "Movie night",
                    "Movie session in Cinema City", BigDecimal.valueOf(15.00), 200,
                    "2022-03-18T12:24:47.241", "2022-06-18T12:24:47.241", tagsInsertedDTO);
            // WHEN
            when(giftCertificateMapper.toModel(giftToBeInsertedDTO)).thenReturn(giftToBeInserted);
            when(tagDAO.findAll()).thenReturn(Arrays.asList(existingInDbMovie, existingInDbDate));
            when(giftCertificateDAO.create(giftToBeInserted)).thenReturn(giftInserted);
            when(tagDAO.create(new Tag(null, "cinema"))).thenReturn(new Tag(45, "cinema"));
            when(tagDAO.getByName("date")).thenReturn(existingInDbDate);
            when(tagDAO.getByName("movie")).thenReturn(existingInDbMovie);
            when(giftCertificateMapper.toDTO(giftInserted)).thenReturn(giftInsertedDTO);
            // THEN
            assertEquals(giftInsertedDTO, giftCertificateService.addGiftCertificate(giftToBeInsertedDTO));
        }

        @Test
        @DisplayName("create gift certificate with invalid input")
        void createGiftCertificateWithInvalidInputShouldThrowException() {
            // GIVEN
            String name = null;
            var giftToBeInsertedDTO = new GiftCertificateDTO(0, name, "Movie session in Cinema City",
                    BigDecimal.valueOf(15.00), 200, "2022-03-18T12:24:47.241", "2022-06-18T12:24:47.241", new HashSet<>());
            // WHEN

            // THEN
            assertThrows(InvalidInputException.class, () -> giftCertificateService.addGiftCertificate(giftToBeInsertedDTO));
        }


        @Test
        @DisplayName("create gift certificate with empty input")
        void createGiftCertificateWithEmptyInputShouldThrowException() {
            // GIVEN
            var giftToBeInsertedDTO = new GiftCertificateDTO(0, "Movie night", "   ",
                    BigDecimal.valueOf(15.00), 200, "2022-03-18T12:24:47.241", "2022-06-18T12:24:47.241", new HashSet<>());
            // WHEN

            // THEN
            assertThrows(InvalidInputException.class, () -> giftCertificateService.addGiftCertificate(giftToBeInsertedDTO));
        }
    }

    @Nested
    @DisplayName("update gift certificate tests")
    class updateGiftCertificateTests {

        @Test
        @DisplayName("gift certificate correctly updated")
        void updateGiftCertificateShouldCorrectlyUpdateSpecifiedParam() throws NotFoundException, InvalidInputException {
            // GIVEN
            var giftCertificateDTO = new GiftCertificateDTO(3, "Movie session",
                    "Movie session in Multikino", BigDecimal.valueOf(30.00), 250,
                    null, null, null);
            var giftCertificate = new GiftCertificate(3, "Movie night",
                    "Movie session in Cinema City", BigDecimal.valueOf(15.00), 200,
                    LocalDateTime.parse("2022-03-18T12:24:47.241"),
                    LocalDateTime.parse("2022-06-18T12:24:47.241"), null);
            // WHEN
            when(giftCertificateDAO.findById(giftCertificateDTO.getId())).thenReturn(giftCertificate);
            giftCertificateService.updateGiftCertificate(giftCertificateDTO);
            // THEN
            verify(giftCertificateDAO, times(1)).updateGiftCertificate(giftCertificate);
        }

        @Test
        @DisplayName("update non existing gift certificate")
        void updateNonExistingGiftCertificateShouldTrowException() throws NotFoundException {
            var giftCertificateDTO = new GiftCertificateDTO(3, "Movie night",
                    "Movie session in Cinema City", BigDecimal.valueOf(15.00), 200,
                    "2022-03-18T12:24:47.241", "2022-06-18T12:24:47.241", new HashSet<>());
            // WHEN
            when(giftCertificateDAO.findById(giftCertificateDTO.getId())).thenReturn(null);
            // THEN
            assertThrows(NotFoundException.class, () -> giftCertificateService.updateGiftCertificate(giftCertificateDTO));
        }

        @Test
        @DisplayName("update gift certificate with invalid input")
        void updateGiftCertificateWithInvalidInputShouldTrowException() {
            // GIVEN
            String name = null;
            var giftToBeUpdatedDTO = new GiftCertificateDTO(0, name, "Movie session in Cinema City",
                    BigDecimal.valueOf(15.00), 200, "2022-03-18T12:24:47.241",
                    "2022-06-18T12:24:47.241", new HashSet<>());
            // WHEN

            // THEN
            assertThrows(InvalidInputException.class, () -> giftCertificateService.addGiftCertificate(giftToBeUpdatedDTO));
        }

        @Test
        @DisplayName("update gift certificate with empty input")
        void updateGiftCertificateWithEmptyInputShouldTrowException() {
            // GIVEN
            var giftToBeUpdatedDTO = new GiftCertificateDTO(0, "Movie night", "     ",
                    BigDecimal.valueOf(15.00), 200, "2022-03-18T12:24:47.241",
                    "2022-06-18T12:24:47.241", new HashSet<>());
            // WHEN

            // THEN
            assertThrows(InvalidInputException.class, () -> giftCertificateService.addGiftCertificate(giftToBeUpdatedDTO));
        }
    }

    @Nested
    @DisplayName("delete gift certificate tests")
    class deleteGiftCertificateTests {

        @Test
        @DisplayName("gift certificate correctly removed")
        void deleteGiftCertificateShouldCorrectlyRemoveGiftCertificate() throws NotFoundException {
            // GIVEN
            var giftCertificateIdToDelete = 5;
            // WHEN
            giftCertificateService.deleteGiftCertificate(giftCertificateIdToDelete);
            // THEN
            verify(giftCertificateDAO, times(1)).delete(giftCertificateIdToDelete);
        }

        @Test
        @DisplayName("delete non existing gift certificate")
        void deleteNonExistingGiftCertificateShouldThrowException() throws NotFoundException {
            // GIVEN
            var giftCertificateIdToDelete = 5;
            // WHEN
            doThrow(new EmptyResultDataAccessException(0)).when(giftCertificateDAO).delete(giftCertificateIdToDelete);
            // THEN
            assertThrows(EmptyResultDataAccessException.class, () -> giftCertificateService.deleteGiftCertificate(giftCertificateIdToDelete));
        }
    }

    private Set<Tag> setUpTags(List<String> tagsNames, boolean isInserted) {
        Set<Tag> tags = new HashSet<>();
        var id = 1;
        Tag tag = null;
        for (String name : tagsNames) {
            if (isInserted) {
                tag = new Tag(id, name);
                id++;
            } else {
                tag = new Tag(null, name);
            }
            tags.add(tag);
        }
        return tags;
    }

    private Set<TagDTO> setUpTagsDTO(List<String> tagsNames, boolean isInserted) {
        Set<TagDTO> tags = new HashSet<>();
        var id = 1;
        TagDTO tag = null;
        for (String name : tagsNames) {
            if (isInserted) {
                tag = new TagDTO(id, name);
                id++;
            } else {
                tag = new TagDTO(null, name);
            }
            tags.add(tag);
        }
        return tags;
    }

}
