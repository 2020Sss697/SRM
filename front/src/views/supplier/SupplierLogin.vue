<script setup>
import { User, Lock } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { ElMessage } from 'element-plus'

const isRegister = ref(false)

const supplierRegisterData = ref({
    username: '',
    password: '',
    rePassword: ''
})


const checkRePassword = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('请再次确认密码'))
    } else if (value !== supplierRegisterData.value.password) {
        callback(new Error('请确保两次输入的密码一样'))
    } else {
        callback()
    }
}

const rules = {
    username: [
        { required: true, message: '请输入用户名', trigger: 'blur' },
        { min: 5, max: 16, message: '长度为5~16位非空字符', trigger: 'blur' }
    ],
    password: [
        { required: true, message: '请输入密码', trigger: 'blur' },
        { min: 5, max: 16, message: '长度为5~16位非空字符', trigger: 'blur' }
    ],
    rePassword: [
        { validator: checkRePassword, trigger: 'blur' }
    ]
}


import { supplierRegisterService, supplierLoginService } from '@/api/supplier.js'
const register = async () => {

    let result = await supplierRegisterService(supplierRegisterData.value);
    ElMessage.success(result.msg ? result.msg : '注册成功');
}


import { userTokenStore } from '@/stores/token.js';
import { useRouter } from 'vue-router';
const router = useRouter()
const tokenStore = userTokenStore();
const login = async () => {

    let result = await supplierLoginService(supplierRegisterData.value);
    ElMessage.success(result.msg ? result.msg : '登录成功');
    tokenStore.setToken(result.data)
    router.push('/')
}
const buyerLogin = ()=>{
    router.push('/buyer/login')
}

const radio1 = ref(2)
const radio2 = ref(3)
</script>

<template>
    <el-row class="login-page">
        <el-col :span="12" class="bg"></el-col>
        <el-col :span="6" :offset="3" class="form">
            <el-form ref="form" size="large" autocomplete="off" v-if="isRegister" :model="supplierRegisterData"
                :rules="rules">
                <el-form-item>
                    <h1>供应商注册</h1>
                </el-form-item>
                <el-form-item prop="username">
                    <el-input :prefix-icon="User" placeholder="请输入用户名"
                        v-model="supplierRegisterData.username"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input :prefix-icon="Lock" type="password" placeholder="请输入密码"
                        v-model="supplierRegisterData.password"></el-input>
                </el-form-item>
                <el-form-item prop="rePassword">
                    <el-input :prefix-icon="Lock" type="password" placeholder="请输入再次密码"
                        v-model="supplierRegisterData.rePassword"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button class="button" type="primary" auto-insert-space @click="register">
                        供应商注册
                    </el-button>
                </el-form-item>
                <el-form-item class="flex">
                    <el-link type="info" :underline="false" @click="isRegister = false">
                        ← 返回
                    </el-link>
                </el-form-item>
            </el-form>

            <el-form ref="form" size="large" autocomplete="off" v-else :model="supplierRegisterData" :rules="rules">
                <el-form-item>
                    <h1>供应商登录</h1>
                </el-form-item>
                <el-form-item prop="username">
                    <el-input :prefix-icon="User" placeholder="请输入用户名"
                        v-model="supplierRegisterData.username"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input name="password" :prefix-icon="Lock" type="password" placeholder="请输入密码"
                        v-model="supplierRegisterData.password"></el-input>
                </el-form-item>
                <el-form-item class="flex">
                    <div class="flex">
                        <el-link type="primary" :underline="false" @click=buyerLogin>采购登录？</el-link>
                    </div>
                </el-form-item>
                <el-form-item>
                    <el-button class="button" type="primary" auto-insert-space @click="login">登录</el-button>
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
</style>@/api/supplier.js