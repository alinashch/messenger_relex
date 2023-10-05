package com.example.chat_relex.repository;

import com.example.chat_relex.models.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    List<UserEntity> findAll();

    UserEntity save(UserEntity userEntity);

    void deleteById(long id);

    UserEntity getById(Long id);

    Optional<UserEntity> getByLogin(String login);

    Optional<UserEntity> getByEmail(String email);

    @Modifying
    @Query(value = "UPDATE  users_entity  SET name = :name WHERE id = :id", nativeQuery = true)
    void updateName(@Param("id") Long Id,
                    @Param("name") String name);

    @Modifying
    @Query(value = "UPDATE  users_entity  SET nickname = :nickname WHERE id = :id", nativeQuery = true)
    void updateNickname(@Param("id") Long Id,
                        @Param("nickname") String nickname);

    @Modifying
    @Query(value = "UPDATE  users_entity  SET email = :email WHERE id = :id", nativeQuery = true)
    void updateEmail(@Param("id") Long id,
                     @Param("email") String email);

    @Modifying
    @Query(value = "UPDATE  users_entity  SET surname = :surname WHERE id = :id", nativeQuery = true)
    void updateSurname(@Param("id") Long id,
                       @Param("surname") String surname);

}
