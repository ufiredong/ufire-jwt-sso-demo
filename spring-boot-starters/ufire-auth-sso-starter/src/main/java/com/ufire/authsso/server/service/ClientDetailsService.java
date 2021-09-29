package com.ufire.authsso.server.service;

import com.ufire.authsso.model.ClientDetail;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @title: ClientDetailsService
 * @Author ufiredong
 * @Date: 2021/9/28 17:33
 * @Des: client 客户端 接口
 * @Version 1.0
 */

public class ClientDetailsService {
    private List<ClientDetail> clientDetails;

    public ClientDetailsService(List<ClientDetail> clientDetails){
        this.clientDetails=clientDetails;
    }


    public Boolean authorize(ClientDetail clientDetail) {
        String clientId = clientDetail.getClientId();
        String secret = clientDetail.getSecret();
        for (ClientDetail client : clientDetails) {
            if (clientId.equals(client.getClientId()) && secret.equals(clientDetail.getSecret())) {
                return true;
            }
        }
        return false;
    }

    public void setClientDetails(List<ClientDetail> clientDetails) {
        this.clientDetails = clientDetails;
    }
}
