package curium.api.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {


//	private String jwtSecret="daf66e01593f61a15b857cf433aae03a005812b31234e149036bcc8dee755dbb";
//
//
//	private long jwtExpirationDate=604800000;
//
//	public JwtTokenProvider(String jwtSecret, long jwtExpirationDate) {
//		this.jwtSecret = jwtSecret;
//		this.jwtExpirationDate = jwtExpirationDate;
//	}
//@Value("${app.jwt-secret}")
//private String jwtSecret;
//
//	@Value("${app-jwt-expiration-milliseconds}")
//	private long jwtExpirationDate;

//	public JwtTokenProvider() {
//	}
	private final String jwtSecret;
	private final long jwtExpirationDate;

	public JwtTokenProvider(JwtConfig jwtConfig) {
		this.jwtSecret = jwtConfig.getJwtSecret();
		this.jwtExpirationDate = jwtConfig.getJwtExpirationMilliseconds();
	}
	// generate JWT token
	public String generateToken(Authentication authentication){

		String username = authentication.getName();

		Date currentDate = new Date();

		Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

		String token = Jwts.builder()
				.subject(username)
				.issuedAt(new Date())
				.expiration(expireDate)
				.signWith(key())
				.compact();

		return token;
	}

	private Key key(){
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}

	// get username from JWT token
	public String getUsername(String token){

		return Jwts.parser()
				.verifyWith((SecretKey) key())
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getSubject();
	}

	// validate JWT token
	public boolean validateToken(String token){
		Jwts.parser()
				.verifyWith((SecretKey) key())
				.build()
				.parse(token);
		return true;

	}
}

