<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.title" placeholder="Müşteri" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-select v-model="listQuery.importance" placeholder="Müşteri" clearable style="width: 90px" class="filter-item">
        <el-option v-for="item in importanceOptions" :key="item" :label="item" :value="item" />
      </el-select>
      <el-select v-model="listQuery.moneyType" placeholder="Para Birimi" clearable class="filter-item" style="width: 130px">
        <el-option v-for="item in moneyTypeOptions" :key="item.key" :label="item.display_name+'('+item.key+')'" :value="item.key" />
      </el-select>
      <el-select v-model="listQuery.sort" style="width: 140px" class="filter-item" @change="handleFilter">
        <el-option v-for="item in sortOptions" :key="item.key" :label="item.label" :value="item.key" />
      </el-select>
      <el-date-picker v-model="listQuery.orderDate" type="datetime" :inline="true" format= "dd-MM-yyyy" placeholder="Please pick a date" />
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        Ara
      </el-button>
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate">
        Ekle
      </el-button>
      <el-button v-waves :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download" @click="handleDownload">
        Excel İndir
      </el-button>
    </div>

    <el-table
      :key="tableKey"
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;"
      @sort-change="sortChange"
    >
      <el-table-column label="ID" prop="id" sortable="custom" align="center" width="80" :class-name="getSortClass('id')">
        <template slot-scope="{row}">
          <span>{{ row.id }}</span>
        </template>
      </el-table-column>

      <el-table-column label="Tutar" width="110px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.amount }}</span>
        </template>
      </el-table-column>

      <el-table-column label="Para Birimi" width="110px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.moneyType }}</span>
        </template>
      </el-table-column>

      <el-table-column label="Tarih" width="200px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.orderDate  }}</span>
        </template>
      </el-table-column>

      <el-table-column label="Aksiyonlar" align="center" width="250" class-name="small-padding fixed-width">
        <template slot-scope="{row,$index}">
          <el-button type="primary" size="mini" @click="handleUpdate(row)">
            Güncelle
          </el-button>
          <el-button v-if="row.status!='deleted'" size="mini" type="danger" @click="handleDelete(row,$index)">
            Sil
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="90px" style="width: 400px; margin-left:50px;">
        <el-form-item label="Para Birimi" prop="moneyType">
          <el-select v-model="temp.moneyType" class="filter-item" placeholder="Please select">
            <el-option v-for="item in moneyTypeOptions" :key="item.key" :label="item.display_name" :value="item.key" />
          </el-select>
        </el-form-item>
        <el-form-item label="Date" prop="timestamp">
          <el-date-picker v-model="temp.timestamp" type="datetime" placeholder="Please pick a date" />
        </el-form-item>
        <el-form-item label="Tutar" prop="amount">
          <el-input v-model="temp.amount" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          İptal Et
        </el-button>
        <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">
          Onayla
        </el-button>
      </div>
    </el-dialog>




    <el-dialog :visible.sync="dialogPvVisible" title="Reading statistics">
      <el-table :data="pvData" border fit highlight-current-row style="width: 100%">
        <el-table-column prop="key" label="Channel" />
        <el-table-column prop="pv" label="Pv" />
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogPvVisible = false">Confirm</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { fetchPv,fetchSaleList, fetchSaleSearchList ,createSale, updateSale, deleteSale, exportSaleExcel, exportSaleExcelByParameters } from '@/api/article'
import {get} from "@/api/inline-edit";
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination


const calendarTypeOptions = [
  { key: 'CN', display_name: 'China' },
  { key: 'US', display_name: 'USA' },
  { key: 'JP', display_name: 'Japan' },
  { key: 'EU', display_name: 'Eurozone' }
]

const moneyTypeOptions = [
  { key: 'TL' , display_name: 'Türk Lirası' },
  { key: 'USD', display_name: 'USA' },
  { key: 'EU' , display_name: 'Euro' }
]

// arr to obj, such as { CN : "China", US : "USA" }
const moneyTypeKeyValue = moneyTypeOptions.reduce((acc, cur) => {
  acc[cur.key] = cur.display_name
  return acc
}, {})


