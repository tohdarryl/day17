package sg.edu.nus.iss.app.workshop17.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Weather implements Serializable{
    //extract "name" : Singapore
    private String city;
    //extract "temp" : 28.61 under "main"
    private String temperature;

    public List<Condition> conditions = new LinkedList<>();

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public static Weather create(String json) throws IOException{
        Weather w = new Weather();
        try(InputStream is = new ByteArrayInputStream(json.getBytes())){
            JsonReader r = Json.createReader(is);
            JsonObject o = r.readObject();
            w.setCity(o.getString("name"));
            JsonObject mainObj = o.getJsonObject("main");
            w. setTemperature(mainObj.getJsonNumber("temp").toString());
            w.conditions = o.getJsonArray("weather")
                                                .stream()
                                                .map(v-> (JsonObject)v)
                                                .map(v-> Condition.createJson(v))
                                                .toList();
        }
        return w;
    }
}
