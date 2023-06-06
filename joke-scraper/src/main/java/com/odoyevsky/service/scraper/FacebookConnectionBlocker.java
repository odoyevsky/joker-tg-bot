package com.odoyevsky.service.scraper;

import com.odoyevsky.exception.BlockedUrlException;
import org.htmlunit.WebClient;
import org.htmlunit.WebRequest;
import org.htmlunit.WebResponse;
import org.htmlunit.util.WebConnectionWrapper;

import java.io.IOException;

public class FacebookConnectionBlocker extends WebConnectionWrapper {
    public FacebookConnectionBlocker(WebClient webClient) {
        super(webClient);
    }

    @Override
    public WebResponse getResponse(WebRequest request) throws IOException {
        if (request.getUrl().getHost().contains("facebook.com")) {
            throw new BlockedUrlException("Blocked URL: " + request.getUrl());
        }
        return super.getResponse(request);
    }
}