export default {
  name: 'ComplexTable',
  components: { Pagination },
  directives: { waves },
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'info',
        deleted: 'danger'
      }
      return statusMap[status]
    },
    typeFilter(type) {
      return moneyTypeKeyValue[type]
    }
  },
  data() {
    return {
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        importance: undefined,
        title: undefined,
        moneyType: undefined,
        sort: '-date'
      },
      importanceOptions: [1, 2, 3],
      calendarTypeOptions,
      moneyTypeOptions,
      sortOptions: [{ label: 'Tarih Artan', key: '+date' }, { label: 'Tarih Azalan', key: '-date' }, { label: 'ID Artan', key: '+id' }, { label: 'ID Azalan', key: '-id' } ],
      statusOptions: ['published', 'draft', 'deleted'],
      temp: {
        id: undefined,
        importance: 1,
        remark: '',
        timestamp: new Date(),
        title: '',
        moneyType: '',
        status: 'published'
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: 'Güncelle',
        create: 'Yeni Kayıt'
      },
      dialogPvVisible: false,
      pvData: [],
      rules: {
        moneyType: [{ required: true, message: 'type is required', trigger: 'change' }],
        timestamp: [{ type: 'date', required: true, message: 'timestamp is required', trigger: 'change' }],
        title: [{ required: true, message: 'title is required', trigger: 'blur' }]
      },
      downloadLoading: false
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      fetchSaleList().then(response => {
        console.log(response)
        this.list = response.map(v => {
            v.id = v.id
            return v
        })
      })
      this.listLoading = false
    },
      
    /*
    getList() {
      this.listLoading = true
      fetchSaleList();
      get('http://localhost:8011/sale?token=token').then(response => {
        //this.total = 3
        this.list = response.data.map(v => {
          v.id = v.id
          v.amount = v.amount
          return v
      })
        setTimeout(() => {
          this.listLoading = false
        }, 1.5 * 1000)

      })
    },*/
    handleFilter() {
      this.listQuery.page = 1
      this.getSearchList()
    },
    getSearchList() {
      this.listLoading = true
      console.log(this.listQuery)
      fetchSaleSearchList(this.listQuery).then(response => {
        console.log(response)
        this.list = response.map(v => {
            v.id = v.id
            return v
        })
      })
      this.listLoading = false
    },
    sortChange(data) {
      const { prop, order } = data
      if (prop === 'id') {
        this.sortByID(order)
      } else if (prop === 'date') {
        this.sortByDate(order)
      }
    },
    sortByID(order) {
      if (order === 'ascending') {
        this.listQuery.sort = '+id'
      } else {
        this.listQuery.sort = '-id'
      }
      this.handleFilter()
    },
    sortByDate(order) {
      if (order === 'ascending') {
        this.listQuery.sort = '+date'
      } else {
        this.listQuery.sort = '-date'
      }
      this.handleFilter()
    },
    resetTemp() {
      this.temp = {
        id: undefined,
        importance: 1,
        remark: '',
        timestamp: new Date(),
        title: '',
        status: 'published',
        type: ''
      }
    },
    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          //this.temp.id = parseInt(Math.random() * 100) + 1024 // mock a id
          createSale(this.temp)
            .then(() => {
              this.$notify({
                title: 'Success',
                message: 'Created Successfully',
                type: 'success',
                duration: 5000
              })
          })
        }
      })
    },
    handleUpdate(row) {
      this.temp = Object.assign({}, row) // copy obj
      this.temp.timestamp = new Date(this.temp.timestamp)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          const tempData = Object.assign({}, this.temp)
          tempData.timestamp = +new Date(tempData.timestamp) // change Thu Nov 30 2017 16:41:05 GMT+0800 (CST) to 1512031311464
          updateSale(tempData).then(() => {
//            const index = this.list.findIndex(v => v.id === this.temp.id)
 //           this.list.splice(index, 1, this.temp)
  //          this.dialogFormVisible = false
            this.$notify({
              title: 'Success',
              message: 'Update Successfully',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    handleDelete(row, index) {
      this.temp.id = row.id;
      deleteSale(this.temp);
      this.$notify({
        title: 'Success',
        message: 'Delete Successfully',
        type: 'success',
        duration: 2000
      })
      this.list.splice(index, 1)
    },
    handleFetchPv(pv) {
      fetchPv(pv).then(response => {
        this.pvData = response.data.pvData
        this.dialogPvVisible = true
      })
    },
    handleDownload() {
      this.downloadLoading = true
      if(this.listQuery.moneyType != '') {
        exportSaleExcelByParameters(this.listQuery).then((res) => {
          let blob = new Blob([res], {
              type: 'application/vnd.ms-excel'
          });
          if (window.navigator && window.navigator.msSaveOrOpenBlob) {
            window.navigator.msSaveOrOpenBlob(blob,'Sale Excel');
          }else{
            let url = URL.createObjectURL(blob);
            let a = document.createElement("a");
            a.href = url;
            a.download = "Export";
            a.click();            
            window.URL.revokeObjectURL(url);
          }
          }).catch(error => {
              console.log(error)
          })

      } else {
        exportSaleExcel().then((res) => {
          //  let blob = new Blob([res.data], {type: 'application/vnd.ms-excel'})
          //  console.log(res)
          if (window.navigator && window.navigator.msSaveOrOpenBlob) {
            window.navigator.msSaveOrOpenBlob(blob,'Sale Excel');
          }else{
            /*
            const link = document.createElement('a')
            link.style.display = 'none'
            link.href = URL.createObjectURL(blob)
            link.download ='Sale Excel' //downloaded file name
            document.body.appendChild(link)
            link.click()
            document.body.removeChild(link)
            console.log(res)
            */
          
            let blob = new Blob([res], {
              type: 'application/vnd.ms-excel'
            }); // 2. Get the blob setting file type in the response object returned by the request. Here is excel as an example.
            
            let url = URL.createObjectURL(blob); // 3. Create a temporary url pointing to the blob object
            
            // 4. After creating the url, you can simulate a series of operations on this file object, for example: preview, download
            let a = document.createElement("a");
            a.href = url;
            //a.href = "http://localhost:8011/sale/excelSales?token=token";
            a.download = "Export";
            a.click();
            // 5. Release this temporary object url
            window.URL.revokeObjectURL(url);
          }
          }).catch(error => {
              console.log(error)
          })
      }
      this.downloadLoading = false
      /*this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = ['timestamp', 'title', 'type', 'importance', 'status']
        const filterVal = ['timestamp', 'title', 'type', 'importance', 'status']
        const data = this.formatJson(filterVal)
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: 'table-list'
        })
        this.downloadLoading = false
      })
      */


    },

    formatJson(filterVal) {
      return this.list.map(v => filterVal.map(j => {
        if (j === 'timestamp') {
          return parseTime(v[j])
        } else {
          return v[j]
        }
      }))
    },
    getSortClass: function(key) {
      const sort = this.listQuery.sort
      return sort === `+${key}` ? 'ascending' : 'descending'
    }
  }
}
</script>
