package com.example.chat_relex.models.Request;


import com.example.chat_relex.models.entity.ChatRoom;
import com.example.chat_relex.models.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChatRoomRequest {

    private Set<User> users;
}
