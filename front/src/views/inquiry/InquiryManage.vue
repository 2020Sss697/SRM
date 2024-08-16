<script setup>
import { Delete, Edit, More,Calendar } from '@element-plus/icons-vue'
import { ref } from 'vue'
import {
    inquiryAddService,
    inquiryDeleteService,
    inquiryListAllService,
    inquiryUpdateService
} from "@/api/inquiry";
import { poAddService } from "@/api/po";
import { quotationListIService, quotationWinService } from "@/api/quotation";
import { ElMessage, ElMessageBox } from "element-plus";

const inquiry = ref([
])
const quotation = ref([])

const inquiryListAll = async () => {
    const result = await inquiryListAllService();
    inquiry.value = result.data
}

inquiryListAll()


const dialogVisible = ref(false)
const dialogVisible1 = ref(false)

const inquiryModel = ref({
    materialName: '',
    materialNum: '',
    endTime:''
})

const rules = {
    materialName: [
        { required: true, message: '请输入物料名称', trigger: 'blur' },
    ],
    materialNum: [
        { required: true, message: '请输入数量', trigger: 'blur' },
    ]
}
const title = ref('')

const form = ref(null)
const addInquiry = async () => {
    const valid = await form.value.validate();
    if (valid) {
        await inquiryAddService(inquiryModel.value);
        ElMessage.success("添加成功")
        await inquiryListAll()
        dialogVisible.value = false
    }
}

const showDialog = (row) => {
    dialogVisible.value = true
    title.value = '编辑询价'
    inquiryModel.value = {
        materialName: row.materialName,
        materialNum: row.materialNum,
        id: row.id
    }
}
const showDialog1 = async (row) => {
    dialogVisible1.value = true;
    const result = await quotationListIService(row.id);
    quotation.value = result.data;
}


const updateInquiry = async () => {
    const valid = await form.value.validate();
    if (valid) {
        await inquiryUpdateService(inquiryModel.value);
        ElMessage.success("修改成功")
        await inquiryListAll()
        dialogVisible.value = false
    }
}
const poModel = ref({
    supplierId: '',
    supplierName: '',
    materialName: '',
    materialNum: '',
    price: '',
})

const deleteInquiry = (row) => {
    ElMessageBox.confirm(
        '你确认要删除该询价吗？',
        '温馨提示',
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning'
        }
    ).then(
        async () => {
            await inquiryDeleteService(row.id)
            ElMessage.success("删除成功")
            await inquiryListAll()
        }
    )
}
const addPo = async (row) => {
    poModel.value = {
        supplierName: row.supplierName,
        materialName: row.materialName,
        materialNum: row.materialNum,
        price: row.price
    }
    await poAddService(poModel.value);
    await quotationWinService(row.id)
    ElMessage.success('添加成功')
    dialogVisible1.value = false

}
const pageSize = ref(3)
const pageNum = ref(1)
const handleSizeChange = (size) => {
    pageSize.value = size
    inquiryListAll()
}

const handleCurrentChange = (num) => {
    pageNum.value = num
    inquiryListAll()
}

</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>询价单管理</span>
            </div>
        </template>
        <el-table :data="inquiry.slice((pageNum - 1) * pageSize, pageNum * pageSize) " style="width: 100%">
            <el-table-column label="序号" width="100" type="index" prop="id"></el-table-column>
            <el-table-column label="物料名称" prop="materialName"></el-table-column>
            <el-table-column label="物料数量" prop="materialNum"></el-table-column>
            <el-table-column label="截止时间" prop="endTime"></el-table-column>
            <el-table-column label="报价详情" width="100">
                <template #default="{ row }">
                    <el-button :icon="More" @click="showDialog1(row)"></el-button>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="100">
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary" @click="showDialog(row)"></el-button>
                    <el-button :icon="Delete" circle plain type="danger" @click="deleteInquiry(row)"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>


        <el-dialog v-model="dialogVisible" :title="title" width="25%">
            <el-form ref="form" :model="inquiryModel" :rules="rules" label-width="100px" style="padding-right: 30px">
                <el-form-item label="物料名称" prop="materialName">
                    <el-input v-model="inquiryModel.materialName" minlength="1" maxlength="10"></el-input>
                </el-form-item>
                <el-form-item label="物料数量" prop="materialNum">
                    <el-input v-model="inquiryModel.materialNum" minlength="1" maxlength="15"></el-input>
                </el-form-item>
                <el-form-item label="截止时间" prop="endTime">
                    <el-date-picker v-model="inquiryModel.endTime" type="datetime" placeholder="选择截止时间"
                        format="YYYY-MM-DD HH:mm:ss" value-format="YYYY-MM-DD HH:mm:ss" />
                </el-form-item>
            </el-form>
            <template #footer>
                <span class=" dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="title === '添加询价' ? addInquiry() : updateInquiry()"> 确认
                    </el-button>
                </span>
            </template>
        </el-dialog>

        <el-dialog v-model="dialogVisible1" :title="title" width="45%">
            <el-table :data="quotation" :default-sort="{ prop: 'price', order: 'descending' }" style="width: 100%">
                <el-table-column label="序号" width="100" type="index" prop="id"></el-table-column>
                <el-table-column label="供应商名称" prop="supplierName"></el-table-column>
                <el-table-column label="价格" prop="price" sortable></el-table-column>
                <el-table-column label="交付时间" prop="deliverTime"></el-table-column>
                <el-table-column label="状态" prop="status">
                    <template #default="scope">
                        <el-tag type="info" size="default" v-if="scope.row.status == 0"></el-tag>
                        <el-tag type="success" size="default" v-if="scope.row.status == 1">胜选</el-tag>
                    </template></el-table-column>
                <el-table-column label="操作" width="100">
                    <template #default="{ row }">
                        <el-button type="primary" @click="addPo(row)">生成订单</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-dialog>

        <div class="pagination-container">
            <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[3, 5, 10, 15]"
                background :total="inquiry.length" layout="jumper, total, sizes, prev, pager, next"
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
        .el-col {
            text-align: center;
        }
    
        .countdown-footer {
            margin-top: 8px;
        }
</style>