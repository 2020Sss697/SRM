<script setup>
import { ref } from 'vue'
import useSupplierInfoStore from '@/stores/supplierInfo.js'
const supplierInfoStore = useSupplierInfoStore();

const supplierInfo = ref({ ...supplierInfoStore.supplierInfo })
const rules = {
    name: [
        { required: true, message: '请输入供应商名称', trigger: 'blur' },
        {
            pattern: /^\S{2,10}$/,
            message: '名称必须是2-10位的非空字符串',
            trigger: 'blur'
        }
    ],
    contacts:[
        { required: true, message: '请输入供应商联系人', trigger: 'blur' },
        {
            pattern: /^\S{1,5}$/,
            message: '名称必须是1-5位的非空字符串',
            trigger: 'blur'
        } 
    ],
    phone:[
        { required: true, message: '请输入联系人电话', trigger: 'blur' },
        {
            pattern: /^\d{11}$/,
            message: '手机号码格式错误',
            trigger: 'blur'
        } 
    ],
    email: [
        { required: true, message: '请输入供应商邮箱', trigger: 'blur' },
        { type: 'email', message: '邮箱格式不正确', trigger: 'blur' }
    ],
    address: [
        { required: true, message: '请输入供应商地址', trigger: 'blur' },
    ]
}


import { supplierInfoUpdateService } from '@/api/supplier.js'
import { ElMessage } from 'element-plus'
const updateSupplierInfo = async () => {

    let result = await supplierInfoUpdateService(supplierInfo.value);
    ElMessage.success(result.msg ? result.msg : '修改成功')
    supplierInfoStore.setSupplierInfo(supplierInfo.value)
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
                <el-form :model="supplierInfo" :rules="rules" label-width="100px" size="large">
                    <el-form-item label="登录名称">
                        <el-input v-model="supplierInfo.username"></el-input>
                    </el-form-item>
                    <el-form-item label="供应商名称" prop="name">
                        <el-input v-model="supplierInfo.name"></el-input>
                    </el-form-item>
                    <el-form-item label="供应商联系人" prop="contacts">
                        <el-input v-model="supplierInfo.contacts"></el-input>
                    </el-form-item>
                    <el-form-item label="联系人电话" prop="phone">
                        <el-input v-model="supplierInfo.phone"></el-input>
                    </el-form-item>
                    <el-form-item label="供应商邮箱" prop="email">
                        <el-input v-model="supplierInfo.email"></el-input>
                    </el-form-item>
                    <el-form-item label="供应商地址" prop="address">
                        <el-input v-model="supplierInfo.address"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="updateSupplierInfo">提交修改</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
    </el-card>
</template>@/api/supplier.js