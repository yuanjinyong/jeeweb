<template>
  <jw-form ref="form" :form-options="options" :entity="entity" :rules="rules">
    <template slot="fieldset">
      <el-form-item label="数据库名" prop="f_db_name">
        <el-select class="jw-field-col-1" v-model="entity.f_db_name">
          <el-option v-for="item in schematas" :key="item.SCHEMA_NAME" :value="item.SCHEMA_NAME"
                     :label="item.SCHEMA_NAME">
            {{item.SCHEMA_NAME}}
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="数据库表名" prop="f_table_name">
        <el-autocomplete class="jw-field-col-1" v-model="entity.f_table_name" icon="search"
                         :fetch-suggestions="loadTables" @select="loadFields">
        </el-autocomplete>
      </el-form-item>
      <el-form-item label="排序" prop="f_order">
        <el-input-number class="jw-field-col-1" v-model="entity.f_order" :step="1"></el-input-number>
      </el-form-item>
      <el-form-item label="主表" prop="f_is_main">
        <el-radio-group class="jw-field-col-1" v-model="entity.f_is_main" @change="onIsMainChange">
          <el-radio :label="1">是</el-radio>
          <el-radio :label="2">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="父类" prop="f_entity_base_class">
        <el-select class="jw-field-col-1" v-model="entity.f_entity_base_class">
          <el-option v-for="baseClass in baseClasses" :key="baseClass.fullName" :value="baseClass.fullName"
                     :label="baseClass.name">
            {{baseClass.fullName}}
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="接口" prop="f_entity_interfaces">
        <el-select class="jw-field-col-3" v-model="f_entity_interfaces" multiple>
          <el-option v-for="interface in interfaces" :key="interface.fullName" :value="interface.fullName"
                     :label="interface.name">
            {{interface.fullName}}
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="Entity" prop="f_entity_class">
        <el-input class="jw-field-col-2" v-model="entity.f_entity_class"
                  placeholder="Map类型请填写“com.jeeweb.framework.core.model.RowMap”。">
        </el-input>
      </el-form-item>
      <el-form-item label="Mapper" prop="f_mapper_class">
        <el-input class="jw-field-col-2" v-model="entity.f_mapper_class"></el-input>
      </el-form-item>
      <el-form-item label="Service" prop="f_service_class">
        <el-input class="jw-field-col-2" v-model="entity.f_service_class"></el-input>
      </el-form-item>
      <el-form-item label="Api" prop="f_rest_class">
        <el-input class="jw-field-col-2" v-model="entity.f_rest_class"></el-input>
      </el-form-item>

      <div class="jw-form-item" style="height: 449px;">
        <ag-grid ref="grid" class="ag-fresh jw-grid" :grid-options="gridOptions"></ag-grid>
      </div>
    </template>
  </jw-form>
</template>


