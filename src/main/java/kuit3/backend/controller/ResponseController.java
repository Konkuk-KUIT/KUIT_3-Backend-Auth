package kuit3.backend.controller;

import kuit3.backend.common.response.BaseErrorResponse;
import kuit3.backend.common.response.BaseResponse;
import kuit3.backend.temp.UserData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static kuit3.backend.common.response.status.BaseExceptionResponseStatus.BAD_REQUEST;

@RestController
public class ResponseController {

    @RequestMapping("/base-response")
    public BaseResponse<UserData> getSuccessResponse(){
        UserData userData = new UserData("soeun", 24);   // @AllArgsConstructor
        return new BaseResponse<>(userData);
    }

    @RequestMapping("/base-error-response")
    public BaseErrorResponse getErrorResponse(){
        return new BaseErrorResponse(BAD_REQUEST);
    }
}
