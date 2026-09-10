//导入请求工具
import register from "@/utils/request.js";

//注册接口
export const userRegisterService = (registerData) => {
    //将对象转换为URLSearchParams格式
    const params = new URLSearchParams(registerData);
    //将对象的每个键值对添加到URLSearchParams中
    for(let key in registerData) {
        params.append(key, registerData[key]);
    }
    //发送POST请求到注册接口
    return register.post("/user/register", params);
}

//登录接口
export const userLoginService = (loginData) => {
    //将对象转换为URLSearchParams格式
    const params = new URLSearchParams(loginData);
    //将对象的每个键值对添加到URLSearchParams中
    for(let key in loginData) {
        params.append(key, loginData[key]);
    }
    //发送POST请求到登录接口
    return register.post("/user/login", params);
}