<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>

<template>
  <div class="jw-form">
    <div class="jw-form-body" :style="formBodyStyle">
      <el-form ref="form" :model="entity" :rules="rules" :inline="true" :label-width="labelWidth">
        <fieldset :disabled="formOptions.operation === 'view'">
          <el-form-item label="名称" prop="f_name">
            <el-input class="jw-field-col-1" v-model="entity.f_name"></el-input>
          </el-form-item>
          <el-form-item label="系统预置" prop="f_is_preset">
            <el-radio-group class="jw-field-col-1" v-model="entity.f_is_preset">
              <el-radio :label="1" disabled>是</el-radio>
              <el-radio :label="2" disabled>否</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="描述" prop="f_desc">
            <el-input class="jw-field-col-2" v-model="entity.f_desc" type="textarea" autosize></el-input>
          </el-form-item>
          <el-form-item label="备注" prop="f_remark">
            <el-input class="jw-field-col-2" v-model="entity.f_remark" type="textarea" autosize></el-input>
          </el-form-item>
        </fieldset>
      </el-form>
    </div>

    <div class="jw-form-footer">
      <el-button @click="onCancelForm('form')">取 消</el-button>
      <el-button type="primary" @click="onSubmitForm('form')" :disabled="formOptions.operation === 'view'">确 定
      </el-button>
    </div>
  </div>
</template>


<script type="text/ecmascript-6">
  export default {
    name: 'roleForm',
    props: {
      formOptions: {
        type: Object,
        default: function () {
          return {
            operation: 'view',
            title: '查看详情',
            maxHeight: 500,
            labelWidth: 100,
            params: {},
            context: {
              featureComponent: {}
            }
          }
        }
      }
    },
    data () {
      return {
        url: 'api/platform/sys/roles',
        entity: {},
        rules: {
          f_name: [
            {required: true, message: '请输入角色名称', trigger: 'blur'},
            {max: 50, message: '长度在50个字符以内', trigger: 'blur'}
          ]
        }
      }
    },
    computed: {
      formBodyStyle () {
        return {
          'max-height': (this.formOptions.maxHeight ? this.formOptions.maxHeight : this.$store.state.layout.dialog.height) + 'px',
          'overflow-y': 'auto'
        }
      },
      labelWidth () {
        return (this.formOptions.labelWidth ? this.formOptions.labelWidth : 150) + 'px'
      },
      featureOptions () {
        return this.formOptions.context.featureComponent.featureOptions
      }
    },
    mounted () {
      window.devMode && console.info('mounted', this.$options.name, this._uid)
      this._init()
    },
    activated () {
      window.devMode && console.info('activated', this.$options.name, this._uid)
    },
    methods: {
      _init () {
        this.query()
      },
      query (params) {
        var vm = this
        if (vm.formOptions.operation === 'add') {
          vm.entity = vm._createEntity()
        } else {
          vm.$http.get(vm.featureOptions.url + '/' + vm.formOptions.params.f_id).then((response) => {
            vm.entity = response.body.success ? response.body.data : {}
          })
        }
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

          if (vm.formOptions.operation === 'add') {
            vm.$http.post(vm.featureOptions.url, vm.entity).then(function (response) {
              vm._submitted(response)
            })
          } else {
            vm.$http.put(vm.featureOptions.url + '/' + vm.formOptions.params.f_id, vm.entity).then(function (response) {
              vm._submitted(response)
            })
          }

          return true
        })
      },
      _createEntity () {
        return {f_is_preset: 2}
      },
      _submitted (response) {
        if (response.body.success) {
          this._closeForm()
          this._refreshGrid()

          this.$emit('submit', {type: this.formOptions.operation, data: response.body.data})
        }
      },
      _closeForm () {
        if (this.formOptions.context.featureComponent.formOptions) {
          this.formOptions.context.featureComponent.formOptions.isShow = false
        }
      },
      _refreshGrid () {
        if (this.formOptions.context.featureComponent.gridOptions) {
          this.formOptions.context.featureComponent.gridOptions.context.params.totalCount = 0
          this.formOptions.context.featureComponent.gridOptions.api.setDatasource(this.formOptions.context.featureComponent.gridOptions.datasource)
        }
      }
    }
  }
</script>
