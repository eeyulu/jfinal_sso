package com.baomidou.kisso.jfinal;

import com.baomidou.kisso.AuthToken;
import com.baomidou.kisso.SSOConfig;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.Token;
import com.baomidou.kisso.common.SSOProperties;
import com.jfinal.core.Controller;

/**
 * Created by Administrator on 2016/12/26 0026.
 */
public class ReplyLoginController extends Controller {


    public void index(){
        StringBuffer buffer = new StringBuffer(getRequest().getParameter("callback")).append("({\"msg\":\"");


        System.out.println("回复请求!");
        Token token = SSOHelper.getToken(getRequest());
        if(token != null){
            //从请求中拿到询问数据
            String askData = getRequest().getParameter("askData");
            if(askData != null && !"".equals(askData)){
                //拿到令牌
                AuthToken authToken = SSOHelper.replyCiphertext(getRequest(), askData);
                System.out.println("拿到令牌!");
                //拿到配置文件
                SSOProperties ssoProperties = SSOConfig.getSSOProperties();
                if(authToken != null){
                    //验证令牌
                    AuthToken verify = authToken.verify(ssoProperties.get("sso.defined." + authToken.getApp() + "_public_key"));
                    System.out.println("验证令牌!");
                    //验证通过
                    if(verify != null){
                      System.out.println("令牌验证通过!正在生成回复令牌!");
                      verify.setUid(token.getUid());
                      verify.setTime(token.getTime());
                      //令牌签名
                      verify.sign(ssoProperties.get("sso.defined.sso_private_key"));
                      buffer.append(verify.encryptAuthToken());
                    }
                }

            }else{
                //伪造信息,拒绝登陆
                buffer.append("-2");
            }

        }else{
            //用户没有登陆
          buffer.append("-1");
        }
        buffer.append("\"})");
        renderJson(buffer.toString());

    }
}