<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
  .el-collapse-item__header {
    background-color: #fbfdff;
  }

  .el-collapse-item__wrap {
    background-color: #fff;
  }
</style>


<template>
  <jw-form ref="form" :form-options="options" :entity="entity" :rules="rules">
    <template slot="fieldset">
      <el-collapse v-model="activeCollapses">
        <el-collapse-item name="baseInfo" title="基本信息">
          <el-form-item label="数据库" prop="TABLE_SCHEMA">
            <el-select class="jw-field-col-1" v-model="entity.TABLE_SCHEMA" :disabled="options.operation !== 'add'">
              <el-option v-for="item in schematas" :key="item.SCHEMA_NAME" :value="item.SCHEMA_NAME"
                         :label="item.SCHEMA_NAME">
                {{item.SCHEMA_NAME}}
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="表名" prop="TABLE_NAME">
            <el-input class="jw-field-col-1" v-model="entity.TABLE_NAME" :disabled="options.operation !== 'add'">
            </el-input>
          </el-form-item>
          <el-form-item label="描述" prop="TABLE_COMMENT">
            <el-input class="jw-field-col-1" v-model="entity.TABLE_COMMENT"></el-input>
          </el-form-item>
          <el-form-item label="自动增量" prop="AUTO_INCREMENT">
            <el-input-number class="jw-field-col-1" v-model="entity.AUTO_INCREMENT" :step="10000"></el-input-number>
          </el-form-item>
          <el-form-item label="类型" prop="TABLE_TYPE">
            <el-select class="jw-field-col-1" v-model="entity.TABLE_TYPE" disabled>
              <el-option :value="'BASE TABLE'" :label="'表'"></el-option>
              <el-option :value="'VIEW'" :label="'视图'"></el-option>
              <el-option :value="'SYSTEM VIEW'" :label="'系统视图'"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="引擎" prop="ENGINE">
            <el-select class="jw-field-col-1" v-model="entity.ENGINE" disabled>
              <el-option :value="''" :label="''"></el-option>
              <el-option :value="'InnoDB'" :label="'InnoDB'"></el-option>
              <el-option :value="'MyISAM'" :label="'MyISAM'"></el-option>
              <el-option :value="'MEMORY'" :label="'MEMORY'"></el-option>
              <el-option :value="'CSV'" :label="'CSV'"></el-option>
              <el-option :value="'PERFORMANCE_SCHEMA'" :label="'PERFORMANCE_SCHEMA'"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="创建时间" prop="CREATE_TIME">
            <el-date-picker class="jw-field-col-1" v-model="entity.CREATE_TIME" type="datetime" disabled>
            </el-date-picker>
          </el-form-item>
          <el-form-item label="修改时间" prop="UPDATE_TIME">
            <el-date-picker class="jw-field-col-1" v-model="entity.UPDATE_TIME" type="datetime" disabled>
            </el-date-picker>
          </el-form-item>
        </el-collapse-item>

        <el-collapse-item name="columnDef" title="列定义">
          <div class="jw-form-item" style="height: 360px;">
            <ag-grid ref="grid" class="ag-fresh jw-grid" :grid-options="gridOptions"></ag-grid>
          </div>
        </el-collapse-item>
      </el-collapse>
    </template>
  </jw-form>
</template>


