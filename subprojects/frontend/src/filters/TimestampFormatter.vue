<script type="text/ecmascript-6">
  import Vue from 'vue'
  import moment from 'moment'

  Vue.filter('timestamp', function (value, format) {
    var timestamp = parseInt(value)

    if (!timestamp) {
      return '-'
    } else if (format) {
      return moment(timestamp).format(format)
    } else {
      return moment(timestamp).format('YYYY-MM-DD HH:mm:ss')
    }
  })
</script>
