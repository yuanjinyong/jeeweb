<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>

<template>
  <div class="jw-form">
    <div class="jw-form-body" style="max-height: 500px;overflow-y: auto;">
      <el-form label-width="100px" ref="form" :inline="true" :model="entity" :rules="rules">
        <fieldset :disabled="params.operation === 'view'">
          <el-form-item label="名称" prop="f_name">
            <el-input v-model="entity.f_name"></el-input>
          </el-form-item>
          <el-form-item label="系统预置" prop="f_is_preset">
            <el-radio-group v-model="entity.f_is_preset">
              <el-radio :label="1" disabled>是</el-radio>
              <el-radio :label="2" disabled>否</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="描述" prop="f_desc">
            <el-input v-model="entity.f_desc" type="textarea" autosize style="width: 496px;"></el-input>
          </el-form-item>
          <el-form-item label="备注" prop="f_remark">
            <el-input v-model="entity.f_remark" type="textarea" autosize style="width: 496px;"></el-input>
          </el-form-item>
        </fieldset>
      </el-form>
    </div>

    <div class="jw-form-footer" style="text-align: right;">
      <el-button @click="onCancelForm('form')">取 消</el-button>
      <el-button type="primary" @click="onSubmitForm('form')" :disabled="params.operation === 'view'">确 定</el-button>
    </div>
  </div>
</template>


<script type="text/ecmascript-6">
  export default {
    name: 'roleForm',
    props: {
      params: {
        type: Object,
        default: function () {
          return {
            operation: 'view',
            entity: {}
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
        if (vm.params.operation === 'add') {
          vm.entity = {
            f_is_preset: 2
          }
        } else {
          vm.$http.get(vm.url + '/' + vm.params.entity.f_id).then(function (response) {
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
        this.$emit('cancel')
      },
      onSubmitForm (formName) {
        var vm = this
        vm.$refs[formName].validate(function (valid) {
          if (!valid) {
            return false
          }

          if (vm.params.operation === 'add') {
            vm.$http.post(vm.url, vm.entity).then(function (response) {
              if (response.body.success) {
                this.$emit('submit')
              }
            })
          } else {
            vm.$http.put(vm.url + '/' + vm.params.entity.f_id, vm.entity).then(function (response) {
              if (response.body.success) {
                this.$emit('submit')
              }
            })
          }

          return true
        })
      }
    }
  }
</script>
