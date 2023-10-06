package com.example.chat_relex.repository;

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

//    @Modifying
//    @Query(value = "UPDATE  user_info   SET firstname = :name WHERE userId  = :id", nativeQuery = true)
//    void updateName(@Param("id") Long Id,
//                    @Param("name") String name);
//
//    @Modifying
//    @Query(value = "UPDATE  user_info   SET lastname = :nickname WHERE userId  = :id", nativeQuery = true)
//    void updateNickname(@Param("id") Long Id,
//                        @Param("nickname") String nickname);
//
//    @Modifying
//    @Query(value = "UPDATE  user_info   SET email = :email WHERE userId  = :id", nativeQuery = true)
//    void updateEmail(@Param("id") Long id,
//                     @Param("email") String email);
//
//    @Modifying
//    @Query(value = "UPDATE  user_info   SET surname = :surname WHERE userId  = :id", nativeQuery = true)
//    void updateSurname(@Param("id") Long id,
//                       @Param("surname") String surname);

    @Modifying
    @Query(value = "UPDATE user_info  SET is_Verified = true WHERE user_id  = :id",  nativeQuery = true)
    void verifyUserById(@Param("id") Long id);

}
