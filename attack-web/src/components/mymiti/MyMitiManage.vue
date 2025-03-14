<script>
export default {
  name:"MyMitiManage",
  data() {
    let checkDuplicate=(rule,value,callback)=>{
      /*if(this.form.no){
        return callback();
      }*/
      this.$axios.get(this.$httpUrl+'/miti/findByNo?no='+this.form.no).then(res=>res.data).then(res=>{
        if(res.code!=200){
          callback();
        }else{
          callback(new Error('缓解措施已经存在'));
        }
      })
    };
    return {
      tableData: [],
      pageSize:10,
      pageNum:1,
      total:0,
      no:'',
      dialogVisible:false,
      form:{
        no:'',
        e:'',
        cd:'',
      },
      rules:{
        no: [
          { required: true, message: '请输入技术编号', trigger: 'blur' },
          { validator:checkDuplicate,trigger: 'blur'}
        ],
        e:[{ required: true, message: '请输入无防御成本', trigger: 'blur' }],
        cd:[{ required: true, message: '请输入有防御成本', trigger: 'blur' }],
      }
    }
  },
  methods:{
    mod(row){
      this.dialogVisible=true
      this.$nextTick(
          ()=>{
            this.form.no=row.no
            this.form.e=row.e
            this.form.cd=row.cd
          }
      )
    },
    del(id){
      this.$axios.get(this.$httpUrl+'/miti/del?no='+id).then(res=>res.data).then(res=>{
        console.log(res)
        if(res.code==200){
          this.dialogVisible=false
          this.$message({
            message: '操作成功',
            type: 'success'
          });
          this.loadPost()
        }else{
          this.$message({
            message: '操作失败',
            type: 'error'
          });
        }
      })
    },
    resetForm() {
      this.$refs.form.resetFields();
    },
    add(){
      this.dialogVisible=true
      this.$nextTick(
          ()=>{
            this.resetForm();
          }
      )
    },
    doSave(){
      this.$axios.post(this.$httpUrl+'/miti/save',this.form).then(res=>res.data).then(res=>{
        console.log(res)
        if(res.code==200){
          this.dialogVisible=false
          this.$message({
            message: '操作成功',
            type: 'success'
          });
          this.loadPost()
        }else{
          this.$message({
            message: '操作失败',
            type: 'error'
          });
        }
      })
    },
    doMod(){
      this.$axios.post(this.$httpUrl+'/miti/saveOrMod',this.form).then(res=>res.data).then(res=>{
        console.log(res)
        if(res.code==200){
          this.dialogVisible=false
          this.$message({
            message: '操作成功',
            type: 'success'
          });
          this.loadPost()
        }else{
          this.$message({
            message: '操作失败',
            type: 'error'
          });
        }
      })
    },
    save(){
      this.$refs.form.validate((valid) => {
        if (valid) {
          if(this.form.no){
            this.doMod();
          }else{
            this.doSave();
          }
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    loadPost(){
      this.$axios.post(this.$httpUrl+'/miti/listPage',{
        pageSize:this.pageSize,
        pageNum:this.pageNum,
        param:{
          no:this.no,
        }
      }).then(res=>res.data).then(res=>{
        console.log(res)
        if(res.code==200){
          this.tableData=res.data,
              this.total=res.total
        }else{
          alert("获取数据失败")
        }
      })
    },
    resetParam(){
      this.no=''
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageNum=1;
      this.pageSize=val;
      this.loadPost()
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.pageNum=val;
      this.loadPost()
    }
  },
  beforeMount() {
    this.loadPost();
  }
};
</script>

<template>
  <div style="margin-bottom: 5px;">
    <div>
      <el-input v-model="no" placeholder="请输入措施编号" suffix-icon="el-icon-search" style="width:200px;" @keyup.enter.native="loadPost"></el-input>
      <el-button type="primary" style="margin-left: 5px;" @click="loadPost">查询</el-button>
      <el-button type="success" style="margin-left: 5px;" @click="resetParam">清除</el-button>
    </div>
    <el-table :data="tableData"
              :header-cell-style="{ background: 'rgb(128,164,145)', color: '#555' }"
              border>
      <el-table-column prop="no" label="缓解措施编号" width="120">
      </el-table-column>
      <el-table-column prop="e" label="潜在收益" width="150">
      </el-table-column>
      <el-table-column prop="cd" label="防御成本" width="150">
      </el-table-column>
      <el-table-column prop="operate" label="操作" width="150">
        <template slot-scope="scope">
          <el-button size="small" type="primary" @click="mod(scope.row)">编辑</el-button>
          <el-popconfirm title="是否确认删除？" @confirm="del(scope.row.no)" style="margin-left: 5px;">
            <el-button slot="reference" size="small" type="danger">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <el-button type="primary" style="margin-top: 5px;margin-left: 500px;" @click="add">新增</el-button>
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[5, 10, 15, 20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>
<!--    <el-dialog
        title="提示"
        :visible.sync="dialogVisible"
        width="30%"
        :before-close="handleClose">
      <el-form ref="form" :rules="rules" :model="form" label-width="80px">
        <el-form-item label="缓解措施编号" prop="no">
          <el-col :span="20">
            <el-input v-model="form.no"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="潜在收益" prop="e">
          <el-col :span="20">
            <el-input v-model="form.e"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="防御成本" prop="cd">
          <el-col :span="20">
            <el-input v-model="form.cd"></el-input>
          </el-col>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </span>
    </el-dialog>-->
    <el-dialog
        title="输入"
        :visible.sync="dialogVisible"
        width="400px"
        center
        top="20vh"
        :before-close="handleClose"
        class="custom-dialog"
    >
      <el-form
          ref="form"
          :rules="rules"
          :model="form"
          label-width="120px"
          label-position="left"
          class="custom-form"
      >
        <el-form-item label="缓解措施编号" prop="no">
          <el-col :span="20">
            <el-input v-model="form.no" placeholder="请输入编号"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="潜在收益" prop="e">
          <el-col :span="20">
            <el-input v-model="form.e" placeholder="请输入潜在收益"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="防御成本" prop="cd">
          <el-col :span="20">
            <el-input v-model="form.cd" placeholder="请输入防御成本"></el-input>
          </el-col>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false" icon="el-icon-close">取 消</el-button>
      <el-button type="primary" @click="save" icon="el-icon-check">确 定</el-button>
    </span>
    </el-dialog>
  </div>
</template>

<style scoped>
.custom-dialog {
  border-radius: 8px;
}

.custom-form .el-form-item {
  margin-bottom: 20px;
}

.dialog-footer {
  text-align: right;
}

.dialog-footer .el-button {
  margin-left: 10px;
}

::v-deep .el-dialog {
  background: linear-gradient(145deg, #ffffff, #f0f8ff); /* 背景渐变 */
  border-radius: 12px; /* 圆角 */
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15); /* 阴影 */
  border: 1px solid #e0e0e0; /* 边框 */
}

::v-deep .el-dialog__header {
  background: linear-gradient(145deg, #409eff, #66b1ff); /* 标题背景渐变 */
  color: #ffffff; /* 文字颜色 */
  padding: 15px 20px; /* 内边距 */
  border-top-left-radius: 12px; /* 圆角 */
  border-top-right-radius: 12px; /* 圆角 */
  border-bottom: 1px solid #e0e0e0; /* 底部边框 */
}

::v-deep .el-dialog__body {
  background-color: #f9f9f9; /* 内容区域背景颜色 */
  padding: 20px; /* 内边距 */
  border-bottom-left-radius: 12px; /* 圆角 */
  border-bottom-right-radius: 12px; /* 圆角 */
}
</style>

