<template>
  <div :style="contentStyle">
    <ag-grid ref="grid" class="ag-fresh jw-grid" :grid-options="gridOptions"></ag-grid>
  </div>
</template>


<script>
  import {ViewlMixin} from 'mixins'
  import {
    DictFilterFramework,
    DictFloatingFilterComponentFramework,
    DictRendererFramework,
    LikeFilterFramework,
    LikeFloatingFilterComponentFramework,
    IndexRendererFramework,
    TimestampRendererFramework
  } from 'components/ag-grid'

  export default {
    name: 'myCompanyView',
    mixins: [ViewlMixin],
    data () {
      return {
        gridOptions: this.$grid.buildOptions({
          context: {
            name: '企业信息',
            url: 'api/zkpms/workbench/my/companies',
            featureComponent: this,
            params: {
              orderBy: 'f_joined_time desc'
            }
          }
        })
      }
    },
    created () {
      this.gridOptions.columnDefs = [{
        headerName: '#',
        pinned: 'left',
        cellStyle: {'text-align': 'right'},
        cellRendererFramework: IndexRendererFramework,
        width: 38
      }, {
        headerName: '名称',
        field: 'f_name',
        tooltipField: 'f_name',
        pinned: 'left',
        // suppressSorting: false,
        suppressFilter: false,
        filterFramework: LikeFilterFramework,
        floatingFilterComponentFramework: LikeFloatingFilterComponentFramework,
        width: 250
      }, {
        headerName: '申请状态',
        field: 'f_joined_status',
        colId: 'f_status',
        suppressFilter: false,
        filterFramework: DictFilterFramework,
        floatingFilterComponentFramework: DictFloatingFilterComponentFramework,
        cellStyle: {'text-align': 'center'},
        cellRendererFramework: DictRendererFramework,
        cellRendererParams: {dict: 'CompayUserStatus'},
        width: 84
      }, {
        headerName: '申请加入时间',
        field: 'f_joined_time',
        cellStyle: {'text-align': 'center'},
        cellRendererFramework: TimestampRendererFramework,
        width: 140
      }, {
        headerName: '申请说明',
        field: 'f_joined_remark',
        tooltipField: 'f_joined_remark',
        width: 200
      }, {
        headerName: '最近登录时间',
        field: 'f_last_login_time',
        cellStyle: {'text-align': 'center'},
        cellRendererFramework: TimestampRendererFramework,
        width: 140
      }, {
        headerName: '审核人',
        field: 'f_joined_auditor_name',
        width: 100
      }, {
        headerName: '审核时间',
        field: 'f_joined_audited_time',
        cellStyle: {'text-align': 'center'},
        cellRendererFramework: TimestampRendererFramework,
        width: 140
      }, {
        headerName: '审核意见',
        field: 'f_joined_audited_comments',
        tooltipField: 'f_joined_audited_comments',
        width: 200
      }, {
        headerName: '企业创建人',
        field: 'f_creator_name',
        width: 100
      }, {
        headerName: '企业联系电话',
        field: 'f_telephone',
        tooltipField: 'f_telephone',
        width: 150
      }, {
        headerName: '企业描述',
        field: 'f_desc',
        tooltipField: 'f_desc',
        width: 200
      }]
    }
  }
</script>
