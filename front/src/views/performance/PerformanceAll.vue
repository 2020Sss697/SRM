<script setup>
import { InfoFilled } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { performanceListAllService } from "@/api/performance";

const performance = ref([
])

const performanceListAll = async () => {
    const result = await performanceListAllService();
    performance.value = result.data
}

performanceListAll()
const dialogVisible1 = ref(false)
const form = ref(null)

const performanceModel = ref({
    supplierName: '',
    quality: '',
    service: '',
    deliver: ''
})
const showDialog1 = (row) => {
    dialogVisible1.value = true
    performanceModel.value = {
        supplierName: row.supplierName,
        quality: row.quality,
        service: row.service,
        deliver: row.deliver,
        score: row.score,
        id: row.id
    }
}
const pageSize = ref(3)
const pageNum = ref(1)
const handleSizeChange = (size) => {
    pageSize.value = size
    performanceList()
}

const handleCurrentChange = (num) => {
    pageNum.value = num
    performanceList()
}
</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>考核列表</span>
            </div>
        </template>
        <el-table :data="performance.slice((pageNum - 1) * pageSize, pageNum * pageSize) " style="width: 100%">
            <el-table-column label="序号" width="100" prop="id" type="index"></el-table-column>
            <el-table-column label="供应商名称" prop="supplierName"></el-table-column>
            <el-table-column label="考核期限" prop="timeLimit"></el-table-column>
            <el-table-column label="状态">
                <template #default="scope">
                    <el-tag type="primary" size="default" v-if="scope.row.status == 0">考核中</el-tag>
                    <el-tag type="danger" size="default" v-if="scope.row.status == 1">考核结束</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="采购员" prop="buyerId"></el-table-column>
            <el-table-column label="详情" width="100">
                <template #default="{ row }">
                    <el-button :icon="InfoFilled" circle plain type="primary" @click="showDialog1(row)"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>

        <el-dialog v-model="dialogVisible1" width="30%">
            <el-form ref="form" :model="performanceModel" label-width="100px" style="padding-right: 40px">
                <el-form-item label="供应商名称：" prop="supplierName">
                    <el-input v-model="performanceModel.supplierName" readonly></el-input>
                </el-form-item>
                <el-form-item label="质量评分：" prop="quality">
                    <el-input v-model="performanceModel.quality" readonly></el-input>
                </el-form-item>
                <el-form-item label="服务评分：" prop="service">
                    <el-input v-model="performanceModel.service" readonly></el-input>
                </el-form-item>
                <el-form-item label="交付评分：" prop="deliver">
                    <el-input v-model="performanceModel.deliver" readonly></el-input>
                </el-form-item>
                <el-form-item label="总分：" prop="score">
                    <el-input v-model="performanceModel.score" readonly></el-input>
                </el-form-item>
            </el-form>
        </el-dialog>
        <div class="pagination-container">
            <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[3, 5, 10, 15]"
                background :total="performance.length" layout="jumper, total, sizes, prev, pager, next"
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
