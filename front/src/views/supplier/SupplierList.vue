<script setup>
import { InfoFilled } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { performanceAddService } from "@/api/performance";
import { supplierListAllService } from "@/api/supplier";
import { ElMessage } from "element-plus";

const supplier = ref([
])

const supplierListAll = async () => {
    const result = await supplierListAllService();
    supplier.value = result.data
}
const performanceRule ={
    timeLimit: [
        { required: true, message: '请选择考核期限', trigger: 'blur' },
    ]
}

supplierListAll()
const dialogVisible = ref(false)
const form = ref(null)

const performanceModel = ref({
    supplierId: '',
    supplierName: '',
    timeLimit: ''
})
const showDialog = (row) => {
    dialogVisible.value = true
    performanceModel.value = {
        supplierId: row.id,
        supplierName: row.name
    }
}

const addPerformance = async () => {
    const valid = await form.value.validate();
    if (valid) {
        await performanceAddService(performanceModel.value);
        ElMessage.success("添加成功")
        dialogVisible.value = false
    }
}

const pageSize = ref(3)
const pageNum = ref(1)
const handleSizeChange = (size) => {
    pageSize.value = size
    supplierListAll()
}

const handleCurrentChange = (num) => {
    pageNum.value = num
    supplierListAll()
}

</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>供应商列表</span>
            </div>
        </template>
        <el-table :data="supplier.slice((pageNum - 1) * pageSize, pageNum * pageSize) " style="width: 100%">
            <el-table-column label="序号" width="100" prop="id"></el-table-column>
            <el-table-column label="供应商名称" prop="name"></el-table-column>
            <el-table-column label="状态">
                <template #default="scope">
                    <el-tag type="warning" size="default" v-if="scope.row.status == 0">注册</el-tag>
                    <el-tag type="success" size="default" v-if="scope.row.status == 1">试用</el-tag>
                    <el-tag type="primary" size="default" v-if="scope.row.status == 2">正式</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="供应商联系人" prop="contacts"></el-table-column>
            <el-table-column label="考核" width="100">
                <template #default="{ row }">
                    <el-button v-if="row.status !== 0" :icon="InfoFilled" circle plain type="primary"
                        @click="showDialog(row)"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>

        <el-dialog v-model="dialogVisible" width="25%">
            <el-form ref="form" :model="performanceModel" label-width="100px" :rules="performanceRule"
                style="padding-right: 40px">
                <el-form-item label="供应商ID" prop="supplierId">
                    <el-input v-model="performanceModel.supplierId" readonly></el-input>
                </el-form-item>
                <el-form-item label="供应商名称" prop="supplierName">
                    <el-input v-model="performanceModel.supplierName" readonly></el-input>
                </el-form-item>
                <el-form-item label="截止时间" prop="timeLimit">
                    <el-date-picker v-model="performanceModel.timeLimit" type="datetime" placeholder="选择考核期限"
                        format="YYYY-MM-DD HH:mm:ss" value-format="YYYY-MM-DD HH:mm:ss" />
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="addPerformance()"> 确认
                    </el-button>
                </span>
            </template>
        </el-dialog>
        <div class="pagination-container">
            <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[3, 5, 10, 15]"
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
