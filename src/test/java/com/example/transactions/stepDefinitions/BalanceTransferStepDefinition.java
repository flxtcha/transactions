package com.example.transactions.stepDefinitions;

import com.example.transactions.Requests.Request;
import com.example.transactions.Requests.RequestFactory;
import com.example.transactions.Requests.RequestType;
import com.example.transactions.model.Account;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.http.HttpResponse;

public class BalanceTransferStepDefinition{
    private BigDecimal initialBalance;
    private BigDecimal  actualBalance;
    private BigDecimal expectedBalance;
    private String fromAccount;
    private final RequestFactory requestFactory;

    public BalanceTransferStepDefinition(RequestFactory requestFactory) {
        this.requestFactory = requestFactory;
    }

    @Given("Bob has a balance of {int}")
    public void bobHasABalanceOf(Integer int1) {

        initialBalance = BigDecimal.valueOf(int1);

        String jsonBody = "{"
                + "\"accountName\": \"Bob Smith\","
                + "\"accountBalance\": "+ initialBalance
                + "}";

        Request postRequest = requestFactory.createRequest(RequestType.POST);
        HttpResponse<String> response = postRequest.send("http://localhost:8080/api/accounts/create", jsonBody);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Account bob = objectMapper.readValue(response.body(), Account.class);
            fromAccount = bob.getId();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @When("he transfers {int} from his account")
    public void heTransfersFromHisAccount(Integer int1) {
        Request postRequest = requestFactory.createRequest(RequestType.POST);
        HttpResponse<String> response = postRequest
                .send("http://localhost:8080/api/accounts/transfer?fromAccount="+fromAccount+"&toAccount=650041a375396c4834b66d07&amount="+int1);
    }
    @Then("his account should have a balance of {int}")
    public void hisAccountShouldHaveABalanceOf(Integer int1) {

        Request getRequest = requestFactory.createRequest(RequestType.GET);
        HttpResponse<String> response = getRequest
                .send("http://localhost:8080/api/accounts/find?accountId="+fromAccount);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Account bob2 = objectMapper.readValue(response.body(), Account.class);
            actualBalance = bob2.getAccountBalance();
            expectedBalance = BigDecimal.valueOf(int1);
            Assert.assertEquals(expectedBalance, actualBalance);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
