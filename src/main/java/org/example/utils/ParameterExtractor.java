package org.example.utils;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class ParameterExtractor {

    @Value("${aws.parameter.name}")
    private String awsParameterName;
    @Autowired
    ApplicationProperties applicationProperties;
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ParameterExtractor.class);


    public String getParametersFromAWS(String paramName) {

        log.info("Getting parameters from aws");
        AWSSimpleSystemsManagement ssm = AWSSimpleSystemsManagementClientBuilder.standard().withRegion(Regions.AP_SOUTH_1).build();

        GetParameterRequest parameterRequest = new GetParameterRequest();

        parameterRequest.setName(paramName);

        GetParameterResult getParameterResult = ssm.getParameter(parameterRequest);
        return getParameterResult.getParameter().getValue();

    }

    public void initializeParameters() {
        log.debug("Initializing ApplicationProperties Object");
        String parameterJsonString = getParametersFromAWS(awsParameterName);
        try {
            applicationProperties = new ObjectMapper().readValue(parameterJsonString, ApplicationProperties.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ApplicationProperties getApplicationProperties() {
        return applicationProperties;
    }

    public void setApplicationProperties(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }
}
