package ci.digitalacademy.reservationimmobiliere.utils;

import org.springframework.security.oauth2.jose.jws.MacAlgorithm;

public final class SecurityUtils {

    public static final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS512;

    public static final String AUTHORITHIES_KEY = "auth";
}
