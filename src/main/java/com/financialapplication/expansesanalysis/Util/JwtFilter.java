package com.financialapplication.expansesanalysis.Util;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtSecurity jwtSecurity;

    private final UserDetailsService userDetailsService;

    public JwtFilter(JwtSecurity jwtSecurity, UserDetailsService userDetailsService) {
        this.jwtSecurity = jwtSecurity;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws IOException {

        String authorization = request.getHeader("Authorization");
        String token = null;
        String mobileNo = null;
        try {
            if (authorization != null && authorization.startsWith("Bearer ")) {
                token = authorization.substring(7).trim();
                mobileNo = jwtSecurity.extractMobile(token);

            }
            if (mobileNo != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(mobileNo);
                if (jwtSecurity.validateToken(token, userDetails.getUsername())) {

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new
                            UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.setStatus(401);
            response.setContentType("application/json");
            response.getWriter().write(
                    new ObjectMapper().writeValueAsString(
                            Map.of(
                                    "error", "Internal Server Error",
                                    "message", "Unauthorized Access",
                                    "serverMessage", e.getMessage()
                            )
                    )
            );
        }


    }
}
