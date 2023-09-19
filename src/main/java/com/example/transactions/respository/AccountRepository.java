package com.example.transactions.respository;

import com.example.transactions.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {

    /*Todo
        Implement custom queries
     */
}
