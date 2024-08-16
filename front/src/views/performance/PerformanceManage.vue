<script lang="ts" setup>
import { reactive, ref } from 'vue'

const size = ref('default')
const labelPosition = ref('right')


const sizeForm = reactive({
    name: '',
    region: '',
    date1: '',
    date2: '',
    delivery: false,
    type: [],
    resource: '',
    desc: '',
})

function onSubmit() {
    console.log('submit!')
}


const value1 = ref('')
const value2 = ref('')

const shortcuts = [
    {
        text: 'Last week',
        value: () => {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
            return [start, end]
        },
    },
    {
        text: 'Last month',
        value: () => {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
            return [start, end]
        },
    },
    {
        text: 'Last 3 months',
        value: () => {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
            return [start, end]
        },
    },
]
</script>

<template>
    <el-card class="page-container">
        <el-form ref="form" style="max-width: 600px" :model="sizeForm" label-width="auto"
            :label-position="labelPosition" :size="size">
            <el-form-item label="供应商名称">

            </el-form-item>
            <el-form-item label="考核时间">
                <div class="block">
                    <el-date-picker v-model="value2" type="daterange" unlink-panels range-separator="To"
                        start-placeholder="开始时间" end-placeholder="结束时间" :shortcuts="shortcuts" :size="size" />
                </div>
            </el-form-item>
            <el-form-item label="Activity type">
                <el-checkbox-group v-model="sizeForm.type">
                    <el-checkbox-button value="Online activities" name="type">
                        Online activities
                    </el-checkbox-button>
                    <el-checkbox-button value="Promotion activities" name="type">
                        Promotion activities
                    </el-checkbox-button>
                </el-checkbox-group>
            </el-form-item>
            <el-form-item label="Resources">
                <el-radio-group v-model="sizeForm.resource">
                    <el-radio border value="Sponsor">Sponsor</el-radio>
                    <el-radio border value="Venue">Venue</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="onSubmit">Create</el-button>
                <el-button>Cancel</el-button>
            </el-form-item>
        </el-form>
    </el-card>
</template>

<style>
.el-radio-group {
    margin-right: 12px;
}
.page-container {
    min-height: 100%;
    box-sizing: border-box;

    .header {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
}
.demo-date-picker {
    display: flex;
    width: 100%;
    padding: 0;
    flex-wrap: wrap;
}

.demo-date-picker .block {
    padding: 30px 0;
    text-align: center;
    border-right: solid 1px var(--el-border-color);
    flex: 1;
}

.demo-date-picker .block:last-child {
    border-right: none;
}

.demo-date-picker .demonstration {
    display: block;
    color: var(--el-text-color-secondary);
    font-size: 14px;
    margin-bottom: 20px;
}
</style>