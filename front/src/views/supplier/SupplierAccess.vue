<script setup>
import { InfoFilled } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { supplierListWaitService } from "@/api/supplier";
import { supplierAccessService } from "@/api/supplier"
import { ElMessage, ElMessageBox } from "element-plus";


const supplier = ref([
])

const supplierListWait = async () => {
    const result = await supplierListWaitService();
    supplier.value = result.data
}

supplierListWait()

const access = (row) => {
    ElMessageBox.confirm(
        '你确认要同意该供应商准入吗？',
        '温馨提示',
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning'
        }
    ).then(
        async () => {
            await supplierAccessService(row.id)
            ElMessage.success("已同意")
            await supplierListWait()
        }
    )
}

const dialogVisible = ref(false)
const form = ref(null)

const supplierModel = ref({
    name: '',
    contacts: '',
    phone: '',
    email: '',
    address:''
})
const showDialog = (row) => {
    dialogVisible.value = true
    supplierModel.value = {
        name: row.name,
        contacts: row.contacts,
        phone: row.phone,
        email: row.email,
        address: row.address,
        id: row.id
    }
}

const currentPage = ref(1)
const pageSize = ref(3)
const total = ref()
const handleSizeChange = (size) => {
    pageSize.value = size
    supplierListWait()
}

const handleCurrentChange = (num) => {
    currentPage.value = num
    supplierListWait()
}
</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>待准入供应商</span>
            </div>
        </template>
        <el-table :data="supplier.slice((currentPage - 1) * pageSize, currentPage * pageSize) " style="width: 100%">
            <el-table-column label="序号" width="100" prop="id" type="index"></el-table-column>
            <el-table-column label="供应商名称" prop="name"></el-table-column>
            <el-table-column label="状态">
                <template #default="scope">
                    <el-tag type="warning" size="default" v-if="scope.row.status == 0">注册</el-tag>
                    <el-tag type="success" size="default" v-if="scope.row.status == 1">试用</el-tag>
                    <el-tag type="primary" size="default" v-if="scope.row.status == 2">正式</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="供应商联系人" prop="contacts"></el-table-column>
            <el-table-column label="联系电话" prop="phone"></el-table-column>
            <el-table-column label="详细信息" width="100">
                <template #default="{ row }">
                    <el-button :icon="InfoFilled" circle plain type="primary" @click="showDialog(row)"></el-button>
                </template>
            </el-table-column>
            <el-table-column label="管理" width="100">
                <template #default="{ row }">
                    <el-button v-if="row.status==0" type="primary" @click="access(row)">准入</el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>

        <el-dialog v-model="dialogVisible" width="30%">
            <el-form ref="form" :model="supplierModel" label-width="100px" style="padding-right: 40px">
                <el-form-item label="供应商名称：" prop="name">
                    <el-input v-model="supplierModel.name" readonly></el-input>
                </el-form-item>
                <el-form-item label="联系人：" prop="contacts">
                    <el-input v-model="supplierModel.contacts" readonly></el-input>
                </el-form-item>
                <el-form-item label="联系电话：" prop="phone">
                    <el-input v-model="supplierModel.phone" readonly></el-input>
                </el-form-item>
                <el-form-item label="邮箱：" prop="email">
                    <el-input v-model="supplierModel.email" readonly></el-input>
                </el-form-item>
                <el-form-item label="地址：" prop="address">
                    <el-input v-model="supplierModel.address" readonly></el-input>
                </el-form-item>
            </el-form>
        </el-dialog>
        <div class="pagination-container">
            <el-pagination v-model:current-page="currentPage" v-model:page-size="pageSize" :page-sizes="[3, 5, 10, 15]"
                background :total="supplier.length" layout="jumper, total, sizes, prev, pager, next"
                @size-change="handleSizeChange" @current-change="handleCurrentChange"
                style="margin-top: 20px; justify-content: flex-end" />
        </div>
    </el-card>
</template>

<style lang="scss" scoped>
.page-container {
    min-height: 100%;
    box-sizing: border-box;

    .header {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
        .pagination-container {
            position: absolute;
            bottom: 100px;
            right: 80px;
        }
}
</style>
