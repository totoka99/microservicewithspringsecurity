package jpasecurity.jpasecurity.service;

import jpasecurity.jpasecurity.config.model.SecurityUser;
import jpasecurity.jpasecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsManager implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmailIgnoreCase(username).map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException(username));

    }
}
