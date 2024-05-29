package kuit.server.service;

import kuit.server.common.exception.UserException;
import kuit.server.controller.validator.PostMemberRequestValidator;
import kuit.server.dao.MemberDao;
import kuit.server.dao.UserDao;
import kuit.server.domain.Member;
import kuit.server.dto.member.request.PostLoginRequest;
import kuit.server.dto.member.request.PostMemberRequest;
import kuit.server.dto.member.response.GetMemberResponse;
import kuit.server.dto.member.response.PostLoginResponse;
import kuit.server.dto.member.response.PostMemberResponse;
import kuit.server.dto.user.PostUserRequest;
import kuit.server.dto.user.PostUserResponse;
import kuit.server.util.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static kuit.server.common.response.status.BaseExceptionResponseStatus.DUPLICATE_EMAIL;
import static kuit.server.common.response.status.BaseExceptionResponseStatus.DUPLICATE_NICKNAME;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberDao memberDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    public Member findOneById(Long id){
        log.info("[MemberService.findOneById]");
        return memberDao.findById(id);
    }

    public GetMemberResponse findMemberResponseById(Long id){
        log.info("[MemberService.findMemberResponseById]");
        return GetMemberResponse.of(findOneById(id));
    }

    public Member createMember(Member member) {
        log.info("[MemberService.member]");
        memberDao.createMember(member);
        return member;
    }
    public PostMemberResponse createMemberByPostMemberResponse(PostMemberRequest postMemberRequest) {
        log.info("[MemberService.createMemberByPostMemberResponse]");
        Member member = createMember(postMemberRequest.toEntity());
        return PostMemberResponse.of(member);
    }

    public String changeNickname(Long id,String nickname){
        log.info("[MemberService.changeNickname]");
        if(memberDao.modifyNickname(id,nickname)==1)
            return "success";
        return "fail";

    }

    public String changeAll(Long id,PostMemberRequest postMemberRequest){
        log.info("[MemberService.changeAll]");
        postMemberRequest.setMemberId(id);
        Member member=postMemberRequest.toEntity();
        if(memberDao.modifyAll(id,member)==1)
            return "success";
        return "fail";
    }

    public List<Member> findAll(){
        log.info("[MemberService.findAll]");
        return memberDao.findAll();
    }

    public List<GetMemberResponse>findMemberResponses(){
        log.info("[MemberService.findMemberResponses]");
        return findAll().stream()
                .map(GetMemberResponse::of)
                .collect(Collectors.toList());
    }

    public String deleteById(Long id){
        log.info("[MemberService.deleteById]");
        if(memberDao.delete(id)==1)
            return "success";
        return "fail";
    }

    public PostLoginResponse login(PostLoginRequest postLoginRequest) {
        log.info("[MemberService.deleteById]");

        // TODO: 1. validation (중복 검사)
        //validateEmail(postLoginRequest.getEmail());
        //String nickname = postLoginRequest.getNickname();

        // TODO: 2. password 암호화
        //String encodedPassword = passwordEncoder.encode(postUserRequest.getPassword());
        //postUserRequest.resetPassword(encodedPassword);

        // TODO: 3. DB insert & userId 반환
        Member member = memberDao.findByEmail(postLoginRequest.getEmail());
        //long userId = memberDao.createMember(postLoginRequest);

        // TODO: 4. JWT 토큰 생성
        String jwt = jwtTokenProvider.createToken(postLoginRequest.getEmail(), member.getMemberId());

        return new PostLoginResponse(jwt);
    }

}
