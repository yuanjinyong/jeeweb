<template>
  <jw-form ref="form" :form-options="options" :entity="entity" :rules="rules">
    <template slot="fieldset">
      <el-form-item label="编码" prop="f_code">
        <el-input class="jw-field-col-1" v-model="entity.f_code"></el-input>
      </el-form-item>
      <el-form-item label="名称" prop="f_name">
        <el-input class="jw-field-col-1" v-model="entity.f_name"></el-input>
      </el-form-item>
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
      <el-form-item label="字典项编码字段" prop="f_code_column">
        <el-select class="jw-field-col-1" v-model="entity.f_code_column">
          <el-option v-for="item in fields" :key="item.COLUMN_NAME" :value="item.COLUMN_NAME" :label="item.COLUMN_NAME">
            {{item.COLUMN_NAME}}
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="字典项名称字段" prop="f_name_column">
        <el-select class="jw-field-col-1" v-model="entity.f_name_column">
          <el-option v-for="item in fields" :key="item.COLUMN_NAME" :value="item.COLUMN_NAME" :label="item.COLUMN_NAME">
            {{item.COLUMN_NAME}}
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="字典项排序字段" prop="f_order_column">
        <el-select class="jw-field-col-1" v-model="entity.f_order_column">
          <el-option v-for="item in fields" :key="item.COLUMN_NAME" :value="item.COLUMN_NAME" :label="item.COLUMN_NAME">
            {{item.COLUMN_NAME}}
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="系统预置" prop="f_is_preset">
        <el-radio-group class="jw-field-col-1" v-model="entity.f_is_preset">
          <el-radio :label="1" disabled>是</el-radio>
          <el-radio :label="2" disabled>否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="查询Where条件" prop="f_where_clause">
        <el-input class="jw-field-col-2" v-model="entity.f_where_clause" type="textarea" autosize></el-input>
      </el-form-item>
      <el-form-item label="备注" prop="f_remark">
        <el-input class="jw-field-col-2" v-model="entity.f_remark" type="textarea" autosize></el-input>
      </el-form-item>

      <div class="jw-form-item" style="height: 320px;">
        <ag-grid ref="grid" class="ag-fresh jw-grid" :grid-options="gridOptions"></ag-grid>
      </div>
    </template>
  </jw-form>
</template>


