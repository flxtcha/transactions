package com.example.transactions.Requests;

import java.io.IOException;

public class RequestFactory {

    public Request createRequest(RequestType requestType){
        switch (requestType) {
            case GET:
                return new GetRequest();
            case POST:
                return new PostRequest();
        }
        return null;
    }
    public void sendRequest(Request request, String uri) throws IOException {
        request.send(uri);
    }
}
