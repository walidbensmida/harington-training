package org.example.chapitre1.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Getter
@Setter
@ConfigurationProperties(prefix = "rsa")
public class RsaKeysConfig {

    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;

}
