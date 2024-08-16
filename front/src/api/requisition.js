import request from '@/utils/request.js'

export const requisitionListService = () => {
    return request.get('/requisition')
}
export const requisitionListAllService = () => {
    return request.get('/requisition/all')
}

export const requisitionAddService = (requisitionData) => {
    return request.post('/requisition', requisitionData)
}

export const requisitionDeleteService = (id) => {
    return request.delete('/requisition?id=' + id)
}

export const requisitionUpdateService = (requisitionData) => {
    return request.put('/requisition', requisitionData)
}

export const requisitionApprovalService = (id) => {
    return request.put('/requisition/approve?id=' + id);
}

export const requisitionRejectService = (id) => {
    return request.put('/requisition/reject?id=' + id);
}