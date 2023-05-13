package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserDtoTest {

    @Test
    void shouldNotPrintSensitiveData() {
        final UserDto user = new UserDto();
        user.setFirstName("John");
        user.setLastName("Dou");
        user.setEmail("jd@mail.com");
        user.setDescription("Desc");

        final String userString = user.toString();

        assertFalse(userString.contains("John"));
        assertFalse(userString.contains("Dou"));
        assertFalse(userString.contains("jd@mail.com"));
        assertTrue(userString.contains("Desc"));
    }

    /*package org.example;


    public class UserDto {
        @SensitiveData
        private String firstName;
        @SensitiveData
        private String lastName;
        @SensitiveData
        private String email;
        private String description;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }*/

}