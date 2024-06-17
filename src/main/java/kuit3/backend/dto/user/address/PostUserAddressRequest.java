package kuit3.backend.dto.user.address;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kuit3.backend.common.argument_resolver.JwtAuthrize;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@RequiredArgsConstructor
public class PostUserAddressRequest {
    @Nullable
    private long userId;

    @NotBlank
    @Length(max = 10)
    private String userAddress;

    @NotBlank
    @Length(max=20)
    private String addressCategory;
}
