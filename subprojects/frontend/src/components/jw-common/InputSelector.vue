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
</style>


<template>
  <div class="jw-input-selector">
    <el-select ref="input" class="jw-input-selector-input"
               v-model="ids"
               multiple readonly
               @click.native.stop="openSelector()"
               placeholder="请选择…">
      <el-option
        v-for="item in model"
        :key="item.id"
        :label="item.name"
        :value="item.id">
      </el-option>
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
      value: {type: Object | Array, default () { return {} }},
      disabled: {type: Boolean, default: false},
      clearable: {type: Boolean, default: false},
      size: {type: String, default: null},
      inputOptions: {type: Object, default () { return {} }}
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
          }) : null
        },
        set (ids) {}
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
          this.model = [{id: val[this.options.idFiled], name: val[this.options.nameFiled]}]
        } else {
          this.model = null
        }
      },
      openSelector () {
        if (this.disabled) {
          return
        }

        this.$refs['selector'].open()
      },
      onSelected (selectedRows, cb) {
        console.info('selectedRows', selectedRows)
        let entity = selectedRows[0]
        this._setModel(entity)
        this.$emit('input', entity)
        this.$emit('on-change', entity)
        cb()
      }
    }
  }
</script>
