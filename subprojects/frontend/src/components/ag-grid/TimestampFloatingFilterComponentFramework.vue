<style scoped>
</style>

<template>
  <div class="jw-floating-filter-cell">
    <el-date-picker
      :ref="'input'"
      v-model="value" size="small"
      :type="datePickerOptions.type"
      :format="datePickerOptions.format"
      :picker-options="datePickerOptions.pickerOptions"
      :editable="false"
      @change="onChange"
      placeholder="请选择时间范围">
    </el-date-picker>
  </div>
</template>

<script type="text/ecmascript-6">
  import Vue from 'vue'

  export default Vue.extend({
    data () {
      return {
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

      if (!this.params.column.colDef.floatingFilterComponentParams) {
        this.params.column.colDef.floatingFilterComponentParams = {}
      }
      if (this.params.column.colDef.floatingFilterComponentParams.suppressFilterButton === undefined) {
        this.params.column.colDef.floatingFilterComponentParams.suppressFilterButton = true
      }
    },
    methods: {
      onParentModelChanged (parentModel) {
        var filterValue = parentModel ? parentModel.filter : null
        if (filterValue && filterValue.length > 0) {
          this.value = [filterValue[0] ? Vue.moment(filterValue[0]).toDate() : null, filterValue[1] ? Vue.moment(filterValue[1]).toDate() : null]
        } else {
          this.value = filterValue ? Vue.moment(filterValue).toDate() : null
        }
      },
      onChange (val) {
        var filterValue = null
        if (this.value.length > 0) {
          if (this.datePickerOptions.type === 'daterange') {
            filterValue = [this.value[0] ? Vue.moment(this.value[0]).startOf('day').format() : null, this.value[1] ? Vue.moment(this.value[1]).endOf('day').format() : null]
          } else {
            filterValue = [this.value[0] ? Vue.moment(this.value[0]).format() : null, this.value[1] ? Vue.moment(this.value[1]).format() : null]
          }
        } else {
          filterValue = this.value ? Vue.moment(this.value).format() : null
        }
        this.params.onFloatingFilterChanged({filter: filterValue})
      }
    }
  })
</script>
