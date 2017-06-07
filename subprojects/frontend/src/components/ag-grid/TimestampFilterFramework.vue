<style scoped>
</style>

<template>
  <el-date-picker
    :ref="'input'"
    v-model="value"
    :type="datePickerOptions.type"
    :format="datePickerOptions.format"
    :picker-options="datePickerOptions.pickerOptions"
    @change="onChange"
    placeholder="请选择时间范围">
  </el-date-picker>
</template>

<script type="text/ecmascript-6">
  import Vue from 'vue'

  export default Vue.extend({
    data () {
      return {
        floatingModel: null,
        value: null
      }
    },
    computed: {
      filterParams () {
        return this.params.column.colDef.filterParams
      },
      datePickerOptions () {
        return this.params.column.colDef.filterParams.datePickerOptions
      }
    },
    created () {
      if (!this.params.column.colDef.filterParams) {
        this.params.column.colDef.filterParams = {}
      }
      if (!this.params.column.colDef.filterParams.datePickerOptions) {
        this.params.column.colDef.filterParams.datePickerOptions = {}
      }
      if (!this.params.column.colDef.filterParams.datePickerOptions.type) {
        this.params.column.colDef.filterParams.datePickerOptions.type = 'daterange'
      }
      if (!this.params.column.colDef.filterParams.datePickerOptions.format) {
        this.params.column.colDef.filterParams.datePickerOptions.format = this.params.column.colDef.filterParams.datePickerOptions.type === 'daterange' ? 'yyyy-MM-dd' : 'yyyy-MM-dd HH:mm:ss'
      }
      if (!this.params.column.colDef.filterParams.datePickerOptions.pickerOptions) {
        this.params.column.colDef.filterParams.datePickerOptions.pickerOptions = {
          shortcuts: this.$jw.buildPickerOptionsShortcuts([
            'today',
            'tomorrow',
            'currentWeek',
            'lastWeek',
            'currentMonth',
            'lastMonth',
            // 'currentQuarter',
            // 'lastQuarter',
            'currentYear',
            'lastYear',
            // 'latestWeek',
            // 'latestMonth',
            // 'latestQuarter',
            'latestYear'
          ])
        }
      }
    },
    methods: {
      isFilterActive () {
        if (this.datePickerOptions.type === 'daterange' || this.datePickerOptions.type === 'datetimerange') {
          return this.value !== undefined && this.value !== null && this.value.length > 1 && this.value[0] && this.value[1]
        } else {
          return this.value !== undefined && this.value !== null && this.value !== ''
        }
      },
      doesFilterPass (params) {
        console && console.info('doesFilterPass', this.$options.name, params)
        return this.params.valueGetter(params.node).contains(this.value)
      },
      getModel () {
        var filterValue = null
        if (this.value && this.value.length > 0) {
          if (this.datePickerOptions.type === 'daterange') {
            filterValue = [this.value[0] ? Vue.moment(this.value[0]).startOf('day').format() : null, this.value[1] ? Vue.moment(this.value[1]).endOf('day').format() : null]
          } else {
            filterValue = [this.value[0] ? Vue.moment(this.value[0]).format() : null, this.value[1] ? Vue.moment(this.value[1]).format() : null]
          }
        } else {
          filterValue = this.value ? Vue.moment(this.value).format() : null
        }
        return {
          filter: filterValue,
          filterType: 'Timestamp',
          type: this.filterParams.type ? this.filterParams.type : (this.datePickerOptions.type === 'daterange' || this.datePickerOptions.type === 'datetimerange' ? 'between' : null)
        }
      },
      setModel (model) {
        this.floatingModel = model
        var filterValue = model ? model.filter : null
        if (filterValue && filterValue.length > 0) {
          this.value = [filterValue[0] ? Vue.moment(filterValue[0]).toDate() : null, filterValue[1] ? Vue.moment(filterValue[1]).toDate() : null]
        } else {
          this.value = filterValue ? Vue.moment(filterValue).toDate() : null
        }
      },
      onChange (val) { // 这里的val是格式化后的字符串，即和看到的输入框中的文本一致
        if (this.floatingModel) {
          this.floatingModel = null
        } else {
          this.params.filterChangedCallback()
        }
      }
    }
  })
</script>
