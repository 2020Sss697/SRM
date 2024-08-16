import request from '@/utils/request.js'

export const quotationListService = ()=>{
    return request.get('/quotation')
}

export const quotationWinService = (id) => {
    return request.put('/quotation/win?id=' + id);
}

export const quotationListAllService = () => {
    return request.get('/quotation/list')
}

export const quotationAddService = (quotationData)=>{
    return request.post('/quotation', quotationData)
}

export const quotationUpdateService = (quotationData) => {
    return request.put('/quotation', quotationData)
}

export const quotationDeleteService = (id) => {
    return request.delete('/quotation?id=' + id)
}

export const quotationListIService = (inquiryId) => {
    return request.get('/quotation/listI?inquiryId=' + inquiryId)
}

export const quotationListIiService = (inquiryId) => {
    return request.get('/quotation/listIi?inquiryId=' + inquiryId)
}