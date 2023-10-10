package com.example.chat_relex.repository;

import com.example.chat_relex.models.entity.RefreshToken;
import com.example.chat_relex.models.entity.Role;
import com.example.chat_relex.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll();

    User save(User userEntity);

    void deleteById(long id);

    boolean existsByEmail(String email);

    boolean existsByLogin(String login);

    User getById(Long id);

    Optional<User> getByLogin(String login);


    Optional<User> getByEmail(String email);

    @Modifying
    @Query(value = "UPDATE user_info  SET is_Verified = true WHERE user_id  = :id",  nativeQuery = true)
    void verifyUserById(@Param("id") Long id);

    @Modifying
    @Query(value = "DELETE FROM refresh_token WHERE user_id = :userId ", nativeQuery = true)
    void deleteToken(@Param("userId") Long id);

    @Modifying
    @Query(value = "DELETE FROM verification WHERE user_id = :userId ", nativeQuery = true)
    void deleteVerification(@Param("userId") Long id);


}
