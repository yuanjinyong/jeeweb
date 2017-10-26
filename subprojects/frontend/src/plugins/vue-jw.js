import Vue from 'vue'

var VueJw = {
  vm: null,
  dateEqual (date1, date2) {
    if ((date1 === null && date2 === null) || (date1 && date2 && date1.getTime() === date2.getTime())) {
      return true
    }
    return false
  },
  buildPickerOptionsShortcuts (shortcutIds) {
    var shortcuts = {
      today: {
        text: '今天',
        onClick (picker) {
          picker.$emit('pick', [this.$moment().startOf('day').toDate(), this.$moment().endOf('day').toDate()])
        }
      },
      tomorrow: {
        text: '昨天',
        onClick (picker) {
          var tomorrow = this.$moment().subtract(1, 'days')
          picker.$emit('pick', [tomorrow.startOf('day').toDate(), tomorrow.endOf('day').toDate()])
        }
      },
      currentWeek: {
        text: '本周',
        onClick (picker) {
          picker.$emit('pick', [this.$moment().startOf('week').toDate(), this.$moment().endOf('day').toDate()])
        }
      },
      lastWeek: {
        text: '上周',
        onClick (picker) {
          var lastWeek = this.$moment().subtract(1, 'weeks')
          picker.$emit('pick', [lastWeek.startOf('week').toDate(), lastWeek.endOf('week').toDate()
          ])
        }
      },
      currentMonth: {
        text: '本月',
        onClick (picker) {
          picker.$emit('pick', [this.$moment().startOf('month').toDate(), this.$moment().endOf('day').toDate()])
        }
      },
      lastMonth: {
        text: '上月',
        onClick (picker) {
          var lastMonth = this.$moment().subtract(1, 'months')
          picker.$emit('pick', [lastMonth.startOf('month').toDate(), lastMonth.endOf('month').toDate()
          ])
        }
      },
      currentQuarter: {
        text: '本季',
        onClick (picker) {
          picker.$emit('pick', [this.$moment().startOf('quarter').toDate(), this.$moment().endOf('day').toDate()])
        }
      },
      lastQuarter: {
        text: '上季',
        onClick (picker) {
          var lastQuarter = this.$moment().subtract(1, 'quarters')
          picker.$emit('pick', [lastQuarter.startOf('quarter').toDate(), lastQuarter.endOf('quarter').toDate()
          ])
        }
      },
      currentYear: {
        text: '今年',
        onClick (picker) {
          picker.$emit('pick', [this.$moment().startOf('year').toDate(), this.$moment().endOf('day').toDate()])
        }
      },
      lastYear: {
        text: '去年',
        onClick (picker) {
          var lastYear = this.$moment().subtract(1, 'years')
          picker.$emit('pick', [lastYear.startOf('year').toDate(), lastYear.endOf('year').toDate()
          ])
        }
      },
      latestWeek: {
        text: '最近一周',
        onClick (picker) {
          picker.$emit('pick', [this.$moment().subtract(6, 'days').startOf('day').toDate(), this.$moment().endOf('day').toDate()])
        }
      },
      latestMonth: {
        text: '最近一个月',
        onClick (picker) {
          picker.$emit('pick', [this.$moment().subtract(1, 'months').startOf('day').toDate(), this.$moment().endOf('day').toDate()])
        }
      },
      latestQuarter: {
        text: '最近三个月',
        onClick (picker) {
          picker.$emit('pick', [this.$moment().subtract(3, 'months').startOf('day').toDate(), this.$moment().endOf('day').toDate()])
        }
      },
      latestYear: {
        text: '最近一年',
        onClick (picker) {
          picker.$emit('pick', [this.$moment().subtract(1, 'years').startOf('day').toDate(), this.$moment().endOf('day').toDate()])
        }
      }
    }

    var pickerOptionsShortcuts = []
    shortcutIds.forEach((shortcutId) => {
      var shortcut = shortcuts[shortcutId]
      if (shortcut) {
        shortcut.$moment = this.vm.moment
        pickerOptionsShortcuts.push(shortcut)
      }
    })

    return pickerOptionsShortcuts
  },
  download (url, params) {
    Vue.http.get(url, {
      responseType: 'blob',
      params: params
    }).then((response) => {
      let downloadLink = document.getElementById('____downloadLink')
      if (!downloadLink) {
        downloadLink = document.createElement('a')
        document.body.appendChild(downloadLink)

        downloadLink.setAttribute('id', '____downloadLink')
        downloadLink.setAttribute('href', '')
        downloadLink.setAttribute('download', '')
      }

      let url = (window.URL || window.webkitURL).createObjectURL(response.bodyBlob) // 创建对象超链接
      downloadLink.setAttribute('href', url)
      let fileName = response.headers.get('Content-Disposition')
      let attachmentName = decodeURIComponent(fileName.substring('attachment; filename="'.length, fileName.length - 1))
      downloadLink.setAttribute('download', attachmentName)

      downloadLink.click() // 模拟点击实现下载
      setTimeout(() => { // 延时释放
        (window.URL || window.webkitURL).revokeObjectURL(url) // 用URL.revokeObjectURL()来释放这个object URL
      }, 100)
    })
  }
}

/**
 * Install plugin.
 */
const plugin = function (Vue) {
  if (plugin.installed) {
    return
  }

  VueJw.vm = Vue

  Vue.prototype.$jw = VueJw
  Vue.jw = VueJw
}

if (typeof window !== 'undefined' && window.Vue) {
  window.Vue.use(plugin)
}

export default plugin
