package pl.sda.intermediate11.bookstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.sda.intermediate11.bookstore.categories.CategoryDTO;
import pl.sda.intermediate11.bookstore.categories.CategorySearchService;
import pl.sda.intermediate11.bookstore.products.ProductDao;
import pl.sda.intermediate11.bookstore.users.dtos.UserLoginDTO;
import pl.sda.intermediate11.bookstore.users.dtos.UserRegistrationDTO;
import pl.sda.intermediate11.bookstore.users.entities.CountryEnum;
import pl.sda.intermediate11.bookstore.users.exceptions.PasswordDoesNotMatchException;
import pl.sda.intermediate11.bookstore.users.exceptions.UserNotExistsException;
import pl.sda.intermediate11.bookstore.users.services.UserContextHolder;
import pl.sda.intermediate11.bookstore.users.services.UserLoginService;
import pl.sda.intermediate11.bookstore.users.services.UserRegistrationService;
import pl.sda.intermediate11.bookstore.users.services.UserValidationService;
import pl.sda.intermediate11.bookstore.weather.WeatherService;

import java.util.List;
import java.util.Map;

@Controller//singleton
public class OnlyOneController {

    @Autowired//spring wstawi tutaj referencję
    private CategorySearchService categorySearchService;

    @Autowired
    private UserValidationService userValidationService;

    @Autowired
    private UserRegistrationService userRegistrationService;

    @Autowired
    private UserLoginService userLoginService;
    @Autowired
    private WeatherService weatherService;
    @Autowired
    private ProductDao productDao;
    //dependency injection-wstrzykiwanie zależności

    @RequestMapping("/")
    public String welcome() {
        return "index";
    }

    @GetMapping("/cats")
    public String categories(Map<String, Object> model, @RequestParam(required = false) String searchText) {
        List<CategoryDTO> categoryDTOS = categorySearchService.filterCategories(searchText);
        model.put("catsdata", categoryDTOS); // to zostanie wyslane na front
        return "catspage"; // takiego html-a bedzie szukac nasza aplikacja
    }

    @PostMapping(value = "/moveCat")
    public void moveCat(@RequestParam String newParentId, @RequestParam String movedId) {
        categorySearchService.moveCategory(newParentId,movedId);
    }

    @PostMapping(value = "/register")//POST - wysłanie danych
    public String registerEffect(@ModelAttribute UserRegistrationDTO userRegistrationDTO, Map<String, Object> model) {
        Map<String, String> errorsMap = userValidationService.validateUserData(userRegistrationDTO);
        model.put("countries", CountryEnum.values());
        model.put("form", userRegistrationDTO);
        if (errorsMap.isEmpty()) {
            userRegistrationService.registerUser(userRegistrationDTO);
            return "registerEffect";
        } else {
            model.putAll(errorsMap);
            return "registerForm";
        }
    }

    @GetMapping(value = "/register")
    public String registerForm(Map<String, Object> model) {
        model.put("form", new UserRegistrationDTO());
        model.put("countries", CountryEnum.values());
        return "registerForm";
    }

    @GetMapping(value = "/login")
    public String login(Map<String, Object> model) {
        model.put("form", new UserLoginDTO());
        return "login";
    }

    @PostMapping(value = "/login")
    public String loginEffect(@ModelAttribute UserLoginDTO userLoginDTO, Map<String, Object> model) {
        try {
            userLoginService.login(userLoginDTO);
        } catch (PasswordDoesNotMatchException | UserNotExistsException e) {
            model.put("error", e.getMessage());
            model.put("form", new UserLoginDTO());
            return "login";
        }
        return "index";
    }

    @GetMapping(value = "/logout")
    public String logOut() {
        UserContextHolder.logOut();
        return "index";
    }

    @GetMapping("/weather")
    @ResponseBody
    public ResponseEntity<String> weather() {
        return ResponseEntity.ok(weatherService.getWeatherInfo());
    }

    @GetMapping(value = "/products")
    public String getProducts(Map<String, Object> model, @RequestParam(required = false) String searchText) {
        model.put("products", productDao.getProductList(searchText, 50));
        return "products";
    }

}
