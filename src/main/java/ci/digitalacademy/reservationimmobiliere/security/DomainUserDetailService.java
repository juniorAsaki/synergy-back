package ci.digitalacademy.reservationimmobiliere.security;

import ci.digitalacademy.reservationimmobiliere.Repository.UserRepository;
import ci.digitalacademy.reservationimmobiliere.models.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class DomainUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<User> user = userRepository.findByEmail(username);

        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        GrantedAuthority authority = new SimpleGrantedAuthority(user.get().getRole().getRole());

        List<GrantedAuthority> authorities = Collections.singletonList(authority);


        return user.map(userRecover -> org.springframework.security.core.userdetails.User.builder()
                .username(userRecover.getEmail())
                .password(userRecover.getPassword())
                .authorities(authorities)
                //      .roles()
                .build()).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
