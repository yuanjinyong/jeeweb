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
        orderBy: null,
        totalCount: 0
      }
    },
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
    pagination: true,
    paginationAutoPageSize: true,
    paginationPageSize: 20,
    paginationFirstPage: 0,
    cacheOverflowSize: 2,
    infiniteInitialRowCount: 1,
    cacheBlockSize: 20,
    maxBlocksInCache: 2,
    maxConcurrentDatasourceRequests: 1,
    getRowNodeId (item) {
      return item.f_id
    },
    localeTextFunc (key, defaultValue) {
      var localeTextMap = {
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

      var localeText = localeTextMap[key]
      if (localeText) {
        return localeText
      } else {
        console && console.warn(key + ': \'' + defaultValue + '\',')
        return defaultValue
      }
    },
    datasource: {
      getRows (params) {
        // console && console.warn('datasource getRows, params', params)
        var page = {
          pageSize: params.endRow - params.startRow,
          pageNo: params.startRow / (params.endRow - params.startRow)
        }
        if (params.sortModel && params.sortModel.length > 0) {
          page.orderBy = ''
          params.sortModel.forEach(function (order, idx, orders) {
            page.orderBy += page.orderBy + order.colId + ' ' + order.sort + ', '
          })
          page.orderBy = page.orderBy.substr(0, page.orderBy.length - 2)
        }

        // console && console.warn('datasource getRows, filterModel', params.filterModel)
        var filters = {}
        for (var key in params.filterModel) {
          params.context.params.totalCount = 0

          var where = params.filterModel[key]
          if ((where.type === 'in' || where.type === 'notIn') && !(where.filter && where.filter.length > 0)) {
            continue
          }

          var filterValue = null
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

        if (params.context.url) {
          Vue.http.get(params.context.url, {params: Object.assign({}, params.context.params, page, filters)}).then(function (response) {
            if (response.body.success) {
              params.context.params.totalCount = response.body.data.totalCount
              params.successCallback(response.body.data.items, response.body.data.totalCount)
            } else {
              params.failCallback()
            }
          })
        } else {
          params.context.params.totalCount = 0
          params.successCallback([], 0)
          Vue.prototype.$alert('请通过gridOptions.context.url配置项设置加载数据的URL地址！', '错误', {
            confirmButtonText: '关闭',
            type: 'error'
          })
        }
      }
    },
    defaultColDef: {
      unSortIcon: true,
      suppressSorting: true,
      suppressMenu: true,
      suppressFilter: true
    },
    columnDefs: []
  },
  buildOptions: function (gridOptions) {
    let options = Vue.lodash.merge({}, this.defaultOptions, gridOptions)
    if (options.rowModelType !== 'infinite') {
      options.datasource = null
    }
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
