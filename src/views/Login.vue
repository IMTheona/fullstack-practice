<script setup>
import { User, Lock } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
//控制注册与登录表单的显示， 默认显示注册
const isRegister = ref(false)
//定义注册表单数据
const registData = ref({
    username: '',
    password: '',
    repassword: ''
})

//定义确认密码的校验规则
const checkRepassword = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('请再次输入密码'))
    } else if (value !== registData.value.password) {
        callback(new Error('两次输入密码不一致!'))
    } else {
        callback()
    }
}
//定义表单校验规则
const rules = {
    username: [
        { required: true, message: '请输入用户名', trigger: 'blur' },
        { min: 3, max: 12, message: '长度在 3 到 12 个字符', trigger: 'blur' }
    ],
    password: [
        { required: true, message: '请输入密码', trigger: 'blur' },
        { min: 6, max: 18, message: '长度在 6 到 18 个字符', trigger: 'blur' }
    ],
    repassword: [
        {validator:checkRepassword, trigger: 'blur'}
    ]
} 

//调用后台接口完成注册
import { userRegisterService ,userLoginService} from '@/api/user.js'
const register = async () => {
    //校验表单
    let result = await userRegisterService(registData.value);
    // if(result.code === 0){
    //     //成功
    //     alert('注册成功')
    // }else{
    //     //失败
    //     alert(result.message? result.message : '注册失败')
    ElMessage.success('注册成功');
}

//调用后台接口完成登录
const loginData = ref({
    username: '',
    password: ''
})
//登录
import { useRouter } from 'vue-router'
import { useTokenStore } from '@/stores/token.js'
const tokenStore = useTokenStore()
const router = useRouter()
const login = async () => {
    //校验表单
    let result = await userLoginService(loginData.value);
    ElMessage.success('登录成功');
    tokenStore.setToken(result.data);
    // 跳转到首页
    router.push('/')
}
</script>

<template>
    <el-row class="login-page">
        <el-col :span="12" class="bg"></el-col>
        <el-col :span="6" :offset="3" class="form">
            <!-- 注册表单 -->
            <el-form ref="form" size="large" autocomplete="off" v-if="isRegister" :model="registData" :rules="rules">
                <el-form-item>
                    <h1>注册</h1>
                </el-form-item>
                <el-form-item prop="username">
                    <el-input v-model="registData.username" :prefix-icon="User" placeholder="请输入用户名"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input v-model="registData.password" :prefix-icon="Lock" type="password" placeholder="请输入密码"></el-input>
                </el-form-item>
                <el-form-item prop="repassword">
                    <el-input v-model="registData.repassword" :prefix-icon="Lock" type="password" placeholder="请输入再次密码"></el-input>
                </el-form-item>
                <!-- 注册按钮 -->
                <el-form-item>
                    <el-button class="button" type="primary" auto-insert-space @click="register">
                        注册
                    </el-button>
                </el-form-item>
                <el-form-item class="flex">
                    <el-link type="info" :underline="false" @click="isRegister = false">
                        ← 返回
                    </el-link>
                </el-form-item>
            </el-form>
            <!-- 登录表单 -->
            <el-form ref="form" size="large" autocomplete="off" v-else :model="loginData" :rules="rules">
                <el-form-item>
                    <h1>登录</h1>
                </el-form-item>
                <el-form-item prop="username">
                    <el-input v-model="loginData.username" :prefix-icon="User" placeholder="请输入用户名" ></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input v-model="loginData.password" name="password" :prefix-icon="Lock" type="password" placeholder="请输入密码"></el-input>
                </el-form-item>
                <el-form-item class="flex">
                    <div class="flex">
                        <el-checkbox>记住我</el-checkbox>
                        <el-link type="primary" :underline="false">忘记密码？</el-link>
                    </div>
                </el-form-item>
                <!-- 登录按钮 -->
                <el-form-item>
                    <el-button class="button" type="primary" auto-insert-space @click="login">
                        登录
                    </el-button>
                </el-form-item>
                <el-form-item class="flex">
                    <el-link type="info" :underline="false" @click="isRegister = true">
                        注册 →
                    </el-link>
                </el-form-item>
            </el-form>
        </el-col>
    </el-row>
</template>

<style lang="scss" scoped>
/* 样式 */
.login-page {
    height: 100vh;
    background-color: #fff;

    .bg {
        background: url('@/assets/logo2.png') no-repeat 60% center / 240px auto,
            url('@/assets/login_bg.jpg') no-repeat center / cover;
        border-radius: 0 20px 20px 0;
    }

    .form {
        display: flex;
        flex-direction: column;
        justify-content: center;
        user-select: none;

        .title {
            margin: 0 auto;
        }

        .button {
            width: 100%;
        }

        .flex {
            width: 100%;
            display: flex;
            justify-content: space-between;
        }
    }
}
</style>