<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .application-item {
    display: inline-block;
    position: relative;
    margin: 0px 20px 20px 0px;
    width: 60px;
    text-align: center;
    vertical-align: top;
    cursor: pointer;
    color: #1f2d3d;
  }

  .application-item-icon {
    padding: 15px;
    color: #fff;
    background-color: #50bfff;
    border-color: #50bfff;
    border-radius: 4px;
  }

  .application-item:hover {
    color: #50bfff;
  }

  .application-item:hover .application-item-icon {
    background: #73ccff;
    border-color: #73ccff;
  }

  .application-item:active {
    color: #48ace6;
  }

  .application-item:active .application-item-icon {
    background: #48ace6;
    border-color: #48ace6;
  }

  .application-item-icon i {
    font-size: 30px;
    width: 30px;
    height: 30px;
  }

  .application-item-title {
    padding-top: 5px;
  }
</style>

<template>
  <div style="padding: 20px;">
    <el-row>
      <el-col :span="24">
        <div class="application-item"
             v-for="application in applicationList" :key="application.id"
             @click="onApplicationClicked(application)">
          <div class="application-item-icon">
            <i :class="application.icon"></i>
          </div>
          <div class="application-item-title">
            {{application.title}}
          </div>
        </div>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">我的申请</el-col>
      <el-col :span="12">我的审批</el-col>
    </el-row>

    <component ref="detail" :is="currentApplication"></component>
  </div>
</template>


<script>
  export default {
    name: 'home',
    data () {
      return {
        currentApplication: null,
        applicationList: [{
          id: 'ClothesDetail',
          title: '新增衣服',
          icon: 'fa fa-user-secret',
          name: 'diy-partner-clothes',
          path: 'admin/diy/partner/clothes/Detail'
        }, {
          id: 'UserDetail',
          title: '新增用户',
          icon: 'fa fa-user',
          name: 'platform-sys-user',
          path: 'admin/platform/sys/user/Detail'
        }, {
          id: 'more',
          title: '更多',
          icon: 'fa fa-plus'
        }],
        detailOptions: {
          context: {
            featureComponent: this
          }
        }
      }
    },
    created () {
      // console.log('created', this.$options.components)
      this.applicationList.forEach((application) => {
        // this.$options.components[application.id] = r => require.ensure([], () => r(require('views/' + application.path + '.vue')))
        this.$options.components[application.id] = r => require.ensure([], () => r(require('views/' + application.path + '.vue')), 'application')
        // this.$options.components[application.id] = r => require.ensure([], () => r(require('views/' + application.path + '.vue')), application.name)
      })
    },
    methods: {
      onApplicationClicked (application) {
        if (application.id !== 'more') {
          this.detailOptions.operation = 'add'
          this.detailOptions.title = application.title
          this.currentApplication = application.id

          setTimeout(() => {
            // console.log(this.$refs['detail'])
            this.$refs['detail'].open(this.detailOptions)
          }, 100)
          /*
          this.$nextTick(() => {
            console.log(this.$refs['detail'])
            this.$refs['detail'].open(this.detailOptions)
          })
          */
        }
      }
    }
  }
</script>
