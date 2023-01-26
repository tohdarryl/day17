package sg.edu.nus.iss.app.workshop17.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import sg.edu.nus.iss.app.workshop17.model.Weather;

@Service
public class WeatherService {
    //business logic that communicates with external API
    private static final String OPEN_WEATHER_URL ="https://api.openweathermap.org/data/2.5/weather";
    
    //Optional; just in case there will not be any data extractable
    public Optional<Weather> getWeather(String city, String unitMeasurement) throws IOException{
        //On Terminal: export OPEN_WEATHER_MAP_API_KEY="" to set apiKey
        String apiKey = System.getenv("OPEN_WEATHER_MAP_API_KEY");
        System.out.println(city);
        //finalise URL: OPEN_WEATHER_URL?q=Singapore&units=metric&appI  d=""
        String weatherUrl = UriComponentsBuilder
                            .fromUriString(OPEN_WEATHER_URL)
                            .queryParam("q", city.replaceAll(" ", "+"))
                            .queryParam("units", unitMeasurement)
                            .queryParam("appId", apiKey)
                            .toUriString();

        System.out.println(weatherUrl);
        RestTemplate template = new RestTemplate();
        //HOLDS the result of the invocation. The payload is a string; matches the 2nd para of getForEntity()
        ResponseEntity<String> resp = null;
        resp = template.getForEntity(weatherUrl, String.class);
        Weather w = Weather.create(resp.getBody());
        if(w != null)
            return Optional.of(w);
        return Optional.empty();
    }
}
