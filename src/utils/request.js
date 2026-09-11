//定制请求的实例

//导入axios  npm install axios
import axios from 'axios';
import { ElMessage } from 'element-plus';
//定义一个变量,记录公共的前缀  ,  baseURL
// const baseURL = 'http://localhost:8080';
const baseURL = '/api';
const instance = axios.create({baseURL})

//添加请求拦截器
import { useTokenStore } from '@/stores/token.js';
instance.interceptors.request.use(
    (config)=>{
        //在发送请求之前的回调
        //添加token
        const tokenStore = useTokenStore();
        //判断有没有token
        if(tokenStore.token){
            config.headers.Authorization = tokenStore.token;
        }
        return config;
    },
    (err)=>{
        //对请求错误
        Promise.reject(err);
    }
)

import router from '@/router';
//添加响应拦截器
instance.interceptors.response.use(
    result=>{
        if(result.data.code === 0){
        return result.data;
        }

        //请求失败
        // alert(result.data.message? result.data.message : '请求失败');
        ElMessage.error(result.data.message || '请求失败');
        return Promise.reject(result.data);//异步的状态转化成失败的状态

    },
    err=>{
        if(err.response.status === 401){
            ElMessage.error('请登陆');
            //清除token
            const tokenStore = useTokenStore();
            tokenStore.removeToken();
            //跳转到登陆页
            router.push('/login');
        }
        else{
            ElMessage.error(err.response.data.message || '请求失败');
        }
        return Promise.reject(err);//异步的状态转化成失败的状态
    }
)

export default instance;