package lk.icbt.ovr.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import lk.icbt.ovr.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}