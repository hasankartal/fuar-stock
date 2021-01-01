<template>
  <div class="app-container">
    <el-table v-loading="listLoading" :data="list" border fit highlight-current-row style="width: 100%">
      <el-table-column align="center" label="ID" width="80">
        <template slot-scope="{row}">
          <span>{{ row.id }}</span>
        </template>
      </el-table-column>


    </el-table>
  </div>
</template>

<script>
  import { fetchList } from '@/api/article'
import {get} from "@/api/inline-edit";
import list from "../example/list";
export default {
  name: 'InlineEditTable',

  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'info',
        deleted: 'danger'
      }
      return statusMap[status]
    }
  },

  components: {},
  data() {
    return {
      list: [{id : '1'}],
      listLoading: false,
      listQuery: {
        page: 1,
        limit: 10
      }
    }
  },
  created() {
    this.getStocks();
  },
  methods: {
    async getStocks() {
      const { data } = await fetchList(this.listQuery)
      const items = data.items
      this.list = items.map(v => {
        //this.$set(v, 'edit', false) // https://vuejs.org/v2/guide/reactivity.html
        //v.originalTitle = v.title //  will be used when user click the cancel botton
         return v
      })
      get('http://localhost:8011/stock').then(response => {
       // this.list = response.data;
        const { data } = response.data;
        const items = data
        this.list = response.data.map(v => {
    //      this.$set(v, 'edit', false) // https://vuejs.org/v2/guide/reactivity.html
          v.id = v.id //  will be used when user click the cancel botton
          return v
        })
      })
    }
  }
}

</script>

<style scoped>
.edit-input {
  padding-right: 100px;
}
.cancel-btn {
  position: absolute;
  right: 15px;
  top: 10px;
}
</style>
