package com.example.chat_relex.service;

import com.example.chat_relex.exceptions.EntityAlreadyExistsException;
import com.example.chat_relex.exceptions.HiddenFriendsException;
import com.example.chat_relex.exceptions.NoFriendException;
import com.example.chat_relex.mapper.UserMapper;
import com.example.chat_relex.models.dto.UserDTO;
import com.example.chat_relex.models.entity.User;
import com.example.chat_relex.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class FriendService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final UserService userService;


    @Transactional
    public List<UserDTO> getFriends(Long id) {
        List<UserDTO> users =userMapper.toDTOFromEntityList( userRepository.findAllFriendsByNickname(id));
        return users;
    }

    @Transactional
    public void addFriend(UserDTO userDTO, String nickname) {
        User friend = userMapper.toEntityFromDTO(userService.getUserByNickName(nickname));
        if (userRepository.isFriend(userDTO.getUserId(), friend.getUserId())) {
            throw new EntityAlreadyExistsException("Пользователь уже добавлен в друзья");
        }
        if (friend.getUserId().equals(userDTO.getUserId())) {
            throw new EntityAlreadyExistsException("Пользователь не может добавить самого себя в друзья");
        }
        userRepository.addFriend(userDTO.getUserId(), friend.getUserId());
        userRepository.addFriend(friend.getUserId(), userDTO.getUserId());

    }

    @Transactional
    public void deleteFriend(UserDTO userDTO, String nickname) {
        User friend = userMapper.toEntityFromDTO(userService.getUserByNickName(nickname));
        if (!userRepository.isFriend(userDTO.getUserId(), friend.getUserId())) {
            throw new NoFriendException("Пользователя не было в друзьях");
        }
        userRepository.deleteFriend(userDTO.getUserId(), friend.getUserId());
    }

    public boolean hasFriend(User userDTO, User friend) {
        return userRepository.isFriend(userDTO.getUserId(), friend.getUserId());
    }

    @Transactional
    public List<UserDTO> showUserFriends( String nickname){
        UserDTO userDTO=userService.getUserByNickName(nickname);
        if(!userDTO.getIsShowFriends()){
            throw new HiddenFriendsException("The user has hidden the friends lis");
        }
        List<UserDTO> users = userMapper.toDTOFromEntityList(userRepository.findAllFriendsByNickname(userDTO.getUserId()));

        return users;
    }
}