import { defineStore } from "pinia";
import { ref } from 'vue'
const useBuyerInfoStore = defineStore('buyerInfo', () => {

    const buyerInfo = ref({})

    const setBuyerInfo = (newBuyerInfo) => {
        buyerInfo.value = newBuyerInfo
    }

    const removeBuyerInfo = () => {
        buyerInfo.value = {}
    }
    return { buyerInfo, setBuyerInfo, removeBuyerInfo }
    }, 
    { 
        persist: true 
    }
)
export default useBuyerInfoStore;

