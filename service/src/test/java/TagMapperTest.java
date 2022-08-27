import com.epam.esm.dto.TagDTO;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.Tag;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TagMapperTest {

    private TagMapper tagMapper;
    private Tag tag;
    private TagDTO tagDTO;

    private final SoftAssertions softAssertions = new SoftAssertions();

    @BeforeEach
    void initEach() {
        tagMapper = new TagMapper();
        tag = new Tag();
        tagDTO = new TagDTO();
    }

    @Test
    @DisplayName("to DTO test")
    void toDTOTest() {
        // GIVEN
        int id = 1;
        String name = "sport";
        // WHEN
        tag.setId(id);
        tag.setName(name);
        tagDTO.setId(id);
        tagDTO.setName(name);
        // THEN
        softAssertions.assertThat(tagMapper.toDTO(tag).getId()).isEqualTo(tagDTO.getId());
        softAssertions.assertThat(tagMapper.toDTO(tag).getName()).isEqualTo(tagDTO.getName());
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("to model test")
    void toModelTest() {
        // GIVEN
        int id = 1;
        String name = "sport";
        // WHEN
        tag.setId(id);
        tag.setName(name);
        tagDTO.setId(id);
        tagDTO.setName(name);
        // THEN
        softAssertions.assertThat(tagMapper.toModel(tagDTO).getId()).isEqualTo(tag.getId());
        softAssertions.assertThat(tagMapper.toModel(tagDTO).getName()).isEqualTo(tag.getName());
        softAssertions.assertAll();
    }
}
