package ci.digitalacademy.reservationimmobiliere.web.resources;


import ci.digitalacademy.reservationimmobiliere.models.User;
import ci.digitalacademy.reservationimmobiliere.services.CustomerService;
import ci.digitalacademy.reservationimmobiliere.services.OwnerService;
import ci.digitalacademy.reservationimmobiliere.services.UserService;
import ci.digitalacademy.reservationimmobiliere.services.dto.*;
import ci.digitalacademy.reservationimmobiliere.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthenticateResource {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserService userService;
    private final OwnerService ownerService;
    private final CustomerService customerService;


    @Value("${security.authentication.jwt.token-validity-in-seconds:0}")
    private long tokenValidityInSeconds;

    @Value("${security.authentication.jwt.token-validity-in-seconds-for-remember-me:0}")
    private long tokenValidityInSecondsForRememberMe;

    private final JwtEncoder jwtEncoder;

    @PostMapping("/authenticate")
    public JWTTokenDTO authorize(@RequestBody LoginDTO login) {
        UserDTO byUserName = userService.getByEmail(login.getEmail());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                login.getEmail(),
                login.getPassword()
        );
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = createToken(authentication, login.isRememberMe(),byUserName);
        return new JWTTokenDTO(jwt);
    }



    public String createToken(Authentication authentication, boolean rememberMe, UserDTO userDTO) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        Instant now = Instant.now();
        Instant validity;
        if (rememberMe) {
            validity = now.plus(this.tokenValidityInSecondsForRememberMe, ChronoUnit.SECONDS);
        } else {
            validity = now.plus(this.tokenValidityInSeconds, ChronoUnit.SECONDS);
        }

        if (authorities.contains("OWNER")){
            Optional<OwnerDTO> byUserId = ownerService.findByUserId(userDTO.getId());
            OwnerDTO ownerDTO = byUserId.get();
            JwtClaimsSet claims = JwtClaimsSet.builder()
                    .issuedAt(now)
                    .expiresAt(validity)
                    .subject(authentication.getName())
                    .claim(SecurityUtils.AUTHORITHIES_KEY, authorities)
                    .claim("id",ownerDTO.getIdPerson())
                    .build();
            JwsHeader jwsHeader = JwsHeader.with(SecurityUtils.JWT_ALGORITHM).build();
            return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
        }
        else if (authorities.contains("ADMIN")){
            Optional<UserDTO> byUserId = userService.getById(userDTO.getId());
            UserDTO userDTO1 = byUserId.get();
            JwtClaimsSet claims = JwtClaimsSet.builder()
                    .issuedAt(now)
                    .expiresAt(validity)
                    .subject(authentication.getName())
                    .claim(SecurityUtils.AUTHORITHIES_KEY, authorities)
                    .claim("id",userDTO1.getId())
                    .build();
            JwsHeader jwsHeader = JwsHeader.with(SecurityUtils.JWT_ALGORITHM).build();
            return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
        }
        else{
            Optional<CustomerDTO> byUserId = customerService.findByUserId(userDTO.getId());
            CustomerDTO customerDTO = byUserId.get();
            JwtClaimsSet claims = JwtClaimsSet.builder()
                    .issuedAt(now)
                    .expiresAt(validity)
                    .subject(authentication.getName())
                    .claim(SecurityUtils.AUTHORITHIES_KEY, authorities)
                    .claim("id",customerDTO.getIdPerson())
                    .build();
            JwsHeader jwsHeader = JwsHeader.with(SecurityUtils.JWT_ALGORITHM).build();
            return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
        }



    }

}
