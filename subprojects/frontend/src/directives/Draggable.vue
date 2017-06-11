/**
* Created by 袁进勇 on 2017/6/11.
*/
<script>
  import Vue from 'vue'

  Vue.directive('draggable', {
    inserted (el, binding, vnode) { // 当绑定元素插入到 DOM 中。
      el.style.cursor = 'move'
      el.onmousedown = function (mouseDownEvent) {
        mouseDownEvent.preventDefault()

        var dis = {x: mouseDownEvent.clientX - el.offsetLeft, y: mouseDownEvent.clientY - el.offsetTop}

        document.onmousemove = function (mouseMoveEvent) {
          el.style.left = (mouseMoveEvent.clientX - dis.x) + 'px'
          el.style.top = (mouseMoveEvent.clientY - dis.y) + 'px'
        }

        document.onmouseup = function () {
          document.onmousemove = null
          document.onmousedown = null
        }
      }
    }
  })
</script>
