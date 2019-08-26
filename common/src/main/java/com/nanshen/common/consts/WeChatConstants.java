package com.nanshen.common.consts;

public class WeChatConstants {


    public static final String WX_LOGIN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
    public static final String APP_ID = "wxb4057452fa708cb8";
    public static final String SECRET = "de130af5d83b8def3df8d5512477263d";
    public static final String LOGIN_GRANT_TYPE = "authorization_code";

    public static final String CHECK_LOGIN_URL="https://api.weixin.qq.com/sns/auth";

    public static final String WX_DEV_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
    public static final String DEV_TOKEN_GRANT_TYPE = "client_credential";


//    https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi
    public static final String JS_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";

    public static final String JS_TICKET_TYPE = "jsapi";


}
