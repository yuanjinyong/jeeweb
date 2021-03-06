<template>
  <jw-form ref="form" :form-options="options" :entity="entity" :rules="rules">
    <template slot="fieldset">
      <el-form-item class="jw-field jw-field-1" label="编码" prop="f_code">
        <el-input v-model="entity.f_code"></el-input>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="名称" prop="f_name">
        <el-input v-model="entity.f_name"></el-input>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="数据库名" prop="f_db_name">
        <el-select v-model="entity.f_db_name">
          <el-option v-for="item in schematas" :key="item.SCHEMA_NAME" :value="item.SCHEMA_NAME"
                     :label="item.SCHEMA_NAME">
            {{item.SCHEMA_NAME}}
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="数据库表名" prop="f_table_name">
        <el-autocomplete v-model="entity.f_table_name" icon="search" :fetch-suggestions="loadTables"
                         @select="loadFields">
        </el-autocomplete>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="字典项编码字段" prop="f_code_column">
        <el-select v-model="entity.f_code_column">
          <el-option v-for="item in fields" :key="item.COLUMN_NAME" :value="item.COLUMN_NAME" :label="item.COLUMN_NAME">
            {{item.COLUMN_NAME}}
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="字典项名称字段" prop="f_name_column">
        <el-select v-model="entity.f_name_column">
          <el-option v-for="item in fields" :key="item.COLUMN_NAME" :value="item.COLUMN_NAME" :label="item.COLUMN_NAME">
            {{item.COLUMN_NAME}}
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="字典项排序字段" prop="f_order_column">
        <el-select v-model="entity.f_order_column">
          <el-option v-for="item in fields" :key="item.COLUMN_NAME" :value="item.COLUMN_NAME" :label="item.COLUMN_NAME">
            {{item.COLUMN_NAME}}
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="系统预置" prop="f_is_preset">
        <el-radio-group v-model="entity.f_is_preset">
          <el-radio :label="101" :disabled="!curUser.superAdmin">是</el-radio>
          <el-radio :label="102" :disabled="!curUser.superAdmin">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item class="jw-field jw-field-2" label="查询Where条件" prop="f_where_clause">
        <el-input v-model="entity.f_where_clause" type="textarea" autosize></el-input>
      </el-form-item>
      <el-form-item class="jw-field jw-field-2" label="备注" prop="f_remark">
        <el-input v-model="entity.f_remark" type="textarea" autosize></el-input>
      </el-form-item>
      <el-form-item class="jw-field jw-field-2" label="字典项列表">
        <ag-grid ref="grid" class="ag-fresh jw-grid" style="height: 320px;" :grid-options="gridOptions"></ag-grid>
      </el-form-item>
    </template>
  </jw-form>
</template>


<script>
  import {DetailMixin} from 'mixins'

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
          floatingFilter: false,
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
              f_db_name: 'defaultSqlMapper',
              f_table_name: 't_sys_dict_item',
              f_tenant_column: 0,
              f_code_column: 'f_item_code',
              f_name_column: 'f_item_name',
              f_order_column: 'f_item_order',
              f_where_clause: null,
              f_is_preset: 102,
              f_remark: null,
              itemList: []
            }

            cb(entity)
            vm.gridOptions.setData(entity.itemList)
          },
          loadRemoteEntity (options, cb) {
            let vm = options.context.detailComponent
            this.$http.get(options.context.url + '/' + options.params.f_id).then((response) => {
              let entity = response.body.success ? response.body.data : {itemList: []}
              cb(entity)
              vm.gridOptions.setData(entity.itemList)
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
    computed: {
      curUser () {
        return this.$store.state.user || {}
      }
    },
    created () {
      this.gridOptions.columnDefs = [{
        pinned: 'left',
        type: ['IndexRender', 'AddHeader'],
        headerComponentParams: {
          operation: {
            isDisabled (params, entity) {
              return params.context.featureComponent.options.operation === 'view'
            },
            onClick (params, entity) {
              params.context.featureComponent.onAddItem()
            }
          }
        }
      }, {
        headerName: '编码',
        field: 'f_item_code',
        suppressSizeToFit: false,
        type: 'TextEditor',
        cellRendererParams: {
          options: {
            isDisabled (params, entity) {
              if (entity.f_id && entity.f_is_preset === '101') {
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
        suppressSizeToFit: false,
        type: 'TextEditor',
        width: 300
      }, {
        headerName: '是否预置',
        field: 'f_is_preset',
        type: 'DictEditor',
        cellRendererParams: {
          dict: 'TrueFalse',
          options: {
            isDisabled (params, entity) {
              if (entity.f_id) {
                return !params.context.featureComponent.curUser.superAdmin
              } else {
                return false
              }
            }
          }
        },
        width: 70
      }, {
        type: 'OperationRender',
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
              return params.context.featureComponent.options.operation === 'view' || (entity.f_id && entity.f_is_preset === '101')
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
          f_is_preset: 102
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
