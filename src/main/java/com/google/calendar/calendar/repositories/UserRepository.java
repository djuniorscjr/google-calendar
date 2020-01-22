package com.google.calendar.calendar.repositories;

import com.google.calendar.calendar.models.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {
}
