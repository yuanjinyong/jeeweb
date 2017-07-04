<style scoped>
</style>

<template>
  <div>
    <div v-if="companies.length === 0" style="margin: auto 20px;color: #fff;font-size: 22px;">
      您还没有有效企业，是否现在<a style="color:#ffff00;cursor: pointer;" @click="onJoinCompany">申请加入企业</a>？
    </div>
    <div v-else>
      <el-dropdown @command="onCommand">
        <el-button>
          {{company.f_name || '我的有效企业列表'}}<i class="el-icon-caret-bottom el-icon--right"></i>
        </el-button>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item v-for="item in companies" :key="item.f_id" :command="'switchCompany_' + item.f_id">
            <i class="fa fa-copyright" style="min-width:14px;"></i> {{item.f_name}}
          </el-dropdown-item>
          <el-dropdown-item command="myCompanies" divided>
            <i class="fa fa-bars" style="min-width:14px;"></i> 我的企业列表
          </el-dropdown-item>
          <el-dropdown-item command="joinCompany" divided>
            <i class="fa fa-plus-square" style="min-width:14px;"></i> 申请加入企业
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>

    <jw-selector ref="selector" :selector-options="selectorOptions">
      <div style="padding: 20px;height: 100%;">
        <ag-grid ref="grid" class="ag-fresh jw-grid" :grid-options="gridOptions"></ag-grid>
      </div>
    </jw-selector>
  </div>
</template>

<script>
  import {
    IndexRendererFramework,
    LikeFilterFramework,
    LikeFloatingFilterComponentFramework
  } from 'components/ag-grid'

  export default {
    name: 'jwCompanyDropdown',
    data () {
      return {
        companies: [],
        company: {},
        selectorOptions: {
          context: {
            name: '待加入的企业',
            featureComponent: this,
            getViewComponent (options) {
              return options.context.featureComponent
            }
          },
          selected (selectedRows, cb) {
            this.options.context.featureComponent.joinCompany(selectedRows, cb)
          }
        },
        gridOptions: this.$grid.buildOptions({
          context: {
            name: '企业信息',
            url: 'api/zkpms/workbench/companies',
            featureComponent: this,
            params: {
              orderBy: 'f_name'
            }
          },
          columnDefs: [{
            headerName: '',
            pinned: 'left',
            checkboxSelection: true,
            cellStyle: {'text-align': 'center'},
            width: 24
          }, {
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
            suppressSorting: false,
            suppressFilter: false,
            filterFramework: LikeFilterFramework,
            floatingFilterComponentFramework: LikeFloatingFilterComponentFramework,
            width: 250
          }, {
            headerName: '创建人',
            field: 'f_creator_name',
            width: 100
          }, {
            headerName: '联系电话',
            field: 'f_telephone',
            tooltipField: 'f_telephone',
            width: 150
          }, {
            headerName: '联系地址',
            field: 'f_address',
            tooltipField: 'f_address',
            width: 200
          }, {
            headerName: '描述',
            field: 'f_desc',
            tooltipField: 'f_desc',
            width: 500
          }]
        })
      }
    },
    computed: {},
    created () {
      this._loadMyCompanies()
    },
    methods: {
      _loadMyCompanies () {
        this.$http.get('api/zkpms/workbench/my/companies', {
          params: {
            f_status: 2, // 只查询审核通过的
            orderBy: 'f_last_login_time desc'
          }
        }).then((response) => {
          this.companies = response.body.success ? response.body.data.items : []
          if (this.companies.length > 0) {
            this._switchCompany(this.companies[0].f_id)
          }
        })
      },
      _switchCompany (companyId) {
        this.$http.post('api/zkpms/workbench/my/companies/' + companyId + '/switch', {}, {showSuccessMessage: false}).then((response) => {
          this.company = response.body.success ? response.body.data : {}
          this.$root.$emit('company-switched', this.company)
        })
      },
      getSelectedRows () {
        return this.gridOptions.api.getSelectedRows()
      },
      clearSelectedRows () {
        this.gridOptions.api.deselectAll()
      },
      onCommand (command) {
        if (command === 'joinCompany') {
          this.onJoinCompany()
        } else if (command === 'myCompanies') {
          let menu = this.findMenuById('GRZX-WDQY')
          if (menu) {
            this.$store.commit('openTab', {path: menu.f_route_path, params: menu})
          }
        } else {
          this._switchCompany(Number.parseInt(command.split('_')[1]))
        }
      },
      onJoinCompany () {
        this.$refs['selector'].open()
      },
      joinCompany (selectedRows, cb) {
        let selectedCompany = selectedRows[0]
        for (let i in this.companies) {
          if (selectedCompany.f_id === this.companies[i].f_id) {
            this.$alert('已经加入过该企业了，无需重复加入！', '错误', {
              confirmButtonText: '关闭',
              type: 'error'
            })
            return
          }
        }

        this.$prompt('请输入申请说明', '申请加入企业', {
          confirmButtonText: '提 交',
          cancelButtonText: '取 消'
        }).then(({value}) => {
          this.$http.post('api/zkpms/workbench/companies/' + selectedCompany.f_id + '/join', {remark: value}, {emulateJSON: true}).then((response) => {
            if (response.body.success) {
              cb()
            }
          })
        }).catch(() => {
        })
      }
    }
  }
</script>
