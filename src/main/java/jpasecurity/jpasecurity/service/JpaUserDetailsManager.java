package jpasecurity.jpasecurity.service;

import jpasecurity.jpasecurity.config.SecurityUser;
import jpasecurity.jpasecurity.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsManager implements UserDetailsService {
    private final UserRepository userRepository;

    public JpaUserDetailsManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsernameIgnoreCase(username).map(SecurityUser::new)
                .orElseThrow(()->new UsernameNotFoundException(username));
    }
}
