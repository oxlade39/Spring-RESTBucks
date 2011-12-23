package com.fb.restbucks.integration;

import com.googlecode.yatspec.junit.Notes;
import com.googlecode.yatspec.junit.SpecResultListener;
import com.googlecode.yatspec.junit.SpecRunner;
import com.googlecode.yatspec.junit.WithCustomResultListeners;
import com.googlecode.yatspec.rendering.NotesRenderer;
import com.googlecode.yatspec.rendering.html.HtmlResultRenderer;
import com.googlecode.yatspec.rendering.html.HyperlinkRenderer;
import com.googlecode.yatspec.rendering.html.index.HtmlIndexRenderer;
import com.googlecode.yatspec.rendering.html.tagindex.HtmlTagIndexRenderer;
import com.googlecode.yatspec.state.givenwhenthen.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.*;

import static com.fb.restbucks.integration.givenwhenthen.Givens.aDeployedServer;
import static com.fb.restbucks.integration.givenwhenthen.Givens.anOrderExistsWithId;
import static com.fb.restbucks.integration.builder.OrderBuilder.forAnAmericano;
import static com.fb.restbucks.integration.matcher.ResponseEntityMatchers.hasStatus;
import static com.fb.restbucks.integration.givenwhenthen.StateExtractors.theResponse;
import static com.fb.restbucks.integration.givenwhenthen.Whens.aGetRequestIsMadeTo;
import static com.fb.restbucks.integration.givenwhenthen.Whens.aPutRequestIsMadeTo;
import static com.googlecode.yatspec.internal.totallylazy.Sequences.sequence;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;

/**
 * @author oxladed
 */
@RunWith(SpecRunner.class)
public class OrderResourceIT extends TestState implements WithCustomResultListeners {

    @Test
    public void postAnOrder() throws Exception {
        given(aDeployedServer());
        when(aPutRequestIsMadeTo("/orders/", forAnAmericano()));
        then(theResponse(), hasStatus(equalTo(HttpStatus.CREATED)));
    }

    @Test
    public void getAnExistingOrder() throws Exception {
        given(anOrderExistsWithId("77777"));
        when(aGetRequestIsMadeTo("/orders/77777"));
        then(theResponse(), hasStatus(equalTo(HttpStatus.OK)));
    }

    @Override
    public Iterable<SpecResultListener> getResultListeners() throws Exception {
        return sequence(fancyHtmlRenderer(),
                new HtmlIndexRenderer(),
                new HtmlTagIndexRenderer());
    }

    private HtmlResultRenderer fancyHtmlRenderer() {
        return new HtmlResultRenderer()
                .withCustomRenderer(Notes.class, new HyperlinkRenderer(new NotesRenderer(), "(?:#)([^\\s]+)", "<a href='http://localhost:8080/'>$1</a>"));
    }
}
