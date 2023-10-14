package com.example.chat_relex.components;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.example.chat_relex.models.Request.MessageRequest;
import com.example.chat_relex.models.Response.MessageResponse;
import com.example.chat_relex.models.entity.Message;
import com.example.chat_relex.service.SocketService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;

@Slf4j
@Component
public class SocketModule {


    private final SocketIOServer server;
    private final SocketService socketService;


    public SocketModule(SocketIOServer server, SocketService socketService) {
        this.server = server;
        this.socketService = socketService;
        server.addConnectListener(onConnected());
        server.addDisconnectListener(onDisconnected());;
        server.addEventListener("send_message", MessageRequest.class, onChatReceived());
    }

    private DataListener<MessageRequest> onChatReceived() {
        return (senderClient, data, ackSender) -> {
            log.info(data.getMessage()+" "+senderClient.getHandshakeData().getSingleUrlParam("chatRoomId")+" "+senderClient.getHandshakeData().getSingleUrlParam("senderName"));
            socketService.sendMessage(senderClient.getHandshakeData().getSingleUrlParam("chatRoomId"), "get_message", senderClient, data.getMessage(),senderClient.getHandshakeData().getSingleUrlParam("senderName") );
        };
    }

    private ConnectListener onConnected() {
        return (client) -> {
            String room = client.getHandshakeData().getSingleUrlParam("chatRoomId");
            client.joinRoom(room);
            log.info("Socket ID[{}]  Connected to socket ", client.getSessionId().toString());
        };
    }

    private DisconnectListener onDisconnected() {
        return client -> {
            log.info("Client[{}] - Disconnected from socket", client.getSessionId().toString());
        };
    }

}

