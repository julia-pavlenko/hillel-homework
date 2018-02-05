import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ValidationWithRegexTests {

    public ValidationWithRegex validator;

    @DataProvider(name = "testDataForValidateEmails")
    public Object[][] createDataForValidateEmails() {
        return new Object[][] {
                { "test@yandex.ru", Boolean.TRUE },
                { "Test123@gmail.com", Boolean.TRUE},
                { "test-1@gmail.com,asdd@yandex.ru", Boolean.TRUE},
                { "", Boolean.FALSE},
                { "asd@mail.ru,dgh@gmail.com,dfg@yandex.ru", Boolean.FALSE},
                { "jytfiytfk", Boolean.FALSE},
        };
    }

    @DataProvider(name = "testDataForBiggerThan1450")
    public Object[][] createDataForBiggerThan1450() {
        return new Object[][] {
                { "1450", Boolean.TRUE },
                { "12234", Boolean.TRUE},
                { "Amount: 1570.88", Boolean.TRUE},
                { "", Boolean.FALSE},
                { "1449.99", Boolean.FALSE},
                { "Amount: 198", Boolean.FALSE},
        };
    }

    @DataProvider(name = "testDataForLessThan100")
    public Object[][] createDataForLessThan100() {
        return new Object[][] {
                { "100", Boolean.TRUE },
                { "0.99", Boolean.TRUE},
                { "Amount: 99.99", Boolean.TRUE},
                { "101", Boolean.FALSE},
                { "", Boolean.FALSE},
                { "Amount: 999", Boolean.FALSE},
        };
    }

    @BeforeTest
    void createValidatorObject() {
        validator = new ValidationWithRegex();
    }

    @Test(dataProvider = "testDataForValidateEmails", description = "Test validateEmails method")
    public void testValidateEmails(String str, Boolean expectedResult){
        Assert.assertEquals((Boolean)validator.validateEmails(str), expectedResult);
    }

    @Test(dataProvider = "testDataForBiggerThan1450", description = "Test isBiggerThan1450 method")
    public void testIsBiggerThan1450(String str, Boolean expectedResult){
        Assert.assertEquals((Boolean)validator.isBiggerThan1450(str), expectedResult);
    }

    @Test(dataProvider = "testDataForLessThan100", description = "Test IsLessThan100 method")
    public void testIsLessThan100(String str, Boolean expectedResult){
        Assert.assertEquals((Boolean)validator.isLessThan100(str), expectedResult);
    }

    @AfterMethod
    void printResult(ITestResult result){
        if(!result.isSuccess()) {
            System.out.println(String.format("\n%s failed with test data: %s", result.getMethod().getDescription(), result.getParameters()[0]));
        }
    }
}
