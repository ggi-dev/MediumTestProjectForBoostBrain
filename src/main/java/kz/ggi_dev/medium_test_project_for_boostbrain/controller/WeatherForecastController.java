package kz.ggi_dev.medium_test_project_for_boostbrain.controller;

import kz.ggi_dev.medium_test_project_for_boostbrain.service.AnalysisWeatherService;
import kz.ggi_dev.medium_test_project_for_boostbrain.service.RestResponseForAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherForecastController {

    @Autowired
    private AnalysisWeatherService analysisWeatherService;

    @RequestMapping(value = "/weather/five-days/analysis/mintemp", method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponseForAnalysis getMinTempForCityForFiveDays(final String city, final String country) {
        RestResponseForAnalysis result = analysisWeatherService.analysisMinTempForFiveDays(city, country);
        return result;
    }

    @RequestMapping(value = "/weather/five-days/analysis/maxtemp", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponseForAnalysis getMaxTempForCityForFiveDays(final String city, final String country) {
        RestResponseForAnalysis result = analysisWeatherService.analysisMaxTempForFiveDays(city, country);
        return result;
    }

    @RequestMapping(value = "/weather/five-days/analysis/averagetemp", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponseForAnalysis getAverageTempForCityForFiveDays(final String city, final String country) {
        RestResponseForAnalysis result = analysisWeatherService.analysisAverageTempForFiveDays(city, country);
        return result;
    }
}

