// import { data } from 'autoprefixer'
export function export2Excel(tHeader, data, excelName) {
  require.ensure([], () => {
    const { export_json_to_excel } = require('@/vendor/Export2Excel')
    export_json_to_excel(tHeader, data, excelName)
  })
}

