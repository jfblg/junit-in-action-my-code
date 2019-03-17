import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsEqual.equalTo;

public class HamcrestTest {
    private List<String> values;

    @Before
    public void setUpList() {
        values = new ArrayList<String>();
        values.add("x");
        values.add("y");
        values.add("one");
    }

    @Test
    public void testWithoutHamcrest() {
        assertTrue("None of expected items is in the list",
                values.contains("one") || values.contains("two") || values.contains("three"));
    }

    // hamcrest provides "wrappers" which may make code easier to read
    // especially when a test fails, the explanation why it failed is
    // easier to understand than Junit assert
    @Test
    public void testWithHamcrest() {
        assertThat(values, hasItem(anyOf(equalTo("one"), equalTo("two"), equalTo("three"))));
    }
}
