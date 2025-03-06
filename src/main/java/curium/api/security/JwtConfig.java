package curium.api.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class JwtConfig {
	private String jwtSecret;
	private long jwtExpirationMilliseconds;

	// Getters et setters
	public String getJwtSecret() {
		return jwtSecret;
	}

	public void setJwtSecret(String jwtSecret) {
		this.jwtSecret = jwtSecret;
	}

	public long getJwtExpirationMilliseconds() {
		return jwtExpirationMilliseconds;
	}

	public void setJwtExpirationMilliseconds(long jwtExpirationMilliseconds) {
		this.jwtExpirationMilliseconds = jwtExpirationMilliseconds;
	}

}
