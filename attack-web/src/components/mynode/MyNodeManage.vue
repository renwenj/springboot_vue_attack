<script>
export default {
  name:"MyNodeManage",
  data() {

    let checkDuplicate=(rule,value,callback)=>{
      if(this.form.id){
        return callback();
      }
      this.$axios.get(this.$httpUrl+'/node/findByNo?name='+this.form.name).then(res=>res.data).then(res=>{
        if(res.code!=200){
          callback();
        }else{
          callback(new Error('节点已经存在'));
        }
      })
    };
    return {
      tableData: [],
      pageSize:5,
      pageNum:1,
      total:0,
      name:'',
      dialogVisible:false,
      form:{
        id:'',
        name:'',
        imp:'',
      },
      rules:{
        name: [
          { required: true, message: '请输入节点名称', trigger: 'blur' },
          { validator:checkDuplicate,trigger: 'blur'}
        ],
        imp: [
          { required: true, message: '请输入重要程度', trigger: 'blur' },
        ],
      }
    }
  },
  methods:{
    mod(row){
      console.log(row)
      this.dialogVisible=true
      this.$nextTick(
          ()=>{
            this.form.id=row.id
            this.form.name=row.name
            this.form.imp=row.imp
          }
      )
    },
    del(id){
      console.log(id)
      this.$axios.get(this.$httpUrl+'/node/del?id='+id).then(res=>res.data).then(res=>{
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
      this.$axios.post(this.$httpUrl+'/node/save',this.form).then(res=>res.data).then(res=>{
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
      this.$axios.post(this.$httpUrl+'/node/update',this.form).then(res=>res.data).then(res=>{
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
          if(this.form.id){
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
      this.$axios.post(this.$httpUrl+'/node/listPage',{
        pageSize:this.pageSize,
        pageNum:this.pageNum,
        param:{
          name:this.name,
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
      this.name=''
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
      <el-input v-model="name" placeholder="请输入节点名称" suffix-icon="el-icon-search" style="width:200px;" @keyup.enter.native="loadPost"></el-input>
      <el-button type="primary" style="margin-left: 5px;" @click="loadPost">查询</el-button>
      <el-button type="success" style="margin-left: 5px;" @click="resetParam">清除</el-button>
    </div>
    <el-table :data="tableData"
              :header-cell-style="{ background: 'rgb(128,164,145)', color: '#555' }"
              border>
      <el-table-column prop="id" label="ID" width="60">
      </el-table-column>
      <el-table-column prop="name" label="节点名称" width="180">
      </el-table-column>
      <el-table-column prop="imp" label="重要程度" width="180">
      </el-table-column>
      <el-table-column prop="operate" label="操作" width="180">
        <template slot-scope="scope">
          <el-button size="small" type="primary" @click="mod(scope.row)">编辑</el-button>
          <el-popconfirm title="是否确认删除？" @confirm="del(scope.row.id)" style="margin-left: 5px;">
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
        <el-form-item label="节点名称" prop="name">
          <el-col :span="20">
            <el-input v-model="form.name"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="重要程度" prop="imp">
          <el-col :span="20">
            <el-input v-model="form.imp"></el-input>
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