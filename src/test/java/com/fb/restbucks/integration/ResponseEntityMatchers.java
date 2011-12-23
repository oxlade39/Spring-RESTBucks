package com.fb.restbucks.integration;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author oxladed
 */
public class ResponseEntityMatchers {

    public static <T> Matcher<ResponseEntity<T>> hasStatus(Matcher<? super HttpStatus> expectedStatus) {
        return new FeatureMatcher<ResponseEntity<T>, HttpStatus>(expectedStatus, "the response status", "http status"){
            @Override
            protected HttpStatus featureValueOf(ResponseEntity<T> responseEntity) {
                return responseEntity.getStatusCode();
            }
        };
    }
}
