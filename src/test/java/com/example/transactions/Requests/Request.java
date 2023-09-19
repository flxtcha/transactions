package com.example.transactions.Requests;

import java.net.http.HttpResponse;

public interface Request {
    HttpResponse<String> send(String uri) ;
    HttpResponse<String> send(String uri, String body) ;
}
