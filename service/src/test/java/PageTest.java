import com.epam.esm.dto.TagDTO;
import com.epam.esm.pagination.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PageTest {

    private TagDTO food;
    private TagDTO fun;

    @BeforeEach
    void initEach() {
        food = new TagDTO(5, "food", "2022-03-18T12:24:47.241");
        fun = new TagDTO(4, "fun", "2022-05-18T12:24:47.241");
    }

    @Nested
    @DisplayName("hasNext test")
    class hasNextTests {

        @Test
        @DisplayName("no more pages")
        void shouldReturnFalseIfThereIsNoMorePages() {
            // GIVEN
            List<TagDTO> tags = new ArrayList<>();
            tags.add(food);
            tags.add(fun);
            // WHEN
            Page<TagDTO> page = new Page<>(1, 2, 2, tags);
            // THEN
            assertFalse(page.hasNext());
        }

        @Test
        @DisplayName("there are more pages")
        void shouldReturnTrueIfThereAreMorePages() {
            // GIVEN
            List<TagDTO> tags = new ArrayList<>();
            tags.add(food);
            tags.add(fun);
            // WHEN
            Page<TagDTO> page = new Page<>(1, 2, 3, tags);
            // THEN
            assertTrue(page.hasNext());
        }
    }

    @Nested
    @DisplayName("hasPrevious test")
    class hasPreviousTests {

        @Test
        @DisplayName("no previous page")
        void shouldReturnFalseIfThereIsNoPreviousPage() {
            // GIVEN
            List<TagDTO> tags = new ArrayList<>();
            tags.add(food);
            tags.add(fun);
            // WHEN
            Page<TagDTO> page = new Page<>(1, 2, 2, tags);
            // THEN
            assertFalse(page.hasPrevious());
        }

        @Test
        @DisplayName("there is previous page")
        void shouldReturnTrueIfThereIsPreviousPage() {
            // GIVEN
            List<TagDTO> tags = new ArrayList<>();
            tags.add(food);
            tags.add(fun);
            // WHEN
            Page<TagDTO> page = new Page<>(2, 2, 5, tags);
            // THEN
            assertTrue(page.hasNext());
        }
    }

    @Test
    @DisplayName("get page index test")
    void shouldReturnPageIndex() {
        // GIVEN
        List<TagDTO> tags = new ArrayList<>();
        tags.add(food);
        tags.add(fun);
        // WHEN
        Page<TagDTO> page = new Page<>(2, 2, 5, tags);
        // THEN
        assertEquals(2, page.getPageIndex());
    }

    @Test
    @DisplayName("get page size test")
    void shouldReturnPageSize() {
        // GIVEN
        List<TagDTO> tags = new ArrayList<>();
        tags.add(food);
        tags.add(fun);
        // WHEN
        Page<TagDTO> page = new Page<>(2, 3, 5, tags);
        // THEN
        assertEquals(3, page.getSize());
    }
}
