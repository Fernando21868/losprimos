package com.example.backend.service.Implementation;

import com.example.backend.config.PasswordEncoderConfig;
import com.example.backend.dto.request.UserAuthenticateDtoRequest;
import com.example.backend.dto.response.UserAuthenticateDtoResponse;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.model.User;
import com.example.backend.repository.IUserRepository;
import com.example.backend.service.Interface.ISessionService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.backend.util.CONSTANTS.SECRET_KEY_TOKEN;

@Service
public class SessionServiceImpl implements ISessionService {
    private final IUserRepository<User> userRepository;
    private final PasswordEncoderConfig passwordEncoderConfig;

    public SessionServiceImpl ( IUserRepository<User> userRepository, PasswordEncoderConfig passwordEncoderConfig ) {
        this.userRepository = userRepository;
        this.passwordEncoderConfig = passwordEncoderConfig;
    }

    @Override
    public UserAuthenticateDtoResponse login (UserAuthenticateDtoRequest userAuthenticateDtoRequest ) {
        //Voy a la base de datos y reviso que el usuario y contraseña existan.
        // ToDo: se podria agregar alguna libreria para encriptar la password

        String usernameDto = userAuthenticateDtoRequest.getUsername();
        String passwordDto = userAuthenticateDtoRequest.getPassword();

        User user = userRepository.findByUsername(usernameDto).orElseThrow(()-> new UserNotFoundException("No se encontre el usuario con el nombre de usuario dado"));
        if(!passwordEncoderConfig.passwordEncoder().matches(passwordDto, user.getPassword())){
            throw new UserNotFoundException("User y/o contraseña incorrecto");
        }

        // User user = userRepository.findByUsernameAndPassword(username, userAuthenticateDtoRequest.getPassword())
        //  .orElseThrow(UserNotFoundException::new);

        List<String> roles = user.getRoles()
          .stream()
          .map(e -> e.getRol().getText())
          .collect(Collectors.toList());

        String token = getJWTToken(usernameDto, roles);

        return new UserAuthenticateDtoResponse(usernameDto, token);
    }

    /**
     * Genera un token para un usuario específico, válido por 10'
     *
     * @param username - user to login
     * @param roles    - collection of user's roles
     * @return String
     */
    /* lista de roles */
    private String getJWTToken ( String username, List<String> roles ) {

        List<GrantedAuthority> grantedAuthorities = roles
          .stream()
          .map(AuthorityUtils::commaSeparatedStringToAuthorityList)
          .flatMap(Collection::stream)
          .collect(Collectors.toList());

        LocalDateTime expired = LocalDateTime.now()
          .plusMinutes(120);
        Date expiredTime = Date.from(expired.atZone(ZoneId.systemDefault())
          .toInstant());

        String token = Jwts
          .builder()
          .setId("softtekJWT")
          .setSubject(username)
          .claim("authorities",
            grantedAuthorities
              .stream()
              .map(GrantedAuthority::getAuthority)
              .collect(Collectors.toList())
          )
          .setIssuedAt(new Date(System.currentTimeMillis()))
          .setExpiration(expiredTime)
          .signWith(SignatureAlgorithm.HS512, SECRET_KEY_TOKEN.getBytes())
          .compact();

        return "Bearer " + token;
    }

    /**
     * Decodifica un token para poder obtener los componentes que contiene el mismo
     *
     * @param token tokenJWT
     * @return Claims
     */
    private static Claims decodeJWT ( String token ) {
        Claims claims = Jwts.parser()
          .setSigningKey(SECRET_KEY_TOKEN.getBytes())
          .parseClaimsJws(token)
          .getBody();
        return claims;
    }

    /**
     * Permite obtener el username según el token indicado
     *
     * @param token token JWT
     * @return String User's Email
     */
    public static String getUsername ( String token ) {
        Claims claims = decodeJWT(token);
        return claims.get("sub", String.class);
    }

}
