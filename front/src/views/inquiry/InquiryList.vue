<script setup>
import { ref, onMounted } from 'vue'
import { inquiryListAllService} from "@/api/inquiry";
import { More } from '@element-plus/icons-vue'
import { quotationListService, quotationListIService, quotationListIiService } from "@/api/quotation";
import { ElMessage } from "element-plus";
import { quotationAddService } from "@/api/quotation";
import dayjs from 'dayjs';
const inquiry = ref([])
const quotation = ref([])

const dialogVisible = ref(false)
const dialogVisible1 = ref(false)

const quotationModel = ref({
    inquiryId: '',
    materialName: '',
    materialNum: '',
    price: '',
    deliverTime:''
})

const rules = {
    price: [
        { required: true, message: '请输入价格', trigger: 'blur' }
    ],
    deliverTime: [
        { required: true, message: '请输入交付时间', trigger: 'blur' }
    ]
}
const title = ref('')
const form = ref(null)

onMounted(async () => {
    await inquiryListAll();
    await getQuotationList();
});

const inquiryListAll = async () => {
    const result = await inquiryListAllService();
    inquiry.value = result.data;
}


const getQuotationList = async () => {
    const result = await quotationListService();
    quotation.value = result.data.map(item => item.inquiryId); 
}
const isPastDeadline = (row) => {
    const currentTime = dayjs();
    const inquiryEndTime = dayjs(row.endTime);
    return currentTime.isAfter(inquiryEndTime);
}


const showDialog = (row) => {
    if (!quotation.value.includes(row.id)) {
        dialogVisible.value = true
        quotationModel.value = {
            inquiryId: row.id,
            materialName: row.materialName,
            materialNum: row.materialNum
        }
    } else {
        ElMessage.warning("该询价单已经被报价！");
    }
}


const showDialog1 = async (row) => {
    dialogVisible1.value = true;
    const currentTime = dayjs()
    const inquiryEndTime = dayjs(row.endTime)
    if (currentTime.isBefore(inquiryEndTime)) {
        const result = await quotationListIiService(row.id)
        quotation.value = result.data;
    } else {
        const result = await quotationListIService(row.id);
        quotation.value = result.data;
    }
}
const addQuotation = async () => {
    const valid = await form.value.validate();
    if (valid) {
        await quotationAddService(quotationModel.value);
        ElMessage.success("添加成功")
        dialogVisible.value = false
        await inquiryListAll();
    }
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
                <span>询价通知</span>
            </div>
        </template>
        <el-table :data="inquiry.slice((pageNum - 1) * pageSize, pageNum * pageSize) " style="width: 100%">
            <el-table-column label="序号" width="100" type="index" prop="id"></el-table-column>
            <el-table-column label="采购员序号" prop="buyerId"></el-table-column>
            <el-table-column label="物料名称" prop="materialName"></el-table-column>
            <el-table-column label="物料数量" prop="materialNum"></el-table-column>
            <el-table-column label="截止时间" prop="endTime"></el-table-column>
            <el-table-column label="详情" width="100">
                <template #default="{ row }">
                    <el-button :icon="More" @click="showDialog1(row)"></el-button>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="100">
                <template #default="{ row }">
                    <el-button v-if="!isPastDeadline(row) " type="primary" @click="showDialog(row)">报价</el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>

        <el-dialog v-model="dialogVisible" :title="title" width="30%">
            <el-form ref="form" :model="quotationModel" :rules="rules" label-width="100px" style="padding-right: 30px">
                <el-form-item label="对应询价单" prop="inquiryId">
                    <el-input v-model="quotationModel.inquiryId" minlength="1" maxlength="10" readonly></el-input>
                </el-form-item>
                <el-form-item label="物料名称" prop="materialName">
                    <el-input v-model="quotationModel.materialName" minlength="1" maxlength="10" readonly></el-input>
                </el-form-item>
                <el-form-item label="物料数量" prop="materialNum">
                    <el-input v-model="quotationModel.materialNum" minlength="1" maxlength="10" readonly></el-input>
                </el-form-item>
                <el-form-item label="价格" prop="price">
                    <el-input v-model="quotationModel.price" minlength="1" maxlength="15"></el-input>
                </el-form-item>
                <el-form-item label="交付时间" prop="deliverTime">
                    <el-input v-model="quotationModel.deliverTime" minlength="1" maxlength="15"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="addQuotation()"> 确认
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
</style>