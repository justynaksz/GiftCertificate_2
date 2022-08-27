import com.epam.esm.dao.impl.TagDAOImpl;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exceptions.AlreadyExistException;
import com.epam.esm.exceptions.InvalidInputException;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.Tag;
import com.epam.esm.service.TagService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

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
        void findByIdShouldReturnCorrectTag() {
            // GIVEN
            int id = 2;
            String name = "fun";
            Tag funTag = setUpTag(id, name);
            TagDTO funDTO = setUpTagDTO(id, name);
            // WHEN
            when(tagDAO.findById(id)).thenReturn(funTag);
            when(tagMapper.toDTO(funTag)).thenReturn(funDTO);
            // THEN
            assertEquals(funDTO, tagService.getById(id));
        }

        @Test
        @DisplayName("tag of given id doesn't exist")
        void findByIdShouldThrowExceptionIfTagDoesNotExist() {
            // GIVEN
            int id = 1;
            // WHEN
            when(tagDAO.findById(id)).thenThrow(EmptyResultDataAccessException.class);
            // THEN
            assertThrows(EmptyResultDataAccessException.class, () -> tagService.getById(id));
        }

        @Test
        @DisplayName("given id is invalid")
        void findByIdShouldThrowExceptionIfIdIsNegative() {
            // GIVEN
            int id = -2;
            // WHEN

            // THEN
            assertThrows(InvalidInputException.class, () -> tagService.getById(id));
        }
    }

    @Test
    @DisplayName("find all test")
    void findAllShouldReturnAllTagsFromDb() {
        // GIVEN
        int funId = 1;
        int photoId = 2;
        int kidsId = 3;

        String fun = "fun";
        String photo = "photo";
        String kids = "kids";

        Tag funTag = setUpTag(funId, fun);
        Tag photoTag = setUpTag(photoId, photo);
        Tag kidsTag = setUpTag(kidsId, kids);

        List<Tag> tags = new ArrayList<>();
        tags.add(funTag);
        tags.add(photoTag);
        tags.add(kidsTag);

        TagDTO funDTO = setUpTagDTO(funId, fun);
        TagDTO photoDTO = setUpTagDTO(photoId, photo);
        TagDTO kidsDTO = setUpTagDTO(kidsId, kids);

        List<TagDTO> tagsDTO = new ArrayList<>();
        tagsDTO.add(funDTO);
        tagsDTO.add(photoDTO);
        tagsDTO.add(kidsDTO);

        // WHEN
        when(tagDAO.findAll()).thenReturn(tags);
        when(tagMapper.toDTO(kidsTag)).thenReturn(kidsDTO);
        when(tagMapper.toDTO(photoTag)).thenReturn(photoDTO);
        when(tagMapper.toDTO(funTag)).thenReturn(funDTO);
        // THEN
        assertEquals(tagsDTO, tagService.getAll());
    }

    @Nested
    @DisplayName("find by name tests")
    class findByName {

        @Test
        @DisplayName("tag is correctly found")
        void findByNameShouldReturnCorrectTag() {
            // GIVEN
            String fun = "fun";
            int id = 1;
            Tag funTag = setUpTag(id, fun);
            TagDTO funDTO = setUpTagDTO(id, fun);
            // WHEN
            when(tagDAO.findByName(fun)).thenReturn(funTag);
            when(tagMapper.toDTO(funTag)).thenReturn(funDTO);
            // THEN
            assertEquals(funDTO, tagService.getByName(fun));
        }

        @Test
        @DisplayName("tag of given name doesn't exist")
        void findByNameShouldThrowExceptionIfTagDoesNotExist() {
            // GIVEN
            String name = "fun";
            // WHEN
            when(tagDAO.findByName(name)).thenThrow(EmptyResultDataAccessException.class);
            // THEN
            assertThrows(EmptyResultDataAccessException.class, () -> tagService.getByName(name));
        }
    }

    @Nested
    @DisplayName("create tag tests")
    class createTagTests {
        @Test
        @DisplayName("tag correctly inserted")
        void createTagShouldInsertNewTagAndReturnInsertedTag() {
            // GIVEN
            Tag tagToBeInserted = new Tag();
            tagToBeInserted.setName("cool");
            TagDTO tagDTOToBeInserted = new TagDTO();
            tagDTOToBeInserted.setName("cool");
            Tag insertedTag = new Tag();
            insertedTag.setName("cool");
            insertedTag.setId(1);
            TagDTO insertedTagDTO = new TagDTO();
            insertedTagDTO.setName("cool");
            insertedTagDTO.setId(1);
            // WHEN
            when(tagMapper.toModel(tagDTOToBeInserted)).thenReturn(tagToBeInserted);
            when(tagDAO.createTag(tagToBeInserted)).thenReturn(insertedTag);
            when(tagMapper.toDTO(insertedTag)).thenReturn(insertedTagDTO);
            // THEN
            assertEquals(insertedTagDTO, tagService.addTag(tagDTOToBeInserted));
        }

        @Test
        @DisplayName("create tag with name which already exist in db")
        void createTagWithNameThatAlreadyExistInDbShouldThrowException() {
            // GIVEN
            Tag tagToBeInserted = new Tag();
            tagToBeInserted.setName("cool");
            TagDTO tagDTOToBeInserted = new TagDTO();
            tagDTOToBeInserted.setName("cool");
            List<Tag> tagsInDb = new ArrayList<>();
            tagsInDb.add(tagToBeInserted);
            // WHEN
            when(tagMapper.toModel(tagDTOToBeInserted)).thenReturn(tagToBeInserted);
            when(tagDAO.findAll()).thenReturn(tagsInDb);
            // THEN
            assertThrows(AlreadyExistException.class, () -> tagService.addTag(tagDTOToBeInserted));
        }

        @Test
        @DisplayName("create tag with invalid name")
        void createTagWithInvalidNameShouldThrowException() {
            // GIVEN
            String name = null;
            TagDTO invalidTag = new TagDTO();
            // WHEN
            invalidTag.setName(name);
            // THEN
            assertThrows(InvalidInputException.class, () -> tagService.addTag(invalidTag));
        }

        @Test
        @DisplayName("create tag with empty name")
        void createTagWithEmptyNameShouldThrowException() {
            // GIVEN
            String name = "  ";
            TagDTO invalidTag = new TagDTO();
            // WHEN
            invalidTag.setName(name);
            // THEN
            assertThrows(InvalidInputException.class, () -> tagService.addTag(invalidTag));
        }
    }

    @Nested
    @DisplayName("delete tag tests")
    class deleteTagTest {
        @Test
        @DisplayName("tag is correctly removed")
        void deleteTagShouldRemoveTagOfGivenIdFromDatabase() {
            // GIVEN
            int id = 1;
            // WHEN
            tagService.deleteTag(id);
            // THEN
            verify(tagDAO, times(1)).deleteTag(id);
        }

        @Test
        @DisplayName("tag of given name doesn't exist")
        void deleteTagOfNonExistingIdShouldThrowException() {
            // GIVEN
            int id = 2;
            // WHEN
            doThrow(new EmptyResultDataAccessException(0)).when(tagDAO).deleteTag(id);
            // THEN
            assertThrows(EmptyResultDataAccessException.class, () -> tagService.deleteTag(id));
        }
    }

    private Tag setUpTag(int id, String name) {
        Tag tag = new Tag();
        tag.setId(id);
        tag.setName(name);
        return tag;
    }

    private TagDTO setUpTagDTO(int id, String name) {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setId(id);
        tagDTO.setName(name);
        return tagDTO;
    }
}
