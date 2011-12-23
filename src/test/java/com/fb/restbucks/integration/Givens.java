package com.fb.restbucks.integration;

import com.googlecode.yatspec.state.givenwhenthen.GivensBuilder;
import com.googlecode.yatspec.state.givenwhenthen.InterestingGivens;

/**
 * @author oxladed
 */
public class Givens {
    public static GivensBuilder aDeployedServer() {
        // may want to actually start the server here but at the moment maven is doing this
        return new GivensBuilder() {
            @Override
            public InterestingGivens build(InterestingGivens interestingGivens) throws Exception {
                interestingGivens.add("Base URL", "http://localhost:8080/restbucks");
                return interestingGivens;
            }
        };
    }
}
