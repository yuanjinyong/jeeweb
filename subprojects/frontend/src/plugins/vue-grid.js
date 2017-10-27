/**
 * Install plugin.
 */
import Vue from 'vue'
import {
  AddHeaderComponenetFramework,
  DictEditorFramework,
  DictFilterFramework,
  DictFloatingFilterComponentFramework,
  DictRendererFramework,
  LikeFilterFramework,
  LikeFloatingFilterComponentFramework,
  IndexRendererFramework,
  OperationRendererFramework,
  TextEditorFramework,
  TimestampFilterFramework,
  TimestampFloatingFilterComponentFramework,
  TimestampRendererFramework,
  ViewRendererFramework
} from 'components/ag-grid'

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
    animateRows: true,
    enableFilter: true,
    floatingFilter: true,
    enableServerSideFilter: true,
    enableServerSideSorting: true,
    suppressContextMenu: true,
    suppressCellSelection: true,
    enableColResize: true,
    headerHeight: 30, // default is 25px
    floatingFiltersHeight: 38, // default is 20px
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
    cacheBlockSize: 20, // 启动自动每页条数时，一次向后台请求的条目数（即发给后台的每页条数）
    maxBlocksInCache: 2,
    maxConcurrentDatasourceRequests: 1,
    columnTypes: {
      'Null': {},
      'AddHeader': {
        headerComponentFramework: AddHeaderComponenetFramework
      },
      'Checkbox': {
        headerName: '',
        pinned: 'left',
        suppressResize: true,
        hide: true,
        checkboxSelection: true,
        cellStyle: {'text-align': 'center'},
        width: 24
      },
      'DictEditor': {
        cellStyle: {'text-align': 'center'},
        cellRendererFramework: DictEditorFramework
      },
      'DictFilter': {
        suppressFilter: false,
        filterFramework: DictFilterFramework,
        floatingFilterComponentFramework: DictFloatingFilterComponentFramework
      },
      'DictRender': {
        cellStyle: {'text-align': 'center'},
        cellRendererFramework: DictRendererFramework
      },
      'IndexRender': {
        headerName: '#',
        pinned: 'left',
        cellStyle: {'text-align': 'right'},
        cellRendererFramework: IndexRendererFramework,
        width: 38
      },
      'LikeFilter': {
        suppressFilter: false,
        filterFramework: LikeFilterFramework,
        floatingFilterComponentFramework: LikeFloatingFilterComponentFramework
      },
      'OperationRender': {
        headerName: '操作',
        pinned: 'right',
        suppressResize: true,
        cellStyle: {'text-align': 'center'},
        cellRendererFramework: OperationRendererFramework
      },
      'TextEditor': {
        cellRendererFramework: TextEditorFramework
      },
      'TimestampFilter': {
        suppressFilter: false,
        filterFramework: TimestampFilterFramework,
        floatingFilterComponentFramework: TimestampFloatingFilterComponentFramework,
        width: 200 // 有filter的为200，没有的为140
      },
      'TimestampRender': {
        cellStyle: {'text-align': 'center'},
        cellRendererFramework: TimestampRendererFramework,
        width: 140 // 有filter的为200，没有的为140
      },
      'ViewRender': {
        cellRendererFramework: ViewRendererFramework
      }
    },
    defaultColDef: {
      unSortIcon: true,
      suppressSizeToFit: true,
      suppressSorting: true,
      suppressMenu: true,
      suppressFilter: true,
      minWidth: 33
    },
    columnDefs: [],
    getRowNodeId (item) {
      return item.f_id
    },
    icons: {
      menu: '<i class="fa fa-bars"/>',
      filter: '<i class="fa fa-filter"/>',
      columns: '<i class="fa fa-handshake-o"/>',
      sortUnSort: '<i class="fa fa-arrows-v"/>',
      sortAscending: '<i class="fa fa-long-arrow-down"/>',
      sortDescending: '<i class="fa fa-long-arrow-up"/>',
      groupExpanded: '<i class="fa fa-minus-square-o"/>',
      groupContracted: '<i class="fa fa-plus-square-o"/>',
      columnMovePin: '<i class="fa fa-hand-grab-o"/>',
      columnMoveAdd: '<i class="fa fa-plus-square-o"/>',
      columnMoveHide: '<i class="fa fa-remove"/>',
      columnMoveMove: '<i class="fa fa-arrows"/>',
      columnMoveLeft: '<i class="fa fa-arrow-left"/>',
      columnMoveRight: '<i class="fa fa-arrow-right"/>',
      columnMoveGroup: '<i class="fa fa-group"/>',
      rowGroupPanel: '<i class="fa fa-bank"/>',
      pivotPanel: '<i class="fa fa-magic"/>',
      valuePanel: '<i class="fa fa-magnet"/>',
      menuPin: 'P', // just showing letters, no graphic
      menuValue: 'V',
      menuAddRowGroup: 'A',
      menuRemoveRowGroup: 'R',
      clipboardCopy: '>>',
      clipboardPaste: '>>',
      checkboxChecked: '<i class="fa fa-check-square-o"/>',
      checkboxUnchecked: '<i class="fa fa-square-o"/>',
      checkboxIndeterminate: '<i class="fa fa-minus-square-o"/>'
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
        pageSize: gridParams.endRow ? (gridParams.endRow - gridParams.startRow) : null,
        pageNo: gridParams.endRow ? (gridParams.startRow / (gridParams.endRow - gridParams.startRow)) : null
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
          } else if (where.type === 'begin') {
            filters[key + '_begin'] = filterValue
          } else if (where.type === 'end') {
            filters[key + '_end'] = filterValue
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
      let gridOptions = this
      if (gridParams.context.url) {
        gridOptions.api.showLoadingOverlay()
        Vue.http.get(gridParams.context.url, {params: Object.assign({}, gridParams.context.params, page, filters)}).then((response) => {
          gridOptions.api.hideOverlay()
          if (response.body.success) {
            gridParams.context.params.totalCount = response.body.data.totalCount
            gridParams.successCallback(response.body.data.items, gridParams.context.params.totalCount)
          } else {
            // gridParams.failCallback()
            gridParams.context.params.totalCount = 0
            gridParams.successCallback([], 0)
          }
          if (gridParams.context.params.totalCount === 0) {
            gridOptions.api.showNoRowsOverlay()
          }
        }, (response) => {
          // gridParams.failCallback()
          gridParams.context.params.totalCount = 0
          gridParams.successCallback([], 0)
          gridOptions.api.showNoRowsOverlay()
        })
      } else {
        gridParams.context.params.totalCount = 0
        gridParams.successCallback([], 0)
        gridOptions.api.showNoRowsOverlay()
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
        gridOptions.api.showLoadingOverlay()
        Vue.http.get(gridOptions.context.url, {params: Object.assign({}, gridOptions.context.params)}).then((response) => {
          gridOptions.api.hideOverlay()
          if (response.body.success) {
            gridOptions.context.params.totalCount = response.body.data.totalCount
            gridOptions.api.setRowData(response.body.data.items)
          } else {
            gridOptions.context.params.totalCount = 0
            gridOptions.api.setRowData([])
          }
        }, (response) => {
          gridOptions.api.hideOverlay()
          gridOptions.context.params.totalCount = 0
          gridOptions.api.setRowData([])
        })
      }
    },
    setData (data) {
      let gridOptions = this
      if (gridOptions.rowModelType === 'inMemory') {
        if (gridOptions.api) {
          gridOptions.api.setRowData(data || [])
        } else {
          console.warn('ag-grid还未初始化完成')
          gridOptions.rowData = data || []
        }
      }
    },
    reload (params) {
      let gridOptions = this
      Vue.lodash.merge(gridOptions.context.params, params, {totalCount: 0})
      if (gridOptions.rowModelType === 'inMemory') {
        gridOptions.getRows4Normal()
      } else {
        if (gridOptions.api) {
          gridOptions.api.setDatasource(gridOptions.datasource)
        } else {
          console.warn('ag-grid还未初始化完成')
        }
      }
    }
  },
  buildOptions (gridOptions) {
    let options = Vue.lodash.merge({}, this.defaultOptions, gridOptions)
    options.datasource.gridOptions = options
    if (options.context.params.tree) {
      options.rowModelType = 'inMemory'
      options.pagination = false
      options.enableServerSideFilter = false
    }
    if (options.rowModelType === 'inMemory') {
      options.datasource = null
      options.rowData = []
    } else {
      options.getRows4Normal = null
      options.getNodeChildDetails = null // 服务端模式下不支持分组和树形结构，只有企业版的ag-grid才支持
    }
    options.onGridReady = (e) => {
      if (options.rowModelType === 'inMemory') {
        options.getRows4Normal()
      }
      if (gridOptions.onGridReady) {
        gridOptions.onGridReady(e)
      }
      e.api.sizeColumnsToFit()
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
