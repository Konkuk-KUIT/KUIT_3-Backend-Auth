package kuit.server.dto.member.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class GetMembersResponse {

    private Long lastMemberId;
    List<GetMemberResponse> memberResponses;

    @Builder
    public GetMembersResponse(List<GetMemberResponse> memberResponses) {
        this.lastMemberId= memberResponses.get(memberResponses.size()-1).getMemberId();
        this.memberResponses = memberResponses;
    }
}
