<template>
  <el-card>
    <el-upload
      class="avatar-uploader"
      action="http://192.168.1.150:8080/api/admin/file/upload"
      :show-file-list="false"
      :on-success="handleAvatarSuccess"
      :before-upload="beforeAvatarUpload"
      :headers="headers"
    >
      <img v-if="imageUrl" :src="imageUrl" class="avatar">
      <i v-else class="el-icon-plus avatar-uploader-icon" />
    </el-upload>
  </el-card>
</template>

<script>
import { getToken } from '@/utils/auth'
export default {
  props: {
    url: {
      type: String,
      default: ''
    },
    dialog: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      imageUrl: '',
      headers: {
        Authorization: getToken()
      }
    }
  },
  watch: {
    'dialog.show': function(val) {
      if (val) {
        this.imageUrl = localStorage.getItem('editUrl')
        console.log('aaa')
      }
    }
  },
  methods: {
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw)
      localStorage.setItem('imageUrl', res)
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg'
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isJPG && isLt2M
    }
  }
}
</script>

<style scoped>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>

