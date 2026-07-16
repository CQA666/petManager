<template>
  <div class="category-wrapper">
    <el-card shadow="always">
      <!-- 操作栏 -->
      <div class="control-btns">
        <el-popconfirm title="确认删除?" @confirm="batchDelete">
          <template #reference>
            <el-button type="danger" size="medium"
                       style="background-color: red;border-color: red;">批量删除</el-button>
          </template>
        </el-popconfirm>

        <el-button type="primary" size="medium" @click="exportVisible = true"
                   style="float:right;margin-right: 10px;background-color: blue;border-color: blue;">导出数据</el-button>
      </div>

      <!-- 查询栏 -->
      <el-form ref="searchForm" :inline="true" :model="listQuery" label-width="90px" class="search-form">
        <el-form-item label="宠物名字">
          <el-input v-model="listQuery.name" placeholder="请输入宠物名字" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="danger" @click="onReset">重置</el-button>
        </el-form-item>
      </el-form>

      <!-- 表格栏 -->
      <el-table ref="multipleTable" v-loading="listLoading" :data="tableData"
                @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="id" label="序号" width="80" align="center" sortable></el-table-column>
        <el-table-column prop="animalImg" label="宠物头像" width="100">
          <template slot-scope="scope">
            <el-image :src="imgBaseUrl + '/' + scope.row.animalImg" alt="宠物头像" width="100"
                      :preview-src-list="[imgBaseUrl + '/' + scope.row.animalImg]"
                      style="height: 50px;width: 50px;border-radius: 5px" />
          </template>
        </el-table-column>
        <el-table-column prop="animalName" label="宠物姓名" show-overflow-tooltip></el-table-column>
        <el-table-column prop="userName" label="领养人" show-overflow-tooltip></el-table-column>
        <el-table-column prop="time" label="领养时间"></el-table-column>
        <el-table-column prop="status" label="领养状态">
          <template slot-scope="scope">
            <el-tag :type="getStatusType(scope.row.status)">{{ scope.row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="reviewComment" label="审核意见" show-overflow-tooltip></el-table-column>
        <el-table-column prop="reviewTime" label="审核时间" show-overflow-tooltip></el-table-column>
        <el-table-column label="操作" width="280">
          <template slot-scope="scope">
            <el-button size="mini" type="primary"
                       v-if="scope.row.status === '审核中' && (userInfo.role === 'SUPER_ADMIN' || userInfo.role === 'ADMIN')"
                       @click="handleReview(scope.row)">审核</el-button>

            <!-- 新增：编辑按钮 -->
            <el-button size="mini" type="warning"
                       v-if="userInfo.role === 'SUPER_ADMIN' || userInfo.role === 'ADMIN'"
                       @click="handleEdit(scope.row)">编辑</el-button>

            <el-button size="mini" type="danger"
                       v-if="userInfo.role === 'SUPER_ADMIN' || userInfo.role === 'ADMIN'"
                       @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页栏 -->
      <Pagination :total="total" :page.sync="listQuery.currentPage" :limit.sync="listQuery.pageSize"
                  @pagination="fetchData" />

      <!-- 审核弹出框 -->
      <el-dialog title="审核领养申请" :visible.sync="reviewFormVisible" width="40%" :modal="false">
        <el-form ref="reviewForm" :model="reviewForm" label-width="100px" class="dialog-form">
          <el-form-item label="宠物姓名">
            <el-input v-model="reviewForm.animalName" disabled></el-input>
          </el-form-item>
          <el-form-item label="领养人">
            <el-input v-model="reviewForm.userName" disabled></el-input>
          </el-form-item>
          <el-form-item label="领养时间">
            <el-input v-model="reviewForm.time" disabled></el-input>
          </el-form-item>
          <el-form-item label="审核结果">
            <el-radio-group v-model="reviewForm.status">
              <el-radio label="领养中">通过</el-radio>
              <el-radio label="审核不通过">不通过</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="审核意见">
            <el-input v-model="reviewForm.reviewComment" type="textarea" rows="3" placeholder="请输入审核意见"></el-input>
          </el-form-item>
          <el-form-item class="footer-item">
            <el-button @click="reviewFormVisible = false">取消</el-button>
            <el-button type="primary" @click="submitReview">确定</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>

      <!-- 编辑弹出框（新增） -->
      <el-dialog title="编辑领养信息" :visible.sync="editFormVisible" width="40%">
        <el-form ref="editFormRef" :model="editForm" label-width="100px" class="dialog-form">
          <el-form-item label="宠物姓名" prop="animalName">
            <el-input v-model="editForm.animalName"></el-input>
          </el-form-item>
          <el-form-item label="领养人" prop="userName">
            <el-input v-model="editForm.userName" disabled></el-input>
          </el-form-item>
          <el-form-item label="领养时间" prop="time ">
            <el-date-picker
                v-model="editForm.time"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择领养时间">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="领养状态" prop="status">
            <el-select v-model="editForm.status" placeholder="请选择状态">
              <el-option label="待领养" value="待领养"></el-option>
              <el-option label="审核中" value="审核中"></el-option>
              <el-option label="领养中" value="领养中"></el-option>
              <el-option label="审核不通过" value="审核不通过"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="审核意见" prop="reviewComment">
            <el-input v-model="editForm.reviewComment" type="textarea" rows="3"></el-input>
          </el-form-item>
          <el-form-item label="审核时间" prop="reviewTime">
            <el-date-picker
                v-model="editForm.reviewTime"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择审核时间">
            </el-date-picker>
          </el-form-item>
          <el-form-item class="footer-item">
            <el-button @click="editFormVisible = false">取消</el-button>
            <el-button type="primary" @click="submitEdit">保存</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>

      <!-- 导出数据 弹出栏 -->
      <el-dialog title="导出数据" :visible.sync="exportVisible" width="500px">
        <div class="export-data" style="display: flex;flex-direction: row;justify-content: space-between;">
          <el-button type="primary" @click="exportTable('xlsx')">EXCEL格式</el-button>
          <el-button type="primary" @click="exportTable('csv')">CSV格式</el-button>
        </div>
        <div class="hints">TIP：请选择要导出数据的格式。</div>
      </el-dialog>
    </el-card>
  </div>
</template>

<script>
import excel from '../utils/excel.js'
import Pagination from '../components/Pagination/index.vue'
import Request from '../utils/request.js'

export default {
  inject: ['userInfo'],
  name: 'Animal',
  components: { Pagination },
  data() {
    return {
      uploadUrl: '/api/file/upload',
      imgBaseUrl: this.HOST,
      listLoading: true,
      listQuery: {
        name: undefined,
        currentPage: 1,
        pageSize: 10
      },
      total: 0,
      tableData: [],
      multipleSelection: [],

      // 审核相关
      reviewFormVisible: false,
      reviewForm: {
        id: null,
        animalName: '',
        userName: '',
        time: '',
        status: '领养中',
        reviewComment: '',
        reviewerId: null
      },

      // 编辑相关（新增）
      editFormVisible: false,
      editForm: {
        id: null,
        animalName: '',
        userName: '',
        time: '',
        status: '',
        reviewComment: '',
        reviewTime: ''
      },

      exportVisible: false
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    getStatusType(status) {
      const statusMap = {
        '领养中': 'success',
        '待领养': 'info',
        '审核中': 'warning',
        '审核不通过': 'danger'
      }
      return statusMap[status] || 'info'
    },

    handleReview(row) {
      this.reviewForm = {
        id: row.id,
        animalName: row.animalName,
        userName: row.userName,
        time: row.time,
        status: '领养中',
        reviewComment: '',
        reviewerId: this.userInfo.id
      }
      this.reviewFormVisible = true
    },

    submitReview() {
      if (!this.reviewForm.reviewComment) {
        this.$message.warning('请填写审核意见')
        return
      }

      Request.put(`/adopt/update`,this.reviewForm).then(response => {
        if (response.code == 0) {
          this.$message.success('审核成功')
          this.reviewFormVisible = false
          this.fetchData()
        } else {
          this.$message.error(response.msg || '审核失败')
        }
      }).catch(() => {
        this.$message.error('审核失败，请稍后重试')
      })
    },

    // 新增：处理编辑
    handleEdit(row) {
      this.editForm = { ...row } // 深拷贝建议用 JSON.parse(JSON.stringify(row))，但此处简单拷贝足够
      this.editFormVisible = true
    },

    // 新增：提交编辑
    submitEdit() {
      Request.put(`/adopt/update`, this.editForm).then(response => {
        if (response.code === '0') {
          this.$message.success('更新成功')
          this.editFormVisible = false
          this.fetchData()
        } else {
          this.$message.error(response.msg || '更新失败')
        }
      }).catch(() => {
        this.$message.error('更新失败，请稍后重试')
      })
    },

    handleSelectionChange(val) {
      this.multipleSelection = val.map(v => v.id)
    },

    handleDelete(index, row) {
      this.$confirm('此操作将删除该条数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        Request.delete("/adopt/deleteBatch?ids=" + row.id).then(response => {
          if (response.code == 0) {
            this.$message.success('删除成功!')
            this.onReset()
          } else {
            this.$message.error('删除失败!')
          }
        })
      }).catch(() => {
        this.$message.info('已取消删除')
      })
    },

    batchDelete() {
      if (this.multipleSelection.length < 1) {
        this.$message.warning('请先选择要删除的数据！')
      } else {
        Request.delete(`/adopt/deleteBatch?ids=${this.multipleSelection.join(',')}`).then(res => {
          if (res.code === '0') {
            this.$message.success('批量删除成功')
            this.onReset()
          } else {
            this.$message.error(res.msg)
          }
        })
      }
    },

    fetchData() {
      this.listLoading = true
      Request.get("/adopt/selectPage", {
        params: {
          name: this.listQuery.name,
          currentPage: this.listQuery.currentPage,
          size: this.listQuery.pageSize,
        }
      }).then(response => {
        if (response.code === '0') {
          const data = response.data
          this.total = data.total
          this.tableData = data.records
        }
      }).finally(() => {
        this.listLoading = false
      })
    },

    onSubmit() {
      this.listQuery.currentPage = 1
      this.fetchData()
    },

    onReset() {
      this.listQuery = {
        name: undefined,
        currentPage: 1,
        pageSize: 10
      }
      this.fetchData()
    },

    exportTable(type) {
      if (!this.tableData.length) {
        this.$message.warning('暂无数据可导出')
        return
      }
      const params = {
        header: ['序号', '宠物姓名', '领养人', '领养时间', '领养状态', '审核意见', '审核时间'],
        key: ['id', 'animalName', 'userName', 'time', 'status', 'reviewComment', 'reviewTime'],
        data: this.tableData.map(item => ({
          id: item.id,
          animalName: item.animalName,
          userName: item.userName,
          time: item.time,
          status: item.status,
          reviewComment: item.reviewComment || '无',
          reviewTime: item.reviewTime || '无'
        })),
        autoWidth: true,
        fileName: '宠物领养数据表',
        bookType: type
      }
      excel.exportDataToExcel(params)
      this.exportVisible = false
    }
  }
}
</script>

<style lang="less" scoped>
.category-wrapper {
  .el-card {
    min-height: 656px;
  }

  .control-btns {
    margin-bottom: 20px;
  }

  .search-form {
    padding: 10px !important;
    background-color: #f7f8fb;
    height: 40px !important;
    line-height: 40px !important;
  }

  .el-table thead {
    font-weight: 600;
    th {
      background-color: #f2f3f7;
    }
  }

  .dialog-form {
    .el-input,
    .el-textarea,
    .el-date-editor {
      width: 90%;
    }
    .footer-item {
      margin-top: 50px;
      text-align: right;
    }
  }

  .export-data {
    display: flex;
    justify-content: space-around;
    margin-bottom: 20px;
  }

  .hints {
    font-size: 12px;
    color: #aaa;
    text-align: center;
  }
}
</style>
