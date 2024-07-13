package econo.buddybridge.chat.chatroom.controller;

import econo.buddybridge.chat.chatroom.dto.ChatRoomResDto;
import econo.buddybridge.chat.chatroom.service.ChatRoomService;
import econo.buddybridge.utils.api.ApiResponse;
import econo.buddybridge.utils.api.ApiResponseGenerator;
import econo.buddybridge.utils.session.SessionUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat/rooms")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    // GET 로그인한 사용자의 채팅방 목록 조회(1대1로 연결된 chatRoom을 전부 찾기, 6개씩 제한)
    @GetMapping("")
    public ApiResponse<ApiResponse.CustomBody<List<ChatRoomResDto>>> getAllChatRooms(
            HttpServletRequest request
    ){
        Long memberId = SessionUtils.getMemberId(request);
        List<ChatRoomResDto> chatRooms = chatRoomService.getChatRoomsByMemberId(memberId);
        return ApiResponseGenerator.success(chatRooms,HttpStatus.OK);
    }

    // GET 채팅방 조회(채팅 메시지 출력)
    @GetMapping("/{room-id}")
    public ApiResponse<ApiResponse.CustomBody<Void>> getChatRoom(

    ){

        return ApiResponseGenerator.success(HttpStatus.OK);
    }
}
