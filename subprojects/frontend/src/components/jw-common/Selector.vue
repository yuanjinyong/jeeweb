<template>
  <el-dialog v-model="visible" :title="options.title" :top="'30px'" :modal="options.modal"
             :close-on-click-modal="options.closeOnClickModal" :modal-append-to-body="options.modalAppendToBody"
             :size="options.elDialogSize[options.size]" :custom-class="'jw-dialog jw-dialog-selector'">
    <div class="jw-selector" style="overflow-y: auto;" :style="{'max-height': maxFormHeight + 'px'}" v-if="visible">
      <slot></slot>
    </div>

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
          size: 'large', // 可选值：mini（phones 1列）、small（tablets 2列）、middle（desktops 3列）、large（ larger desktops 4列）、full（全屏）
          elDialogSize: {mini: 'tiny', small: 'small', middle: 'small', large: 'large', full: 'full'},
          maxHeight: null,
          params: {},
          selected: null,
          cancelled: null,
          closed: null
        }
      }
    },
    computed: {
      maxFormHeight () {
        return this.options.maxHeight ? (this.options.maxHeight - 59 - 76) : (this.$store.state.layout.window.height - 75 - 30 - 59 - 76 - 30 - 35)
      }
    },
    methods: {
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
      close () { // 供外部调用的接口
        this.visible = false
        this.$emit('closed')
        if (this.options.closed) {
          this.options.closed.call(this)
        }
      },
      open (options) { // 供外部调用的接口
        this.options.title = '请选择' + this.options.context.name
        this.options.params = {}
        this.$lodash.merge(this.options, this.selectorOptions, options)
        this._addDraggable()
        this.visible = true
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