<script>
  import {DetailMixin} from 'mixins'
  import {
    AddHeaderComponenetFramework,
    IndexRendererFramework,
    OperationRendererFramework
  } from 'components/ag-grid'

  export default {
    name: 'schemaTableDetail',
    mixins: [DetailMixin],
    data () {
      return {
        schematas: [],
        activeCollapses: ['baseInfo', 'columnDef'],
        gridOptions: this.$grid.buildOptions({
          rowModelType: 'normal',
          pagination: false,
          enableFilter: false,
          floatingFilter: false,
          context: {
            featureComponent: this,
            getPermissions (params, operation) {
              return {add: true, remove: true}
            }
          }
        }),
        options: {
          formClass: 'jw-collapse-form',
          context: {
            name: '数据库表',
            url: 'api/schema/information/tables',
            detailComponent: this
          },
          beforeOpen (options, cb) {
            options.context.detailComponent.init(cb)
          },
          createEntity (options, cb) {
            let entity = {
              TABLE_SCHEMA: 'zkpms_company',
              TABLE_NAME: null,
              TABLE_COMMENT: null,
              AUTO_INCREMENT: null,
              TABLE_TYPE: 'BASE TABLE',
              ENGINE: 'InnoDB',
              CREATE_TIME: null,
              UPDATE_TIME: null,
              columnList: []
            }

            cb(entity)
            let vm = options.context.detailComponent
            vm.$nextTick(() => {
              vm.gridOptions.api.setRowData(entity.columnList || [])
            })
          },
          loadRemoteEntity (options, cb) {
            this.$http.get(options.context.url + '/' + options.params.TABLE_SCHEMA + '/' + options.params.TABLE_NAME).then((response) => {
              let entity = response.body.success ? response.body.data : {columnList: []}
              cb(entity)
              options.context.detailComponent.gridOptions.api.setRowData(entity.columnList || [])
            })
          }
        },
        entity: {columnList: []},
        rules: {
          f_name: [
            {required: true, message: '请输入企业名称', trigger: 'blur'},
            {max: 50, message: '长度在128个字符以内', trigger: 'blur'}
          ],
          'creator.f_name': [
            {required: true, message: '请输入姓名', trigger: 'blur'},
            {max: 50, message: '长度在32个字符以内', trigger: 'blur'}
          ],
          'creator.f_telephone': [
            {required: true, message: '请输入手机号', trigger: 'blur'}
          ],
          'creator.f_account': [
            {required: true, message: '请输入登录账号', trigger: 'blur'}
          ],
          'creator.f_password': [
            {required: true, message: '请输入登录密码', trigger: 'blur'}
          ]
        }
      }
    },
    created () {
      this.gridOptions.columnDefs = [{
        headerName: '序号',
        field: 'ORDINAL_POSITION',
        headerComponentFramework: AddHeaderComponenetFramework,
        headerComponentParams: {
          operation: {
            isDisabled (params, entity) {
              return params.context.featureComponent.options.operation === 'view'
            },
            onClick (params, entity) {
              params.context.featureComponent.$refs['selector'].open()
            }
          }
        },
        cellStyle: {'text-align': 'right'},
        cellRendererFramework: IndexRendererFramework,
        width: 38
      }, {
        headerName: '列名',
        field: 'COLUMN_NAME',
        tooltipField: 'COLUMN_NAME',
        width: 200
      }, {
        headerName: '数据类型',
        field: 'DATA_TYPE',
        width: 92
      }, {
        headerName: '长度',
        field: 'DATA_TYPE',
        width: 92
      }, {
        headerName: '默认',
        field: 'COLUMN_DEFAULT',
        width: 92
      }, {
        headerName: '主键',
        field: 'COLUMN_KEY',
        width: 92
      }, {
        headerName: '非空',
        field: 'IS_NULLABLE',
        width: 92
      }, {
        headerName: '无符号',
        field: 'COLUMN_DEFAULT',
        width: 92
      }, {
        headerName: '自增',
        field: 'COLUMN_DEFAULT',
        width: 92
      }, {
        headerName: '注释',
        field: 'COLUMN_COMMENT',
        width: 300
      }, {
        headerName: '操作',
        field: '',
        cellStyle: {'text-align': 'center'},
        cellRendererFramework: OperationRendererFramework,
        cellRendererParams: {
          operations: [{
            id: 'remove',
            title: '删除URL',
            isDisabled (params, entity) {
              return params.context.featureComponent.options.operation === 'view'
            },
            onClick (params, entity) {
              params.context.featureComponent.onRemoveUrl(entity)
            }
          }]
        },
        width: 48
      }]
    },
    methods: {
      init (cb) {
        Promise.all([
          this.$http.get('api/schema/information/schematas', {params: {orderBy: 'SCHEMA_NAME'}})
        ]).then((responses) => {
          let response = responses[0]
          this.schematas = response.body.success ? response.body.data.items : []

          cb()
        }).catch((e) => {
          cb()
        })
      },
      onSubmit () {
        this.$refs['form'].validate((valid) => {
          if (!valid) {
            return false
          }

//          this.$http.post('api/zkpms/register/company', this.company).then((response) => {
//            if (response.body.success) {
//              this.$router.push({path: '/', params: this.company.creator})
//            }
//          })

          return true
        })
      }
    }
  }
</script>
