package uk.gov.digital.ho.proving.income;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import uk.gov.digital.ho.proving.income.acl.EarningsService;
import uk.gov.digital.ho.proving.income.acl.JSONObjectBackedEarningsService;

import java.text.SimpleDateFormat;

@Configuration
@PropertySources(value = {
        @PropertySource(value = "classpath:application.properties"),
        @PropertySource(value = "classpath:test-application.properties", ignoreResourceNotFound = true)
})
public class ServiceConfiguration {
    private static Logger LOGGER = LoggerFactory.getLogger(ServiceConfiguration.class);

    @Autowired
    private Environment env;

    @Bean
    public Jackson2ObjectMapperBuilder jacksonBuilder() {
        Jackson2ObjectMapperBuilder b = new Jackson2ObjectMapperBuilder();
        b.indentOutput(true).dateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        return b;
    }

    @Bean
    public ObjectMapper getMapper() {
        ObjectMapper m = new ObjectMapper();
        m.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        m.enable(SerializationFeature.INDENT_OUTPUT);
        return m;
    }

    @Bean
    public EarningsService getRevenueService() {
        return new JSONObjectBackedEarningsService();
    }

}
