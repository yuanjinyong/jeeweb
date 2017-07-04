<template>
  <el-dialog v-model="visible" :title="options.title" :top="options.top + 'px'" :modal="options.modal"
             :close-on-click-modal="options.closeOnClickModal" :modal-append-to-body="options.modalAppendToBody"
             :size="options.elDialogSize[options.size]" :custom-class="'jw-dialog jw-dialog-' + options.size">
    <el-collapse-transition>
      <div class="jw-dialog-body" :style="{'max-height': maxBodyHeight + 'px'}" v-if="visible">
        <div class="jw-form">
          <div class="jw-form-body">
            <el-form ref="form" :model="entity" :rules="rules" :inline="options.inline"
                     :label-width="options.labelWidth+'px'">
              <fieldset :disabled="options.operation === 'view'">
                <slot name="fieldset"></slot>
              </fieldset>

              <slot></slot>
            </el-form>
          </div>

          <slot name="other"></slot>
        </div>
      </div>
    </el-collapse-transition>

    <div slot="footer" class="dialog-footer jw-dialog-footer">
      <slot name="buttons"></slot>
      <slot name="defaultButtons">
        <el-button @click="onCancel">取 消</el-button>
        <el-button type="primary" @click="onSubmit" :disabled="options.operation === 'view'">确 定</el-button>
      </slot>
    </div>
  </el-dialog>
</template>


