package com.fb.restbucks.integration.renderer;

import com.googlecode.yatspec.rendering.Renderer;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

/**
* @author dan
*/
public class ResponseEntityRenderer implements Renderer<ResponseEntity> {
    @Override
    public String render(ResponseEntity responseEntity) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<pre>");
        appendStatusCode(responseEntity, stringBuilder);
        appendHeaders(responseEntity, stringBuilder);
        appendBody(responseEntity, stringBuilder);
        stringBuilder.append("</pre>");
        return stringBuilder.toString();
    }

    private StringBuilder appendBody(ResponseEntity responseEntity, StringBuilder stringBuilder) {
        stringBuilder.append("<p>");
        stringBuilder.append(responseEntity.getBody()).append("</p>");
        return stringBuilder;
    }

    private void appendHeaders(ResponseEntity responseEntity, StringBuilder stringBuilder) {
        stringBuilder.append("<p>");
        for (Map.Entry<String, List<String>> headers : responseEntity.getHeaders().entrySet()) {
            stringBuilder.append(headers.getKey()).append(" : ").append(headers.getValue());
            stringBuilder.append("<br/>");
        }
        stringBuilder.append("</p>");
    }

    private void appendStatusCode(ResponseEntity responseEntity, StringBuilder stringBuilder) {
        stringBuilder.append("<p>");
        stringBuilder.append(responseEntity.getStatusCode());
        stringBuilder.append("</p>");
    }
}
