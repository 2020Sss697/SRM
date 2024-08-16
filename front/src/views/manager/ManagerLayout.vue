<script setup>
import {
    Management,
    User,
    Crop,
    EditPen,
    Star,
    SwitchButton,
    CaretBottom
} from '@element-plus/icons-vue'
import avatar from '@/assets/default.png'

import { managerInfoService } from '@/api/manager';
import useManagerInfoStore from '@/stores/managerInfo'
import { userTokenStore } from '@/stores/token';
const tokenStore = userTokenStore();
const managerInfoStore = useManagerInfoStore();

const getManagerInfo = async () => {
    let result = await managerInfoService();
    managerInfoStore.setManagerInfo(result.data);
}

getManagerInfo();


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
                managerInfoStore.removeManagerInfo()

                router.push('/manager/login')

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
        router.push('/manager/' + command)
    }
}
</script>

<template>
    <el-container class="layout-container">
        <el-aside width="200px">
            <div class="el-aside__logo"></div>
            <el-menu active-text-color="#409eff" background-color="#324057" text-color="#ffffff" router>
                <el-menu-item index="/requisition/approval">
                    <el-icon>
                        <Management />
                    </el-icon>
                    <span>采购申请</span>
                </el-menu-item>
                <el-menu-item index="/quotation/list">
                    <el-icon>
                        <Management />
                    </el-icon>
                    <span>报价列表</span>
                </el-menu-item>
                <el-menu-item index="/po/approval">
                    <el-icon>
                        <Management />
                    </el-icon>
                    <span>订单管理</span>
                </el-menu-item>
                <el-menu-item index="/performance/all">
                    <el-icon>
                        <Star />
                    </el-icon>
                    <span>供应商绩效考核</span>
                </el-menu-item>
            </el-menu>
        </el-aside>
        <el-container>
            <el-header>
                <div>用户：<strong>{{ managerInfoStore.managerInfo.name }}</strong></div>
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
</style>@/api/manager.js