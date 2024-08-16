<script setup>
import { User, Lock } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { ElMessage } from 'element-plus'

const managerLoginData = ref({
    username: '',
    password: ''
})

const rules = {
    username: [
        { required: true, message: '请输入用户名', trigger: 'blur' },
        { min: 5, max: 16, message: '长度为5~16位非空字符', trigger: 'blur' }
    ],
    password: [
        { required: true, message: '请输入密码', trigger: 'blur' },
        { min: 5, max: 16, message: '长度为5~16位非空字符', trigger: 'blur' }
    ]
}

const isBuyerLogin = () => {
    router.push('/buyer/login')
}

import { managerLoginService } from '@/api/manager.js'
import { userTokenStore } from '@/stores/token.js';
import { useRouter } from 'vue-router';
const router = useRouter()
const tokenStore = userTokenStore();
const managerLogin = async () => {

    let result = await managerLoginService(managerLoginData.value);
    ElMessage.success(result.msg ? result.msg : '登录成功');
    tokenStore.setToken(result.data)
    router.push('/manager')
}

const radio1 = ref(2)
const radio2 = ref(3)
</script>

<template>
    <el-row class="login-page">
        <el-col :span="12" class="bg"></el-col>
        <el-col :span="6" :offset="3" class="form">
            <!-- 登录表单 -->
            <el-form ref="form" size="large" autocomplete="off" :model="managerLoginData" :rules="rules">
                <el-form-item>
                    <h1>经理登录</h1>
                </el-form-item>
                <el-form-item prop="username">
                    <el-input :prefix-icon="User" placeholder="请输入用户名" v-model="managerLoginData.username"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input name="password" :prefix-icon="Lock" type="password" placeholder="请输入密码"
                        v-model="managerLoginData.password"></el-input>
                </el-form-item>
                <!-- 登录按钮 -->
                <el-form-item>
                    <el-button class="button" type="primary" auto-insert-space @click="managerLogin">登录</el-button>
                </el-form-item>
                <el-form-item class="flex">
                    <el-link type="info" :underline="false" @click=isBuyerLogin>
                        采购登录 →
                    </el-link>
                </el-form-item>
            </el-form>
        </el-col>
    </el-row>
</template>

<style lang="scss" scoped>
.login-page {
    height: 100vh;
    background-color: #fff;

    .bg {
        background: url('@/assets/back.jpg') no-repeat center / cover;
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
            justify-content: flex-end;
        }
    }
}
</style>@/api/manager.js