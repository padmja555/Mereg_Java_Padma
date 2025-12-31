
package AdminTestcase;

import org.testng.annotations.DataProvider;

public class ParentInfoDataProvider {
    
    @DataProvider(name = "parentData")
    public Object[][] provideParentData() {
        return new Object[][] {
            // Positive scenarios
            {"John", "Doe", "Father", "123 Main St", "Apt 4B", "United States", "New York", "New York", "10001", "Mobile", "1", "1234567890", "test@example.com", true, "All valid inputs"},
            
            // Negative scenarios - Empty required fields
            {"", "Doe", "Father", "123 Main St", "Apt 4B", "United States", "New York", "New York", "10001", "Mobile", "1", "1234567890", "test@example.com", false, "Empty first name"},
            {"John", "", "Father", "123 Main St", "Apt 4B", "United States", "New York", "New York", "10001", "Mobile", "1", "1234567890", "test@example.com", false, "Empty last name"},
            {"John", "Doe", "", "123 Main St", "Apt 4B", "United States", "New York", "New York", "10001", "Mobile", "1", "1234567890", "test@example.com", false, "Empty relationship"},
            {"John", "Doe", "Father", "", "Apt 4B", "United States", "New York", "New York", "10001", "Mobile", "1", "1234567890", "test@example.com", false, "Empty address"},
            {"John", "Doe", "Father", "123 Main St", "Apt 4B", "", "New York", "New York", "10001", "Mobile", "1", "1234567890", "test@example.com", false, "Empty country"},
            {"John", "Doe", "Father", "123 Main St", "Apt 4B", "United States", "", "New York", "10001", "Mobile", "1", "1234567890", "test@example.com", false, "Empty state"},
            {"John", "Doe", "Father", "123 Main St", "Apt 4B", "United States", "New York", "", "10001", "Mobile", "1", "1234567890", "test@example.com", false, "Empty city"},
            {"John", "Doe", "Father", "123 Main St", "Apt 4B", "United States", "New York", "New York", "", "Mobile", "1", "1234567890", "test@example.com", false, "Empty zip code"},
            {"John", "Doe", "Father", "123 Main St", "Apt 4B", "United States", "New York", "New York", "10001", "", "1", "1234567890", "test@example.com", false, "Empty phone type"},
            {"John", "Doe", "Father", "123 Main St", "Apt 4B", "United States", "New York", "New York", "10001", "Mobile", "", "1234567890", "test@example.com", false, "Empty country code"},
            {"John", "Doe", "Father", "123 Main St", "Apt 4B", "United States", "New York", "New York", "10001", "Mobile", "1", "", "test@example.com", false, "Empty phone number"},
            {"John", "Doe", "Father", "123 Main St", "Apt 4B", "United States", "New York", "New York", "10001", "Mobile", "1", "1234567890", "", false, "Empty email"},
            
            // Negative scenarios - Invalid formats
            {"John", "Doe", "Father", "123 Main St", "Apt 4B", "United States", "New York", "New York", "invalid-zip", "Mobile", "1", "1234567890", "test@example.com", false, "Invalid zip code"},
            {"John", "Doe", "Father", "123 Main St", "Apt 4B", "United States", "New York", "New York", "10001", "Mobile", "1", "invalid-phone", "test@example.com", false, "Invalid phone number"},
            {"John", "Doe", "Father", "123 Main St", "Apt 4B", "United States", "New York", "New York", "10001", "Mobile", "1", "1234567890", "invalid-email", false, "Invalid email format"},
            
            // Negative scenarios - Special characters and XSS attempts
            {"<script>alert('xss')</script>", "Doe", "Father", "123 Main St", "Apt 4B", "United States", "New York", "New York", "10001", "Mobile", "1", "1234567890", "test@example.com", false, "XSS in first name"},
            {"John", "<script>alert('xss')</script>", "Father", "123 Main St", "Apt 4B", "United States", "New York", "New York", "10001", "Mobile", "1", "1234567890", "test@example.com", false, "XSS in last name"},
            
            // Negative scenarios - SQL injection attempts
            {"John'; DROP TABLE users;--", "Doe", "Father", "123 Main St", "Apt 4B", "United States", "New York", "New York", "10001", "Mobile", "1", "1234567890", "test@example.com", false, "SQL injection in first name"},
        };
    }
}
