<script setup>
import { ref } from 'vue'
import { quotationListAllService } from "@/api/quotation";

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
                <span>报价单</span>
            </div>
        </template>
        <el-table :data="quotation.slice((pageNum - 1) * pageSize, pageNum * pageSize) " style="width: 100%">
            <el-table-column label="序号" width="100" prop="id" type="index"></el-table-column>
            <el-table-column label="对应询价单" prop="inquiryId"></el-table-column>
            <el-table-column label="供应商名称" prop="supplierName"></el-table-column>
            <el-table-column label="物料名称" prop="materialName"></el-table-column>
            <el-table-column label="物料数量" prop="materialNum"></el-table-column>
            <el-table-column label="交付时间" prop="deliverTime"></el-table-column>
            <el-table-column label="价格" prop="price"></el-table-column>
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
.avatar-uploader {
    :deep() {
        .avatar {
            width: 178px;
            height: 178px;
            display: block;
        }

        .el-upload {
            border: 1px dashed var(--el-border-color);
            border-radius: 6px;
            cursor: pointer;
            position: relative;
            overflow: hidden;
            transition: var(--el-transition-duration-fast);
        }

        .el-upload:hover {
            border-color: var(--el-color-primary);
        }

        .el-icon.avatar-uploader-icon {
            font-size: 28px;
            color: #8c939d;
            width: 178px;
            height: 178px;
            text-align: center;
        }
    }
}

.editor {
    width: 100%;

    :deep(.ql-editor) {
        min-height: 200px;
    }
}
</style>