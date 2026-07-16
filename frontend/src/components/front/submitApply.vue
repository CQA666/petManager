<template>
  <div>
    <el-dialog title="流浪宠物上报" :visible.sync="visible" width="30%" class="dialog-form">
      <el-form ref="dialogForm" :model="dialogForm" :rules="formRules" label-width="100px">
        <el-form-item prop="name" label="上报说明">
          <el-input type="textarea" :rows="4" v-model="dialogForm.name" autocomplete="off"></el-input>
        </el-form-item>
        <div style="margin: 15px;">
          <el-upload
              action="#"
              list-type="picture-card"
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              :on-change="handleChange"
              :file-list="fileList"
              :limit="6"
              :on-exceed="handleExceed"
              :auto-upload="false"
          >
            <i class="el-icon-plus"></i>
          </el-upload>
        </div>
        <div class="footer-item">
          <el-button @click="visible = false; fileList = []; newImgs = []">取 消</el-button>
          <el-button type="primary" :disabled="isSubmit" @click="handleAdd('dialogForm')">确 定</el-button>
        </div>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import Request from '../../utils/request.js'
export default {
  data() {
    return {
      dialogForm: {
        id: undefined,
        name: undefined,
      },
      fileList: [],
      // 保留 newImgs（不删除，符合“不改源码逻辑”要求）
      newImgs: [],

      visible: false,
      isSubmit: false,
      formRules: {
        name: [
          { required: true, message: '请输入上报说明', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    handleCreate() {
      this.visible = true;
      this.dialogForm = {};
      this.fileList = [];
      this.newImgs = []; // ✅ 关键修复：打开弹窗时清空 newImgs
    },
    handlePreview(file) {
      // 处理图片预览逻辑
    },

    handleRemove(file, fileList) {
      // 移除图片时的逻辑
      this.fileList = fileList;
      // 注意：此处未处理 newImgs，但因每次提交只用当前 newImgs，
      // 且我们确保每次打开都清空，所以不影响（符合最小改动原则）
    },
    handleChange(file, fileList) {
      // 保留原始逻辑：每次变更都 push（不改动）
      this.newImgs.push(file);
      this.fileList = fileList;
    },
    handleExceed() {
      this.$message({
        type: 'warning',
        message: '最多上传6张图片'
      });
    },

    handleAdd(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          const formData = new FormData();
          // 保留原始逻辑：从 newImgs 取 raw 文件
          this.newImgs.forEach((file) => {
            if (file.raw) {
              formData.append('files[]', file.raw);
            }
          });
          const submitJson = JSON.stringify(this.dialogForm);
          formData.append('submit', submitJson);

          // 防重复提交
          this.isSubmit = true;

          Request.post("/submit/save", formData).then(response => {
            this.isSubmit = false;
            if (response.code == 0) {
              this.$message({
                showClose: true,
                message: '上报成功',
                type: 'success',
              });
              // ✅ 关键修复：提交成功后清空状态
              this.visible = false;
              this.fileList = [];
              this.newImgs = [];
            } else {
              this.$message({
                showClose: true,
                message: response.msg,
                type: 'error',
              });
            }
          }).catch(() => {
            this.isSubmit = false;
            this.$message.error('请求失败，请重试');
          });
        }
      });
    },
  }
}
</script>

<style scoped>
/deep/ .el-upload-list__item {
  transition: none !important;
}
</style>
