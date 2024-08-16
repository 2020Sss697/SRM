<script>
export default {
    data() {
        return {
            welcomeMessage: "欢迎使用SRM供应商关系管理系统"
        };
    }
};
</script>

<template>
    <el-card class="page-container">
        <el-container>
            <el-main class="welcome-message">
                <h1>{{ welcomeMessage }}</h1>
            </el-main>
        </el-container>
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
}

.welcome-page {
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
}

.welcome-message {
    text-align: center;
    font-size: 30px;
    color: #409eff
}
</style>
