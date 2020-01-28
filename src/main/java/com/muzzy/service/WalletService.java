package com.muzzy.service;

/**
 * Created by Paweł Mazur on 27.01.2020
 *
 * Temporary service for Wallets
 */

import com.muzzy.domain.Wallet;
import org.springframework.stereotype.Component;

@Component
public interface WalletService extends CrudService<Wallet, String> {

}
