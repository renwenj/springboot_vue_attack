<script>
 export default {
   name:"MyHeader",
   data(){
     return{
       user:JSON.parse(sessionStorage.getItem('CurUser'))
     }
   },
   props:{
     fold_icon:String
   },
   methods:{
     toUser(){
       console.log('to user')
       this.$router.push("/MyHome")
     },
     logOut(){
       console.log('log out')
       this.$confirm('是否退出登录？', '提示', {
         confirmButtonText: '退出登录',  //确认按钮的文字显示
         type: 'warning',
         center: true, //文字居中显示
       })
           .then(() => {
             this.$message(
                 {
                   type:"success",
                   message:"成功退出登录"
                 }
             )
             this.$router.push("/")
             sessionStorage.clear()
           })
           .catch(() => {
             this.$message(
                 {
                   type:"info",
                   message:"已取消"
                 }
             )
           });
     },
     collapse(){
       this.$emit('doCollapse')
     }
   },
   created(){
     this.$router.push("/MyHome")
   }
 };
</script>

<template>
  <div style="display: flex;line-height: 60px;">
   <div>
     <i :class="fold_icon" style="font-size:20px;" @click="collapse"></i>
   </div>
    <div style="flex:1;text-align:center; font-size:30px;">
      <span>网络攻防推演系统</span>
    </div>
   <el-dropdown>
     <span>{{user.name}}</span>
     <i class="el-icon-arrow-down" style="margin-left: 5px"></i>
     <el-dropdown-menu slot="dropdown">
       <el-dropdown-item @click.native="toUser">个人中心</el-dropdown-item>
       <el-dropdown-item @click.native="logOut">退出登录</el-dropdown-item>
     </el-dropdown-menu>
   </el-dropdown>
  </div>
</template>

<style scoped>

</style>