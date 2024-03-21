package com.devspark.matchservice.controller;

import com.devspark.matchservice.pojo.dto.GetMyMatchedUserListDTO;
import com.devspark.matchservice.pojo.dto.GetMyRecommendUserListDTO;
import com.devspark.matchservice.pojo.vo.GetMatchedUserListVO;
import com.devspark.matchservice.pojo.vo.GetRecomUserListVO;
import com.devspark.matchservice.service.MatchService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/match")
@AllArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @PostMapping("/get-my-recommend-list")
    public ResponseEntity<GetRecomUserListVO> getMyRecommendationUserList(@RequestBody @Valid GetMyRecommendUserListDTO requestBody){
        GetRecomUserListVO myRecommendListVO = matchService.getMyRecommendUserList(requestBody);
        return new ResponseEntity<>(myRecommendListVO, HttpStatus.OK);
    }


    @PostMapping("/get-my-matched-list")
    public ResponseEntity<GetMatchedUserListVO> getMyMatchedUserList(@RequestBody @Valid GetMyMatchedUserListDTO requestBody){
        GetMatchedUserListVO getMyMatchedUserListVO = matchService.getMyMatchedUserList(requestBody);
        return new ResponseEntity<>(getMyMatchedUserListVO, HttpStatus.OK);
    }

}
