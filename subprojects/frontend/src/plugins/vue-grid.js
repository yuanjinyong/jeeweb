/**
 * Install plugin.
 */
import Vue from 'vue'

var VueGrid = {
  defaultOptions: {
    debug: false,
    context: {
      name: '',
      url: null,
      getDetailComponent: null,
      getFeatureComponent: null,
      getPermissions: null,
      params: {
        // tree: false, // true、false
        orderBy: null,
        totalCount: 0
      }
    },
    animateRows: true,
    enableFilter: true,
    floatingFilter: true,
    enableServerSideFilter: true,
    enableServerSideSorting: true,
    suppressContextMenu: true,
    suppressMenuMainPanel: true,
    suppressMenuColumnPanel: true,
    suppressCellSelection: true,
    enableColResize: true,
    headerHeight: 30, // default is 25px
    floatingFiltersHeight: 32, // default is 20px
    rowHeight: 30, // default is 25px
    rowSelection: 'single', // single, multiple
    rowModelType: 'infinite',
    rowData: null,
    pagination: true,
    paginationAutoPageSize: true, // 是否启用自动每页条数
    paginationPageSize: 10, // 不启用自动每页条数时使用该值作为初始每页条数
    paginationFirstPage: 0,
    cacheOverflowSize: 2,
    infiniteInitialRowCount: 1,
    cacheBlockSize: 30, // 启动自动每页条数时，一次向后台请求的条目数（即发给后台的每页条数）
    maxBlocksInCache: 2,
    maxConcurrentDatasourceRequests: 1,
    defaultColDef: {
      unSortIcon: true,
      suppressSorting: true,
      suppressMenu: true,
      suppressFilter: true
    },
    columnDefs: [],
    getRowNodeId (item) {
      return item.f_id
    },
    localeTextFunc (key, defaultValue) {
      let localeTextMap = {
        // 公共
        loadingOoo: '加载中……',
        noRowsToShow: '无记录',

        // 过滤相关
        filterOoo: '过滤…',
        equals: '=',
        notEqual: '!=',
        startsWith: '^(起始)',
        endsWith: '$(结束)',
        contains: '*(包含)',
        notContains: '!(不包含)',
        clearFilter: '清除',
        applyFilter: '确定',

        // 翻页相关
        page: '第',
        to: '-',
        of: '共',
        first: '第一页',
        previous: '上一页',
        next: '下一页',
        last: '最末页',
        more: '未知'
      }

      let localeText = localeTextMap[key]
      if (localeText) {
        return localeText
      } else {
        console && console.warn(key + ': \'' + defaultValue + '\',')
        return defaultValue
      }
    },
    datasource: {
      getRows (gridParams) {
        // console && console.warn('datasource getRows', this, 'gridParams', gridParams)
        let page = this.gridOptions.buildPage(gridParams)
        let filters = this.gridOptions.buildFilter(gridParams)
        this.gridOptions.getRows4Infinite(gridParams, page, filters)
      }
    },
    buildPage (gridParams) {
      let page = {
        pageSize: gridParams.endRow - gridParams.startRow,
        pageNo: gridParams.startRow / (gridParams.endRow - gridParams.startRow)
      }
      if (gridParams.sortModel && gridParams.sortModel.length > 0) {
        page.orderBy = ''
        gridParams.sortModel.forEach(function (order, idx, orders) {
          page.orderBy += page.orderBy + order.colId + ' ' + order.sort + ', '
        })
        page.orderBy = page.orderBy.substr(0, page.orderBy.length - 2)
      }

      return page
    },
    buildFilter (gridParams) {
      let filters = {}
      for (let key in gridParams.filterModel) {
        gridParams.context.params.totalCount = 0

        let where = gridParams.filterModel[key]
        if ((where.type === 'in' || where.type === 'notIn') && !(where.filter && where.filter.length > 0)) {
          continue
        }

        let filterValue = null
        if (where.filterType === 'String' && !(where.type === 'in' || where.type === 'notIn')) {
          filterValue = where.filter ? where.filter.trim() : null
          if (!filterValue) {
            continue
          }
        } else {
          filterValue = where.filter
          if (filterValue === undefined || filterValue === null) {
            continue
          }
        }

        if (where.filterType === 'String') {
          if (where.type === 'like') {
            filters[key + '_like'] = filterValue
          } else if (where.type === 'leftLike') {
            filters[key + '_leftLike'] = filterValue
          } else if (where.type === 'rightLike') {
            filters[key + '_rightLike'] = filterValue
          } else if (where.type === 'in') {
            if (filterValue.length === 1) {
              filters[key] = filterValue[0]
            } else {
              filters[key + '_in'] = '\'' + filterValue.join('\',\'') + '\''
            }
          } else {
            filters[key] = filterValue
          }
        } else if (where.filterType === 'Integer') {
          if (where.type === 'in') {
            if (filterValue.length === 1) {
              filters[key] = filterValue[0]
            } else {
              filters[key + '_in'] = filterValue.join(',')
            }
          } else {
            filters[key] = filterValue
          }
        } else if (where.filterType === 'Timestamp') {
          if (where.type === 'between') {
            filters[key + '_begin'] = filterValue[0]
            filters[key + '_end'] = filterValue[1]
          } else {
            filters[key] = filterValue
          }
        } else {
          filters[key] = where.filter
        }
      }

      return filters
    },
    getRows4Infinite (gridParams, page, filters) {
      if (gridParams.context.url) {
        Vue.http.get(gridParams.context.url, {params: Object.assign({}, gridParams.context.params, page, filters)}).then((response) => {
          if (response.body.success) {
            gridParams.context.params.totalCount = response.body.data.totalCount
            gridParams.successCallback(response.body.data.items, gridParams.context.params.totalCount)
          } else {
            // gridParams.failCallback()
            gridParams.context.params.totalCount = 0
            gridParams.successCallback([], 0)
          }
        })
      } else {
        gridParams.context.params.totalCount = 0
        gridParams.successCallback([], 0)
        Vue.prototype.$alert('请通过gridOptions.context.url配置项设置加载数据的URL地址！', '错误', {
          confirmButtonText: '关闭',
          type: 'error'
        })
      }
    },
    getRows4Normal () {
      // console.log('getRows4Normal', typeof this.api)
      let gridOptions = this
      if (gridOptions.context.url) {
        gridOptions.context.params.totalCount = 0
        Vue.http.get(gridOptions.context.url, {params: Object.assign({}, gridOptions.context.params)}).then((response) => {
          if (response.body.success) {
            gridOptions.context.params.totalCount = response.body.data.totalCount
            gridOptions.api.setRowData(response.body.data.items)
          } else {
            gridOptions.context.params.totalCount = 0
            gridOptions.api.setRowData([])
          }
        })
      }
    }
  },
  buildOptions: function (gridOptions) {
    let options = Vue.lodash.merge({}, this.defaultOptions, gridOptions)
    options.datasource.gridOptions = options
    if (options.context.params.tree) {
      options.rowModelType = 'normal'
      options.pagination = false
      options.enableServerSideFilter = false
    }
    if (options.rowModelType === 'normal') {
      options.datasource = null
      options.rowData = []
    } else {
      options.getRows4Normal = null
      options.getNodeChildDetails = null // 服务端模式下不支持分组和树形结构，只有企业版的ag-grid才支持
    }
    options.onGridReady = (e) => {
      if (options.rowModelType === 'normal') {
        options.getRows4Normal()
      }
      if (gridOptions.onGridReady) {
        gridOptions.onGridReady(e)
      }
    }

    // console.log('buildOptions', options)
    return options
  }
}

const plugin = function (Vue) {
  if (plugin.installed) {
    return
  }

  Vue.prototype.$grid = VueGrid
  Vue.grid = VueGrid
}

if (typeof window !== 'undefined' && window.Vue
) {
  window.Vue.use(plugin)
}

export default plugin
