<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>

<template>
  <div class="jw-form">
    <div class="jw-form-body" :style="formBodyStyle">
      <el-form ref="form" :model="entity" :rules="rules" :inline="true" :label-width="labelWidth">
        <fieldset :disabled="formOptions.operation === 'view'">
          <el-form-item label="URL" prop="f_url">
            <el-input v-model="entity.f_url"></el-input>
          </el-form-item>
          <el-form-item label="Patterns" prop="f_patterns">
            <el-input v-model="entity.f_patterns"></el-input>
          </el-form-item>
          <el-form-item label="Methods" prop="f_methods">
            <el-input v-model="entity.f_methods"></el-input>
          </el-form-item>
          <el-form-item label="Params" prop="f_params">
            <el-input v-model="entity.f_params"></el-input>
          </el-form-item>
          <el-form-item label="Headers" prop="f_headers">
            <el-input v-model="entity.f_headers"></el-input>
          </el-form-item>
          <el-form-item label="Consumes" prop="f_consumes">
            <el-input v-model="entity.f_consumes"></el-input>
          </el-form-item>
          <el-form-item label="Produces" prop="f_produces">
            <el-input v-model="entity.f_produces"></el-input>
          </el-form-item>
          <el-form-item label="Custom" prop="f_custom">
            <el-input v-model="entity.f_custom"></el-input>
          </el-form-item>
          <el-form-item label="Handler Method" prop="f_handler_method">
            <el-input v-model="entity.f_handler_method" style="width: 496px;"></el-input>
          </el-form-item>
          <el-form-item label="记录日志" prop="f_is_log">
            <el-radio-group v-model="entity.f_is_log" style="width: 196px;">
              <el-radio :label="1" disabled>是</el-radio>
              <el-radio :label="2" disabled>否</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="扫描生成" prop="f_is_auto">
            <el-radio-group v-model="entity.f_is_auto" style="width: 196px;">
              <el-radio :label="1" disabled>是</el-radio>
              <el-radio :label="2" disabled>否</el-radio>
            </el-radio-group>
          </el-form-item>
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
  export default {
    name: 'urlForm',
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
        entity: {},
        rules: {}
      }
    },
    computed: {
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
          vm.entity = {}
        } else {
          vm.$http.get(vm.featureOptions.url + '/' + vm.formOptions.params.f_id).then(function (response) {
            var result = response.body
            if (result.success) {
              vm.entity = result.data
            } else {
              vm.entity = {}
            }
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
