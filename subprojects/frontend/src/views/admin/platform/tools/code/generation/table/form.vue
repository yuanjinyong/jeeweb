<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>

<template>
  <div class="jw-form">
    <div class="jw-form-body" :style="formBodyStyle">
      <el-form ref="form" :model="entity" :rules="rules" :inline="true" :label-width="labelWidth">
        <fieldset :disabled="formOptions.operation === 'view'">
          <el-form-item label="数据库名" prop="f_db_name">
            <el-select v-model="entity.f_db_name">
              <el-option v-for="item in schematas"
                :key="item.SCHEMA_NAME"
                :value="item.SCHEMA_NAME"
                :label="item.SCHEMA_NAME">
                {{item.SCHEMA_NAME}}
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="数据库表名" prop="f_table_name">
            <el-autocomplete v-model="entity.f_table_name" icon="search" class="jw-field-col-1"
              :fetch-suggestions="loadTables" @select="loadFields">
            </el-autocomplete>
          </el-form-item>
          <el-form-item label="排序" prop="f_order">
            <el-input-number v-model="entity.f_order" :step="1"></el-input-number>
          </el-form-item>
          <el-form-item label="主表" prop="f_is_main">
            <el-radio-group v-model="entity.f_is_main">
              <el-radio :label="1">是</el-radio>
              <el-radio :label="2">否</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="父类" prop="f_entity_base_class">
            <el-select v-model="entity.f_entity_base_class">
              <el-option v-for="baseClass in baseClasses"
                :key="baseClass.fullName"
                :value="baseClass.fullName"
                :label="baseClass.name">
                {{baseClass.fullName}}
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="接口" prop="f_entity_interfaces">
            <el-select v-model="f_entity_interfaces" class="jw-field-col-3" multiple>
              <el-option v-for="interface in interfaces"
                :key="interface.fullName"
                :value="interface.fullName"
                :label="interface.name">
                {{interface.fullName}}
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="Entity" prop="f_entity_class">
            <el-input v-model="entity.f_entity_class" class="jw-field-col-2"
              placeholder="Map类型请填写“com.jeeweb.framework.core.model.RowMap”。"></el-input>
          </el-form-item>
          <el-form-item label="Mapper" prop="f_mapper_class">
            <el-input v-model="entity.f_mapper_class" class="jw-field-col-2"></el-input>
          </el-form-item>
          <el-form-item label="Service" prop="f_service_class">
            <el-input v-model="entity.f_service_class" class="jw-field-col-2"></el-input>
          </el-form-item>
          <el-form-item label="Api" prop="f_rest_class">
            <el-input v-model="entity.f_rest_class" class="jw-field-col-2"></el-input>
          </el-form-item>

          <ag-grid-vue style="width: 100%; height: 450px;" class="ag-fresh jw-grid" :grid-options="gridOptions">
          </ag-grid-vue>
        </fieldset>
      </el-form>
    </div>

    <div class="jw-form-footer" style="text-align: right;">
      <el-button @click="onCancelForm('form')">取 消</el-button>
      <el-button type="primary" @click="onSubmitForm('form')" :disabled="formOptions.operation === 'view'">确 定
      </el-button>
    </div>
  </div>
</template>


