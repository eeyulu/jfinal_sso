/**
 * Copyright (c) 2011-2014, hubin (243194995@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.baomidou.kisso.jfinal;

import java.util.logging.Logger;

import com.baomidou.kisso.MyToken;
import com.baomidou.kisso.Res;
import com.baomidou.kisso.SSOConfig;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.plugin.SSOJfinalInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

/**
 * 首页
 * <p>
 * SSOJfinalInterceptor 登录权限拦截, 你也可以自己实现
 * </p>
 */
@Before(SSOJfinalInterceptor.class)
public class IndexController extends Controller {
	private static final Logger logger = Logger.getLogger("IndexController");

	/**
	 * <p>
	 * SSOHelper.getToken(request)
	 * 
	 * 从 Cookie 解密 token 使用场景，拦截器
	 * </p>
	 * 
	 * <p>
	 * SSOHelper.attrToken(request)
	 * 
	 * 非拦截器使用减少二次解密
	 * </p>
	 */

    public void index(){
    	logger.info("进入主页!");
        //从request中拿到token,反正解密。
    	MyToken token = SSOHelper.attrToken(getRequest());
        if(token != null){
        	logger.info("登陆令牌UID"+token.getUid());
        	logger.info("登陆令牌IP地址"+token.getIp());
            render("index.html");
            return;
        }
        render("/login.jsp");
    }

	/**
	 * <p>
	 * 支持APP端测试
	 * </p>
	 * 
	 * @author 成都瘦人  lendo.du@gmail.com
	 * 
	 */
	public void appTest() {
		Res res = new Res();
		res.setData("测试请求已成功");
		renderJson(res);
	}
}
