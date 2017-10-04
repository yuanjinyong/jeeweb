<template>
  <el-select v-model="model"
             :clearable="clearable"
             :size="size"
             :disabled="disabled"
             @change="onChange">
    <el-option v-for="item in dictItems"
               :key="item.f_item_code"
               :value="item.f_item_code"
               :label="item.f_item_text"
               :disabled="isDisabled(item)">
      {{item.f_item_text}}
    </el-option>
  </el-select>
</template>


<script>
  import Vue from 'vue'

  export default {
    name: 'jwDict',
    props: {
      value: {type: String | Number, default: null},
      disabled: {type: Boolean, default: false},
      clearable: {type: Boolean, default: false},
      size: {type: String, default: null},
      dictCode: {type: String, required: true},
      dictOptions: {
        type: Object,
        default () {
          return {}
        }
      }
    },
    data () {
      return {
        model: null,
        options: {}
      }
    },
    computed: {
      dictItems () {
        return Vue.store.state.dicts[this.dictCode]
      }
    },
    watch: {
      value (newval, oldval) {
        if (newval || newval === 0) {
          this.model = newval + ''
        } else {
          this.model = null
        }
      }
    },
    created () {
      this.$lodash.merge(this.options, this.dictOptions)
      if (this.value || this.value === 0) {
        this.model = this.value + ''
      } else {
        this.model = null
      }
    },
    methods: {
      isDisabled (item) {
        if (this.options.isDisabled) {
          return this.options.isDisabled.call(this, item)
        }

        return false
      },
      onChange (value) {
        if (typeof this.value === 'string') {
          this.$emit('input', this.model)
          this.$emit('on-change', this.model)
        } else {
          this.$emit('input', this.model * 1)
          this.$emit('on-change', this.model * 1)
        }
      }
    }
  }
</script>
