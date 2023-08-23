package data.providers;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

public class DataGenerator {
    private static Faker faker = new Faker();

    public static String getFullName() {
        return faker.name().fullName();
    }

    public static String getEmail() {
        return faker.internet().emailAddress();
    }

    public static String getPassword() {
        return faker.internet().password();
    }

    public static String getAnyString() {
        return RandomStringUtils.randomAlphabetic(10).concat(" ")
                .concat(RandomStringUtils.randomAlphabetic(10)).concat(" ")
                .concat(RandomStringUtils.randomAlphabetic(10));
    }
}
