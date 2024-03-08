package bs.www.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Date;
import java.util.Map;


/**
 * 生成令牌
 * 客户端请求头会包含属性“Authorization”，值为JWT令牌
 */

public class JwtUtil {

    private static final String KEY = "ww2048104136ww";

	//接收业务数据,生成token并返回
    public static String genToken(Map<String, Object> claims) {
        return JWT.create()
                .withClaim("claims", claims)
                //设置token过期时间为48小时
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 48))
                .sign(Algorithm.HMAC256(KEY));
    }

	//接收token,验证token,并返回业务数据
    public static Map<String, Object> parseToken(String token) {
        return JWT.require(Algorithm.HMAC256(KEY))
                .build()
                .verify(token)
                .getClaim("claims")
                .asMap();
    }

}
