package com.college.cms.security;

import com.college.cms.modules.user.entity.User;
import com.college.cms.modules.user.entity.UserRole;
import com.college.cms.modules.user.repository.UserRepository;
import com.college.cms.modules.user.repository.UserRoleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class
JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        // Skip if no auth header
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {
            // Validate token
            if (!jwtService.isTokenValid(token)) {
                sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED,
                        "Token is invalid or expired", "AUTH_003");
                return;
            }

            String email = jwtService.extractEmail(token);

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Fetch user from database
                Optional<User> userOptional = userRepository.findByEmail(email);

                if (userOptional.isEmpty()) {
                    sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED,
                            "User not found", "USER_404");
                    return;
                }

                User user = userOptional.get();

                // Check if user is active (treat null as active for backwards compatibility)
                if (user.getIsActive() != null && !user.getIsActive()) {
                    sendErrorResponse(response, HttpServletResponse.SC_FORBIDDEN,
                            "Account is deactivated", "AUTH_403");
                    return;
                }

                // Load user roles as authorities
                List<UserRole> userRoles = userRoleRepository.findByUserId(user.getId());
                List<SimpleGrantedAuthority> authorities = userRoles.stream()
                        .map(ur -> new SimpleGrantedAuthority("ROLE_" + ur.getRole().getCode()))
                        .collect(Collectors.toList());

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(email, null, authorities);

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED,
                    "Authentication failed", "AUTH_001");
        }
    }

    private void sendErrorResponse(HttpServletResponse response, int status,
                                   String message, String errorCode) throws IOException {
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("status", status);
        errorResponse.put("message", message);
        errorResponse.put("errorCode", errorCode);
        errorResponse.put("timestamp", LocalDateTime.now().toString());

        objectMapper.writeValue(response.getOutputStream(), errorResponse);
    }
}