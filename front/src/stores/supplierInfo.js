import { defineStore} from "pinia";
import {ref} from 'vue'
const useSupplierInfoStore = defineStore('supplierInfo',()=>{

    const supplierInfo = ref({})

    const setSupplierInfo = (newSupplierInfo)=>{
        supplierInfo.value = newSupplierInfo
    }

    const removeSupplierInfo = ()=>{
        supplierInfo.value = {}
    }
    return { supplierInfo, setSupplierInfo, removeSupplierInfo }
    },
    {
        persist:true
    }
)

export default useSupplierInfoStore;