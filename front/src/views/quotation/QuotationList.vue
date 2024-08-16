<script setup>
import { Delete, Edit } from '@element-plus/icons-vue'
import { ref } from 'vue'
import {
    quotationListAllService,
} from "@/api/quotation";
import { ElMessage, ElMessageBox } from "element-plus";


const quotation = ref([
])

const quotationListAll = async () => {
    const result = await quotationListAllService();
    quotation.value = result.data
}



quotationListAll()
const pageSize = ref(3)
const pageNum = ref(1)
const handleSizeChange = (size) => {
    pageSize.value = size
    quotationListAll()
}

const handleCurrentChange = (num) => {
    pageNum.value = num
    quotationListAll()
}
</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>报价单列表</span>
            </div>
        </template>
        <el-table :data="quotation.slice((pageNum - 1) * pageSize, pageNum * pageSize)" style="width: 100%">
            <el-table-column label="序号" width="100" type="index" prop="id"></el-table-column>
            <el-table-column label="对应询价单" prop="inquiryId"></el-table-column>
            <el-table-column label="供应商" prop="supplierName"></el-table-column>
            <el-table-column label="物料名称" prop="materialName"></el-table-column>
            <el-table-column label="价格" prop="price"></el-table-column>
            <el-table-column label="创建时间" prop="createTime"></el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>
        <div class="pagination-container">
            <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[3, 5, 10, 15]"
                background :total="quotation.length" layout="jumper, total, sizes, prev, pager, next"
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