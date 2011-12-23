package com.fb.restbucks.integration.givenwhenthen;

import com.googlecode.yatspec.state.givenwhenthen.CapturedInputAndOutputs;
import com.googlecode.yatspec.state.givenwhenthen.StateExtractor;
import org.springframework.http.ResponseEntity;

/**
 * @author oxladed
 */
public class StateExtractors {
    public static <T> StateExtractor<ResponseEntity<T>> theResponse() {
        return new StateExtractor<ResponseEntity<T>>() {
            @Override
            public ResponseEntity<T> execute(CapturedInputAndOutputs capturedInputAndOutputs) throws Exception {
                return capturedInputAndOutputs.getType("Response", ResponseEntity.class);
            }
        };
    }
}
