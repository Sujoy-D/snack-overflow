package entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/**
 * Test class for Filter entity.
 * Tests all constructors, getters, validation logic, and edge cases.
 */
class FilterTest {

    private Tag vegetarianTag;
    private Tag glutenFreeTag;
    private List<String> excludeIngredients;
    private List<Tag> includedTags;

    @BeforeEach
    void setUp() {
        vegetarianTag = new Tag(1, "Vegetarian");
        glutenFreeTag = new Tag(2, "Gluten-Free");
        excludeIngredients = Arrays.asList("peanuts", "shellfish");
        includedTags = Arrays.asList(vegetarianTag, glutenFreeTag);
    }

    @Test
    void testValidFilterCreation() {
        // Arrange & Act
        Filter filter = new Filter(30, "Italian", "Dinner", excludeIngredients, includedTags);

        // Assert
        assertEquals(30, filter.getCookingTimeMax());
        assertEquals("Italian", filter.getCuisine());
        assertEquals("Dinner", filter.getMealType());
        assertEquals(excludeIngredients, filter.getExcludeIngredients());
        assertEquals(includedTags, filter.getIncludedTags());
    }

    @Test
    void testFilterWithNullCookingTime() {
        // Arrange & Act
        Filter filter = new Filter(null, "Italian", "Dinner", excludeIngredients, includedTags);

        // Assert
        assertNull(filter.getCookingTimeMax());
        assertEquals("Italian", filter.getCuisine());
        assertEquals("Dinner", filter.getMealType());
        assertEquals(excludeIngredients, filter.getExcludeIngredients());
        assertEquals(includedTags, filter.getIncludedTags());
    }

    @Test
    void testFilterWithNullCuisine() {
        // Arrange & Act
        Filter filter = new Filter(30, null, "Dinner", excludeIngredients, includedTags);

        // Assert
        assertEquals(30, filter.getCookingTimeMax());
        assertNull(filter.getCuisine());
        assertEquals("Dinner", filter.getMealType());
        assertEquals(excludeIngredients, filter.getExcludeIngredients());
        assertEquals(includedTags, filter.getIncludedTags());
    }

    @Test
    void testFilterWithNullMealType() {
        // Arrange & Act
        Filter filter = new Filter(30, "Italian", null, excludeIngredients, includedTags);

        // Assert
        assertEquals(30, filter.getCookingTimeMax());
        assertEquals("Italian", filter.getCuisine());
        assertNull(filter.getMealType());
        assertEquals(excludeIngredients, filter.getExcludeIngredients());
        assertEquals(includedTags, filter.getIncludedTags());
    }

    @Test
    void testFilterWithEmptyLists() {
        // Arrange
        List<String> emptyExcludeIngredients = new ArrayList<>();
        List<Tag> emptyIncludedTags = new ArrayList<>();

        // Act
        Filter filter = new Filter(30, "Italian", "Dinner", emptyExcludeIngredients, emptyIncludedTags);

        // Assert
        assertEquals(30, filter.getCookingTimeMax());
        assertEquals("Italian", filter.getCuisine());
        assertEquals("Dinner", filter.getMealType());
        assertTrue(filter.getExcludeIngredients().isEmpty());
        assertTrue(filter.getIncludedTags().isEmpty());
    }

