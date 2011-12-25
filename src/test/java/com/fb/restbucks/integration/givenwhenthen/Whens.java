package com.fb.restbucks.integration.givenwhenthen;

import com.fb.restbucks.domain.Resource;
import com.fb.restbucks.integration.builder.NullObjectBuilder;
import com.fb.restbucks.integration.builder.TestBuilder;
import com.googlecode.yatspec.state.givenwhenthen.ActionUnderTest;
import com.googlecode.yatspec.state.givenwhenthen.CapturedInputAndOutputs;
import com.googlecode.yatspec.state.givenwhenthen.InterestingGivens;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static com.fb.restbucks.config.DispatcherConfig.messageConverters;
import static com.fb.restbucks.integration.builder.NullObjectBuilder.nullObject;
import static java.util.Arrays.asList;

/**
 * @author oxladed
 */
public class Whens {

    public static <T> ActionUnderTest aPOSTRequestIsMadeTo(final String relativePath, final TestBuilder<T> bodyBuilder) {
        return new RequestExecutor<T>(relativePath, bodyBuilder, HttpMethod.POST);
    }
    
    public static <T> ActionUnderTest aGETRequestIsMadeTo(final String relativePath) {
        TestBuilder<T> nullObject = nullObject();
        return new RequestExecutor<T>(relativePath, nullObject, HttpMethod.GET);
    }

    private static MultiValueMap<String, String> headers() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(asList(MediaType.APPLICATION_JSON));
        return httpHeaders;
    }

    private static class RequestExecutor<T> implements ActionUnderTest {
        private final String relativePath;
        private final TestBuilder<T> bodyBuilder;
        private final HttpMethod method;

        public RequestExecutor(String relativePath, TestBuilder<T> bodyBuilder, HttpMethod method) {
            this.relativePath = relativePath;
            this.bodyBuilder = bodyBuilder;
            this.method = method;
        }

        @Override
        public CapturedInputAndOutputs execute(InterestingGivens interestingGivens, CapturedInputAndOutputs capturedInputAndOutputs) throws Exception {
            T body = bodyBuilder.build();
            capturedInputAndOutputs.add("Request: relative path", relativePath);
            capturedInputAndOutputs.add("Request entity: ", body);

            HttpEntity<T> requestEntity = new HttpEntity<T>(body, headers());
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.setMessageConverters(messageConverters());

            String baseUrl = interestingGivens.getType("Base URL", String.class);
            String path = baseUrl + relativePath;
            ResponseEntity<Resource> response = restTemplate.exchange(path, method, requestEntity, Resource.class);

            capturedInputAndOutputs.add("Response", response);

            return capturedInputAndOutputs;
        }
    }
}
