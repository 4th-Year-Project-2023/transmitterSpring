package org.example.domain;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterResult;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;


@Component
public class GetParameter {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(GetParameter.class);


    public void printParam(String paramName) {
        log.debug("before ssm");
        AWSSimpleSystemsManagement ssm = AWSSimpleSystemsManagementClientBuilder.standard().withRegion(Regions.AP_SOUTH_1).build();
        log.debug("before getparamreq");
        GetParameterRequest parameterRequest = new GetParameterRequest();
        log.debug("before setParamname");
        parameterRequest.setName(paramName);
        log.debug("before getParamResult");
        GetParameterResult getParameterResult = ssm.getParameter(parameterRequest);
//        System.out.println("Hey");
        System.out.println(getParameterResult.getParameter().getValue());

    }
}
