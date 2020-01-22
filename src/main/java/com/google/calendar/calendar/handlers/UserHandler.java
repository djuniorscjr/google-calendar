package com.google.calendar.calendar.handlers;

import com.google.calendar.calendar.models.User;
import com.google.calendar.calendar.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class UserHandler {

    private UserRepository userRepository;
    private Validator validator;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserHandler(UserRepository userRepository, Validator validator, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.validator = validator;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Mono<ServerResponse> createPerson(final ServerRequest request) {
        Mono<User> userMono = request.bodyToMono(User.class);

        return userMono.flatMap(user -> {
                    final Set<ConstraintViolation<User>> validate = validator.validate(user);
                    if (validate.isEmpty()) {
                        user = user.toBuilder().password(bCryptPasswordEncoder.encode(user.getPassword())).build();
                        return userRepository.save(user).flatMap(userSaved -> ServerResponse.status(HttpStatus.CREATED)
                                .contentType(APPLICATION_JSON).build());
                    }
                    throw new ConstraintViolationException(validate);
                }).switchIfEmpty(Mono.defer(() -> ServerResponse.status(HttpStatus.BAD_REQUEST).build()));

    }
}
