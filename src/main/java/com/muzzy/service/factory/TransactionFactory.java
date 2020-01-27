package com.muzzy.service.factory;

import com.muzzy.domain.Transaction;
import com.muzzy.domain.TransactionInput;
import com.muzzy.domain.TransactionOutput;
import com.muzzy.service.TransactionOutputService;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Set;

public class TransactionFactory {
    @Autowired
    private TransactionOutputService transactionOutputService;

    public Transaction sendFunds(PrivateKey privateKey, PublicKey sender, PublicKey receiver, float value) {
        //Quick break!
        if (transactionOutputService.getBalance(sender) < value) {
            System.out.println("You don't have coins for this transaction");
            return null;
        }

        // Mandatory obj.
        Set<TransactionOutput> transactionOutputSet = transactionOutputService.getTransctionByPublicKey(sender);
        ArrayList<TransactionInput> inputs = new ArrayList<>();
        float total = 0F;

        for (TransactionOutput utxo: transactionOutputSet
        ) {
            inputs.add(new TransactionInput(utxo.getId(),utxo));
            total += utxo.getValue();
            if (total > value) break;
        }

        Transaction transaction = new Transaction().builder().sender(sender).reciever(receiver).value(value).inputs(inputs).transactionOutputService(transactionOutputService).build();
        transaction.generateSignature(privateKey);
        return transaction;

    }
}
