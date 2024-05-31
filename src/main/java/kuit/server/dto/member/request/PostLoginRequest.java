package kuit.server.dto.member.request;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostLoginRequest {

    @NotBlank(message = "email: 공백은 허용되지 않습니다")
    private String email;

    @NotBlank(message = "password: 공백은 허용되지 않습니다")
    private String password;

}
