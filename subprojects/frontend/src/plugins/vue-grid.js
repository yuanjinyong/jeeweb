/**
 * Install plugin.
 */
import $ from 'jquery'
import Vue from 'vue'

const agGridOptions = {
  debug: false,
  context: {
    url: null,
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
  headerHeight: 35,
  rowHeight: 35,
  rowSelection: 'multiple',
  rowModelType: 'infinite',
  pagination: true,
  paginationAutoPageSize: true,
  // paginationPageSize: 20,
  paginationFirstPage: 0,
  paginationOverflowSize: 2,
  infiniteInitialRowCount: 1,
  // infiniteBlockSize: 20,
  maxPagesInCache: 2,
  maxConcurrentDatasourceRequests: 1,
  getRowNodeId: function (item) {
    return item.f_id
  },
  localeTextFunc: function (key, defaultValue) {
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
    getRows (gridParams) {
      // console && console.debug('datasource getRows, gridParams', gridParams)
      var page = {
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

      var filters = {}
      for (var key in gridParams.filterModel) {
        var where = gridParams.filterModel[key]
        if (!where.filter) {
          continue
        }

        gridParams.context.params.totalCount = 0
        if (where.filterType === 'text' && where.type === 'equals') {
          filters[key] = where.filter
        } else if (where.filterType === 'text' && where.type === 'contains') {
          filters[key + '_like'] = where.filter
        } else if (where.filterType === 'text' && where.type === 'startsWith') {
          filters[key + '_rightLike'] = where.filter
        } else if (where.filterType === 'text' && where.type === 'endsWith') {
          filters[key + '_leftLike'] = where.filter
        } else {
          filters[key] = where.filter ? where.filter : null
        }
      }

      Vue.http.get(gridParams.context.url, {params: Object.assign({}, gridParams.context.params, page, filters)}).then(function (response) {
        if (response.body.success) {
          gridParams.context.params.totalCount = response.body.data.totalCount
          gridParams.successCallback(response.body.data.items, response.body.data.totalCount)
        } else {
          gridParams.failCallback()
        }
      })
    }
  },
  columnDefs: []
}

const buildGridOptions = function (gridOptions) {
  return $.extend({}, agGridOptions, gridOptions)
}

var VueGrid = {
  defaultOptions: {
    debug: false,
    context: {
      featureComponent: {},
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
    headerHeight: 35,
    rowHeight: 35,
    rowSelection: 'multiple',
    rowModelType: 'infinite',
    pagination: true,
    paginationAutoPageSize: true,
    // paginationPageSize: 20,
    paginationFirstPage: 0,
    paginationOverflowSize: 2,
    infiniteInitialRowCount: 1,
    // infiniteBlockSize: 20,
    maxPagesInCache: 2,
    maxConcurrentDatasourceRequests: 1,
    getRowNodeId: function (item) {
      return item.f_id
    },
    localeTextFunc: function (key, defaultValue) {
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
      getRows (gridParams) {
        // console && console.debug('datasource getRows, gridParams', gridParams)
        var page = {
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

        var filters = {}
        for (var key in gridParams.filterModel) {
          var where = gridParams.filterModel[key]
          if (!where.filter) {
            continue
          }

          gridParams.context.params.totalCount = 0
          if (where.filterType === 'text' && where.type === 'equals') {
            filters[key] = where.filter
          } else if (where.filterType === 'text' && where.type === 'contains') {
            filters[key + '_like'] = where.filter
          } else if (where.filterType === 'text' && where.type === 'startsWith') {
            filters[key + '_rightLike'] = where.filter
          } else if (where.filterType === 'text' && where.type === 'endsWith') {
            filters[key + '_leftLike'] = where.filter
          } else {
            filters[key] = where.filter ? where.filter : null
          }
        }

        Vue.http.get(gridParams.context.featureComponent.featureOptions.url, {params: Object.assign({}, gridParams.context.params, page, filters)}).then(function (response) {
          if (response.body.success) {
            gridParams.context.params.totalCount = response.body.data.totalCount
            gridParams.successCallback(response.body.data.items, response.body.data.totalCount)
          } else {
            gridParams.failCallback()
          }
        })
      }
    },
    columnDefs: []
  },
  buildOptions: function (gridOptions) {
    return $.extend({}, this.defaultOptions, gridOptions)
  }
}

const plugin = function (Vue) {
  if (plugin.installed) {
    return
  }

  Vue.prototype.$agGridOptions = agGridOptions
  Vue.agGridOptions = agGridOptions
  Vue.prototype.$buildGridOptions = buildGridOptions
  Vue.buildGridOptions = buildGridOptions
  Vue.prototype.$grid = VueGrid
  Vue.grid = VueGrid
}

if (typeof window !== 'undefined' && window.Vue) {
  window.Vue.use(plugin)
}

export default plugin
