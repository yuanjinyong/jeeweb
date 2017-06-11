<template>
  <el-dialog v-model="visible" :title="options.title" :modal="options.mode"
             :close-on-click-modal="options.closeOnClickModal" :custom-class="'jw-dialog jw-dialog-' + options.size">
    <div class="jw-form" v-if="visible">
      <div class="jw-form-body" style="overflow-y: auto;" :style="{'max-height': maxFormHeight + 'px'}">
        <el-form ref="form" :model="entity" :rules="rules" :inline="options.inline"
                 :label-width="options.labelWidth+'px'">
          <fieldset :disabled="options.operation === 'view1'">
            <el-form-item label="URL" prop="f_url">
              <el-input class="jw-field-col-2" v-model="entity.f_url"></el-input>
            </el-form-item>
            <el-form-item label="Patterns" prop="f_patterns">
              <el-input class="jw-field-col-2" v-model="entity.f_patterns"></el-input>
            </el-form-item>
            <el-form-item label="Methods" prop="f_methods">
              <el-input class="jw-field-col-1" v-model="entity.f_methods"></el-input>
            </el-form-item>
            <el-form-item label="Params" prop="f_params">
              <el-input class="jw-field-col-1" v-model="entity.f_params"></el-input>
            </el-form-item>
            <el-form-item label="Headers" prop="f_headers">
              <el-input class="jw-field-col-1" v-model="entity.f_headers"></el-input>
            </el-form-item>
            <el-form-item label="Consumes" prop="f_consumes">
              <el-input class="jw-field-col-1" v-model="entity.f_consumes"></el-input>
            </el-form-item>
            <el-form-item label="Produces" prop="f_produces">
              <el-input class="jw-field-col-1" v-model="entity.f_produces"></el-input>
            </el-form-item>
            <el-form-item label="Custom" prop="f_custom">
              <el-input class="jw-field-col-1" v-model="entity.f_custom"></el-input>
            </el-form-item>
            <el-form-item label="Handler Method" prop="f_handler_method">
              <el-input class="jw-field-col-2" v-model="entity.f_handler_method"></el-input>
            </el-form-item>
            <el-form-item label="记录日志" prop="f_is_log">
              <el-radio-group class="jw-field-col-1" v-model="entity.f_is_log">
                <el-radio :label="1" disabled>是</el-radio>
                <el-radio :label="2" disabled>否</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="扫描生成" prop="f_is_auto">
              <el-radio-group class="jw-field-col-1" v-model="entity.f_is_auto">
                <el-radio :label="1" disabled>是</el-radio>
                <el-radio :label="2" disabled>否</el-radio>
              </el-radio-group>
            </el-form-item>
          </fieldset>
        </el-form>
      </div>
    </div>

    <div slot="footer" class="dialog-footer jw-dialog-footer">
      <el-button @click="onCancel">取 消</el-button>
      <el-button type="primary" @click="onSubmit" :disabled="options.operation === 'view'">确 定</el-button>
    </div>
  </el-dialog>
</template>


<script>
  export default {
    name: 'urlDetail',
    props: {
      detailOptions: {
        type: Object,
        default () {
          return {}
        }
      }
    },
    data () {
      return {
        visible: false,
        options: {
          context: {
            name: 'URL',
            url: 'api/platform/sys/urls',
            getGridComponent: null,
            getFeatureComponent: null,
            getPermissions: null
          },
          operation: 'view', // 可选值：add、edit、view、audit
          title: '查看详情',
          mode: true, // 是否为模态对话框
          closeOnClickModal: false, // 点击遮罩层是否关闭对话框
          size: 'small', // 可选值：mini（1列）、small（2列）、normal（3列）、large（4列）、full（全屏）
          inline: true,
          labelWidth: 150, // 单位px
          maxHeight: null,
          params: {}
        },
        titles: {add: '新增', edit: '修改', view: '查看', audit: '审核'},
        entity: {},
        rules: {}
      }
    },
    computed: {
      maxFormHeight () {
        return this.options.maxHeight ? (this.options.maxHeight - 135) : (this.$store.state.layout.window.height - 60)
      }
    },
    mounted () {
      let dialog = this.$el.firstChild
      let dialogHeader = dialog.firstChild
      dialogHeader.style.cursor = 'move'
      dialogHeader.onmousedown = function (mouseDownEvent) {
        mouseDownEvent.preventDefault()

        let minLeft = -dialog.clientWidth / 2 + 40
        let offset = {left: mouseDownEvent.clientX - dialog.offsetLeft, top: mouseDownEvent.clientY - dialog.offsetTop}

        document.onmousemove = function (mouseMoveEvent) {
          let left = mouseMoveEvent.clientX - offset.left
          let top = mouseMoveEvent.clientY - offset.top
          dialog.style.left = (left < minLeft) ? minLeft : left + 'px'
          dialog.style.top = (top < 0 ? 0 : top) + 'px'
        }

        document.onmouseup = function () {
          document.onmousemove = null
          document.onmousedown = null
        }
      }
    },
    methods: {
      close () { // 供外部调用的接口
        this.visible = false
        this.$emit('closed')
      },
      open (options) { // 供外部调用的接口
        this.$lodash.merge(this.options, {title: this.titles[options.operation]}, this.detailOptions, options)
        this._loadEntity()
        this.visible = true
      },
      _loadEntity () {
        if (this.options.operation === 'add') {
          this.entity = this._createEntity()
        } else {
          this.$http.get(this.options.context.url + '/' + this.options.params.f_id).then((response) => {
            this.entity = response.body.success ? response.body.data : {}
          })
        }
      },
      _createEntity () {
        return {}
      },
      onCancel () {
        this.close()
        this.$emit('canceled')
      },
      onSubmit () {
        this.$refs['form'].validate((valid) => {
          if (!valid) {
            return false
          }

          if (this.options.operation === 'add') {
            this.$http.post(this.options.context.url, this.entity).then((response) => {
              this._submitted(response.body)
            })
          } else if (this.options.operation === 'edit') {
            this.$http.put(this.options.context.url + '/' + this.options.params.f_id, this.entity).then((response) => {
              this._submitted(response.body)
            })
          } else if (this.options.operation === 'audit') {
            this.$http.put(this.options.context.url + '/' + this.options.params.f_id + '/audit', this.entity).then((response) => {
              this._submitted(response.body)
            })
          } else {
            this.$emit('submit', {type: this.options.operation, data: this.entity})
          }

          return true
        })
      },
      _submitted (result) {
        if (result.success) {
          let gridComponent = this.options.context.getGridComponent && this.options.context.getGridComponent.call(this, this.options)
          console.warn('gridComponent', gridComponent)
          if (gridComponent) {
//          this.formOptions.context.featureComponent.gridOptions.context.params.totalCount = 0
//          this.formOptions.context.featureComponent.gridOptions.api.setDatasource(this.formOptions.context.featureComponent.gridOptions.datasource)
          }

          this.close()
          this.$emit('submitted', {type: this.options.operation, data: result.data})
        }
      }
    }
  }
</script>
