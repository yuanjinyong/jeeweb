<template>
  <div :style="contentStyle">
    <ag-grid ref="grid" id="processInstanceGrid" class="ag-fresh jw-grid" :grid-options="gridOptions"></ag-grid>

    <process-instance-detail ref="detail" :detail-options="detailOptions"></process-instance-detail>
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
    ViewRendererFramework
  } from 'components/ag-grid'
  import ProcessInstanceDetail from './detail'
  //  import {ProcessInstanceDetail} from 'views'

  export default {
    name: 'processInstanceView',
    mixins: [ViewlMixin],
    components: {
      ProcessInstanceDetail
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
            url: 'api/activiti/process/instances',
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
          start: true,
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
        headerName: '编码',
        field: 'f_key',
        pinned: 'left',
        suppressSorting: false,
        suppressFilter: false,
        filterFramework: LikeFilterFramework,
        floatingFilterComponentFramework: LikeFloatingFilterComponentFramework,
        cellRendererFramework: ViewRendererFramework,
        cellRendererParams: {
          operation: {
            title: '编辑流程配置',
            onClick (params, entity) {
              let url = Vue.cfg.apiUrl + 'activiti/modeler.html?view=true&modelId=' + entity.f_id
              window.open(url)
            }
          }
        },
        width: 150
      }, {
        headerName: '名称',
        field: 'f_name',
        pinned: 'left',
        suppressSorting: false,
        suppressFilter: false,
        filterFramework: LikeFilterFramework,
        floatingFilterComponentFramework: LikeFloatingFilterComponentFramework,
        cellRendererFramework: ViewRendererFramework,
        width: 160
      }, {
        headerName: '版本',
        field: 'f_version',
        width: 40
      }, {
        headerName: '部署批次',
        field: 'f_deployment_id',
        cellStyle: {'text-align': 'right'},
        width: 80
      }, {
        headerName: '模型名',
        field: 'f_resource_name',
        tooltipField: 'f_resource_name',
        width: 200
      }, {
        headerName: '模型名',
        field: 'f_diagram_resource_name',
        tooltipField: 'f_diagram_resource_name',
        width: 220
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
            id: 'start',
            title: '启动流程',
            type: 'info',
            icon: 'fa fa-play',
            permission: 'start',
            onClick (params, entity) {
              params.context.featureComponent.onStart(entity)
            }
          }]
        },
        width: 100
      }]
    },
    methods: {
      onStart (entity) {
        let loading = this.$loading({text: '流程启动中……', target: '#processDefinitionGrid'})
        this.$http.post(this.gridOptions.context.url + '/' + entity.f_id + '/start').then(() => {
          loading.close()
        }).catch(() => {
          loading.close()
        })
      }
    }
  }
</script>