<script>
  import {DetailMixin} from 'mixins'
  import {
    CheckboxEditorFramework,
    DictEditorFramework
  } from 'components/ag-grid'

  export default {
    name: 'generationRuleTableDetail',
    mixins: [DetailMixin],
    props: {
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
        }, {
          name: 'ITenant',
          fullName: 'com.jeeweb.framework.business.model.ITenant'
        }],
        gridOptions: this.$grid.buildOptions({
          rowModelType: 'inMemory',
          pagination: false,
          enableFilter: false,
          floatingFilter: false,
          context: {
            name: '生成规则字段配置',
            url: null,
            featureComponent: this
          }
        }),
        options: {
          context: {
            name: '生成规则数据库表',
            url: null,
            detailComponent: this
          },
          createEntity (options, cb) {
            let vm = options.context.detailComponent
            let entity = {
              f_db_name: 'jeeweb',
              f_table_name: null,
              f_order: 1,
              f_is_main: 1,
              f_entity_interface: null,
              f_entity_base_class: vm.baseClasses[0].fullName,
              f_entity_class: vm.generateRule.f_package_name + '.entity.' + vm.generateRule.f_code + 'Entity',
              f_mapper_base_class: 'com.jeeweb.framework.business.mapper.BaseMapper',
              f_mapper_class: vm.generateRule.f_package_name + '.mapper.' + vm.generateRule.f_code + 'Mapper',
              f_service_base_class: 'com.jeeweb.framework.business.service.BaseService',
              f_service_class: vm.generateRule.f_package_name + '.service.' + vm.generateRule.f_code + 'Service',
              f_rest_base_class: 'com.jeeweb.framework.business.web.api.BaseApi',
              f_rest_class: vm.generateRule.f_package_name + '.web.api.' + vm.generateRule.f_code + 'Api',
              fieldList: []
            }

            cb(entity)
            vm.$nextTick(() => {
              vm.gridOptions.api.setRowData(entity.fieldList || [])
            })
          },
          loadLocalEntity (options, cb) {
            let vm = options.context.detailComponent
            let entity = vm.$lodash.cloneDeep(options.params)
            cb(entity)
            vm.$nextTick(() => {
              vm.gridOptions.api.setRowData(entity.fieldList || [])
            })
          }
        },
        entity: {fieldList: []},
        rules: {}
      }
    },
    computed: {
      f_entity_interfaces: {
        get () {
          if (this.entity.f_entity_interface) {
            return this.entity.f_entity_interface.split(',')
          }
          return []
        },
        set (v) {
          if (v && v.length > 0) {
            this.entity.f_entity_interface = v.join(',')
          } else {
            this.entity.f_entity_interface = null
          }
        }
      }
    },
    created () {
      this.gridOptions.columnDefs = [
        {
          headerName: '#',
          field: 'f_order',
          pinned: 'left',
          cellStyle: {'text-align': 'right'},
          width: 30
        },
        {
          headerName: '字段名',
          field: 'f_column_name',
          pinned: 'left',
          width: 120
        },
        {
          headerName: 'Database',
          children: [
            {
              headerName: '描述',
              field: 'f_column_comment',
              tooltipField: 'f_column_comment',
              width: 280
            },
            {
              headerName: '列类型',
              field: 'f_column_type',
              width: 100
            }]
        },
        {
          headerName: 'Entity',
          children: [
            {
              headerName: 'Java数据类型',
              field: 'f_java_type',
              editable: true,
              cellRendererFramework: DictEditorFramework,
              cellRendererParams: {
                dict: [{
                  f_item_code: 'java.lang.String',
                  f_item_text: 'java.lang.String'
                }, {
                  f_item_code: 'java.lang.Integer',
                  f_item_text: 'java.lang.Integer'
                }, {
                  f_item_code: 'java.math.BigDecimal',
                  f_item_text: 'java.math.BigDecimal'
                }, {
                  f_item_code: 'java.sql.Timestamp',
                  f_item_text: 'java.sql.Timestamp'
                }, {
                  f_item_code: 'java.lang.Double',
                  f_item_text: 'java.lang.Double'
                }, {
                  f_item_code: 'java.lang.Boolean',
                  f_item_text: 'java.lang.Boolean'
                }]
              },
              width: 180
            },
            {
              headerName: '主键',
              field: 'f_is_primary',
              cellStyle: {'text-align': 'center'},
              cellRendererFramework: CheckboxEditorFramework,
              width: 40
            },
            {
              headerName: '继承',
              headerTooltip: '继承父类的字段',
              field: 'f_is_super_class_field',
              cellStyle: {'text-align': 'center'},
              cellRendererFramework: CheckboxEditorFramework,
              width: 40
            }, {
              headerName: '覆写',
              headerTooltip: '覆写或实现接口的字段',
              field: 'f_is_override_field',
              cellStyle: {'text-align': 'center'},
              cellRendererFramework: CheckboxEditorFramework,
              width: 40
            }]
        },
        {
          headerName: 'Mapper.xml',
          children: [
            {
              headerName: '插入',
              field: 'f_is_insert',
              cellStyle: {'text-align': 'center'},
              cellRendererFramework: CheckboxEditorFramework,
              width: 40
            },
            {
              headerName: '更新',
              field: 'f_is_update',
              cellStyle: {'text-align': 'center'},
              cellRendererFramework: CheckboxEditorFramework,
              width: 40
            },
            {
              headerName: '查询',
              field: 'f_is_select',
              cellStyle: {'text-align': 'center'},
              cellRendererFramework: CheckboxEditorFramework,
              width: 40
            },
            {
              headerName: '等于',
              field: 'f_is_equal',
              cellStyle: {'text-align': 'center'},
              cellRendererFramework: CheckboxEditorFramework,
              width: 40
            },
            {
              headerName: '模糊',
              field: 'f_is_like',
              cellStyle: {'text-align': 'center'},
              cellRendererFramework: CheckboxEditorFramework,
              width: 40
            },
            {
              headerName: '左模',
              field: 'f_is_left_like',
              cellStyle: {'text-align': 'center'},
              cellRendererFramework: CheckboxEditorFramework,
              width: 40
            },
            {
              headerName: '右模',
              field: 'f_is_right_like',
              cellStyle: {'text-align': 'center'},
              cellRendererFramework: CheckboxEditorFramework,
              width: 40
            },
            {
              headerName: 'In',
              field: 'f_is_in',
              cellStyle: {'text-align': 'center'},
              cellRendererFramework: CheckboxEditorFramework,
              width: 40
            },
            {
              headerName: 'Not In',
              headerTooltip: 'Not In',
              field: 'f_is_not_in',
              cellStyle: {'text-align': 'center'},
              cellRendererFramework: CheckboxEditorFramework,
              width: 40
            },
            {
              headerName: 'Between',
              headerTooltip: 'Between',
              field: 'f_is_between',
              cellStyle: {'text-align': 'center'},
              cellRendererFramework: CheckboxEditorFramework,
              width: 40
            }]
        },
        {
          headerName: 'Web',
          children: [
            {
              headerName: '搜索',
              field: 'f_is_search',
              cellStyle: {'text-align': 'center'},
              cellRendererFramework: CheckboxEditorFramework,
              width: 40
            },
            {
              headerName: '表格',
              field: 'f_is_grid',
              cellStyle: {'text-align': 'center'},
              cellRendererFramework: CheckboxEditorFramework,
              width: 40
            },
            {
              headerName: '表单',
              field: 'f_is_form',
              cellStyle: {'text-align': 'center'},
              cellRendererFramework: CheckboxEditorFramework,
              width: 40
            }]
        }
      ]
    },
    mounted () {
      this._loadSchematas()
    },
    methods: {
      loadTables (query, callback) {
        this.loadTablesTimer && clearTimeout(this.loadTablesTimer)
        this.loadTablesTimer = setTimeout(() => {
          this._loadTables(query, callback)
        }, 500)
      },
      _loadTables (tableName, callback) {
        this.$http.get('api/schema/information/tables', {
          params: {
            TABLE_SCHEMA: this.entity.f_db_name,
            TABLE_NAME_like: tableName,
            orderBy: 'TABLE_NAME'
          }
        }).then((response) => {
          let tables = []
          if (response.body.success) {
            response.body.data.items.forEach((table) => {
              tables.push({value: table.TABLE_NAME})
            })
          }
          callback && callback(tables)
        })
      },
      loadFields (item) {
        this.$http.get('api/schema/information/columns', {
          params: {
            TABLE_SCHEMA: this.entity.f_db_name,
            TABLE_NAME: this.entity.f_table_name,
            orderBy: 'ORDINAL_POSITION'
          }
        }).then((response) => {
          if (response.body.success) {
            this.entity.fieldList = []
            response.body.data.items.forEach((column) => {
              let field = this._buildFieldEntity(column)
              this.entity.fieldList.push(field)
            })
          } else {
            this.entity.fieldList = []
          }
          this.gridOptions.api.setRowData(this.entity.fieldList)
        })
      },
      _buildFieldEntity (column) {
        var cfg = {
          javaType: {
            bigint: 'java.lang.Integer',
            // blob
            // char
            date: 'java.sql.Timestamp',
            datetime: 'java.sql.Timestamp',
            decimal: 'java.math.BigDecimal',
            double: 'java.lang.Double',
            // enum
            float: 'java.lang.Float',
            int: 'java.lang.Integer',
            // longblob
            longtext: 'java.lang.String',
            // mediumblob
            mediumtext: 'java.lang.String',
            // set
            smallint: 'java.lang.Integer',
            text: 'java.lang.String',
            time: 'java.sql.Timestamp',
            timestamp: 'java.sql.Timestamp',
            tinyblob: 'java.lang.Integer',
            tinyint: 'java.lang.Integer',
            tinytext: 'java.lang.Integer',
            varchar: 'java.lang.String'
          },
          superClassField: ['f_id', 'f_parent_id', 'f_parent_path'],
          overrideField: ['f_tenant_id', 'f_creator_id', 'f_creator_name', 'f_created_time', 'f_is_preset'],
          suppressInsertField: ['f_id'],
          suppressUpdateField: ['f_id', 'f_creator_id', 'f_created_time'],
          equalField: ['f_status'],
          likeField: ['f_name'],
          inField: ['f_status'],
          betweenField: ['f_created_time']
        }

        return {
          f_order: column.ORDINAL_POSITION,
          f_column_name: column.COLUMN_NAME,
          f_column_comment: column.COLUMN_COMMENT,
          f_column_type: column.COLUMN_TYPE,
          f_java_type: cfg.javaType[column.DATA_TYPE],
          f_is_primary: column.COLUMN_KEY === 'PRI' ? 1 : 2,
          f_is_super_class_field: cfg.superClassField.indexOf(column.COLUMN_NAME) >= 0 ? 1 : 2,
          f_is_override_field: cfg.overrideField.indexOf(column.COLUMN_NAME) >= 0 ? 1 : 2,
          f_is_insert: cfg.suppressInsertField.indexOf(column.COLUMN_NAME) >= 0 ? 2 : 1,
          f_is_update: cfg.suppressUpdateField.indexOf(column.COLUMN_NAME) >= 0 ? 2 : 1,
          f_is_select: 1,
          f_is_equal: cfg.equalField.indexOf(column.COLUMN_NAME) >= 0 ? 1 : 2,
          f_is_like: cfg.likeField.indexOf(column.COLUMN_NAME) >= 0 ? 1 : 2,
          f_is_left_like: 2,
          f_is_right_like: 2,
          f_is_in: cfg.inField.indexOf(column.COLUMN_NAME) >= 0 ? 1 : 2,
          f_is_not_in: 2,
          f_is_between: cfg.betweenField.indexOf(column.COLUMN_NAME) >= 0 ? 1 : 2,
          f_is_search: 2,
          f_is_grid: 2,
          f_is_form: 2
        }
      },
      _loadSchematas () {
        this.$http.get('api/schema/information/schematas', {params: {orderBy: 'SCHEMA_NAME'}}).then((response) => {
          this.schematas = response.body.success ? response.body.data.items : []
        })
      },
      onIsMainChange () {
        if (this.entity.f_is_main === 2) {
          this.entity.f_service_base_class = null
          this.entity.f_service_class = null
          this.entity.f_rest_base_class = null
          this.entity.f_rest_class = null
        } else {
          this.entity.f_service_base_class = 'com.jeeweb.framework.business.service.BaseService'
          this.entity.f_service_class = this.generateRule.f_package_name + '.service.' + this.generateRule.f_code + 'Service'
          this.entity.f_rest_base_class = 'com.jeeweb.framework.business.web.api.BaseApi'
          this.entity.f_rest_class = this.generateRule.f_package_name + '.web.api.' + this.generateRule.f_code + 'Api'
        }
      }
    }
  }
</script>
