package com.fb.restbucks.integration;

import com.fb.restbucks.domain.Resource;
import com.googlecode.yatspec.state.givenwhenthen.ActionUnderTest;
import com.googlecode.yatspec.state.givenwhenthen.CapturedInputAndOutputs;
import com.googlecode.yatspec.state.givenwhenthen.InterestingGivens;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static com.fb.restbucks.config.DispatcherConfig.messageConverters;
import static java.util.Arrays.asList;

/**
 * @author oxladed
 */
public class Whens {

    public static <T> ActionUnderTest aPutRequestIsMadeTo(final String relativePath, final T body) {
        return new ActionUnderTest() {
            @Override
            public CapturedInputAndOutputs execute(InterestingGivens interestingGivens, CapturedInputAndOutputs capturedInputAndOutputs) throws Exception {
                capturedInputAndOutputs.add("Request: relative path", relativePath);
                capturedInputAndOutputs.add("Request entity: ", body);

                HttpEntity<T> requestEntity = new HttpEntity<T>(body, headers());
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.setMessageConverters(messageConverters());

                String baseUrl = interestingGivens.getType("Base URL", String.class);
                String path = baseUrl + relativePath;
                ResponseEntity<Resource> response = restTemplate.exchange(path, HttpMethod.POST, requestEntity, Resource.class);

                capturedInputAndOutputs.add("Response", response);

                return capturedInputAndOutputs;
            }
        };
    }


    private static MultiValueMap<String, String> headers() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(asList(MediaType.APPLICATION_JSON));
        return httpHeaders;
    }
}
