package Utilities;

import org.testng.annotations.DataProvider;

public class NegativeParentGuardianDataProvider {

    @DataProvider(name = "parentGuardianInvalidData")  // ✅ Match this with the test
    public Object[][] provideInvalidData() {
        return new Object[][] {
            {"", "Kumar", "Mother", "123 Street", "Telangana", "Hyderabad", "500001", "98765", "invalid.com"},
            {"Anu", "", "Father", "456 Lane", "Karnataka", "Bangalore", "abcd", "123456789", "anu@"},
            {"@#$$", "1234", "Guardian", "", "AP", "Vijayawada", "000000", "abc", "test@wrong"}
        };
    }
}
