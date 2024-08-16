<script setup>
import { Check, Close } from '@element-plus/icons-vue'
import { ref } from 'vue'
import {
    requisitionListAllService,
    requisitionRejectService,
    requisitionApprovalService
} from "@/api/requisition";
import { ElMessage, ElMessageBox } from "element-plus";

const requisition = ref([
])


const requisitionListAll = async () => {
    const result = await requisitionListAllService();
    requisition.value = result.data
}

requisitionListAll()

const approve = (row) => {
    ElMessageBox.confirm(
        '你确认要同意该申请吗？',
        '温馨提示',
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning'
        }
    ).then(
        async () => {
            await requisitionApprovalService(row.id)
            ElMessage.success("已同意该采购申请")
            await requisitionListAll()
        }
    )
}
const reject = (row) => {
    ElMessageBox.confirm(
        '你确认要拒绝该申请吗？',
        '温馨提示',
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning'
        }
    ).then(
        async () => {
            await requisitionRejectService(row.id)
            ElMessage.error("已拒绝该采购申请")
            await requisitionListAll()
        }
    )
}
const pageSize = ref(3)
const pageNum = ref(1)
const handleSizeChange = (size) => {
    pageSize.value = size
    requisitionListAll()
}

const handleCurrentChange = (num) => {
    pageNum.value = num
    requisitionListAll()
}

</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>采购申请审批</span>
            </div>
        </template>
        <el-table :data="requisition.slice((pageNum - 1) * pageSize, pageNum * pageSize) " style="width: 100%">
            <el-table-column label="序号" width="100" type="index" prop="id"></el-table-column>
            <el-table-column label="采购员" prop="buyerName"></el-table-column>
            <el-table-column label="物料名称" prop="materialName"></el-table-column>
            <el-table-column label="物料数量" prop="materialNum"></el-table-column>
            <el-table-column label="创建时间" prop="createTime"></el-table-column>
            <el-table-column label="状态" prop="status">
                <template #default="scope">
                    <el-tag type="warning" size="default" v-if="scope.row.status == 0">等待审核</el-tag>
                    <el-tag type="success" size="default" v-if="scope.row.status == 1">审核通过</el-tag>
                    <el-tag type="danger" size="default" v-if="scope.row.status == 2">审核拒绝</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="100">
                <template #default="{ row }">
                    <el-button :icon="Check" v-if="row.status == 0" circle plain type="primary"
                        @click="approve(row)"></el-button>
                    <el-button :icon="Close" v-if="row.status == 0" circle plain type="danger"
                        @click="reject(row)"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>
        <div class="pagination-container">
            <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[3, 5, 10, 15]"
                background :total="requisition.length" layout="jumper, total, sizes, prev, pager, next"
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