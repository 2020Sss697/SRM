<script setup>
import { ref } from 'vue'
import { myPoService, addTrackingNumService, poDeliverService } from "@/api/po";
import { ElMessage,ElMessageBox } from "element-plus";

const po = ref([])

const dialogVisible = ref(false)
const poModel = ref({
    materialName: '',
    materialNum: '',
    price: '',
    trackingNum:''
})
const rules = {
    trackingNum: [
        { required: true, message: '请输入物流单号', trigger: 'blur' }
    ]
}

const title = ref('')
const form = ref(null)


const myPoList = async () => {
    const result = await myPoService();
    po.value = result.data;
}

myPoList()

const showDialog = (row) => {
    dialogVisible.value = true
    poModel.value = {
        materialName: row.materialName,
        materialNum: row.materialNum,
        price: row.price,
        id: row.id
    }
}
const showDialog1 = (row) => {
    dialogVisible.value = true
    poModel.value = {
        materialName: row.materialName,
        materialNum: row.materialNum,
        price: row.price,
        trackingNum: row.trackingNum,
        id: row.id
    }
}

const addTrackingNum = async() =>{
    const valid = await form.value.validate();
    if (valid) {
        await addTrackingNumService(poModel.value);
        ElMessage.success("添加成功")
        dialogVisible.value = false
        await myPoList();
    }
}
const deliver = (row) => {
    ElMessageBox.confirm(
        '你确认要发货吗？',
        '温馨提示',
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning'
        }
    ).then(
        async () => {
            await poDeliverService(row.id)
            await myPoList()
        }
    )
}
const pageSize = ref(3)
const pageNum = ref(1)
const handleSizeChange = (size) => {
    pageSize.value = size
    myPoList()
}

const handleCurrentChange = (num) => {
    pageNum.value = num
    myPoList()
}
</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>我的订单</span>
            </div>
        </template>
        <el-table :data="po.slice((pageNum - 1) * pageSize, pageNum * pageSize) " style="width: 100%">
            <el-table-column label="序号" width="100" type="index" prop="id"></el-table-column>
            <el-table-column label="采购员序号" prop="buyerId"></el-table-column>
            <el-table-column label="物料名称" prop="materialName"></el-table-column>
            <el-table-column label="物料数量" prop="materialNum"></el-table-column>
            <el-table-column label="价格" prop="price"></el-table-column>
            <el-table-column label="创建时间" prop="createTime"></el-table-column>
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
                    <el-button v-if="row.status==1" type="primary" @click="deliver(row)">发货</el-button>
                    <el-button v-if="row.status == 3" type="primary" @click="showDialog1(row)">查看详情</el-button>

                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>

        <el-dialog v-model="dialogVisible" :title="title" width="30%">
            <el-form ref="form" :model="poModel" :rules="rules" label-width="100px" style="padding-right: 30px">
                <el-form-item label="物料名称" prop="materialName">
                    <el-input v-model="poModel.materialName" minlength="1" maxlength="10" readonly></el-input>
                </el-form-item>
                <el-form-item label="物料数量" prop="materialNum">
                    <el-input v-model="poModel.materialNum" minlength="1" maxlength="10" readonly></el-input>
                </el-form-item>
                <el-form-item label="价格" prop="price">
                    <el-input v-model="poModel.price" minlength="1" maxlength="15" readonly></el-input>
                </el-form-item>
                <el-form-item label="物流单号" prop="trackingNum">
                    <el-input v-model="poModel.trackingNum" minlength="1" maxlength="10"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="addTrackingNum()"> 确认
                    </el-button>
                </span>
            </template>
        </el-dialog>
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