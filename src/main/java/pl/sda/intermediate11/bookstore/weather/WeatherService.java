package pl.sda.intermediate11.bookstore.weather;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.intermediate11.bookstore.database.HibernateUserRepository;
import pl.sda.intermediate11.bookstore.users.daos.UserDAO;
import pl.sda.intermediate11.bookstore.users.services.UserContextHolder;
import pl.sda.intermediate11.bookstore.weather.model.WeatherResult;
import retrofit2.Retrofit;
import retrofit2.adapter.java8.Java8CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.CompletableFuture;

@Service
public class WeatherService {

    @Autowired
    private HibernateUserRepository userDAO;

    public String getWeatherInfo() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(Java8CallAdapterFactory.create())
                .build();

        OpenWeatherMapJ8 openWeatherMapJ8 = retrofit.create(OpenWeatherMapJ8.class);

        String email = UserContextHolder.getUserLoggedIn();
        String city = userDAO.findUserByEmail(email).get().getCity();
        CompletableFuture<WeatherResult> weatherByCity = openWeatherMapJ8.getCurrentWeatherByCity(
                city,
                "ea900b66f547fd7b23625544873a4200",
                "metric",
                "pl"
        );

        return weatherByCity.thenApplyAsync(forecast -> new Gson().toJson(forecast)).join();
    }
}
