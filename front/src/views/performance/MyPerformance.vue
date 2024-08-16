<script setup>
import { More } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { performanceListMyService } from "@/api/performance";
import { ElMessage } from "element-plus";

const performance = ref([
])

const performanceListMy = async () => {
    const result = await performanceListMyService();
    performance.value = result.data
}

performanceListMy()
const dialogVisible = ref(false)
const dialogVisible1 = ref(false)
const form = ref(null)

const performanceModel = ref({
    supplierName: '',
    quality: '',
    service: '',
    deliver: '',
    remarks:''
})

const showDialog = (row) => {
    dialogVisible.value = true
    performanceModel.value = {
        supplierName: row.supplierName,
        quality: row.quality,
        service: row.service,
        deliver: row.deliver,
        score: row.score,
        remarks: row.remarks,
        id: row.id
    }
}
const textarea2 = ref('')
const pageSize = ref(3)
const pageNum = ref(1)
const handleSizeChange = (size) => {
    pageSize.value = size
    performanceListMy()
}

const handleCurrentChange = (num) => {
    pageNum.value = num
    performanceListMy()
}

</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>我的绩效表现</span>
            </div>
        </template>
        <el-table :data="performance.slice((pageNum - 1) * pageSize, pageNum * pageSize)" style="width: 100%">
            <el-table-column label="序号" width="100" prop="id" type="index"></el-table-column>
            <el-table-column label="考核期限" prop="timeLimit"></el-table-column>
            <el-table-column label="质量得分" prop="quality"></el-table-column>
            <el-table-column label="服务得分" prop="service"></el-table-column>
            <el-table-column label="交付得分" prop="deliver"></el-table-column>
            <el-table-column label="总分" prop="score"></el-table-column>
            <el-table-column label="状态">
                <template #default="scope">
                    <el-tag type="primary" size="default" v-if="scope.row.status == 0">考核中</el-tag>
                    <el-tag type="danger" size="default" v-if="scope.row.status == 1">考核结束</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="详情" width="100">
                <template #default="{ row }">
                    <el-button :icon="More" @click="showDialog(row)"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>

        <el-dialog v-model="dialogVisible" width="30%">
            <el-form ref="form" :model="performanceModel" label-width="100px" style="padding-right: 40px">
                <el-form-item label="备注" prop="remarks">
                    <el-input v-model="performanceModel.remarks" readonly style="width: 240px"
                        :autosize="{ minRows: 2, maxRows: 4 }" type="textarea" />
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