<script>
  import Vue from 'vue'

  export default {
    name: 'jwForm',
    props: {
      formOptions: {
        type: Object,
        default () {
          return {}
        }
      },
      entity: {
        type: Object,
        required: true
      },
      rules: {
        type: Object,
        default () {
          return {}
        }
      }
    },
    data () {
      return {
        visible: false,
        bus: new Vue(),
        titles: {add: '新增', edit: '修改', view: '查看', audit: '审核'},
        options: {
          context: {
            name: '',
            url: null,
            getGridComponent: null,
            getFeatureComponent: null,
            getPermissions: null
          },
          operation: 'view', // 可选值：add、edit、view、audit
          title: '查看详情',
          draggable: true, // 是否可以拖动
          modal: true, // 是否为模态对话框
          closeOnClickModal: false, // 点击遮罩层是否关闭对话框
          modalAppendToBody: true, // 遮罩层是否插入至 body 元素上，若为 false，则遮罩层会插入至 Dialog 的父元素上
          top: 75, //
          size: 'small', // 可选值：mini（phones 1列）、small（tablets 2列）、middle（desktops 3列）、large（ larger desktops 4列）、full（全屏）
          elDialogSize: {mini: 'tiny', small: 'small', middle: 'small', large: 'large', full: 'full'},
          inline: true,
          labelWidth: 150, // 单位px
          maxHeight: null,
          params: {},
          queryString: {},
          beforeOpen: null,
          opened: null,
          createEntity: null,
          loadRemoteEntity: null,
          loadLocalEntity: null,
          submitEntity: null,
          submitted: null,
          cancelled: null,
          closed: null
        }
      }
    },
    computed: {
      maxBodyHeight () {
        return this.options.maxHeight ? (this.options.maxHeight - 59 - 76) : (this.$store.state.layout.window.height - this.options.top - 59 - 76 - 35)
      }
    },
    methods: {
      close () { // 供外部调用的接口
        this.visible = false
        this.$emit('closed')
        if (this.options.closed) {
          this.options.closed.call(this)
        }
      },
      open (options) { // 供外部调用的接口
        this.options.params = {}
        this.$lodash.merge(this.options, {title: this.titles[options.operation]}, this.formOptions, options)
        if (this.$store.state.layout.window.width < 768) {
          this.options.size = 'mini'
        }

        if (this.options.beforeOpen) {
          this.options.beforeOpen.call(this, this.options, () => {
            this._open()
          })
        } else {
          this._open()
        }
      },
      _open () {
        this._loadEntity()
        this._addDraggable()
        this.visible = true
        if (this.options.opened) {
          this.options.opened.call(this, this.options)
        }
      },
      _addDraggable () {
        if (!this.options.draggable || this.canDraggable) {
          return
        }

        let dialog = this.$el.firstChild
        let dialogHeader = dialog.firstChild
        dialogHeader.style.cursor = 'move'
        dialogHeader.onmousedown = function (mouseDownEvent) {
          mouseDownEvent.preventDefault()

          let minLeft = -dialog.clientWidth / 2 + 40
          let offset = {
            left: mouseDownEvent.clientX - dialog.offsetLeft,
            top: mouseDownEvent.clientY - dialog.offsetTop
          }

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

        this.canDraggable = true
      },
      _loadEntity () {
        if (this.options.operation === 'add') {
          if (this.options.createEntity) {
            this.options.createEntity.call(this, this.options, (entity) => {
              this.bus.$emit('loaded-entity', entity)
            })
          } else {
            this.bus.$emit('loaded-entity', {})
          }
        } else {
          if (this.options.context.url) {
            if (this.options.loadRemoteEntity) {
              this.options.loadRemoteEntity.call(this, this.options, (entity) => {
                this.bus.$emit('loaded-entity', entity)
              })
            } else {
              this.$http.get(this.options.context.url + '/' + this.options.params.f_id, {params: this.options.queryString}).then((response) => {
                this.bus.$emit('loaded-entity', response.body.success ? response.body.data : {})
              })
            }
          } else {
            if (this.options.loadLocalEntity) {
              this.options.loadLocalEntity.call(this, this.options, (entity) => {
                this.bus.$emit('loaded-entity', entity)
              })
            } else {
              this.bus.$emit('loaded-entity', this.$lodash.cloneDeep(this.options.params))
            }
          }
        }
      },
      onCancel () {
        this.close()
        this.$emit('cancelled')
        if (this.options.cancelled) {
          this.options.cancelled.call(this)
        }
      },
      onSubmit () {
        this.$refs['form'].validate((valid) => {
          if (!valid) {
            return false
          }

          if (this.options.submitEntity) {
            this.options.submitEntity.call(this, this.options, (entity) => {
              this._submitted({success: true, data: entity})
            })
            return true
          }

          if (!this.options.context.url) {
            this._submitted({success: true, data: this.entity})
            return true
          }

          if (this.options.operation === 'add') {
            this.$http.post(this.options.context.url, this.entity, {params: this.options.queryString}).then((response) => {
              this._submitted(response.body)
            })
          } else if (this.options.operation === 'edit') {
            this.$http.put(this.options.context.url + '/' + this.options.params.f_id, this.entity, {params: this.options.queryString}).then((response) => {
              this._submitted(response.body)
            })
          } else if (this.options.operation === 'audit') {
            this.$http.put(this.options.context.url + '/' + this.options.params.f_id + '/audit', this.entity, {params: this.options.queryString}).then((response) => {
              this._submitted(response.body)
            })
          } else {
            this._submitted({success: true, data: this.entity})
          }

          return true
        })
      },
      _submitted (result) {
        if (result.success) {
          this._refreshGrid()
          this.close()
          this.$emit('submitted', {type: this.options.operation, data: result.data})
          if (this.options.submitted) {
            this.options.submitted.call(this, {operation: this.options.operation, data: result.data})
          }
        }
      },
      _refreshGrid () {
        let gridComponent = this.options.context.getGridComponent && this.options.context.getGridComponent.call(this, this.options)
        if (gridComponent) {
          let gridOptions = gridComponent.gridOptions
          if (gridOptions.rowModelType === 'normal') {
            gridOptions.getRows4Normal()
          } else {
            gridOptions.context.params.totalCount = 0
            gridOptions.api.setDatasource(gridOptions.datasource)
          }
        }
      }
    }
  }
</script>