<script type="text/ecmascript-6">
  import { AgGridVue } from 'ag-grid-vue'
  import DictRendererFramework from 'components/ag-grid/DictRendererFramework'

  export default {
    name: 'generateRuleTableForm',
    components: {
      'ag-grid-vue': AgGridVue
    },
    props: {
      formOptions: {
        type: Object,
        default: function () {
          return {
            operation: 'view',
            title: '查看详情',
            maxHeight: 400,
            labelWidth: 100,
            params: {},
            context: {
              featureComponent: {}
            }
          }
        }
      },
      generateRule: {
        type: Object,
        default: function () {
          return {
            tableList: []
          }
        }
      }
    },
    data () {
      return {
        schematas: [],
        baseClasses: [{
          name: 'BaseEntity',
          fullName: 'com.jeeweb.framework.business.entity.BaseEntity'
        }, {
          name: 'TreeNodeEntity',
          fullName: 'com.jeeweb.framework.business.entity.TreeNodeEntity'
        }],
        interfaces: [{
          name: 'ICreator',
          fullName: 'com.jeeweb.framework.business.model.ICreator'
        }, {
          name: 'IPreset',
          fullName: 'com.jeeweb.framework.business.model.IPreset'
        }],
        gridOptions: this.$grid.buildOptions({
          context: {
            featureComponent: this
          },
          rowModelType: 'normal',
          rowData: [],
          pagination: false,
          enableFilter: false,
          floatingFilter: false,
          suppressContextMenu: true,
          suppressMenuMainPanel: true,
          suppressMenuColumnPanel: true
        }),
        entity: {fieldList: []},
        rules: {
          f_name: [
            {required: true, message: '请输入角色名称', trigger: 'blur'},
            {max: 50, message: '长度在50个字符以内', trigger: 'blur'}
          ]
        }
      }
    },
    computed: {
      f_entity_interfaces: {
        get: function () {
          if (this.entity.f_entity_interface) {
            return this.entity.f_entity_interface.split(',')
          }
          return []
        },
        set: function (v) {
          if (v && v.length > 0) {
            this.entity.f_entity_interface = v.join(',')
          } else {
            this.entity.f_entity_interface = null
          }
        }
      },
      formBodyStyle () {
        return {
          'max-height': (this.formOptions.maxHeight ? this.formOptions.maxHeight : 500) + 'px',
          'overflow-y': 'auto'
        }
      },
      labelWidth () {
        return (this.formOptions.labelWidth ? this.formOptions.labelWidth : 100) + 'px'
      },
      featureOptions () {
        return this.formOptions.context.featureComponent.featureOptions
      }
    },
    created () {
      this.gridOptions.columnDefs = [
        {
          headerName: '#',
          field: 'f_order',
          pinned: 'left',
          cellStyle: {'text-align': 'right'},
          suppressSorting: true,
          suppressMenu: true,
          suppressFilter: true,
          width: 30
        },
        {
          headerName: '字段名',
          field: 'f_column_name',
          pinned: 'left',
          suppressSorting: true,
          suppressMenu: true,
          suppressFilter: true,
          width: 120
        },
        {
          headerName: '描述',
          field: 'f_column_comment',
          suppressSorting: true,
          suppressMenu: true,
          suppressFilter: true,
          width: 180
        },
        {
          headerName: 'Java数据类型',
          field: 'f_full_java_type',
          suppressSorting: true,
          suppressMenu: true,
          suppressFilter: true,
          width: 180
        },
        {
          headerName: '父类字段',
          field: 'f_is_super_class_field',
          cellStyle: {'text-align': 'center'},
          cellRendererFramework: DictRendererFramework,
          cellRendererParams: {dict: 'YesNo'},
          suppressSorting: true,
          suppressMenu: true,
          suppressFilter: true,
          width: 60
        },
        {
          headerName: '主键',
          field: 'f_is_primary',
          cellStyle: {'text-align': 'center'},
          cellRendererFramework: DictRendererFramework,
          cellRendererParams: {dict: 'YesNo'},
          suppressSorting: true,
          suppressMenu: true,
          suppressFilter: true,
          width: 60
        },
        {
          headerName: '插入',
          children: [
            {
              headerName: '插入',
              field: 'f_is_insert',
              cellStyle: {'text-align': 'center'},
              cellRendererFramework: DictRendererFramework,
              cellRendererParams: {dict: 'YesNo'},
              suppressSorting: true,
              suppressMenu: true,
              suppressFilter: true,
              width: 60
            },
            {
              headerName: '更新',
              field: 'f_is_update',
              cellStyle: {'text-align': 'center'},
              cellRendererFramework: DictRendererFramework,
              cellRendererParams: {dict: 'YesNo'},
              suppressSorting: true,
              suppressMenu: true,
              suppressFilter: true,
              width: 60
            },
            {
              headerName: '查询',
              field: 'f_is_select',
              cellStyle: {'text-align': 'center'},
              cellRendererFramework: DictRendererFramework,
              cellRendererParams: {dict: 'YesNo'},
              suppressSorting: true,
              suppressMenu: true,
              suppressFilter: true,
              width: 60
            },
            {
              headerName: '等于',
              field: 'f_is_equal',
              cellStyle: {'text-align': 'center'},
              cellRendererFramework: DictRendererFramework,
              cellRendererParams: {dict: 'YesNo'},
              suppressSorting: true,
              suppressMenu: true,
              suppressFilter: true,
              width: 60
            },
            {
              headerName: '模糊',
              field: 'f_is_like',
              cellStyle: {'text-align': 'center'},
              cellRendererFramework: DictRendererFramework,
              cellRendererParams: {dict: 'YesNo'},
              suppressSorting: true,
              suppressMenu: true,
              suppressFilter: true,
              width: 60
            },
            {
              headerName: '左模',
              field: 'f_is_left_like',
              cellStyle: {'text-align': 'center'},
              cellRendererFramework: DictRendererFramework,
              cellRendererParams: {dict: 'YesNo'},
              suppressSorting: true,
              suppressMenu: true,
              suppressFilter: true,
              width: 60
            },
            {
              headerName: '右模',
              field: 'f_is_right_like',
              cellStyle: {'text-align': 'center'},
              cellRendererFramework: DictRendererFramework,
              cellRendererParams: {dict: 'YesNo'},
              suppressSorting: true,
              suppressMenu: true,
              suppressFilter: true,
              width: 60
            },
            {
              headerName: 'In',
              field: 'f_is_in',
              cellStyle: {'text-align': 'center'},
              cellRendererFramework: DictRendererFramework,
              cellRendererParams: {dict: 'YesNo'},
              suppressSorting: true,
              suppressMenu: true,
              suppressFilter: true,
              width: 60
            },
            {
              headerName: 'Not In',
              field: 'f_is_not_in',
              cellStyle: {'text-align': 'center'},
              cellRendererFramework: DictRendererFramework,
              cellRendererParams: {dict: 'YesNo'},
              suppressSorting: true,
              suppressMenu: true,
              suppressFilter: true,
              width: 60
            },
            {
              headerName: 'Between',
              field: 'f_is_between',
              cellStyle: {'text-align': 'center'},
              cellRendererFramework: DictRendererFramework,
              cellRendererParams: {dict: 'YesNo'},
              suppressSorting: true,
              suppressMenu: true,
              suppressFilter: true,
              width: 60
            }]
        },
        {
          headerName: '搜索',
          field: 'f_is_search',
          cellStyle: {'text-align': 'center'},
          cellRendererFramework: DictRendererFramework,
          cellRendererParams: {dict: 'YesNo'},
          suppressSorting: true,
          suppressMenu: true,
          suppressFilter: true,
          width: 60
        },
        {
          headerName: '表格',
          field: 'f_is_grid',
          cellStyle: {'text-align': 'center'},
          cellRendererFramework: DictRendererFramework,
          cellRendererParams: {dict: 'YesNo'},
          suppressSorting: true,
          suppressMenu: true,
          suppressFilter: true,
          width: 60
        },
        {
          headerName: '表单',
          field: 'f_is_form',
          cellStyle: {'text-align': 'center'},
          cellRendererFramework: DictRendererFramework,
          cellRendererParams: {dict: 'YesNo'},
          suppressSorting: true,
          suppressMenu: true,
          suppressFilter: true,
          width: 60
        }
      ]
    },
    mounted () {
      window.devMode && console.info('mounted', this.$options.name, this._uid)
      this._init()
    },
    activated () {
      window.devMode && console.info('activated', this.$options.name, this._uid)
    },
    methods: {
      loadTables (query, callback) {
        var vm = this
        vm.loadTablesTimer && clearTimeout(vm.loadTablesTimer)
        vm.loadTablesTimer = setTimeout(() => {
          vm._loadTables(query, callback)
        }, 500)
      },
      _loadTables (tableName, callback) {
        var vm = this
        vm.$http.get('api/platform/schema/information/tables', {
          params: {
            TABLE_SCHEMA: vm.entity.f_db_name,
            TABLE_NAME_like: tableName,
            orderBy: 'TABLE_NAME'
          }
        }).then(function (response) {
          if (response.body.success) {
            var tables = []
            response.body.data.items.forEach((table) => {
              tables.push({value: table.TABLE_NAME})
            })
            callback && callback(tables)
          } else {
            callback && callback([])
          }
        })
      },
      loadFields (item) {
        var vm = this
        vm.$http.get('api/platform/schema/information/columns', {
          params: {
            TABLE_SCHEMA: vm.entity.f_db_name,
            TABLE_NAME: vm.entity.f_table_name,
            orderBy: 'ORDINAL_POSITION'
          }
        }).then(function (response) {
          if (response.body.success) {
            vm.entity.fieldList = []
            response.body.data.items.forEach((column) => {
              vm.entity.fieldList.push(vm._buildFieldEntity(column))
            })
          } else {
            vm.entity.fieldList = []
          }
          vm.gridOptions.api.setRowData(vm.entity.fieldList)
        })
      },
      _buildFieldEntity (column) {
        return {
          f_order: column.ORDINAL_POSITION,
          f_column_name: column.COLUMN_NAME,
          f_column_comment: column.COLUMN_COMMENT,
          f_full_java_type: 'java.lang.String',
          f_is_primary: 2,
          f_is_super_class_field: 2,
          f_is_insert: 2,
          f_is_update: 2,
          f_is_select: 2,
          f_is_equal: 2,
          f_is_like: 2,
          f_is_left_like: 2,
          f_is_right_like: 2,
          f_is_in: 2,
          f_is_not_in: 2,
          f_is_between: 2,
          f_is_search: 2,
          f_is_grid: 2,
          f_is_form: 2
        }
      },
      _init () {
        this._loadSchematas()
        this.query()
      },
      _loadSchematas () {
        var vm = this
        vm.$http.get('api/platform/schema/information/schematas', {params: {orderBy: 'SCHEMA_NAME'}}).then(function (response) {
          vm.schematas = response.body.success ? response.body.data.items : []
        })
      },
      query (params) {
        var vm = this
        if (vm.formOptions.operation === 'add') {
          vm.entity = vm._createEntity()
        } else {
          vm.entity = vm.formOptions.params
        }
        vm.gridOptions.api.setRowData(vm.entity.fieldList)
      },
      onCancelForm (formName) {
        this._closeForm()
        this.$emit('cancel')
      },
      onSubmitForm (formName) {
        var vm = this
        vm.$refs[formName].validate(function (valid) {
          if (!valid) {
            return false
          }

          vm._closeForm()
          vm.$emit('submit', {type: vm.formOptions.operation, data: vm.entity})
          return true
        })
      },
      _createEntity () {
        return {
          f_db_name: 'zhuku_master',
          f_table_name: null,
          f_order: 1,
          f_is_main: 1,
          f_entity_interface: null,
          f_entity_base_class: this.baseClasses[0],
          f_entity_class: this.generateRule.f_package_name + '.entity.' + this.generateRule.f_code + 'Entity',
          f_mapper_base_class: 'com.jeeweb.framework.business.mapper.BaseMapper',
          f_mapper_class: this.generateRule.f_package_name + '.mapper.' + this.generateRule.f_code + 'Mapper',
          f_service_base_class: 'com.jeeweb.framework.business.service.BaseService',
          f_service_class: this.generateRule.f_package_name + '.service.' + this.generateRule.f_code + 'Service',
          f_rest_base_class: 'com.jeeweb.framework.business.web.controller.BaseController',
          f_rest_class: this.generateRule.f_package_name + '.web.api.' + this.generateRule.f_code + 'Api',
          fieldList: []
        }
      },
      _closeForm () {
        if (this.formOptions.context.featureComponent.formOptions) {
          this.formOptions.context.featureComponent.formOptions.isShow = false
        }
      }
    }
  }
</script>
