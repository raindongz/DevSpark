package com.devspark.matchservice.controller;

import com.devspark.matchservice.pojo.dto.GetMyRecommendUserListDTO;
import com.devspark.matchservice.pojo.dto.LikeOrUnlikeDTO;
import com.devspark.matchservice.pojo.vo.GetMatchedUserListVO;
import com.devspark.matchservice.pojo.vo.GetRecomUserListVO;
import com.devspark.matchservice.pojo.vo.LikeOrUnlikeVO;
import com.devspark.matchservice.service.MatchService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/match")
@AllArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @PostMapping("/get-my-recommend-list")
    public ResponseEntity<GetRecomUserListVO> getMyRecommendationUserList(@RequestHeader(name = "user_id")Long userId){
        GetRecomUserListVO myRecommendListVO = matchService.getMyRecommendUserList(userId);
        return new ResponseEntity<>(myRecommendListVO, HttpStatus.OK);
    }

    @PostMapping("/get-my-matched-list")
    public ResponseEntity<GetMatchedUserListVO> getMyMatchedUserList(@RequestHeader(name = "user_id")Long userId){
        GetMatchedUserListVO getMyMatchedUserListVO = matchService.getMyMatchedUserList(userId);
        return new ResponseEntity<>(getMyMatchedUserListVO, HttpStatus.OK);
    }

    @PostMapping("/like-or-unlike")
    public ResponseEntity<LikeOrUnlikeVO> likeOrUnlike(@RequestHeader(name = "user_id")Long userId, @RequestBody @Valid LikeOrUnlikeDTO requestBody){
        LikeOrUnlikeVO likeOrUnlikeVO = matchService.likeOrUnlike(userId, requestBody);
        return new ResponseEntity<>(likeOrUnlikeVO, HttpStatus.OK);
    }

}
