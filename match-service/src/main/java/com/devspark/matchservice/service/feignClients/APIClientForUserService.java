package com.devspark.matchservice.service.feignClients;

import com.devspark.matchservice.pojo.dto.GetMyMatchedUserListDTO;
import com.devspark.matchservice.pojo.dto.GetMyRecommendUserListDTO;
import com.devspark.matchservice.pojo.vo.GetMatchedUserListVO;
import com.devspark.matchservice.pojo.vo.GetRecomUserListVO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "http://localhost:8082", value = "user-micro-service")
public interface APIClientForUserService {

    @PostMapping("/api/user-info/get-my-recommend-list")
    GetRecomUserListVO getRecommendUserList(@RequestBody @Valid GetMyRecommendUserListDTO getMyRecommendUserListDTO);

    @PostMapping("/api/user-info/get-user-list")
    GetMatchedUserListVO getMatchedUserInfoList(@RequestBody @Valid GetMyMatchedUserListDTO getMyMatchedUserListDTO);
}
