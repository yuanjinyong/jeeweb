<style>
  .jw-input-selector-input.el-select .el-input .el-input__icon {
    transform: translateY(-50%);
  }

  .jw-input-selector-input.el-select .el-input .el-input__icon.is-reverse {
    transform: translateY(-50%);
  }

  .jw-input-selector-input .el-icon-caret-top:before {
    content: "\E61D";
  }

  .jw-input-selector-popper {
    display: none;
  }
</style>


<template>
  <div class="jw-input-selector">
    <el-select ref="input" class="jw-input-selector-input" popper-class="jw-input-selector-popper"
               v-model="ids" :size="size" multiple readonly placeholder="请选择…"
               @visible-change="openSelector" @remove-tag="onRemoveTag">
      <el-option v-for="item in model" :key="item.id" :label="item.name" :value="item.id"></el-option>
    </el-select>

    <jw-selector ref="selector" class="jw-input-selector-selector" :selector-options="options.selectorOptions">
      <slot></slot>
    </jw-selector>
  </div>
</template>


<script>
  export default {
    name: 'jwInputSelector',
    props: {
      value: {
        type: Object | Array,
        default () {
          return {f_id: null, f_name: null}
        }
      },
      disabled: {type: Boolean, default: false},
      size: {type: String, default: null},
      inputOptions: {
        type: Object,
        default () {
          return {}
        }
      }
    },
    data () {
      return {
        model: null,
        options: {
          idFiled: 'f_id',
          nameFiled: 'f_name',
          selectorOptions: {
            context: {
              inputSelectorComponent: this
            },
            selected (selectedRows, cb) {
              this.options.context.inputSelectorComponent.onSelected(selectedRows, cb)
            }
          }
        }
      }
    },
    computed: {
      ids: {
        get () {
          return this.model ? this.model.map((item, idx) => {
            return item.id
          }) : []
        },
        set (ids) {
        }
      }
    },
    watch: {
      value (newval, oldval) {
        this._setModel(newval)
      }
    },
    created () {
      this.$lodash.merge(this.options, this.inputOptions)
      this._setModel(this.value)
    },
    methods: {
      _setModel (val) {
        if (val) {
          let id = val[this.options.idFiled]
          if (id || id === 0) {
            this.model = [{id: id, name: val[this.options.nameFiled]}]
            return
          }
        }

        this.model = null
      },
      openSelector (visible) {
        if (visible) {
          this.$refs['selector'].open()
        }
      },
      onSelected (selectedRows, cb) {
        let entity = selectedRows[0]
        this._setModel(entity)
        this.$emit('input', entity)
        this.$emit('on-change', entity)
        cb()
      },
      onRemoveTag (tagComponent) {
        this.model = null

        let entity = {}
        entity[this.options.idFiled] = null
        entity[this.options.nameFiled] = null
        this.$emit('input', entity)

        entity[this.options.idFiled] = tagComponent.value
        entity[this.options.nameFiled] = tagComponent.label
        this.$emit('on-remove', entity)
      }
    }
  }
</script>
