<script setup>
import { Delete, Edit } from '@element-plus/icons-vue'
import { ref } from 'vue'
import {
    requisitionAddService,
    requisitionDeleteService,
    requisitionListService,
    requisitionUpdateService
} from "@/api/requisition";
import {
    inquiryAddService
} from "@/api/inquiry";
import { ElMessage, ElMessageBox } from "element-plus";

const requisition = ref([
])


const requisitionList = async () => {
    const result = await requisitionListService();
    requisition.value = result.data
}

requisitionList()


const dialogVisible = ref(false)
const dialogVisible1 = ref(false)

const requisitionModel = ref({
    materialName: '',
    materialNum: ''
})

const requisitionRules = {
    materialName: [
        { required: true, message: '请输入物料名称', trigger: 'blur' },
    ],
    materialNum: [
        { required: true, message: '请输入数量', trigger: 'blur' },
    ]
}
const inquiryModel = ref({
    materialName: '',
    materialNum: '',
    endTime: ''
})
const inquiryRules = {
    materialName: [
        { required: true, message: '请输入物料名称', trigger: 'blur' },
    ],
    materialNum: [
        { required: true, message: '请输入数量', trigger: 'blur' },
    ],
    endTime:[
        { required: true, message: '请选择截止时间', trigger: 'blur' },
    ]
}
const title = ref('')

const form = ref(null)
const addRequisition = async () => {
    const valid = await form.value.validate();
    if (valid) {
        await requisitionAddService(requisitionModel.value);
        ElMessage.success("添加成功")
        await requisitionList()
        dialogVisible.value = false
    }
}


const showDialog = (row) => {
    dialogVisible.value = true
    title.value = '编辑申请'
    requisitionModel.value = {
        materialName: row.materialName,
        materialNum: row.materialNum,
        id: row.id
    }
}

const updateRequisition = async () => {
    const valid = await form.value.validate();
    if (valid) {
        await requisitionUpdateService(requisitionModel.value);
        ElMessage.success("修改成功")
        await requisitionList()
        dialogVisible.value = false
    }
}

const clearData = () => {
    requisitionModel.value = {
        materialName: '',
        materialNum: ''
    }
}

const deleteRequisition = (row) => {
    ElMessageBox.confirm(
        '你确认要删除该申请吗？',
        '温馨提示',
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning'
        }
    ).then(
        async () => {
            await requisitionDeleteService(row.id)
            ElMessage.success("删除成功")
            await requisitionList()
        }
    )
}
const showDialog1 = (row) => {
    dialogVisible1.value = true
    title.value = '编辑询价'
    inquiryModel.value = {
        materialName: row.materialName,
        materialNum: row.materialNum,
        id: row.id
    }
}
const addInquiry = async () => {
    const valid = await form.value.validate();
    if (valid) {
        await inquiryAddService(inquiryModel.value);
        ElMessage.success("添加成功")
        dialogVisible1.value = false
    }
}
const pageSize = ref(5)
const pageNum = ref(1)
const handleSizeChange = (size) => {
    pageSize.value = size
    requisitionList()
}

const handleCurrentChange = (num) => {
    pageNum.value = num
    requisitionList()
}
</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>采购申请管理</span>
                <div class="extra">
                    <el-button type="primary"
                        @click="dialogVisible = true; title = '添加申请'; clearData()">添加申请</el-button>
                </div>
            </div>
        </template>
        <el-table :data="requisition" style="width: 100%">
            <el-table-column label="序号" width="100" type="index" prop="id"></el-table-column>
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
                    <el-button v-if="row.status !== 1" :icon="Edit" circle plain type="primary"
                        @click="showDialog(row)"></el-button>
                    <el-button v-if="row.status !== 1" :icon="Delete" circle plain type="danger"
                        @click="deleteRequisition(row)"></el-button>
                    <el-button v-if="row.status == 1" type="primary" @click="showDialog1(row)">生成询价</el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>


        <el-dialog v-model="dialogVisible" :title="title" width="30%">
            <el-form ref="form" :model="requisitionModel" :rules="requisitionRules" label-width="100px"
                style="padding-right: 30px">
                <el-form-item label="物料名称" prop="materialName">
                    <el-input v-model="requisitionModel.materialName" minlength="1" maxlength="10"></el-input>
                </el-form-item>
                <el-form-item label="物料数量" prop="materialNum">
                    <el-input v-model="requisitionModel.materialNum" minlength="1" maxlength="15"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="title === '添加申请' ? addRequisition() : updateRequisition()"> 确认
                    </el-button>
                </span>
            </template>
        </el-dialog>

        <el-dialog v-model="dialogVisible1" :title="title" width="25%">
            <el-form ref="form" :model="inquiryModel" :rules="inquiryRules" label-width="100px"
                style="padding-right: 30px">
                <el-form-item label="物料名称" prop="materialName">
                    <el-input v-model="inquiryModel.materialName" readonly></el-input>
                </el-form-item>
                <el-form-item label="物料数量" prop="materialNum">
                    <el-input v-model="inquiryModel.materialNum" readonly></el-input>
                </el-form-item>
                <el-form-item label="截止时间" prop="endTime">
                    <el-date-picker v-model="inquiryModel.endTime" type="datetime" placeholder="选择截止时间"
                        format="YYYY-MM-DD HH:mm:ss" value-format="YYYY-MM-DD HH:mm:ss" />
                </el-form-item>
            </el-form>
            <template #footer>
                <span class=" dialog-footer">
                    <el-button @click="dialogVisible1 = false">取消</el-button>
                    <el-button type="primary" @click="addInquiry()"> 确认
                    </el-button>
                </span>
            </template>
        </el-dialog>
        <div class="pagination-container">
            <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[5, 10, 15]"
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