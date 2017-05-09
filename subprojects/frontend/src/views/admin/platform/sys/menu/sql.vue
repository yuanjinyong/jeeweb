<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>

<template>
  <div class="jw-form">
    <div class="jw-form-body" style="max-height: 500px;overflow-y: auto;">
      <el-form ref="form" :model="entity">
        <fieldset>
          <el-form-item prop="sql">
            <el-input v-model="entity.sql" type="textarea" autosize style="width: 100%;"></el-input>
          </el-form-item>
        </fieldset>
      </el-form>
    </div>

    <div class="jw-form-footer" style="text-align: right;">
      <el-button @click="onCancelForm('form')">取 消</el-button>
      <el-button type="primary" @click="onSubmitForm('form')">确 定</el-button>
    </div>
  </div>
</template>


<script type="text/ecmascript-6">
  export default {
    name: 'sqlForm',
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
        url: 'api/platform/sys/menus',
        entity: {sql: null}
      }
    },
    mounted () {
      window.devMode && console.info('mounted', this.$options.name, this._uid)
      this._init()
    },
    methods: {
      _init () {
        this.query()
      },
      query (params) {
        var vm = this
        vm.$http.get(vm.url + '/' + vm.params.entity.f_id + '/sql').then(function (response) {
          var data = response.body.success ? response.body.data : {}
          console.debug('data', data)
          var menuStr = 'menuStr'
          var menuUrlStr = 'menuUrlStr'
          var roleMenuStr = 'roleMenuStr'
          var sql = ''
          sql += '/*Data for the table `t_sys_menu` */\n\n'
          sql += menuStr + '\n\n\n'
          sql += '/*Data for the table `t_sys_menu_url` */\n\n'
          sql += menuUrlStr + '\n\n\n'
          sql += '/*Data for the table `t_sys_role_menu` */\n\n'
          sql += roleMenuStr + '\n\n\n'

          vm.entity.sql = sql
          console.debug('sql', vm.entity.sql)
        })
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

          vm.$http.post(vm.url, vm.entity, {emulateJSON: true}).then(function (response) {
            if (response.body.success) {
              this.$emit('submit')
            }
          })

          return true
        })
      }
    }
  }
</script>
