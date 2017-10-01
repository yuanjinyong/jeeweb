<template>
  <div :style="contentStyle">
    <ag-grid ref="grid" id="processModelGrid" class="ag-fresh jw-grid" :grid-options="gridOptions"></ag-grid>

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
          export: true,
          deploy: true,
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
        headerName: '编码',
        field: 'model.key',
        pinned: 'left',
        cellRendererFramework: ViewRendererFramework,
        width: 150
      }, {
        headerName: '名称',
        field: 'model.name',
        pinned: 'left',
        width: 160
      }, {
        headerName: '创建时间',
        field: 'model.createTime',
        cellStyle: {'text-align': 'center'},
        cellRendererFramework: TimestampRendererFramework,
        width: 140
      }, {
        headerName: '修改时间',
        field: 'model.lastUpdateTime',
        cellStyle: {'text-align': 'center'},
        cellRendererFramework: TimestampRendererFramework,
        width: 140
      }, {
        headerName: '部署批次',
        field: 'model.deploymentId',
        width: 80
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
              return entity.model && typeof entity.model.deploymentId === 'string'
            }
          }, {
            id: 'editor',
            title: '编辑流程节点',
            type: 'info',
            icon: 'fa fa-sliders',
            permission: 'editor',
            isDisabled (params, entity) {
              return entity.model && typeof entity.model.deploymentId === 'string'
            },
            onClick (params, entity) {
              params.context.featureComponent.onEditor(entity)
            }
          }, {
            id: 'deploy',
            title: '发布流程模型',
            type: 'warning',
            icon: 'fa fa-cloud-upload',
            permission: 'deploy',
            isDisabled (params, entity) {
              return entity.model && typeof entity.model.deploymentId === 'string'
            },
            onClick (params, entity) {
              params.context.featureComponent.onDeploy(entity)
            }
          }, {
            id: 'export',
            title: '下载流程模型',
            icon: 'fa fa-file-excel-o',
            permission: 'export',
            onClick (params, entity) {
              params.context.featureComponent.onExport(entity)
            }
          }]
        },
        width: 120
      }]
    },
    methods: {
      onEditor (entity) {
        window.open(Vue.cfg.apiUrl + 'activiti/modeler.html?view=false&modelId=' + entity.f_id)
      },
      onDeploy (entity) {
        let loading = this.$loading({text: '流程发布中……', target: '#processModelGrid'})
        this.$http.post(this.gridOptions.context.url + '/' + entity.f_id + '/deploy').then(() => {
          loading.close()
          this.gridOptions.api.setDatasource(this.gridOptions.datasource)
        }).catch(() => {
          loading.close()
        })
      },
      onExport (entity) {
        window.open(Vue.cfg.apiUrl + this.gridOptions.context.url + '/' + entity.f_id + '/export')
      }
    }
  }
</script>
