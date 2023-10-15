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

    boolean existsByEmail(String email);

    boolean existsByLogin(String login);

    boolean existsByNickname(String nickname);

    User getById(Long id);

    Optional<User> getByNickname(String nickname);

    Optional<User> getByLogin(String login);

    Optional<User> getByEmail(String email);

    @Modifying
    @Query(value = "DELETE FROM refresh_token WHERE user_id = :userId ", nativeQuery = true)
    void deleteToken(@Param("userId") Long id);

    @Modifying
    @Query(value ="UPDATE user_info  SET is_verified = true WHERE user_id = :id", nativeQuery = true)
    void verifyUserById(@Param("id") Long id);

    @Modifying
    @Query(value ="UPDATE user_info  SET is_active = true WHERE user_id = :id", nativeQuery = true)
    void setActive(@Param("id") Long id);

    @Modifying
    @Query(value ="UPDATE user_info  SET is_active = false WHERE user_id = :id", nativeQuery = true)
    void setNotActive(@Param("id") Long id);

    @Modifying
    @Query(value ="UPDATE user_info  SET is_show_friends = true WHERE user_id = :id", nativeQuery = true)
    void setShowFriends(@Param("id") Long id);

    @Modifying
    @Query(value ="UPDATE user_info  SET is_show_friends = false WHERE user_id = :id", nativeQuery = true)
    void setNotShowFriend(@Param("id") Long id);


    @Query(value = """
            SELECT u.*
            FROM user_info u
            JOIN friend f
                ON u.user_id = f.friend_id
                AND f.user_id = :userId """, nativeQuery = true)
    List<User> findAllFriendsByNickname(@Param("userId") Long userId);

    @Query(value = "SELECT EXISTS(SELECT 1 FROM friend WHERE user_id = :userId AND friend_id = :friendId)",
            nativeQuery = true)
    boolean isFriend(@Param("userId") Long id, @Param("friendId") Long friendId);

    @Modifying
    @Query(value = """
            INSERT INTO friend(user_id, friend_id)
            VALUES (:userId, :friendId)""", nativeQuery = true)
    void addFriend(@Param("userId") Long id, @Param("friendId") Long friendId);

    @Modifying
    @Query(value = "DELETE FROM friend WHERE user_id = :userId AND friend_id = :friendId", nativeQuery = true)
    void deleteFriend(@Param("userId") Long id, @Param("friendId") Long friendId);
}
