package com.ufire.authsso.server.properties;

import com.ufire.authsso.model.ClientDetail;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @title: SsoServer
 * @Author ufiredong
 * @Date: 2021/9/24 15:27
 * @Des:
 * @Version 1.0
 */
@ConfigurationProperties(
        prefix = "jwt.sso.server.allow"
)
public class SsoServer {
    private List<ClientDetail> clientDetails;

    public List<ClientDetail> getClientDetails() {
        return clientDetails;
    }

    public void setClientDetails(List<ClientDetail> clientDetails) {
        this.clientDetails = clientDetails;
    }
}