    @Test
    void testFilterWithNullExcludeIngredients() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Filter(30, "Italian", "Dinner", null, includedTags);
        });
        assertEquals("Exclude ingredients list cannot be null", exception.getMessage());
    }

    @Test
    void testFilterWithNullIncludedTags() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Filter(30, "Italian", "Dinner", excludeIngredients, null);
        });
        assertEquals("Included tags list cannot be null", exception.getMessage());
    }

    @Test
    void testFilterWithZeroCookingTime() {
        // Arrange & Act
        Filter filter = new Filter(0, "Italian", "Dinner", excludeIngredients, includedTags);

        // Assert
        assertEquals(0, filter.getCookingTimeMax());
        assertEquals("Italian", filter.getCuisine());
        assertEquals("Dinner", filter.getMealType());
    }

    @Test
    void testFilterWithNegativeCookingTime() {
        // Arrange & Act
        Filter filter = new Filter(-10, "Italian", "Dinner", excludeIngredients, includedTags);

        // Assert
        assertEquals(-10, filter.getCookingTimeMax());
        assertEquals("Italian", filter.getCuisine());
        assertEquals("Dinner", filter.getMealType());
    }

    @Test
    void testFilterWithEmptyStrings() {
        // Arrange & Act
        Filter filter = new Filter(30, "", "", excludeIngredients, includedTags);

        // Assert
        assertEquals(30, filter.getCookingTimeMax());
        assertEquals("", filter.getCuisine());
        assertEquals("", filter.getMealType());
        assertEquals(excludeIngredients, filter.getExcludeIngredients());
        assertEquals(includedTags, filter.getIncludedTags());
    }

    @Test
    void testFilterToString() {
        // Arrange
        Filter filter = new Filter(30, "Italian", "Dinner", excludeIngredients, includedTags);

        // Act
        String result = filter.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("Filter{"));
        assertTrue(result.contains("time<=30"));
        assertTrue(result.contains("cuisine='Italian'"));
        assertTrue(result.contains("mealType='Dinner'"));
        assertTrue(result.contains("exclude=" + excludeIngredients.toString()));
        assertTrue(result.contains("tags=" + includedTags.toString()));
    }

    @Test
    void testFilterToStringWithNullValues() {
        // Arrange
        Filter filter = new Filter(null, null, null, new ArrayList<>(), new ArrayList<>());

        // Act
        String result = filter.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("Filter{"));
        assertTrue(result.contains("time<=null"));
        assertTrue(result.contains("cuisine='null'"));
        assertTrue(result.contains("mealType='null'"));
        assertTrue(result.contains("exclude=[]"));
        assertTrue(result.contains("tags=[]"));
    }

    @Test
    void testFilterWithSingleIngredientAndTag() {
        // Arrange
        List<String> singleIngredient = Arrays.asList("nuts");
        List<Tag> singleTag = Arrays.asList(vegetarianTag);

        // Act
        Filter filter = new Filter(45, "Mexican", "Lunch", singleIngredient, singleTag);

        // Assert
        assertEquals(45, filter.getCookingTimeMax());
        assertEquals("Mexican", filter.getCuisine());
        assertEquals("Lunch", filter.getMealType());
        assertEquals(1, filter.getExcludeIngredients().size());
        assertEquals("nuts", filter.getExcludeIngredients().get(0));
        assertEquals(1, filter.getIncludedTags().size());
        assertEquals(vegetarianTag, filter.getIncludedTags().get(0));
    }

    @Test
    void testFilterWithMultipleIngredientsAndTags() {
        // Arrange
        List<String> multipleIngredients = Arrays.asList("peanuts", "shellfish", "eggs", "milk");
        List<Tag> multipleTags = Arrays.asList(vegetarianTag, glutenFreeTag, new Tag(3, "Dairy-Free"));

        // Act
        Filter filter = new Filter(60, "Asian", "Breakfast", multipleIngredients, multipleTags);

        // Assert
        assertEquals(60, filter.getCookingTimeMax());
        assertEquals("Asian", filter.getCuisine());
        assertEquals("Breakfast", filter.getMealType());
        assertEquals(4, filter.getExcludeIngredients().size());
        assertTrue(filter.getExcludeIngredients().containsAll(multipleIngredients));
        assertEquals(3, filter.getIncludedTags().size());
        assertTrue(filter.getIncludedTags().containsAll(multipleTags));
    }

    @Test
    void testFilterSharesListReferences() {
        // Arrange
        List<String> originalExcludeIngredients = new ArrayList<>(Arrays.asList("peanuts", "shellfish"));
        List<Tag> originalIncludedTags = new ArrayList<>(Arrays.asList(vegetarianTag, glutenFreeTag));
        Filter filter = new Filter(30, "Italian", "Dinner", originalExcludeIngredients, originalIncludedTags);

        // Act - Modify original lists after filter creation
        originalExcludeIngredients.add("eggs");
        originalIncludedTags.add(new Tag(3, "Dairy-Free"));

        // Assert - Filter shares the same list references, so changes are reflected
        assertEquals(3, filter.getExcludeIngredients().size());
        assertEquals(3, filter.getIncludedTags().size());
        assertTrue(filter.getExcludeIngredients().contains("eggs"));
        assertTrue(filter.getIncludedTags().stream().anyMatch(tag -> tag.getName().equals("Dairy-Free")));
        
        // Verify that the lists are the same objects
        assertSame(originalExcludeIngredients, filter.getExcludeIngredients());
        assertSame(originalIncludedTags, filter.getIncludedTags());
    }
}
