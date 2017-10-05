<template>
  <el-dialog v-model="visible"
             :title="options.title"
             :top="'30px'"
             :modal="options.modal"
             :modal-append-to-body="options.modalAppendToBody"
             :close-on-click-modal="options.closeOnClickModal"
             :size="options.elDialogSize[options.size]"
             :custom-class="'jw-dialog jw-dialog-selector'">
    <el-collapse-transition>
      <div class="jw-dialog-body" :style="{'max-height': maxBodyHeight + 'px'}" v-if="visible">
        <div :class="'jw-selector jw-selector-' + options.size" :style="{'height': options.height + 'px'}">
          <slot></slot>
        </div>
      </div>
    </el-collapse-transition>

    <div slot="footer" class="dialog-footer jw-dialog-footer">
      <slot name="buttons"></slot>
      <slot name="defaultButtons">
        <el-button @click="onCancel">取 消</el-button>
        <el-button type="primary" @click="onSelect">选 择</el-button>
      </slot>
    </div>
  </el-dialog>
</template>


<script>
  import Vue from 'vue'

  export default {
    name: 'jwSelector',
    props: {
      selectorOptions: {
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
        options: {
          context: {
            name: '',
            getViewComponent: null
          },
          title: '请选择',
          draggable: true, // 是否可以拖动
          modal: false, // 是否为模态对话框
          closeOnClickModal: false, // 点击遮罩层是否关闭对话框
          modalAppendToBody: true, // 遮罩层是否插入至 body 元素上，若为 false，则遮罩层会插入至 Dialog 的父元素上
          size: 'small', // 可选值：mini（phones 1列）、small（tablets 2列）、middle（desktops 3列）、large（ larger desktops 4列）、full（全屏）
          elDialogSize: {mini: 'tiny', small: 'small', middle: 'small', large: 'large', full: 'full'},
          height: 429, // 默认10行的高度
          params: {},
          beforeOpen: null,
          opened: null,
          selected: null,
          cancelled: null,
          closed: null
        }
      }
    },
    computed: {
      maxBodyHeight () {
        return this.$store.state.layout.window.height - 80 - 30 - 56 - 56 - 30 - 35
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
        if (this.selectorOptions.context && this.selectorOptions.context.name) {
          this.options.title = '请选择' + this.selectorOptions.context.name
        }
        this.options.params = {}
        this.$lodash.merge(this.options, this.selectorOptions, options)

        if (this.options.beforeOpen) {
          this.options.beforeOpen.call(this, this.options, () => {
            this._open()
          })
        } else {
          this._open()
        }
      },
      _open () {
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
      onCancel () {
        this.close()
        this.$emit('cancelled')
        if (this.options.cancelled) {
          this.options.cancelled.call(this)
        }
      },
      onSelect () {
        if (this.options.selected) {
          let selectedRows = null
          let viewComponent = this.options.context.getViewComponent && this.options.context.getViewComponent.call(this, this.options)
          if (viewComponent && viewComponent.getSelectedRows) {
            selectedRows = viewComponent.getSelectedRows()
            if (!selectedRows || selectedRows.length === 0) {
              this.$alert('请先选择一条记录！', '错误', {
                confirmButtonText: '关闭',
                type: 'error'
              })
              return
            }
          }

          this.options.selected.call(this, selectedRows, (close) => {
            if (close === undefined || close === true) {
              if (viewComponent && viewComponent.clearSelectedRows) {
                selectedRows = viewComponent.clearSelectedRows()
              }
              this.close()
            }
          })
        }
      }
    }
  }
</script>
