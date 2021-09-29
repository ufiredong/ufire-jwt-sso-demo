package com.ufire.endpoint;

import com.ufire.model.ClientDetail;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @title: ClientDetailsService
 * @Author ufiredong
 * @Date: 2021/9/28 17:33
 * @Des: client 客户端 接口
 * @Version 1.0
 */
@Component
public class ClientDetailsService {
    private List<ClientDetail> clientDetails;

    ClientDetailsService(List<ClientDetail> clientDetails) {
        clientDetails.add(new ClientDetail("client1", "123456", "http://localhost:8099"));
        clientDetails.add(new ClientDetail("client2", "123456", "http://localhost:8091"));
        clientDetails.add(new ClientDetail("client3", "123456", "http://localhost:8092"));
        this.clientDetails = clientDetails;
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
}
