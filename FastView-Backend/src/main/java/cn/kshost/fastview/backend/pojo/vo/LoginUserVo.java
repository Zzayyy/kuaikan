package cn.kshost.fastview.backend.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserVo
{
      String  avatar;
      String  username;
      String  nickname;
      String  accessToken;
      String  refreshToken;
      @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
      LocalDateTime expires;
}
