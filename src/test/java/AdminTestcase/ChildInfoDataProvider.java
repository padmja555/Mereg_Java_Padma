
package AdminTestcase;

import org.testng.annotations.DataProvider;

public class ChildInfoDataProvider {

    @DataProvider(name = "childData")
    public static Object[][] childData() {
        return new Object[][] {
            // firstName, lastName, dob, isPositiveScenario, description
            {"John", "Smith", "01/01/2010", true, "Valid data - positive case"},
            {"", "Smith", "01/01/2010", false, "First name blank - negative case"},
            {"John", "", "01/01/2010", false, "Last name blank - negative case"},
            {"John", "Smith", "", false, "DOB blank - negative case"},
            {"", "", "", false, "All fields blank - negative case"},
            {"John", "Smith", "invalid-date", false, "DOB invalid format - negative case"}
        };
    }
}
