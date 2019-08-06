package kz.ggi_dev.medium_test_project_for_boostbrain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.util.Pair;
import kz.ggi_dev.medium_test_project_for_boostbrain.model.ListWeather;
import kz.ggi_dev.medium_test_project_for_boostbrain.model.WeatherForecastForCity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@Service
@Scope("singleton")
public class AnalysisWeatherService {

    private final String CODE_200 = "200";
    private final String CODE_404 = "404";
    private final String CODE_500 = "500";
    private final String CODE_500_VALUE = "Error in URL!";
    private final String CODE_404_VALUE = "City not found!";
    private final String CODE_ERROR_VALUE = "Error!";
    private final String STRING_FOR_ADD_APPID_IN_URL = "&appid=";
    private final String TIME_AND_TEMP_STRING_PATTERN = "time=%d;temp=%.2f";
    private final String AVERAGE_TEMP_STRING_PATTERN = "temp=%.2f";

    @Value("${weather.key}")
    private String weather_key;
    @Value("${weather.urlapi}")
    private String urlApiWeatherForecastFiveDays;
    private ObjectMapper mapper;

    public AnalysisWeatherService() {
        this.mapper = new ObjectMapper();
    }

    public RestResponseForAnalysis analysisMinTempForFiveDays(final String city, final String country) {
        Pair<WeatherForecastForCity, RestResponseForAnalysis> data = prepareDataForAnalysis(city, country);
        RestResponseForAnalysis restResponseForAnalysis = data.getValue();
        if (data.getKey() == null) return restResponseForAnalysis;
        ListWeather listWeatherWithMinTemp = data.getKey().getListWeather().get(0);
        for (ListWeather lw: data.getKey().getListWeather()) {
            if (lw.getMain().getTempMin() < listWeatherWithMinTemp.getMain().getTempMin())
                listWeatherWithMinTemp = lw;
        }
        data.getValue().setValue(String.format(
                TIME_AND_TEMP_STRING_PATTERN,
                listWeatherWithMinTemp.getDt(),
                listWeatherWithMinTemp.getMain().getTempMin()));
        return data.getValue();
    }

    public  RestResponseForAnalysis analysisMaxTempForFiveDays(final String city, final String country) {
        Pair<WeatherForecastForCity, RestResponseForAnalysis> data = prepareDataForAnalysis(city, country);
        RestResponseForAnalysis restResponseForAnalysis = data.getValue();
        if (data.getKey() == null) return restResponseForAnalysis;
        ListWeather listWeatherWithMaxTemp = data.getKey().getListWeather().get(0);
        for (ListWeather lw: data.getKey().getListWeather()) {
            if (lw.getMain().getTempMax() > listWeatherWithMaxTemp.getMain().getTempMax())
                listWeatherWithMaxTemp = lw;
        }
        data.getValue().setValue(String.format(
                TIME_AND_TEMP_STRING_PATTERN,
                listWeatherWithMaxTemp.getDt(),
                listWeatherWithMaxTemp.getMain().getTempMax()));
        return data.getValue();
    }

    public  RestResponseForAnalysis analysisAverageTempForFiveDays(final String city, final String country) {
        Pair<WeatherForecastForCity, RestResponseForAnalysis> data = prepareDataForAnalysis(city, country);
        RestResponseForAnalysis restResponseForAnalysis = data.getValue();
        if (data.getKey() == null) return restResponseForAnalysis;
        double sumTemp = 0;
        int count = 0;
        for (ListWeather lw: data.getKey().getListWeather()) {
            sumTemp += lw.getMain().getTemp();
            count++;
        }
        data.getValue().setValue(String.format(
                AVERAGE_TEMP_STRING_PATTERN,
                sumTemp / count));
        return data.getValue();
    }

    private Pair<WeatherForecastForCity, RestResponseForAnalysis> prepareDataForAnalysis(final String city, final String country) {
        String strUrl = urlApiWeatherForecastFiveDays + city + "," + country + STRING_FOR_ADD_APPID_IN_URL + weather_key;
        WeatherForecastForCity weatherForecastForCity = this.getWeatherForecastForCity(strUrl);
        if (!CODE_200.equals(weatherForecastForCity.getCod())) {
            return new Pair<>(null, this.ifCodeIsNot200(weatherForecastForCity.getCod()));
        }
        return new Pair<>(weatherForecastForCity, new RestResponseForAnalysis(CODE_200,""));
    }

    private RestResponseForAnalysis ifCodeIsNot200(final String code) {
        RestResponseForAnalysis result;
        if (CODE_500.equals(code)) result = new RestResponseForAnalysis(CODE_500,CODE_500_VALUE);
            else if (CODE_404.equals(code)) result = new RestResponseForAnalysis(CODE_404,CODE_404_VALUE);
                else result = new RestResponseForAnalysis(CODE_404,CODE_ERROR_VALUE);
        return result;
    }

    private WeatherForecastForCity getWeatherForecastForCity(final String strUrl) {
        WeatherForecastForCity result = new WeatherForecastForCity();
        URL urlToApi;
        try {
            urlToApi = new URL(strUrl);
            try {
                result = this.mapper.readValue(urlToApi, WeatherForecastForCity.class);
            } catch (IOException e) {
                result.setCod(CODE_404);
            }
        } catch (MalformedURLException e) {
           result.setCod(CODE_500);
           return result;
        }
        return result;
    }
}
