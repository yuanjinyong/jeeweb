<style>
  body {
    font-size: 14px;
    background: #fff;
    margin: 0px;
    padding: 0px;
    overflow: auto;
  }

  .jw-menu-item-text {
    font-size: 18px;
  }

  .jw-menu-item-text > i {
    font-size: 18px;
    margin-right: 10px;
  }

  .el-tabs--card > .el-tabs__header .el-tabs__nav-prev, .el-tabs--card > .el-tabs__header .el-tabs__nav-next {
    /*background-color: #eef1f6;*/
  }

  .el-tabs--card > .el-tabs__header .el-tabs__item {
    margin: 0px 1px;
    background-color: #eef1f6;
    border: 1px solid #d1dbe5;
    border-radius: 4px 4px 0 0;
    font-size: 18px;
  }

  .el-tabs--card > .el-tabs__header .el-tabs__item.is-active {
    background-color: #fff;
  }

  .jw-grid .ag-root {
    border: 1px solid #ddd;
    border-radius: 4px;
    -moz-user-select: text;
    -webkit-user-select: text;
    -ms-user-select: text;
  }

  .jw-grid .ag-header {
    background: #f1f1f1;
    border-bottom: 1px solid #ddd;
  }

  .jw-grid .ag-menu {
    border: 1px solid #ddd;
    border-radius: 4px;
  }

  .jw-grid .ag-floating-filter-body input {
    height: 20px;
    border: 1px solid #d1dbe5;
    border-top-left-radius: 2px;
    border-bottom-left-radius: 2px;
  }

  .jw-grid .ag-floating-filter-button button {
    border: 1px solid #d1dbe5;
    border-left: none;
    border-top-right-radius: 2px;
    border-bottom-right-radius: 2px;
  }

  .jw-grid .ag-ltr .ag-header-cell {
    border-right: 1px solid #ddd;
  }

  .jw-grid .ag-ltr .ag-pinned-right-header {
    border-left: 1px solid #ddd;
  }

  .jw-grid .ag-ltr .ag-cell-no-focus {
    border-right: 1px dotted #ddd;
    border-left: 1px solid transparent;
  }

  .jw-grid .ag-ltr .ag-cell-last-left-pinned {
    border-right: 1px solid #ddd;
  }

  .jw-grid .ag-ltr .ag-cell-first-right-pinned {
    border-left: 1px solid #ddd;
  }

  .jw-grid .ag-group-cell {
    font-style: normal;
  }

  .jw-grid #south {
    text-align: right;
  }

  .jw-grid .ag-paging-row-summary-panel {
    width: auto;
    margin-right: 20px;
  }

  .jw-dialog {
    min-width: 650px;
  }

  .jw-dialog .el-dialog__header {
    padding: 20px;
    background-color: #f1f1f1;
  }

  .jw-dialog .el-dialog__body {
    padding: 0px;
    color: #48576a;
  }

  .jw-form {
    margin: 0px;
    padding: 0px;
  }

  .jw-form .jw-form-body {
    padding: 30px 20px;
  }

  .jw-form .jw-form-footer {
    padding: 20px;
    background-color: #f1f1f1;
  }

  .el-form--inline .el-form-item {
    margin-right: 0px;
  }

  .el-radio {
    color: inherit;
  }

  .el-checkbox {
    color: inherit;
  }

  .el-select {
    width: 196px;
  }

  .el-input-number {
    width: 196px;
  }
</style>

<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script type="text/ecmascript-6">
  export default {
    name: 'app'
  }
</script>
