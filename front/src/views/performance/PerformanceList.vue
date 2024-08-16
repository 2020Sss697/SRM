<script setup>
import { InfoFilled,EditPen } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { performanceListService,performanceUpdateService } from "@/api/performance";
import { ElMessage } from "element-plus";

const performance = ref([
])

const performanceRules = {
    quality: [
        { required: true, message: '请输入质量得分', trigger: 'blur' }
    ],
    service: [
        { required: true, message: '请输入服务得分', trigger: 'blur' },
    ],
    deliver: [
        { required: true, message: '请输入交付得分', trigger: 'blur' },
    ],
    remarks: [
        { required: true, message: '请输入备注', trigger: 'blur' },
    ]
}
const performanceList = async () => {
    const result = await performanceListService();
    performance.value = result.data
}

performanceList()
const dialogVisible = ref(false)
const dialogVisible1 = ref(false)
const form = ref(null)

const performanceModel = ref({
    supplierName:'',
    quality:'',
    service:'',
    deliver:'',
    remarks:''
})
const showDialog = (row) => {
    dialogVisible.value = true
    performanceModel.value = {
        supplierName: row.supplierName,
        id: row.id
    }
}
const showDialog1 = (row) => {
    dialogVisible1.value = true
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
const updatePerformance = async () => {
    const valid = await form.value.validate();
    if (valid) {
        await performanceUpdateService(performanceModel.value);
        ElMessage.success("评分成功")
        await performanceList()
        dialogVisible.value = false
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
            <el-table-column label="详情" width="100">
                <template #default="{ row }">
                    <el-button v-if="row.status==0" :icon="EditPen" circle plain type="primary"
                        @click="showDialog(row)"></el-button>
                    <el-button v-if="row.status == 1" :icon="InfoFilled" circle plain type="primary"
                        @click="showDialog1(row)"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>

        <el-dialog v-model="dialogVisible" width="30%">
            <el-form ref="form" :model="performanceModel" :rules="performanceRules" label-width="100px"
                style="padding-right: 40px">
                <el-form-item label="供应商名称：" prop="supplierName">
                    <el-input v-model="performanceModel.supplierName" readonly></el-input>
                </el-form-item>
                <el-form-item label="质量评分：" prop="quality">
                    <el-input v-model="performanceModel.quality"></el-input>
                </el-form-item>
                <el-form-item label="服务评分：" prop="service">
                    <el-input v-model="performanceModel.service"></el-input>
                </el-form-item>
                <el-form-item label="交付评分：" prop="deliver">
                    <el-input v-model="performanceModel.deliver"></el-input>
                </el-form-item>
                <el-form-item label="备注" prop="remarks">
                    <el-input v-model="performanceModel.remarks" style="width: 240px"
                        :autosize="{ minRows: 2, maxRows: 4 }" type="textarea" />
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="updatePerformance()"> 确认
                    </el-button>
                </span>
            </template>
        </el-dialog>

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
