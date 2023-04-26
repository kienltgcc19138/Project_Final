package vn.kien.event.eventbe.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.kien.event.eventbe.entity.Users;
import vn.kien.event.eventbe.repository.IUsersRepository;


@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final IUsersRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Không tồn tại tài khoản " + username));
    }
}
