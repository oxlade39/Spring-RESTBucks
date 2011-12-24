package com.fb.restbucks.integration.givenwhenthen;

import com.fb.restbucks.integration.container.Tomcat;
import com.fb.restbucks.integration.builder.OrderBuilder;
import com.googlecode.yatspec.state.givenwhenthen.CapturedInputAndOutputs;
import com.googlecode.yatspec.state.givenwhenthen.GivensBuilder;
import com.googlecode.yatspec.state.givenwhenthen.InterestingGivens;
import com.googlecode.yatspec.state.givenwhenthen.StateExtractor;
import org.springframework.http.ResponseEntity;

import static com.fb.restbucks.integration.builder.OrderBuilder.forAnAmericano;
import static com.fb.restbucks.integration.givenwhenthen.StateExtractors.theResponse;
import static com.fb.restbucks.integration.givenwhenthen.Whens.aPUTRequestIsMadeTo;

/**
 * @author oxladed
 */
public class Givens {
    public static GivensBuilder aDeployedServer() {
        // may want to actually start the server here but at the moment maven is doing this
        return new GivensBuilder() {
            @Override
            public InterestingGivens build(InterestingGivens interestingGivens) throws Exception {
                new Tomcat().start("/restbucks", 8080);
                interestingGivens.add("Base URL", "http://localhost:8080/restbucks");
                return interestingGivens;
            }
        };
    }

    public static GivensBuilder anOrderExistsWithId(final String orderId) {
        return new GivensBuilder() {
            @Override
            public InterestingGivens build(InterestingGivens interestingGivens) throws Exception {
                aDeployedServer().build(interestingGivens);
                CapturedInputAndOutputs temporary = new CapturedInputAndOutputs();
                OrderBuilder order = forAnAmericano().withId(orderId);
                aPUTRequestIsMadeTo("/orders/", order).execute(interestingGivens, temporary);
                StateExtractor<ResponseEntity<String>> responseExtractor = theResponse();
                ResponseEntity<String> responseEntity = responseExtractor.execute(temporary);
                interestingGivens.add("Order Location", responseEntity.getHeaders().getLocation());
                return interestingGivens;
            }
        };
    }
}
