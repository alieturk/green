package app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Contains app configuration for
 * Json Web Tokens (JWT's).
 *
 * The information in the variables is configured through
 * either the 'Application.Properties' or a yml file.
 */
@Configuration
public class JWTConfig {

    //Issuer of the token (our team)
    @Value("${greenology.app.jwt.issuer}")
    private String issuer;

    //Passphrase/secret key being used to
    // encode the tokens with
    @Value("${greenology.app.jwt.passphrase}")
    private String passphrase;

    //Contains the amount of time a token
    // is valid in seconds
    @Value("${greenology.app.jwt.expirationTime}")
    private int tokenDurationValidity;

    //Contains the URI paths that require
    //user authentication.
    @Value("#{'${greenology.app.jwt.securedPaths}'.split(',')}")
    private List<String> SECURED_PATHS;

    public List<String> getSecuredPaths() {
        return SECURED_PATHS;
    }

    public String getIssuer() {
        return issuer;
    }

    public String getPassphrase() {
        return passphrase;
    }

    public int getTokenDurationValidity() {
        return tokenDurationValidity;
    }
}
