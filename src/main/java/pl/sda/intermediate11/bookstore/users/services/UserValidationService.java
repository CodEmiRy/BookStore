package pl.sda.intermediate11.bookstore.users.services;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pl.sda.intermediate11.bookstore.users.dtos.UserRegistrationDTO;

import java.util.Map;

@Service
public class UserValidationService {


    public static final String FIRST_NAME_VAL_RES = "firstNameValRes";
    public static final String LAST_NAME_VAL_RES = "lastNameValRes";
    public static final String ZIP_CODE_VAL_RES = "zipCodeValRes";
    public static final String CITY_VAL_RES = "cityValRes";
    public static final String COUNTRY_VAL_RES = "countryValRes";
    public static final String STREET_VAL_RES = "streetValRes";
    public static final String BIRTH_DATA_VAL_RES = "birthDataValRes";
    public static final String PESEL_VAL_RES = "peselValRes";
    public static final String EMAIL_VAL_RES = "emailValRes";
    public static final String PASSWORD_VAL_RES = "passwordValRes";
    public static final String PHONE_VAL_RES = "phoneValRes";


    public Map<String, String> validateUserData(UserRegistrationDTO userRegistrationDTO) {

        Map<String, String> resultMap = Maps.newHashMap();
        if (textPartIsNotValid(userRegistrationDTO.getFirstName(), "[A-Z][a-z]{2,}")) {
            resultMap.put(FIRST_NAME_VAL_RES, "Imię jest wymagane.");
        }
        if (textPartIsNotValid(userRegistrationDTO.getLastName(), "[A-Z][a-z]{2,}(|-[A-Z][a-z]{2,})")) {
            resultMap.put(LAST_NAME_VAL_RES, "Nazwisko jest wymagane.");
        }

        if (zipCodeIsNotValid(userRegistrationDTO.getUserAddress().getZipCode())) {
            resultMap.put(ZIP_CODE_VAL_RES, "Kod pocztowy jest wymagany.");
        }
        if (addressPartIsNotValid(userRegistrationDTO.getUserAddress().getCity())) {
            resultMap.put(CITY_VAL_RES, "Miasto jest wymagane.");

        }
        if (addressPartIsNotValid(userRegistrationDTO.getUserAddress().getCountry())) {
            resultMap.put(COUNTRY_VAL_RES, "Kraj jest wymagany.");

        }
        if (addressPartIsNotValid(userRegistrationDTO.getUserAddress().getStreet())) {
            resultMap.put(STREET_VAL_RES, "Ulica jest wymagana.");
        }
        if (textPartIsNotValid(userRegistrationDTO.getBirthDate(),
                "^(19[0-9]{2}|20[0-1][0-9])-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$")) {
            resultMap.put(BIRTH_DATA_VAL_RES, "Zły format. Data urodzin powinna być podana w formacie RRRR-MM-DD");
        }
        if (textPartIsNotValid(userRegistrationDTO.getPesel(), "[0-9]{11}")) {
            resultMap.put(PESEL_VAL_RES, "Zły format. Pesel powinien składać się z 11 cyfr.");
        }
        if (textPartIsNotValid(userRegistrationDTO.getEmail(), "^[\\w]+[@][\\w]+[\\.][a-z]{2,}$")) {
            resultMap.put(EMAIL_VAL_RES, "Zły format. Popraw.");
        }
        if (textPartIsNotValid(userRegistrationDTO.getPassword(),
                "^((?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{10,20})$")) {
            resultMap.put(PASSWORD_VAL_RES, "Złe hasło. Popraw.");
        }
        if (textPartIsNotValid(userRegistrationDTO.getPhone(),
                "^(\\+48|)( |)([1-9][0-9]{2}-[0-9]{3}-[0-9]{3})$")) {
            resultMap.put(PHONE_VAL_RES, "Zły format nr telefonu. Popraw.");
        }

        return resultMap;
    }

    private boolean addressPartIsNotValid(String city) {
        return StringUtils.isBlank(city);
    }

    private boolean zipCodeIsNotValid(String zipCode) {
        return StringUtils.isBlank(zipCode)
                || !zipCode.trim().matches("[0-9]{2}-[0-9]{3}");
    }

    private boolean textPartIsNotValid(String sameName, String regex) {
        return StringUtils.isBlank(sameName)
                || !sameName.trim().matches(regex);
    }

}
