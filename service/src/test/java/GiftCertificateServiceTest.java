import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.impl.GiftCertificateDAOImpl;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exceptions.InvalidInputException;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.pagination.Page;
import com.epam.esm.service.GiftCertificateService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
            int id = 1;
            // WHEN
            when(giftCertificateDAO.findById(id)).thenThrow(NotFoundException.class);
            // THEN
            assertThrows(NotFoundException.class, () -> giftCertificateService.getById(id));
        }

        @Test
        @DisplayName("given id is invalid")
        void findByIdShouldThrowExceptionIfIdIsNegative() {
            // GIVEN
            int id = -2;
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
        void findAllShouldReturnListOfAllGiftCertificatesInDb() {
            // GIVEN
            int pageNumber = 1;
            int pageSize = 10;
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
            Page<GiftCertificateDTO> page = new Page<>(pageNumber, pageSize, giftCertificatesDTO.size(), giftCertificatesDTO);
            // WHEN
            when(giftCertificateDAO.findAll(pageNumber, pageSize)).thenReturn(giftCertificates);
            when(giftCertificateMapper.toDTO(paintballCertificate)).thenReturn(paintballCertificateDTO);
            when(giftCertificateMapper.toDTO(aquaparkCertificate)).thenReturn(aquaparkCertificateDTO);
            // THEN
            assertEquals(page, giftCertificateService.getAll(pageNumber,pageSize));
        }

        @Test
        @DisplayName("find all when no records in db")
        void findAllShouldReturnEmptyListWhenNoDataInDatabase() {
            // GIVEN
            int pageNumber = 1;
            int pageSize = 10;
            Page<GiftCertificateDTO> page = new Page<>(pageNumber, pageSize, 0, new ArrayList<>());
            // WHEN
            when(giftCertificateDAO.findAll(pageNumber, pageSize)).thenReturn(new ArrayList<>());
            // THEN
            assertEquals(page, giftCertificateService.getAll(pageNumber,pageSize));
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
            tagsInDb.add(new Tag(1, "cinema", LocalDateTime.parse("2022-03-18T12:24:47.241")));
            tagsInDb.add(new Tag(2, "date", LocalDateTime.parse("2022-03-18T12:24:47.241")));
            tagsInDb.add(new Tag(3, "movie", LocalDateTime.parse("2022-03-18T12:24:47.241")));
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
            when(tagDAO.getByName("cinema")).thenReturn(new Tag(1, "cinema", LocalDateTime.parse("2022-03-18T12:24:47.241") ));
            when(tagDAO.getByName("date")).thenReturn(new Tag(2, "date", LocalDateTime.parse("2022-03-18T12:24:47.241")));
            when(tagDAO.getByName("movie")).thenReturn(new Tag(3, "movie", LocalDateTime.parse("2022-03-18T12:24:47.241")));
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
            var existingInDbMovie = new Tag(29, "movie", LocalDateTime.parse("2022-03-18T12:24:47.241"));
            var existingInDbDate = new Tag(7, "date", LocalDateTime.parse("2022-03-18T12:24:47.241"));
            Set<Tag> tagsInserted = new HashSet<>();
            tagsInserted.add(new Tag(45, "cinema", LocalDateTime.parse("2022-03-18T12:24:47.241")));
            tagsInserted.add(new Tag(29, "movie", LocalDateTime.parse("2022-03-18T12:24:47.241")));
            tagsInserted.add(new Tag(7, "date", LocalDateTime.parse("2022-03-18T12:24:47.241")));
            Set<TagDTO> tagsInsertedDTO = new HashSet<>();
            tagsInsertedDTO.add(new TagDTO(45, "cinema", "2022-03-18T12:24:47.241"));
            tagsInsertedDTO.add(new TagDTO(29, "movie", "2022-03-18T12:24:47.241"));
            tagsInsertedDTO.add(new TagDTO(7, "date","2022-03-18T12:24:47.241"));
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
            when(tagDAO.create(new Tag(null, "cinema", LocalDateTime.parse("2022-03-18T12:24:47.241")))).thenReturn(new Tag(45, "cinema", LocalDateTime.parse("2022-03-18T12:24:47.241")));
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
            var giftToBeInsertedDTO = new GiftCertificateDTO(0, null, "Movie session in Cinema City",
                    BigDecimal.valueOf(15.00), 200, "2022-03-18T12:24:47.241", "2022-06-18T12:24:47.241", new HashSet<>());
            // WHEN

            // THEN
            assertThrows(InvalidInputException.class, () -> giftCertificateService.addGiftCertificate(giftToBeInsertedDTO));
        }

        @Test
        @DisplayName("create gift certificate with null input")
        void createGiftCertificateWithNullInputShouldThrowException() {
            // GIVEN

            // WHEN

            // THEN
            assertThrows(InvalidInputException.class, () -> giftCertificateService.addGiftCertificate(null));
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
            var giftCertificateDTOInput = new GiftCertificateDTO(3, "Movie session",
                    "Movie session in Multikino", null, null,
                    null, null, null);
            var giftCertificate = new GiftCertificate(3, "Movie night",
                    "Movie session in Cinema City", BigDecimal.valueOf(15.00), 200,
                    LocalDateTime.parse("2022-03-18T12:24:47.241"),
                    LocalDateTime.parse("2022-06-18T12:24:47.241"), null);
            var giftCertificateUpdated = new GiftCertificate(3, "Movie session",
                    "Movie session in Multikino", BigDecimal.valueOf(15.00), 200,
                    null, null, null);
            var giftCertificateUpdatedDTO = new GiftCertificateDTO(3, "Movie session",
                    "Movie session in Multikino", BigDecimal.valueOf(15.00), 200,
                    null, null, null);
            // WHEN
            when(giftCertificateDAO.findById(giftCertificateDTOInput.getId())).thenReturn(giftCertificate);
            when(giftCertificateDAO.updateGiftCertificate(giftCertificateUpdated)).thenReturn(giftCertificateUpdated);
            when(giftCertificateMapper.toDTO(giftCertificateUpdated)).thenReturn(giftCertificateUpdatedDTO);
            // THEN
            assertEquals(giftCertificateUpdatedDTO, giftCertificateService.updateGiftCertificate(giftCertificate.getId(), giftCertificateDTOInput));
        }

        @Test
        @DisplayName("update non existing gift certificate")
        void updateNonExistingGiftCertificateShouldTrowException() throws NotFoundException {
            var giftCertificateDTO = new GiftCertificateDTO(3, "Movie night",
                    "Movie session in Cinema City", BigDecimal.valueOf(15.00), 200,
                    "2022-03-18T12:24:47.241", "2022-06-18T12:24:47.241", new HashSet<>());
            // WHEN
            when(giftCertificateDAO.findById(giftCertificateDTO.getId())).thenThrow(NotFoundException.class);
            // THEN
            assertThrows(NotFoundException.class, () -> giftCertificateService.updateGiftCertificate(3, giftCertificateDTO));
        }

        @Test
        @DisplayName("update gift certificate with empty input")
        void updateGiftCertificateWithEmptyInputShouldTrowException() throws NotFoundException {
            // GIVEN
            var giftToBeUpdatedDTO = new GiftCertificateDTO(1, "Movie night", "     ",
                    BigDecimal.valueOf(15.00), 200, "2022-03-18T12:24:47.241",
                    "2022-06-18T12:24:47.241", new HashSet<>());
            var giftInDatabase = new GiftCertificate(1, "Movie night", "Movie night description",
                    BigDecimal.valueOf(20.00), 200, LocalDateTime.parse("2022-03-18T12:24:47.241"),
                    LocalDateTime.parse("2022-06-18T12:24:47.241"), new HashSet<>());
            // WHEN
            when(giftCertificateDAO.findById(giftToBeUpdatedDTO.getId())).thenReturn(giftInDatabase);
            // THEN
            assertThrows(InvalidInputException.class, () -> giftCertificateService.updateGiftCertificate(giftInDatabase.getId(), giftToBeUpdatedDTO));
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
            doThrow(new NotFoundException("Item of id = " + giftCertificateIdToDelete + " not found.")).when(giftCertificateDAO).delete(giftCertificateIdToDelete);
            // THEN
            assertThrows(NotFoundException.class, () -> giftCertificateService.deleteGiftCertificate(giftCertificateIdToDelete));
        }
    }

    private Set<Tag> setUpTags(List<String> tagsNames, boolean isInserted) {
        Set<Tag> tags = new HashSet<>();
        var id = 1;
        Tag tag;
        for (String name : tagsNames) {
            if (isInserted) {
                tag = new Tag(id, name, LocalDateTime.parse("2022-03-18T12:24:47.241"));
                id++;
            } else {
                tag = new Tag(null, name, LocalDateTime.parse("2022-03-18T12:24:47.241"));
            }
            tags.add(tag);
        }
        return tags;
    }

    private Set<TagDTO> setUpTagsDTO(List<String> tagsNames, boolean isInserted) {
        Set<TagDTO> tags = new HashSet<>();
        var id = 1;
        TagDTO tag;
        for (String name : tagsNames) {
            if (isInserted) {
                tag = new TagDTO(id, name, "2022-03-18T12:24:47.241");
                id++;
            } else {
                tag = new TagDTO(null, name, "2022-03-18T12:24:47.241");
            }
            tags.add(tag);
        }
        return tags;
    }
}
