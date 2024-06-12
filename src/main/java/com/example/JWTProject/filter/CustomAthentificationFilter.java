package com.example.JWTProject.filter;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
public class CustomAthentificationFilter extends UsernamePasswordAuthenticationFilter{
    private final AuthenticationManager authenticationManager;

    public CustomAthentificationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        System.out.println("fail");
        super.unsuccessfulAuthentication(request, response, failed);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println("success");
    Algorithm algorithm =Algorithm.HMAC256("secret".getBytes());
    String access_token = JWT.create().withSubject( userDetails.getUsername()).
            withExpiresAt(new Date(System.currentTimeMillis()+10*60*1000)).
            withIssuer(request.getRequestURL().toString()).
            withClaim("roles",userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())).sign(algorithm);
    String refresh_token = JWT.create().withSubject( userDetails.getUsername()).
                withExpiresAt(new Date(System.currentTimeMillis()+30*60*1000)).
                withIssuer(request.getRequestURL().toString()).sign(algorithm);
   response.setHeader("access_token",access_token);
   response.setHeader("refresh_token",refresh_token);

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("hello");
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        log.info("Username is :{}",username);
        log.info("password is :{}",password);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
        System.out.println("testt");

        return authenticationManager.authenticate(authenticationToken);
    }
}
