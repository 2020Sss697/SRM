import { defineStore } from "pinia";
import { ref } from 'vue'
const useManagerInfoStore = defineStore('managerInfo', () => {
    //定义状态相关的内容

    const managerInfo = ref({})

    const setManagerInfo = (newManagerInfo) => {
        managerInfo.value = newManagerInfo
    }

    const removeManagerInfo = () => {
        managerInfo.value = {}
    }
    return { managerInfo, setManagerInfo, removeManagerInfo }
}, { persist: true })

export default useManagerInfoStore;