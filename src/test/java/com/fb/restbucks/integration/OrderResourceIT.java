package com.fb.restbucks.integration;

import com.fb.restbucks.domain.*;
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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.*;

import javax.swing.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.fb.restbucks.integration.Givens.aDeployedServer;
import static com.fb.restbucks.integration.ResponseEntityMatchers.hasStatus;
import static com.fb.restbucks.integration.StateExtractors.theResponse;
import static com.fb.restbucks.integration.Whens.aPutRequestIsMadeTo;
import static com.googlecode.yatspec.internal.totallylazy.Sequences.sequence;
import static java.util.Collections.singletonMap;
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

    private Order forAnAmericano() {
        return order();
    }

    private Order order() {
        Order order = new Order();
        order.setId("34390340");
        order.setStatus(OrderStatus.PENDING);
        Item item = new Item();
        item.setMilk(Milk.SKIM);
        item.setName("americano");
        item.setQuantity(1);
        item.setSize(Size.SMALL);
        order.setItems(Arrays.asList(item));
        return order;
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
