package com.codingshuttle.youtube.hospitalManagement.security;

import com.codingshuttle.youtube.hospitalManagement.dto.LoginRequestDto;
import com.codingshuttle.youtube.hospitalManagement.dto.LoginResponseDto;
import com.codingshuttle.youtube.hospitalManagement.dto.SignupResponseDto;
import com.codingshuttle.youtube.hospitalManagement.entity.User;
import com.codingshuttle.youtube.hospitalManagement.entity.type.AuthProviderType;
import com.codingshuttle.youtube.hospitalManagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    @Autowired
    private AuthUtil authUtil;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponseDto login(LoginRequestDto loginRequestDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
        );

        User user = (User) authentication.getPrincipal();

        String token = authUtil.generateAccessToken(user);

        return new LoginResponseDto(token, user.getId());
    }

    public User signUpInternal(LoginRequestDto signupRequestDto, AuthProviderType authProviderType, String providerId){
        User user = userRepository.findByUsername(signupRequestDto.getUsername()).orElse(null);

        if(user!=null){
            throw new IllegalArgumentException("User with same username already exists");
        }

        user =  userRepository.save(
                User.builder()
                        .username(signupRequestDto.getUsername())
                        .providerType(authProviderType)
                        .providerId(providerId)
                        .build()
        );

        if(authProviderType == AuthProviderType.EMAIL){
            user.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));
        }

        return userRepository.save(user);
    }

    public SignupResponseDto signup(LoginRequestDto signupRequestDto) {
        User user = signUpInternal(signupRequestDto, AuthProviderType.EMAIL, null);
        return new SignupResponseDto(user.getId(), user.getUsername());
    }

    @Transactional
    public ResponseEntity<LoginResponseDto> handleOAuth2LoginRequest(OAuth2User oAuth2User, String registrationId){
        AuthProviderType authProviderType = authUtil.getProviderTypeFromRegistrationId(registrationId);
        String providerId = authUtil.determineProviderIdFromOAuth2User(oAuth2User, registrationId);

        // Check weather user already presetnt with provider type and id
        User user = userRepository.findByProviderIdAndProviderType(providerId, authProviderType).orElse(null);

        String email = oAuth2User.getAttribute("email");

        User userEmail = userRepository.findByUsername(email).orElse(null);

        // If user exists then login if not create new user
        if(user==null && userEmail==null){
            // signup flow
            String username = authUtil.determineUsernameFrom0Auth2User(oAuth2User,registrationId,providerId);
            user = signUpInternal(new LoginRequestDto(username,null), authProviderType, providerId);
        } else if (user != null) {
            // User already have account (logged in using OAuth)

            // If Email is not written yet and email is not null
            if(email!=null && !email.isBlank() && !email.equals(user.getUsername())){
                user.setUsername(email);
                userRepository.save(user);
            }
        }
        else{
            // If user already signedup with email then don't allow user to login with oauth2 provider
            throw new BadCredentialsException("This Email is already Registered with provider: " + userEmail.getProviderId());
        }

        // Login the user
        LoginResponseDto loginResponseDto = new LoginResponseDto(authUtil.generateAccessToken(user), user.getId());
        return ResponseEntity.ok(loginResponseDto);
    }


}
