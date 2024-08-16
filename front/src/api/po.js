import request from '@/utils/request.js'

export const poListService = () => {
    return request.get('/po')
}

export const myPoService = () => {
    return request.get('/po/myPo')
}

export const poAddService = (poData) => {
    return request.post('/po', poData)
}

export const poApprovalService = (id) => {
    return request.put('/po/approve?id=' + id);
}

export const poRejectService = (id) => {
    return request.put('/po/reject?id=' + id);
}

export const poDeliverService = (id) => {
    return request.put('/po/deliver?id=' + id);
}

export const poReceiveService = (id) => {
    return request.put('/po/receive?id=' + id);
}

export const addTrackingNumService = (poData) => {
    return request.put('/po/addTrackingNum',poData);
}