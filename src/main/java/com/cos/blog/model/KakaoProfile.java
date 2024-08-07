package com.cos.blog.model;

import lombok.Data;

@Data
public class KakaoProfile {
    public long id;
    public String connected_at;
    public Properties properties;
    public KakaoAccount kakao_account;

    @Data
    public class Properties {
        public String nickname;
    }

    @Data
    public class KakaoAccount {
        public Boolean profile_nickname_needs_agreement;
        public Profile profile;
        public String email; // 이메일 필드 추가

        @Data
        public class Profile {
            public String nickname;
            public Boolean is_default_nickname;
        }
    }
}
