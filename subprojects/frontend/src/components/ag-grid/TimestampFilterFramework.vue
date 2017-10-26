<template>
  <el-date-picker ref="input" v-model="value"
                  :type="datePickerOptions.type"
                  :format="datePickerOptions.format"
                  :picker-options="datePickerOptions.pickerOptions"
                  :title="datePickerOptions.placeholder"
                  :placeholder="datePickerOptions.placeholder"
                  @change="onChange">
  </el-date-picker>
</template>

<script>
  import Vue from 'vue'

  export default Vue.extend({
    data () {
      return {
        defaultFilterParams: {
          type: 'between',
          datePickerOptions: {
            type: 'daterange',
            format: 'yyyy-MM-dd',
            placeholder: '请选择日期范围',
            pickerOptions: {
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
      this.params.column.colDef.filterParams = this.$lodash.merge({}, this.defaultFilterParams, this.params.column.colDef.filterParams || {})

      if (this.filterParams.type === 'between') {
        this.value = [null, null]
      }
    },
    methods: {
      isFilterActive () {
        if (this.filterParams.type === 'between') {
          return this.value[0] && this.value[1]
        } else {
          return this.value !== undefined && this.value !== null && this.value !== ''
        }
      },
      doesFilterPass (params) {
        console && console.info('doesFilterPass', this.$options.name, params)
        return this.params.valueGetter(params.node).contains(this.value)
      },
      getModel () {
        let filterValue = null
        if (this.filterParams.type === 'between') {
          if (this.datePickerOptions.type === 'daterange') {
            filterValue = [this.value[0] ? Vue.moment(this.value[0]).startOf('day').format() : null, this.value[1] ? Vue.moment(this.value[1]).endOf('day').format() : null]
          } else {
            filterValue = [this.value[0] ? Vue.moment(this.value[0]).format() : null, this.value[1] ? Vue.moment(this.value[1]).format() : null]
          }
        } else if (this.filterParams.type === 'begin') {
          filterValue = this.value ? Vue.moment(this.value).startOf('day').format() : null
        } else if (this.filterParams.type === 'end') {
          filterValue = this.value ? Vue.moment(this.value).endOf('day').format() : null
        } else {
          filterValue = this.value ? Vue.moment(this.value).format() : null
        }

        return {
          filter: filterValue,
          filterType: 'Timestamp',
          type: this.filterParams.type
        }
      },
      setModel (model) {
        this.floatingModel = model
        let filterValue = model.filter
        if (this.filterParams.type === 'between') {
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
