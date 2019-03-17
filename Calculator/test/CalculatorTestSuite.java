import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(value = Suite.class)
@Suite.SuiteClasses(value = {
        CalculatorTest.class,
        CalculatorParametrizedTest.class })
public class CalculatorTestSuite {
}
