package com.devspark.userservice.controller;


import com.devspark.userservice.pojo.dto.GetRecomUserListDTO;
import com.devspark.userservice.pojo.vo.GetRecomUserListVO;
import com.devspark.userservice.service.UserInfoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user-info")
@AllArgsConstructor
public class UserInfoController {

    private UserInfoService userInfoService;

    @PostMapping("/get-my-recommend-list")
    public ResponseEntity<?> getRecommendUserList(@RequestBody @Valid GetRecomUserListDTO getRecomUserListDTO){
        GetRecomUserListVO getRecomUserListVO = userInfoService.getMyRecommendUserList(getRecomUserListDTO);
        return new ResponseEntity<>(getRecomUserListVO, HttpStatus.OK);
    }

}