<script>
  import {DetailMixin} from 'mixins'
  import {
    AddHeaderComponenetFramework,
    DictEditorFramework,
    IndexRendererFramework,
    OperationRendererFramework,
    TextEditorFramework
  } from 'components/ag-grid'

  export default {
    name: 'dictDetail',
    mixins: [DetailMixin],
    data () {
      return {
        schematas: [],
        fields: [],
        gridOptions: this.$grid.buildOptions({
          rowModelType: 'inMemory',
          rowData: [],
          pagination: false,
          enableFilter: false,
          context: {
            name: '字典项',
            url: null,
            featureComponent: this,
            getPermissions (params, operation) {
              return {add: true, remove: true}
            }
          }
        }),
        options: {
          context: {
            name: '字典组',
            url: 'api/platform/sys/dicts',
            detailComponent: this
          },
          beforeOpen (options, cb) {
            options.context.detailComponent._loadSchematas(cb)
          },
          createEntity (options, cb) {
            let vm = options.context.detailComponent
            let entity = {
              f_code: null,
              f_name: null,
              f_db_name: 'zhuku_master',
              f_table_name: 't_sys_dict_item',
              f_tenant_column: 0,
              f_code_column: 'f_item_code',
              f_name_column: 'f_item_name',
              f_order_column: 'f_item_order',
              f_where_clause: null,
              f_is_preset: 2,
              f_remark: null,
              itemList: []
            }

            cb(entity)
            vm.$nextTick(() => {
              vm.gridOptions.api.setRowData(entity.itemList || [])
            })
          },
          loadRemoteEntity (options, cb) {
            let vm = options.context.detailComponent
            this.$http.get(options.context.url + '/' + options.params.f_id).then((response) => {
              let entity = response.body.success ? response.body.data : {itemList: []}
              cb(entity)
              vm.gridOptions.api.setRowData(entity.itemList || [])
            })
          }
        },
        entity: {},
        rules: {
          f_code: [
            {required: true, message: '请输入编码', trigger: 'blur'},
            {max: 50, message: '长度在512个字符以内', trigger: 'blur'}
          ],
          f_name: [
            {required: true, message: '请输入名称', trigger: 'blur'},
            {max: 50, message: '长度在512个字符以内', trigger: 'blur'}
          ],
          f_db_name: [
            {required: true, message: '请选择数据库名', trigger: 'blur'}
          ],
          f_table_name: [
            {required: true, message: '请输入数据表名', trigger: 'blur'}
          ],
          f_code_column: [
            {required: true, message: '请选择字典项编码字段', trigger: 'blur'}
          ],
          f_name_column: [
            {required: true, message: '请选择字典项名称字段', trigger: 'blur'}
          ],
          f_order_column: [
            {required: true, message: '请选择字典项排序字段', trigger: 'blur'}
          ]
        }
      }
    },
    created () {
      this.gridOptions.columnDefs = [{
        headerName: '排序',
        headerComponentFramework: AddHeaderComponenetFramework,
        headerComponentParams: {
          operation: {
            isDisabled (params, entity) {
              return params.context.featureComponent.options.operation === 'view'
            },
            onClick (params, entity) {
              params.context.featureComponent.onAddItem()
            }
          }
        },
        field: 'f_item_order',
        cellStyle: {'text-align': 'right'},
        cellRendererFramework: IndexRendererFramework,
        width: 38
      }, {
        headerName: '编码',
        field: 'f_item_code',
        cellRendererFramework: TextEditorFramework,
        cellRendererParams: {
          editorOptions: {
            rule: [],
            isDisabled (params, entity) {
              if (entity.f_id && entity.f_is_preset === '1') {
                return true
              } else {
                return false
              }
            }
          }
        },
        width: 200
      }, {
        headerName: '名称',
        field: 'f_item_text',
        cellRendererFramework: TextEditorFramework,
        width: 300
      }, {
        headerName: '是否预置',
        field: 'f_is_preset',
        cellStyle: {'text-align': 'center'},
        cellRendererFramework: DictEditorFramework,
        cellRendererParams: {
          dict: 'YesNo2',
          editorOptions: {
            rule: [],
            isDisabled (params, entity) {
              if (entity.f_id) {
                return true
              } else {
                return false
              }
            }
          }
        },
        width: 75
      }, {
        headerName: '操作',
        field: '',
        cellStyle: {'text-align': 'center'},
        cellRendererFramework: OperationRendererFramework,
        cellRendererParams: {
          operations: [{
            id: 'up',
            title: '上移',
            type: 'primary',
            icon: 'fa fa-arrow-up',
            permission: '',
            isDisabled (params, entity) {
              return params.context.featureComponent.options.operation === 'view' || params.node.firstChild
            },
            onClick (params, entity) {
              params.context.featureComponent.onMoveUp(entity)
            }
          }, {
            id: 'down',
            title: '下移',
            type: 'primary',
            icon: 'fa fa-arrow-down',
            permission: '',
            isDisabled (params, entity) {
              return params.context.featureComponent.options.operation === 'view' || params.node.lastChild
            },
            onClick (params, entity) {
              params.context.featureComponent.onMoveDown(entity)
            }
          }, {
            id: 'remove',
            isDisabled (params, entity) {
              return params.context.featureComponent.options.operation === 'view' || (entity.f_id && entity.f_is_preset === '1')
            },
            onClick (params, entity) {
              params.context.featureComponent.onRemoveItem(entity)
            }
          }]
        },
        width: 70
      }]
    },
    methods: {
      _loadSchematas (cb) {
        this.$http.get('api/schema/information/schematas', {params: {orderBy: 'SCHEMA_NAME'}}).then((response) => {
          this.schematas = response.body.success ? response.body.data.items : []
          cb()
        })
      },
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
          this.fields = response.body.success ? response.body.data.items : []
        })
      },
      onAddItem () {
        this.entity.itemList.push({
          f_item_code: null,
          f_item_text: null,
          f_item_order: 10,
          f_is_preset: 2
        })
        this.gridOptions.api.setRowData(this.entity.itemList)
      },
      onRemoveItem (entity) {
        for (let i = 0; i < this.entity.itemList.length; i++) {
          if (this.entity.itemList[i].f_item_code === entity.f_item_code) {
            this.entity.itemList.splice(i, 1)
            break
          }
        }

        this.gridOptions.api.setRowData(this.entity.itemList)
      },
      onMoveUp (entity) {
        for (let i = 0; i < this.entity.itemList.length; i++) {
          if (this.entity.itemList[i].f_item_code === entity.f_item_code) {
            let pre = this.entity.itemList[i - 1]
            let cur = this.entity.itemList[i]
            this.entity.itemList.splice(i - 1, 2, cur, pre)
            break
          }
        }

        this.gridOptions.api.setRowData(this.entity.itemList)
      },
      onMoveDown (entity) {
        for (let i = 0; i < this.entity.itemList.length; i++) {
          if (this.entity.itemList[i].f_item_code === entity.f_item_code) {
            let cur = this.entity.itemList[i]
            let next = this.entity.itemList[i + 1]
            this.entity.itemList.splice(i, 2, next, cur)
            break
          }
        }

        this.gridOptions.api.setRowData(this.entity.itemList)
      }
    }
  }
</script>
