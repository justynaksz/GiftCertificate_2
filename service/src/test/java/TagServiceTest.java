import com.epam.esm.dao.impl.TagDAOImpl;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exceptions.AlreadyExistException;
import com.epam.esm.exceptions.InvalidInputException;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.Tag;
import com.epam.esm.pagination.Page;
import com.epam.esm.service.TagService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @InjectMocks
    private TagService tagService;

    @Mock
    private TagDAOImpl tagDAO;

    @Mock
    private TagMapper tagMapper;

    @Nested
    @DisplayName("find by id tests")
    class findByIdTest {

        @Test
        @DisplayName("tag is correctly found")
        void findByIdShouldReturnCorrectTag() throws NotFoundException, InvalidInputException {
            // GIVEN
            var id = 2;
            var name = "fun";
            var funTag = new Tag (id, name, LocalDateTime.parse("2022-03-18T12:24:47.241"));
            var funDTO = new TagDTO (id, name, "2022-03-18T12:24:47.241");
            // WHEN
            when(tagDAO.findById(id)).thenReturn(funTag);
            when(tagMapper.toDTO(funTag)).thenReturn(funDTO);
            // THEN
            assertEquals(funDTO, tagService.getById(id));
        }

        @Test
        @DisplayName("tag of given id doesn't exist")
        void findByIdShouldThrowExceptionIfTagDoesNotExist() throws NotFoundException {
            // GIVEN
            var id = 1;
            // WHEN
            when(tagDAO.findById(id)).thenThrow(EmptyResultDataAccessException.class);
            // THEN
            assertThrows(EmptyResultDataAccessException.class, () -> tagService.getById(id));
        }

        @Test
        @DisplayName("given id is invalid")
        void findByIdShouldThrowExceptionIfIdIsNegative() {
            // GIVEN
            var id = -2;
            // WHEN

            // THEN
            assertThrows(InvalidInputException.class, () -> tagService.getById(id));
        }
    }

    @Nested
    @DisplayName("find all tests")
    class findAllTest {
        @Test
        @DisplayName("find all test")
        void findAllShouldReturnAllTagsFromDb() {
            // GIVEN
            int pageNumber = 1;
            int pageSize = 10;

            var funId = 1;
            var photoId = 2;
            var kidsId = 3;

            var fun = "fun";
            var photo = "photo";
            var kids = "kids";

            var funTag = new Tag (funId, fun, LocalDateTime.parse("2022-03-18T12:24:47.241"));
            var photoTag = new Tag (photoId, photo, LocalDateTime.parse("2022-03-18T12:24:47.241"));
            var kidsTag = new Tag (kidsId, kids, LocalDateTime.parse("2022-03-18T12:24:47.241"));

            List<Tag> tags = new ArrayList<>();
            tags.add(funTag);
            tags.add(photoTag);
            tags.add(kidsTag);

            var funDTO = new TagDTO (funId, fun, "2022-03-18T12:24:47.241");
            var photoDTO = new TagDTO (photoId, photo, "2022-03-18T12:24:47.241");
            var kidsDTO = new TagDTO (kidsId, kids, "2022-03-18T12:24:47.241");

            List<TagDTO> tagsDTO = new ArrayList<>();
            tagsDTO.add(funDTO);
            tagsDTO.add(photoDTO);
            tagsDTO.add(kidsDTO);

            Page<TagDTO> page = new Page<>(pageNumber, pageSize, tagsDTO.size(), tagsDTO);
            // WHEN
            when(tagDAO.findAll(pageNumber, pageSize)).thenReturn(tags);
            when(tagMapper.toDTO(kidsTag)).thenReturn(kidsDTO);
            when(tagMapper.toDTO(photoTag)).thenReturn(photoDTO);
            when(tagMapper.toDTO(funTag)).thenReturn(funDTO);
            // THEN
            assertEquals(page, tagService.getAll(pageNumber,pageSize));
        }

        @Test
        @DisplayName("find all when no record in db")
        void findAllWhenNoRecordsInDatabase() {
            // GIVEN
            int pageNumber = 1;
            int pageSize = 10;
            Page<TagDTO> page = new Page<>(pageNumber, pageSize, 0, new ArrayList<>());
            // WHEN
            when(tagDAO.findAll(pageNumber, pageSize)).thenReturn(new ArrayList<>());
            // THEN
            assertEquals(page, tagService.getAll(pageNumber, pageSize));
        }
    }


    @Nested
    @DisplayName("find by name tests")
    class findByName {

        @Test
        @DisplayName("tag is correctly found")
        void findByNameShouldReturnCorrectTag() throws NotFoundException, InvalidInputException {
            // GIVEN
            var fun = "fun";
            var id = 1;
            var funTag = new Tag(id, fun, LocalDateTime.parse("2022-03-18T12:24:47.241"));
            var funDTO = new TagDTO(id, fun, "2022-03-18T12:24:47.241");
            // WHEN
            when(tagDAO.getByName(fun)).thenReturn(funTag);
            when(tagMapper.toDTO(funTag)).thenReturn(funDTO);
            // THEN
            assertEquals(funDTO, tagService.getByName(fun));
        }

        @Test
        @DisplayName("find tag with empty name")
        void findByNameShouldThrowExceptionIfNameIsEmpty() {
            // GIVEN
            var name = "  ";
            // WHEN

            // THEN
            assertThrows(InvalidInputException.class, () -> tagService.getByName(name));
        }

        @Test
        @DisplayName("find tag with null name")
        void findByNameShouldThrowExceptionIfNameIsNull() {
            // GIVEN

            // WHEN

            // THEN
            assertThrows(InvalidInputException.class, () -> tagService.getByName(null));
        }

        @Test
        @DisplayName("tag of given name doesn't exist")
        void findByNameShouldThrowExceptionIfTagDoesNotExist() throws NotFoundException {
            // GIVEN
            var name = "fun";
            // WHEN
            when(tagDAO.getByName(name)).thenThrow(EmptyResultDataAccessException.class);
            // THEN
            assertThrows(EmptyResultDataAccessException.class, () -> tagService.getByName(name));
        }
    }

    @Nested
    @DisplayName("find by params tests")
    class findByParamsTests {
        @Test
        @DisplayName("tags correctly found by name")
        void getByParamCorrectlyFoundByName() throws InvalidInputException {
            // GIVEN
            int pageSize = 10;
            int pageNumber = 1;
            var funTag = new Tag (25, "funDay", LocalDateTime.parse("2022-03-18T12:24:47.241"));
            var outdoorFun = new Tag (578, "outdoor fun", LocalDateTime.parse("2022-03-18T12:24:47.241"));
            var funTagDTO = new TagDTO (25, "funDay", "2022-03-18T12:24:47.241");
            var outdoorFunDTO = new TagDTO (578, "outdoor fun", "2022-03-18T12:24:47.241");
            List<Tag> tags = new ArrayList<>();
            List<TagDTO> tagsDTO = new ArrayList<>();
            tags.add(funTag);
            tags.add(outdoorFun);
            tagsDTO.add(funTagDTO);
            tagsDTO.add(outdoorFunDTO);
            Page<TagDTO> page = new Page<>(pageNumber, pageSize, tagsDTO.size(), tagsDTO);
            // WHEN
            when(tagDAO.getTagsByParam(pageNumber,pageSize,null, "fun")).thenReturn(tags);
            when(tagMapper.toDTO(funTag)).thenReturn(funTagDTO);
            when(tagMapper.toDTO(outdoorFun)).thenReturn(outdoorFunDTO);
            // THEN
            assertEquals(page, tagService.getTagsByParam(pageNumber,pageSize,null, "fun"));
        }

        @Test
        @DisplayName("invalid name")
        void getByParamWithInvalidName() {
            // GIVEN

            // WHEN

            // THEN
            assertThrows(InvalidInputException.class, () -> tagService.getTagsByParam(1,10,null, "  "));
        }

        @Test
        @DisplayName("invalid id")
        void getByParamWithInvalidId(){
            // GIVEN

            // WHEN

            // THEN
            assertThrows(InvalidInputException.class, () -> tagService.getTagsByParam(1,10,-30, "test"));
        }

        @Test
        @DisplayName("no params return all tags")
        void getByParamWithoutParamsShouldReturnAllTags() throws InvalidInputException {
            // GIVEN
            int pageSize = 10;
            int pageNumber = 1;
            var funTag = new Tag (25, "funDay", LocalDateTime.parse("2022-03-18T12:24:47.241"));
            var outdoorFun = new Tag (578, "outdoor", LocalDateTime.parse("2022-03-18T12:24:47.241"));
            var funTagDTO = new TagDTO (25, "funDay", "2022-03-18T12:24:47.241");
            var outdoorFunDTO = new TagDTO (578, "outdoor", "2022-03-18T12:24:47.241");
            List<Tag> tags = new ArrayList<>();
            List<TagDTO> tagsDTO = new ArrayList<>();
            tags.add(funTag);
            tags.add(outdoorFun);
            tagsDTO.add(funTagDTO);
            tagsDTO.add(outdoorFunDTO);
            Page<TagDTO> page = new Page<>(pageNumber, pageSize, tagsDTO.size(), tagsDTO);
            // WHEN
            when(tagDAO.getTagsByParam(pageNumber,pageSize,null, null)).thenReturn(tags);
            when(tagMapper.toDTO(funTag)).thenReturn(funTagDTO);
            when(tagMapper.toDTO(outdoorFun)).thenReturn(outdoorFunDTO);
            // THEN
            assertEquals(page, tagService.getTagsByParam(pageNumber,pageSize,null, null));
        }
    }

    @Nested
    @DisplayName("create tag tests")
    class createTagTests {
        @Test
        @DisplayName("tag correctly inserted")
        void createTagShouldInsertNewTagAndReturnInsertedTag() throws InvalidInputException, AlreadyExistException {
            // GIVEN
            var tagToBeInserted = new Tag();
            tagToBeInserted.setName("cool");
            var tagDTOToBeInserted = new TagDTO(null, "cool", "2022-03-18T12:24:47.241");
            var insertedTag = new Tag();
            insertedTag.setName("cool");
            insertedTag.setId(1);
            var insertedTagDTO = new TagDTO(1, "cool", "2022-03-18T12:24:47.241");
            // WHEN
            when(tagMapper.toModel(tagDTOToBeInserted)).thenReturn(tagToBeInserted);
            when(tagDAO.create(tagToBeInserted)).thenReturn(insertedTag);
            when(tagMapper.toDTO(insertedTag)).thenReturn(insertedTagDTO);
            // THEN
            assertEquals(insertedTagDTO, tagService.create(tagDTOToBeInserted));
        }

        @Test
        @DisplayName("create tag with name which already exist in db")
        void createTagWithNameThatAlreadyExistInDbShouldThrowException() {
            // GIVEN
            var tagToBeInserted = new Tag();
            tagToBeInserted.setName("cool");
            var tagDTOToBeInserted = new TagDTO(null, "cool", "2022-03-18T12:24:47.241");
            List<Tag> tagsInDb = new ArrayList<>();
            tagsInDb.add(tagToBeInserted);
            // WHEN
            when(tagMapper.toModel(tagDTOToBeInserted)).thenReturn(tagToBeInserted);
            when(tagDAO.findAll()).thenReturn(tagsInDb);
            // THEN
            assertThrows(AlreadyExistException.class, () -> tagService.create(tagDTOToBeInserted));
        }

        @Test
        @DisplayName("create tag with invalid name")
        void createTagWithInvalidNameShouldThrowException() {
            // GIVEN
            var invalidTag = new TagDTO(1, null, "2022-03-18T12:24:47.241");
            // WHEN

            // THEN
            assertThrows(InvalidInputException.class, () -> tagService.create(invalidTag));
        }

        @Test
        @DisplayName("create tag with empty name")
        void createTagWithEmptyNameShouldThrowException() {
            // GIVEN
            var name = "  ";
            var invalidTag = new TagDTO(1, name, "2022-03-18T12:24:47.241");
            // WHEN

            // THEN
            assertThrows(InvalidInputException.class, () -> tagService.create(invalidTag));
        }
    }

    @Nested
    @DisplayName("delete tag tests")
    class deleteTagTest {
        @Test
        @DisplayName("tag is correctly removed")
        void deleteTagShouldRemoveTagOfGivenIdFromDatabase() throws NotFoundException, InvalidInputException {
            // GIVEN
            var id = 1;
            // WHEN
            tagService.delete(id);
            // THEN
            verify(tagDAO, times(1)).delete(id);
        }

        @Test
        @DisplayName("tag of given name doesn't exist")
        void deleteTagOfNonExistingIdShouldThrowException() throws NotFoundException {
            // GIVEN
            var id = 2;
            // WHEN
            doThrow(new EmptyResultDataAccessException(0)).when(tagDAO).delete(id);
            // THEN
            assertThrows(EmptyResultDataAccessException.class, () -> tagService.delete(id));
        }
    }
}
