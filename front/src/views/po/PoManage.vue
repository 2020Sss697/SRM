<script setup>
import { InfoFilled } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { poListService, poReceiveService } from "@/api/po";
import { ElMessage, ElMessageBox, scrollbarContextKey } from "element-plus";

const po = ref([
])

const poList = async () => {
    const result = await poListService();
    po.value = result.data
}

poList()
const receive = (row) => {
    ElMessageBox.confirm(
        '你确定要收货吗？',
        '温馨提示',
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning'
        }
    ).then(
        async () => {
            await poReceiveService(row.id)
            await poList()
        }
    )
}
const pageSize = ref(3)
const pageNum = ref(1)
const handleSizeChange = (size) => {
    pageSize.value = size
    poList()
}

const handleCurrentChange = (num) => {
    pageNum.value = num
    poList()
}
</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>订单列表</span>
            </div>
        </template>
        <el-table :data="po.slice((pageNum - 1) * pageSize, pageNum * pageSize) " style="width: 100%">
            <el-table-column label="序号" width="100" prop="id" type="index"></el-table-column>
            <el-table-column label="供应商名称" prop="supplierName"></el-table-column>
            <el-table-column label="物料名称" prop="materialName"></el-table-column>
            <el-table-column label="物料数量" prop="materialNum"></el-table-column>
            <el-table-column label="物流单号" prop="trackingNum"></el-table-column>
            <el-table-column label="状态">
                <template #default="scope">
                    <el-tag type="info" size="default" v-if="scope.row.status == 0">待审批</el-tag>
                    <el-tag type="primary" size="default" v-if="scope.row.status == 1">待发货</el-tag>
                    <el-tag type="danger" size="default" v-if="scope.row.status == 2">驳回</el-tag>
                    <el-tag type="success" size="default" v-if="scope.row.status == 3">已发货</el-tag>
                    <el-tag type="warning" size="default" v-if="scope.row.status == 4">已结束</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="100">
                <template #default="{ row }">
                    <el-button v-if="row.status == 3" type="primary" @click="receive(row)">确认收货</el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>
        <div class="pagination-container">
            <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[3, 5, 10, 15]"
                background :total="po.length" layout="jumper, total, sizes, prev, pager, next"
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
