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

  .jw-grid {
    width: 100%;
    height: 100%;
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

  .jw-grid .jw-floating-filter-cell {
    padding: 0px 1px;
  }

  .jw-grid .jw-floating-filter-cell .el-input {
  }

  .jw-grid .jw-floating-filter-cell .el-select {
    width: 100%;
  }

  .jw-grid .jw-floating-filter-cell .el-tag {
    height: 20px;
    line-height: 20px;
    margin: 1px 0 1px 4px;
  }

  .jw-grid .jw-floating-filter-cell .el-date-editor--daterange.el-input {
    width: 100%;
  }

  .jw-grid .ag-floating-filter-button button {
    border: 1px solid #d1dbe5;
    border-left: none;
    border-top-right-radius: 2px;
    border-bottom-right-radius: 2px;
  }

  .jw-grid .ag-ltr .ag-header-group-cell {
    border-right: 1px solid #ddd;
    text-align: center;
  }

  .jw-grid .ag-ltr .ag-header-group-cell-with-group {
    border-bottom: 1px solid #ddd;
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
    min-width: 771px;
    margin-bottom: 0px;
    top: 30px;
  }

  .jw-dialog-large {
    min-width: 1472px;
    margin-bottom: 30px;
    top: 30px;
  }

  .jw-dialog-full {
    margin-bottom: 0px;
    top: 0px;
  }

  .jw-dialog .el-dialog__header {
    padding: 20px;
    background-color: #f1f1f1;
  }

  .jw-dialog .el-dialog__footer {
    padding: 20px;
    background-color: #f1f1f1;
  }

  .jw-dialog .el-dialog__body {
    padding: 0px;
    color: #48576a;
  }

  .jw-dialog.el-dialog--tiny {
    min-width: 0px;
  }

  .jw-sub-dialog {
    min-width: 410px;
    margin-bottom: 20px;
    top: 20px;
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
    text-align: right;
  }

  .jw-form .el-form-item {
    margin-bottom: 5px;
  }

  .el-form--inline .el-form-item {
    margin-right: 0px;
  }

  .el-form--inline .el-form-item .el-form-item__content {
    line-height: 20px;
  }

  .jw-form .jw-field-col-1 {
    min-width: 205px;
    width: 205px;
  }

  .jw-form .jw-field-col-2 {
    min-width: 205px;
    width: 564px;
  }

  .jw-sub-dialog .jw-form .jw-field-col-2 {
    min-width: 205px;
    width: 205px;
  }

  .jw-form .jw-field-col-3 {
    min-width: 205px;
    width: 923px;
  }

  .jw-sub-dialog .jw-form .jw-field-col-3 {
    min-width: 205px;
    width: 205px;
  }

  .jw-form .el-radio {
    color: inherit;
  }

  .jw-form .el-checkbox {
    color: inherit;
  }

  .jw-form .el-select {
    min-width: 205px;
  }

  .jw-form .el-radio-group {
    min-width: 205px;
    line-height: 36px;
  }

  .jw-form .el-input-number {
    min-width: 205px;
  }

  .jw-form .el-input-number__decrease, .el-input-number__increase {
    line-height: 35px;
  }

  .jw-form .el-date-editor.el-input {
    min-width: 205px;
    width: 205px;
  }

  .jw-form .el-checkbox {
    line-height: 36px;
  }

  .jw-form .el-textarea {
    line-height: 36px;
  }

  .jw-form .el-textarea__inner {
    min-height: 36px;
  }

  .jw-form .jw-textarea-full-width {
    width: 100%;
  }

  .jw-form .jw-textarea-nowrap .el-textarea__inner {
    white-space: nowrap;
  }

  .jw-form .el-form-item__error {
    background: #fff;
    z-index: 9999999;
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
