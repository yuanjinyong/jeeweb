/**
 * Install plugin.
 */
import $ from 'jquery'

const agGridOptions = {
  debug: false,
  context: {url: null},
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
  // paginationPageSize: 20,
  paginationFirstPage: 0,
  paginationAutoPageSize: true,
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
      console.warn(key + ': \'' + defaultValue + '\',')
      return defaultValue
    }
  },
  datasource: {
    getRows (gridParams) {
      console.debug('this', this, 'gridParams', gridParams)
    }
  },
  columnDefs: []
}

const buildGridOptions = function (gridOptions) {
  return $.extend({}, agGridOptions, gridOptions)
}

const plugin = function (Vue) {
  if (plugin.installed) {
    return
  }

  Vue.prototype.$agGridOptions = agGridOptions
  Vue.agGridOptions = agGridOptions
  Vue.prototype.$buildGridOptions = buildGridOptions
  Vue.buildGridOptions = buildGridOptions
}

if (typeof window !== 'undefined' && window.Vue) {
  window.Vue.use(plugin)
}

export default plugin
