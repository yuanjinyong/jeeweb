<style>
  * {
    box-sizing: border-box;
    -moz-box-sizing: border-box; /* Firefox */
    -webkit-box-sizing: border-box; /* Safari */
  }

  body {
    font-size: 14px;
    /*background-color: #324157;*/
    margin: 0px;
    padding: 0px;
    overflow: auto;
    height: 100%;
  }

  fieldset {
    padding: 0;
    margin: 0;
    border: 0;
    min-width: 0;
  }

  a {
    color: #20a0ff;
  }

  a:hover {
    color: #4db3ff
  }

  #app {
    height: 100%;
  }

  .pull-right {
    float: right !important;
  }

  .fa {
    text-align: center;
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

  /********************************************************************************************************************/
  /* jw-grid */
  /********************************************************************************************************************/
  .jw-grid {
    width: 100%;
    height: 100%;
    border: 1px solid #ddd;
    border-radius: 4px;
    background: #f5f7f9;
  }

  .jw-grid .ag-overlay-no-rows-center {
    background-color: #f5f7f9;
    border: 1px solid #eee;
    border-radius: 4px;
    padding: 6px;
  }

  .jw-grid .ag-root {
    border: none;
    border-bottom: 1px solid #eee;
    -moz-user-select: text;
    -webkit-user-select: text;
    -ms-user-select: text;
  }

  .jw-grid .ag-header {
    background: #f5f7f9;
    border-bottom: 1px solid #eee;
  }

  .jw-grid .ag-body {
    background-color: #fff;
  }

  .jw-grid .ag-row-odd {
    background-color: #f6f8ff;
  }

  .jw-grid .ag-row-selected {
    background-color: #b0e0e6;
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
    min-width: 20px;
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
    border-right: 1px solid #eee;
    text-align: center;
  }

  .jw-grid .ag-ltr .ag-header-group-cell-with-group {
    border-bottom: 1px solid #eee;
  }

  .jw-grid .ag-ltr .ag-header-cell {
    border-right: 1px solid #eee;
  }

  .jw-grid .ag-ltr .ag-header-viewport .ag-header-cell:last-child {
    border-right: 1px solid transparent;
  }

  .jw-grid .ag-ltr .ag-pinned-right-header {
    border-left: 1px solid #eee;
  }

  .jw-grid .ag-ltr .ag-cell-no-focus {
    border-right: 1px solid transparent;
    border-left: 1px solid transparent;
    outline-width: 0;
  }

  .jw-grid .ag-ltr .ag-pinned-left-cols-viewport {
    border-right: 1px solid #eee;
  }

  .jw-grid .ag-ltr .ag-pinned-right-cols-viewport {
    border-left: 1px solid #eee;
  }

  .jw-grid .ag-group-cell {
    font-style: normal;
  }

  .jw-grid .ag-paging-panel {
    text-align: right;
  }

  .jw-grid .ag-paging-row-summary-panel {
    width: auto;
    margin-right: 20px;
  }

  /********************************************************************************************************************/
  /* jw-dialog */
  /********************************************************************************************************************/
  .jw-dialog {
    margin-bottom: 0px;
    width: auto;
  }

  .jw-dialog.jw-dialog-full {
    top: 0px;
    width: 100%;
  }

  .jw-dialog .el-dialog__header {
    padding: 10px 20px;
    background-color: #f1f1f1;
    line-height: initial;
  }

  .jw-dialog .el-dialog__footer {
    padding: 10px 20px;
    background-color: #f1f1f1;
    line-height: initial;
  }

  .jw-dialog .el-dialog__body {
    padding: 0px;
  }

  .jw-dialog .jw-dialog-body {
    overflow-y: auto;
  }

  /********************************************************************************************************************/
  /* jw-form */
  /********************************************************************************************************************/
  .jw-form {
    padding: 20px;
  }

  .jw-form .jw-form-body {
  }

  .jw-form .jw-form-footer {
    padding: 20px;
  }

  .jw-form .el-form--inline fieldset {
    margin-bottom: -10px;
  }

  .jw-form .el-form--inline .jw-field {
    margin-right: 0px;
    margin-bottom: 10px;
    min-height: 36px;
  }

  .jw-form .el-form--inline .jw-field .el-form-item__label {
    width: 100px;
    padding: 10px 5px 0px 0;
  }

  /* large 1470 -  */
  .jw-form-mini {
    width: 390px; /* 20 + 350 + 20 */
  }

  .jw-form-small {
    width: 745px; /* 20 + 350 + 4 + 350 + 20 + 1 */
  }

  .jw-form-middle {
    width: 1099px; /* 20 + 350 + 4 + 350 + 4 + 350 + 20 + 1 */
  }

  .jw-form-large {
    width: 1453px; /* 20 + 350 + 4 + 350 + 4 + 350 + 4 + 350 + 20 + 1 */
  }

  .jw-form-full {
    width: 100%;
  }

  .jw-form .el-form--inline .jw-field.jw-field-1 .el-form-item__content,
  .jw-form.jw-form-full .el-form--inline .jw-field.jw-field-1 .el-form-item__content,
  .jw-form.jw-form-mini .el-form--inline .jw-field.jw-field-2 .el-form-item__content,
  .jw-form.jw-form-mini .el-form--inline .jw-field.jw-field-3 .el-form-item__content,
  .jw-form.jw-form-mini .el-form--inline .jw-field.jw-field-4 .el-form-item__content {
    width: 250px;
  }

  .jw-form .el-form--inline .jw-field.jw-field-2 .el-form-item__content,
  .jw-form.jw-form-full .el-form--inline .jw-field.jw-field-2 .el-form-item__content,
  .jw-form.jw-form-small .el-form--inline .jw-field.jw-field-3 .el-form-item__content,
  .jw-form.jw-form-small .el-form--inline .jw-field.jw-field-4 .el-form-item__content {
    width: 604px; /* 250 + 4 + 350 */
  }

  .jw-form .el-form--inline .jw-field.jw-field-3 .el-form-item__content,
  .jw-form.jw-form-full .el-form--inline .jw-field.jw-field-3 .el-form-item__content,
  .jw-form.jw-form-middle .el-form--inline .jw-field.jw-field-4 .el-form-item__content {
    width: 958px; /* 250 + 4 + 350 + 4 + 350 */
  }

  .jw-form .el-form--inline .jw-field.jw-field-4 .el-form-item__content {
    width: 1312px; /* 250 + 4 + 350 + 4 + 350 + 4 + 350 */
  }

  /* middle 1116 -  1470 */
  @media (max-width: 1470px) {
    .jw-form-large {
      width: 1099px; /* 20 + 350 + 4 + 350 + 4 + 350 + 4 + 350 + 20 + 1 */
    }

    .jw-form .el-form--inline .jw-field.jw-field-4 .el-form-item__content {
      width: 958px; /* 250 + 4 + 350 + 4 + 350 */
    }
  }

  /* small 762 - 1116 */
  @media (max-width: 1116px) {
    .jw-form-middle {
      width: 745px; /* 20 + 350 + 4 + 350 + 4 + 350 + 20 + 1 */
    }

    .jw-form-large {
      width: 745px; /* 20 + 350 + 4 + 350 + 4 + 350 + 4 + 350 + 20 + 1 */
    }

    .jw-form .el-form--inline .jw-field.jw-field-3 .el-form-item__content,
    .jw-form.jw-form-full .el-form--inline .jw-field.jw-field-3 .el-form-item__content,
    .jw-form.jw-form-middle .el-form--inline .jw-field.jw-field-4 .el-form-item__content {
      width: 604px; /* 250 + 4 + 350 + 4 + 350 */
    }

    .jw-form .el-form--inline .jw-field.jw-field-4 .el-form-item__content {
      width: 604px; /* 250 + 4 + 350 + 4 + 350 + 4 + 350 */
    }
  }

  /* mini 0 - 762 */
  @media (max-width: 762px) {
    .jw-form-small {
      width: 390px; /* 20 + 350 + 4 + 350 + 20 + 1 */
    }

    .jw-form-middle {
      width: 390px; /* 20 + 350 + 4 + 350 + 4 + 350 + 20 + 1 */
    }

    .jw-form-large {
      width: 390px; /* 20 + 350 + 4 + 350 + 4 + 350 + 4 + 350 + 20 + 1 */
    }

    .jw-form .el-form--inline .jw-field.jw-field-2 .el-form-item__content,
    .jw-form.jw-form-full .el-form--inline .jw-field.jw-field-2 .el-form-item__content,
    .jw-form.jw-form-small .el-form--inline .jw-field.jw-field-3 .el-form-item__content,
    .jw-form.jw-form-small .el-form--inline .jw-field.jw-field-4 .el-form-item__content {
      width: 250px; /* 250 + 4 + 350 */
    }

    .jw-form .el-form--inline .jw-field.jw-field-3 .el-form-item__content,
    .jw-form.jw-form-full .el-form--inline .jw-field.jw-field-3 .el-form-item__content,
    .jw-form.jw-form-middle .el-form--inline .jw-field.jw-field-4 .el-form-item__content {
      width: 250px; /* 250 + 4 + 350 + 4 + 350 */
    }

    .jw-form .el-form--inline .jw-field.jw-field-4 .el-form-item__content {
      width: 250px; /* 250 + 4 + 350 + 4 + 350 + 4 + 350 */
    }
  }

  .jw-form .el-form--inline .jw-field .el-form-item__content .jw-grid {
    border: 1px solid #bfcbd9;
  }

  .jw-form .el-input-number {
    width: 100%;
  }

  .jw-form .el-autocomplete {
    width: 100%;
  }

  .jw-form .el-select {
    width: 100%;
  }

  .jw-form .el-radio-group {
    width: 100%;
  }

  .jw-form .el-date-editor {
    width: 100%;
  }

  .jw-form .el-textarea__inner {
    min-height: 36px;
  }

  .jw-textarea-nowrap .el-textarea__inner {
    white-space: nowrap;
  }

  .jw-form .el-form-item__error {
    background: #fff;
    z-index: 9999999;
  }

  /********************************************************************************************************************/
  /* jw-form-view */
  /********************************************************************************************************************/
  .jw-form-view .el-input__inner,
  .jw-form-view .el-input.is-disabled .el-input__inner,
  .jw-form-view .el-textarea__inner {
    background: inherit;
    border: none;
    border-bottom: 1px solid #bfcbd9;
    border-radius: 0;
    color: #1f2d3d;
    cursor: not-allowed;
  }

  .jw-form-view .el-radio__input.is-disabled.is-checked .el-radio__inner {
    border-color: #20a0ff;
    background: #20a0ff;
  }

  .jw-form-view .el-radio__input.is-disabled .el-radio__inner {
    background: inherit;
  }

  .jw-form-view .el-radio__input.is-disabled + .el-radio__label {
    color: #1f2d3d;
  }

  .jw-form-view .el-input-number__decrease,
  .jw-form-view .el-input-number__increase {
    display: none;
  }

  .jw-form-view .el-autocomplete .el-input__icon,
  .jw-form-view .el-select .el-input__icon,
  .jw-form-view .el-date-editor .el-input__icon {
    display: none;
  }

  .jw-form-view .el-checkbox,
  .jw-form-view .el-checkbox .el-checkbox__inner,
  .jw-form-view .el-radio,
  .jw-form-view .el-radio .el-radio__inner {
    cursor: not-allowed;
  }

  .jw-form-view .el-checkbox .el-checkbox__inner:hover,
  .jw-form-view .el-radio .el-radio__inner:hover {
    border: 1px solid #bfcbd9;
  }

  .jw-form-view .el-select .el-input__inner:hover {
    border-bottom: 1px solid #bfcbd9;
  }

  .jw-form-view .jw-grid .el-input__inner,
  .jw-form-view .jw-grid .el-input.is-disabled .el-input__inner,
  .jw-form-view .jw-grid .el-textarea__inner {
    background: inherit;
    border: none;
    border-radius: 0;
    color: #1f2d3d;
    cursor: default;
  }

  /********************************************************************************************************************/
  /* jw-selector */
  /********************************************************************************************************************/
  .jw-selector {
  }

  .jw-selector-small {
    width: 705px;
  }
</style>


<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
  export default {
    name: 'app'
  }
</script>
