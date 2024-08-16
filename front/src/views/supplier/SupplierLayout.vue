<script setup>
import {
    Management,
    UserFilled,
    User,
    SwitchButton,
    CaretBottom,
    Star
} from '@element-plus/icons-vue'
import avatar from '@/assets/default.png'

import { supplierInfoService } from '@/api/supplier.js';
import useSupplierInfoStore from '@/stores/supplierInfo.js'
import { userTokenStore } from '@/stores/token.js';
const tokenStore = userTokenStore();
const supplierInfoStore = useSupplierInfoStore();

const getSupplierInfo = async () => {

    let result = await supplierInfoService();

    supplierInfoStore.setSupplierInfo(result.data);
}

getSupplierInfo();


import { useRouter } from 'vue-router'
const router = useRouter();
import { ElMessage, ElMessageBox } from 'element-plus'
const handleCommand = (command) => {

    if (command === 'logout') {

        ElMessageBox.confirm(
            '您确认要退出登录吗?',
            '温馨提示',
            {
                confirmButtonText: '确认',
                cancelButtonText: '取消',
                type: 'warning',
            }
        )
            .then(async () => {

                tokenStore.removeToken()
                supplierInfoStore.removeSupplierInfo()

                router.push('/login')

                ElMessage({
                    type: 'success',
                    message: '退出登录成功',
                })

            })
            .catch(() => {
                ElMessage({
                    type: 'info',
                    message: '用户取消退出登录',
                })
            })
    } else {

        router.push('/supplier/' + command)
    }
}
</script>

<template>
    <el-container class="layout-container">
        <el-aside width="200px">
            <div class="el-aside__logo"></div>
            <el-menu active-text-color="#409eff" background-color="#324057" text-color="#ffffff" router>
                <el-menu-item index="/welcome">
                    <el-icon>
                        <Management />
                    </el-icon>
                    <span>首页</span>
                </el-menu-item>
                <el-menu-item index="/inquiry/list">
                    <el-icon>
                        <Management />
                    </el-icon>
                    <span>询价通知</span>
                </el-menu-item>
                <el-menu-item index="/quotation/manage">
                    <el-icon>
                        <Management />
                    </el-icon>
                    <span>我的报价</span>
                </el-menu-item>
                <el-menu-item index="/po/myPo">
                    <el-icon>
                        <Management />
                    </el-icon>
                    <span>我的订单</span>
                </el-menu-item>
                <el-menu-item index="/performance/myPf">
                    <el-icon>
                        <Star />
                    </el-icon>
                    <span>我的绩效表现</span>
                </el-menu-item>
                <el-sub-menu index="supplier">
                    <template #title>
                        <el-icon>
                            <UserFilled />
                        </el-icon>
                        <span>个人中心</span>
                    </template>
                    <el-menu-item index="/supplier/info">
                        <el-icon>
                            <User />
                        </el-icon>
                        <span>基本资料</span>
                    </el-menu-item>
                </el-sub-menu>
            </el-menu>
        </el-aside>
        <el-container>
            <el-header>
                <div>用户：<strong>{{ supplierInfoStore.supplierInfo.name }}</strong></div>
                <el-dropdown placement="bottom-end" @command="handleCommand">
                    <span class="el-dropdown__box">
                        <el-avatar :src="avatar" />
                        <el-icon>
                            <CaretBottom />
                        </el-icon>
                    </span>
                    <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item command="logout" :icon="SwitchButton">退出登录</el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
            </el-header>
            <el-main>
                <router-view></router-view>
            </el-main>
            <el-footer>供应商关系管理系统 ©2024 Created by 李帅</el-footer>
        </el-container>
    </el-container>
</template>

<style lang="scss" scoped>
.layout-container {
    height: 100vh;

    .el-aside {
        background-color: #324057;

        &__logo {
            height: 120px;
            background: url('@/assets/2.png') no-repeat center / 200px auto;
        }

        .el-menu {
            border-right: none;
        }
    }

    .el-header {
        background-color: #fff;
        display: flex;
        align-items: center;
        justify-content: space-between;

        .el-dropdown__box {
            display: flex;
            align-items: center;

            .el-icon {
                color: #999;
                margin-left: 10px;
            }

            &:active,
            &:focus {
                outline: none;
            }
        }
    }

    .el-footer {
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 14px;
        color: #666;
    }
}
</style>@/api/supplier.js