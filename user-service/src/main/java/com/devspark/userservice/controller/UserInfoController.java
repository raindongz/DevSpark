package com.devspark.userservice.controller;


import com.devspark.userservice.entity.UserInfoEntity;
import com.devspark.userservice.pojo.dto.GetRecomUserListDTO;
import com.devspark.userservice.pojo.dto.UserListQueryDTO;
import com.devspark.userservice.pojo.vo.matchedService.getMatchedListApi.MatchedUserInfo;
import com.devspark.userservice.pojo.vo.matchedService.getRecommendApi.GetRecomUserListVO;
import com.devspark.userservice.service.UserInfoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-info")
@AllArgsConstructor
public class UserInfoController {

    private UserInfoService userInfoService;

    @PostMapping("/get-my-recommend-list")
    public ResponseEntity<GetRecomUserListVO> getRecommendUserList(@RequestBody @Valid GetRecomUserListDTO getRecomUserListDTO){
        GetRecomUserListVO getRecomUserListVO = userInfoService.getMyRecommendUserList(getRecomUserListDTO);
        return new ResponseEntity<>(getRecomUserListVO, HttpStatus.OK);
    }

    @GetMapping("/my-profile")
    public ResponseEntity<UserInfoEntity> getAllUsers(@RequestHeader(name = "user_id")Long userId) {
        UserInfoEntity myProfile = userInfoService.getMyProfile(userId);
        return ResponseEntity.ok(myProfile);
    }

    @PostMapping("/save-user-info")
    public ResponseEntity<UserInfoEntity> saveUserInfo(@RequestBody UserInfoEntity userInfoEntity){
        UserInfoEntity userAdded = userInfoService.saveUserInfo(userInfoEntity);
        return ResponseEntity.ok(userAdded);
    }

    @PostMapping("/get-user-list")
    public ResponseEntity<List<MatchedUserInfo>> getUserList(@RequestBody UserListQueryDTO userListQueryDTO){
        List<MatchedUserInfo> userList = userInfoService.getUserList(userListQueryDTO);
        return ResponseEntity.ok(userList);
    }

}
