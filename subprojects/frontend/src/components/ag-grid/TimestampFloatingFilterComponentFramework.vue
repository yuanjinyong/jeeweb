<template>
  <div class="jw-floating-filter-cell">
    <el-date-picker ref="input" v-model="value" size="small"
                    :editable="false"
                    :type="datePickerOptions.type"
                    :format="datePickerOptions.format"
                    :picker-options="datePickerOptions.pickerOptions"
                    :title="datePickerOptions.placeholder"
                    :placeholder="datePickerOptions.placeholder"
                    @change="onChange">
    </el-date-picker>
  </div>
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
        defaultFloatingFilterComponentParams: {
          suppressFilterButton: true
        },
        parentModel: null,
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
      this.params.column.colDef.floatingFilterComponentParams = this.$lodash.merge({}, this.defaultFloatingFilterComponentParams, this.params.column.colDef.floatingFilterComponentParams || {})

      if (this.filterParams.type === 'between') {
        this.value = [null, null]
      }
    },
    methods: {
      onParentModelChanged (parentModel) {
        if (this.filterParams.type === 'between') {
          let filterValue = parentModel ? parentModel.filter : [null, null]
          let newValue = [null, null]
          if (this.datePickerOptions.type === 'daterange') {
            newValue = [filterValue[0] ? Vue.moment(filterValue[0]).startOf('day').toDate() : null, filterValue[1] ? Vue.moment(filterValue[1]).startOf('day').toDate() : null]
          } else {
            newValue = [filterValue[0] ? Vue.moment(filterValue[0]).toDate() : null, filterValue[1] ? Vue.moment(filterValue[1]).toDate() : null]
          }
          if (Vue.jw.dateEqual(this.value[0], newValue[0]) && Vue.jw.dateEqual(this.value[1], newValue[1])) {
            this.parentModel = null
          } else {
            this.parentModel = parentModel
            this.value = newValue
          }
        } else {
          let filterValue = parentModel ? parentModel.filter : null
          let newValue = filterValue ? Vue.moment(filterValue).toDate() : null
          if (Vue.jw.dateEqual(this.value, newValue)) {
            this.parentModel = null
          } else {
            this.parentModel = parentModel
            this.value = newValue
          }
        }
      },
      onChange (val) {
        this.value = this.value || [null, null]
        if (this.parentModel) {
          this.parentModel = null
          return
        }

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

        this.params.onFloatingFilterChanged({filter: filterValue})
      }
    }
  })
</script>
