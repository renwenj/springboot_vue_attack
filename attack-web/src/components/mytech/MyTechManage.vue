<script>
export default {
  name:"MyTechManage",
  data() {
    let checkDuplicate=(rule,value,callback)=>{
      /*if(this.form.no){
        return callback();
      }*/
      this.$axios.get(this.$httpUrl+'/tech/findByNo?no='+this.form.no).then(res=>res.data).then(res=>{
        if(res.code!=200){
          callback();
        }else{
          callback(new Error('技术已经存在'));
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
        ca0:'',
        ca1:'',
        pa0:'',
        pa1:'',
        r:'',
      },
      rules:{
        no: [
          { required: true, message: '请输入技术编号', trigger: 'blur' },
          { validator:checkDuplicate,trigger: 'blur'}
        ],
        ca0:[{ required: true, message: '请输入无防御攻击成本', trigger: 'blur' }],
        ca1:[{ required: true, message: '请输入有防御攻击成本', trigger: 'blur' }],
        pa0:[{ required: true, message: '请输入无防御攻击概率', trigger: 'blur' }],
        pa1:[{ required: true, message: '请输入有防御攻击概率', trigger: 'blur' }],
        r:[{ required: true, message: '请输入攻击风险', trigger: 'blur' }],
      }
    }
  },
  methods:{
    mod(row){
      this.dialogVisible=true
      this.$nextTick(
          ()=>{
            this.form.no=row.no
            this.form.ca0=row.ca0
            this.form.ca1=row.ca1
            this.form.pa0=row.pa0
            this.form.pa1=row.pa1
            this.form.r=row.r
          }
      )
    },
    del(id){
      this.$axios.get(this.$httpUrl+'/tech/del?no='+id).then(res=>res.data).then(res=>{
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
      this.$axios.post(this.$httpUrl+'/tech/save',this.form).then(res=>res.data).then(res=>{
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
      this.$axios.post(this.$httpUrl+'/tech/saveOrMod',this.form).then(res=>res.data).then(res=>{
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
      this.$axios.post(this.$httpUrl+'/tech/listPage',{
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
      <el-input v-model="no" placeholder="请输入技术编号" suffix-icon="el-icon-search" style="width:200px;" @keyup.enter.native="loadPost"></el-input>
      <el-button type="primary" style="margin-left: 5px;" @click="loadPost">查询</el-button>
      <el-button type="success" style="margin-left: 5px;" @click="resetParam">清除</el-button>
    </div>
    <el-table :data="tableData"
              :header-cell-style="{ background: 'rgb(128,164,145)', color: '#555' }"
              border>
      <el-table-column prop="no" label="技术编号" width="120">
      </el-table-column>
      <el-table-column prop="ca0" label="无防御攻击成本" width="150">
      </el-table-column>
      <el-table-column prop="ca1" label="有防御攻击成本" width="150">
      </el-table-column>
      <el-table-column prop="pa0" label="无防御攻击概率" width="150">
      </el-table-column>
      <el-table-column prop="pa1" label="有防御攻击概率" width="150">
      </el-table-column>
      <el-table-column prop="r" label="攻击风险" width="120">
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
    <el-dialog
        title="提示"
        :visible.sync="dialogVisible"
        width="30%"
        :before-close="handleClose">
      <el-form ref="form" :rules="rules" :model="form" label-width="80px">
        <el-form-item label="技术编号" prop="no">
          <el-col :span="20">
            <el-input v-model="form.no"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="无防御攻击成本" prop="ca0">
          <el-col :span="20">
            <el-input v-model="form.ca0"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="有防御攻击成本" prop="ca1">
          <el-col :span="20">
            <el-input v-model="form.ca1"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="无防御攻击概率" prop="pa0">
          <el-col :span="20">
            <el-input v-model="form.pa0"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="有防御攻击概率" prop="pa1">
          <el-col :span="20">
            <el-input v-model="form.pa1"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="攻击风险" prop="r">
          <el-col :span="20">
            <el-input v-model="form.r"></el-input>
          </el-col>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<style scoped>

</style>