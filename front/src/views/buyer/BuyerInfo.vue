<script setup>
import { ref } from 'vue'
import useBuyerInfoStore from '@/stores/buyerInfo.js'
const buyerInfoStore = useBuyerInfoStore();

const buyerInfo = ref({ ...buyerInfoStore.buyerInfo })
const buyerRules = {
    name: [
        { required: true, message: '请输入供应商名称', trigger: 'blur' },
        {
            pattern: /^\S{2,10}$/,
            message: '名称必须是2-10位的非空字符串',
            trigger: 'blur'
        }
    ],
    phone: [
        { required: true, message: '请输入联系人电话', trigger: 'blur' },
        {
            pattern: /^\d{11}$/,
            message: '手机号码格式错误',
            trigger: 'blur'
        }
    ]
}


import { buyerInfoUpdateService } from '@/api/buyer.js'
import { ElMessage } from 'element-plus'
const updateBuyerInfo = async () => {

    let result = await buyerInfoUpdateService(buyerInfo.value);
    ElMessage.success(result.msg ? result.msg : '修改成功')
    buyerInfoStore.setInfo(buyerInfo.value)
}
</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>基本资料</span>
            </div>
        </template>
        <el-row>
            <el-col :span="12">
                <el-form :model="buyerInfo" :rules="buyerRules" label-width="100px" size="large">
                    <el-form-item label="用户名">
                        <el-input v-model="buyerInfo.username" readonly></el-input>
                    </el-form-item>
                    <el-form-item label="采购员名字" prop="name">
                        <el-input v-model="buyerInfo.name"></el-input>
                    </el-form-item>
                    <el-form-item label="采购员电话" prop="phone">
                        <el-input v-model="buyerInfo.phone"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="updateBuyerInfo">提交修改</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
    </el-card>
</template>@/api/buyer.js