<script setup>
import { Delete, Edit } from '@element-plus/icons-vue'
import { ref } from 'vue'
import {
    quotationDeleteService,
    quotationListService,
    quotationUpdateService
} from "@/api/quotation";
import { ElMessage, ElMessageBox } from "element-plus";

const quotation = ref([
])

const quotationRules = {
    inquiryId:[
        {required: true, message:'请输入对应询价单',trigger:'blur'}
    ],
    materialName: [
        { required: true, message: '请输入物料名称', trigger: 'blur' },
    ],
    price: [
        { required: true, message: '请输入价格', trigger: 'blur' },
    ]
}
const quotationList = async () => {
    const result = await quotationListService();
    quotation.value = result.data
}
quotationList()

//控制抽屉是否显示
const dialogVisible1 = ref(false)

const quotationModel = ref({
    inquiryId:'',
    materialName: '',
    price: ''
})

const form = ref(null)

const showDialog1 = (row) => {
    dialogVisible1.value = true
    quotationModel.value = {
        inquiryId: row.inquiryId,
        materialName: row.materialName,
        price: row.price,
        id: row.id
    }
}

const updateQuotation = async () => {
    const valid = await form.value.validate();
    if (valid) {
        await quotationUpdateService(quotationModel.value);
        ElMessage.success("修改成功")
        await quotationList()
        dialogVisible1.value = false
    }
}

const deleteQuotation = (row) => {
    ElMessageBox.confirm(
        '你确认要删除该报价吗？',
        '温馨提示',
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning'
        }
    ).then(
        async () => {
            await quotationDeleteService(row.id)
            ElMessage.success("删除成功")
            await quotationList()
        }
    )
}
const pageSize = ref(3)
const pageNum = ref(1)
const handleSizeChange = (size) => {
    pageSize.value = size
    quotationList()
}

const handleCurrentChange = (num) => {
    pageNum.value = num
    quotationList()
}
</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>我的报价</span>
            </div>
        </template>
        <el-table :data="quotation.slice((pageNum - 1) * pageSize, pageNum * pageSize) " style="width: 100%">
            <el-table-column label="序号" width="100" type="index" prop="id"></el-table-column>
            <el-table-column label="对应询价单" prop="inquiryId"></el-table-column>
            <el-table-column label="物料名称" prop="materialName"></el-table-column>
            <el-table-column label="价格" prop="price"></el-table-column>
            <el-table-column label="状态" prop="status">
                <template #default="scope">
                    <el-tag type="info" size="default" v-if="scope.row.status == 0"></el-tag>
                    <el-tag type="success" size="default" v-if="scope.row.status == 1">胜选</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="100">
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary" @click="showDialog1(row)"></el-button>
                    <el-button :icon="Delete" circle plain type="danger" @click="deleteQuotation(row)"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>


        <el-dialog v-model="dialogVisible1" width="30%">
            <el-form ref="form" :model="quotationModel" :rules="quotationRules" label-width="100px"
                style="padding-right: 30px">
                <el-form-item label="对应询价单" prop="inquiryId">
                    <el-input v-model="quotationModel.inquiryId" minlength="1" maxlength="10"></el-input>
                </el-form-item>
                <el-form-item label="物料名称" prop="materialName">
                    <el-input v-model="quotationModel.materialName" minlength="1" maxlength="10"></el-input>
                </el-form-item>
                <el-form-item label="价格" prop="price">
                    <el-input v-model="quotationModel.price" minlength="1" maxlength="15"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible1 = false">取消</el-button>
                    <el-button type="primary" @click="updateQuotation()"> 确认
                    </el-button>
                </span>
            </template>
        </el-dialog>
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