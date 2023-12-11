// 查询列表接口
const getMedicinePage = (params) => {
  return $axios({
    url: '/medicine/page',
    method: 'get',
    params
  })
}

// 删除接口
const deleteMedicine = (ids) => {
  return $axios({
    url: '/medicine',
    method: 'delete',
    params: { ids }
  })
}

// 修改接口
const editMedicine = (params) => {
  return $axios({
    url: '/medicine',
    method: 'put',
    data: { ...params }
  })
}

// 新增接口
const addMedicine = (params) => {
  return $axios({
    url: '/medicine',
    method: 'post',
    data: { ...params }
  })
}

// 查询详情
const queryMedicineById = (id) => {
  return $axios({
    url: `/medicine/${id}`,
    method: 'get'
  })
}

// 获取药品分类列表
const getCategoryList = (params) => {
  return $axios({
    url: '/category/list',
    method: 'get',
    params
  })
}

// 查药品列表的接口
const queryMedicineList = (params) => {
  return $axios({
    url: '/medicine/list',
    method: 'get',
    params
  })
}

// 文件down预览
const commonDownload = (params) => {
  return $axios({
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
    },
    url: '/common/download',
    method: 'get',
    params
  })
}

// 起售停售---批量起售停售接口
const medicineStatusByStatus = (params) => {
  return $axios({
    url: `/medicine/status/${params.status}`,
    method: 'post',
    params: { ids: params.id }
  })
}