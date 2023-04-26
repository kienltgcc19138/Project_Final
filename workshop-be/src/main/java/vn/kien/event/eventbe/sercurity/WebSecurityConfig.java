package vn.kien.event.eventbe.sercurity;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import vn.kien.event.eventbe.exception.AccessDeniedExceptionHandler;
import vn.kien.event.eventbe.exception.AuthenticationExceptionHandler;
import vn.kien.event.eventbe.jwt.AuthTokenFilter;
import vn.kien.event.eventbe.services.AuthService;

/**
 * Le-Hong-Quan
 * Date: 19/02/2023
 * Time: 11:20
 */
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = {"vn.kien.event.eventbe"})
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final AccessDeniedExceptionHandler accessDeniedExceptionHandler;
    private final AuthenticationExceptionHandler authenticationExceptionHandler;
    private final AuthService authService;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(authService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers("/**").permitAll()
                .antMatchers("/src/**").permitAll()
//                .antMatchers("/status**", "/topic", "/ws/**").permitAll()
                .anyRequest().authenticated().and().exceptionHandling().authenticationEntryPoint(authenticationExceptionHandler)
                .and().exceptionHandling().accessDeniedHandler(accessDeniedExceptionHandler)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
