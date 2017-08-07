<template>
  <div :style="contentStyle">
    <ag-grid ref="grid" class="ag-fresh jw-grid" :grid-options="gridOptions"></ag-grid>

    <process-model-detail ref="detail" :detail-options="detailOptions"></process-model-detail>
    <process-model-editor ref="editor" :detail-options="editorOptions"></process-model-editor>
  </div>
</template>


<script>
  import Vue from 'vue'
  import {ViewlMixin} from 'mixins'
  import {
    AddHeaderComponenetFramework,
    IndexRendererFramework,
    LikeFilterFramework,
    LikeFloatingFilterComponentFramework,
    OperationRendererFramework,
    TimestampRendererFramework,
    ViewRendererFramework
  } from 'components/ag-grid'
  import ProcessModelDetail from './detail'
  import ProcessModelEditor from './editor'
  //  import {ProcessModelDetail, ProcessModelEditor} from 'views'

  export default {
    name: 'processModelView',
    mixins: [ViewlMixin],
    components: {
      ProcessModelDetail,
      ProcessModelEditor
    },
    data () {
      return {
        editorOptions: {
          size: 'full',
          context: {
            featureComponent: this,
            getGridComponent (options) {
              return options.context.featureComponent.$refs['grid']
            }
          }
        },
        detailOptions: {
          context: {
            featureComponent: this,
            getGridComponent (options) {
              return options.context.featureComponent.$refs['grid']
            }
          }
        },
        gridOptions: this.$grid.buildOptions({
          context: {
            name: '流程模型',
            url: 'api/activiti/process/models',
            featureComponent: this,
            getPermissions (params, operation) {
              return params.context.featureComponent.permission
            },
            getDetailComponent (params, operation) {
              return params.context.featureComponent.$refs['detail']
            }
          }
        })
      }
    },
    computed: {
      permission () {
        return {
          editor: this.hasPermission('GZLGL-LCMXGL-BJ'),
          add: this.hasPermission('GZLGL-LCMXGL-ZJ'),
          edit: this.hasPermission('GZLGL-LCMXGL-XG'),
          remove: this.hasPermission('GZLGL-LCMXGL-SC')
        }
      }
    },
    created () {
      this.gridOptions.columnDefs = [{
        headerName: '',
        pinned: 'left',
        hide: this.mode !== 'selector',
        checkboxSelection: this.mode === 'selector',
        cellStyle: {'text-align': 'center'},
        width: 24
      }, {
        headerName: '#',
        pinned: 'left',
        headerComponentFramework: this.mode !== 'selector' ? AddHeaderComponenetFramework : null,
        cellStyle: {'text-align': 'right'},
        cellRendererFramework: IndexRendererFramework,
        width: 38
      }, {
        headerName: 'ID',
        field: 'f_id',
        pinned: 'left',
        suppressSorting: false,
        suppressFilter: false,
        filterFramework: LikeFilterFramework,
        floatingFilterComponentFramework: LikeFloatingFilterComponentFramework,
        cellRendererFramework: ViewRendererFramework,
        cellRendererParams: {
          operation: {
            title: '查看流程',
            onClick (params, entity) {
              let url = Vue.cfg.apiUrl + 'activiti/modeler.html?view=true&modelId=' + entity.f_id
              window.open(url)
            }
          }
        },
        width: 80
      }, {
        headerName: '编码',
        field: 'model.key',
        pinned: 'left',
        suppressSorting: false,
        suppressFilter: false,
        filterFramework: LikeFilterFramework,
        floatingFilterComponentFramework: LikeFloatingFilterComponentFramework,
        cellRendererFramework: ViewRendererFramework,
        width: 150
      }, {
        headerName: '名称',
        field: 'model.name',
        pinned: 'left',
        suppressSorting: false,
        suppressFilter: false,
        filterFramework: LikeFilterFramework,
        floatingFilterComponentFramework: LikeFloatingFilterComponentFramework,
        width: 160
      }, {
        headerName: '创建时间',
        field: 'model.createTime',
        suppressSorting: false,
        cellStyle: {'text-align': 'center'},
        cellRendererFramework: TimestampRendererFramework,
        width: 140
      }, {
        headerName: '修改时间',
        field: 'model.lastUpdateTime',
        suppressSorting: false,
        cellStyle: {'text-align': 'center'},
        cellRendererFramework: TimestampRendererFramework,
        width: 140
      }, {
        headerName: '描述',
        field: 'description',
        tooltipField: 'description',
        width: 200
      }, {
        headerName: '操作',
        field: '',
        pinned: 'right',
        hide: this.mode === 'selector',
        cellStyle: {'text-align': 'center'},
        cellRendererFramework: OperationRendererFramework,
        cellRendererParams: {
          operations: [{
            id: 'edit',
            permission: 'edit'
          }, {
            id: 'remove',
            permission: 'remove',
            isDisabled (params, entity) {
              return entity.f_is_preset === 1
            }
          }]
        },
        width: 80
      }]
    }
  }
</script>
