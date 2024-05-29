package kuit.server.dto.member.response;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PostLoginResponse {

    private String jwt;
}
