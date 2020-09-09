package com.qiqi.common.constant;

/**
 * @author QiQiDream
 * @since 2019/11/18 10:20
 */
public class StaticConstants {
    /**
     * 请求头类型：
     * application/x-www-form-urlencoded ： form表单格式
     * application/json ： json格式
     */
    public static final String REQUEST_HEADERS_CONTENT_TYPE = "application/json";

    /**
     * 非法请求
     */
    public static final String ROLE_UNAUTHORIZED = "role_unauthorized";

    /**
     * JWT token默认设置
     */
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "JWT";

    /**
     * JWT 在 Redis 中保存的key前缀
     */
    public static final String REDIS_JWT_KEY_PREFIX = "Dev:jwt:";

    /**
     * 验证码 在 Redis 中保存的key前缀
     */
    public static final String REDIS_CAPTCHA_KEY_PREFIX = "Dev:captcha:";

}
