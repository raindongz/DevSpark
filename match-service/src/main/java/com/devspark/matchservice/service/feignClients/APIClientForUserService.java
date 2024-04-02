package com.devspark.matchservice.service.feignClients;

import com.devspark.matchservice.pojo.dto.GetMyMatchedUserListDTO;
import com.devspark.matchservice.pojo.dto.GetMyRecommendUserListDTO;
import com.devspark.matchservice.pojo.vo.GetMatchedUserListVO;
import com.devspark.matchservice.pojo.vo.GetRecomUserListVO;
import com.devspark.matchservice.pojo.vo.MatchedUserInfo;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("user-service")
public interface APIClientForUserService {

    @PostMapping("/api/user-info/get-my-recommend-list")
    GetRecomUserListVO getRecommendUserList(@RequestBody @Valid GetMyRecommendUserListDTO getMyRecommendUserListDTO);

    @PostMapping("/api/user-info/get-user-list")
    List<MatchedUserInfo> getMatchedUserInfoList(@RequestBody @Valid GetMyMatchedUserListDTO getMyMatchedUserListDTO);
}
