<script>
 import MyAside from "@/components/MyAside.vue";
 import MyHeader from "@/components/MyHeader.vue";
 /*import MyMain from "@/components/MyMain.vue";*/

 export default {
   name:"MyIndex",
   components: {MyHeader, MyAside},
   data(){
     return{
       isCollapse:false,
       aside_width:'200px',
       fold_icon:'el-icon-s-fold'
     }
   },
   methods:{
     doCollapse(){
       console.log(111)
       this.isCollapse=!this.isCollapse
       if(!this.isCollapse){//展开
        this.aside_width='200px'
         this.fold_icon='el-icon-s-fold'
       }else{//关闭
         this.aside_width='64px'
         this.fold_icon='el-icon-s-unfold'
       }
     }
   }
 };
</script>

<template>
  <el-container style="height: 100%; border: 1px solid #eee">
    <el-aside :width="aside_width" style="height: 100vh;background-color: rgb(238, 241, 246);">
     <MyAside :isCollapse="isCollapse"></MyAside>
    </el-aside>

    <el-container style="height: 100%">
      <el-header style="text-align: right; font-size: 12px; height: 100%;border-bottom: rgba(169,169,169,0.3) 1px solid;">
        <MyHeader @doCollapse="doCollapse" :fold_icon="fold_icon"></MyHeader>
      </el-header>

      <el-main style="height: 100%">
<!--       <MyMain></MyMain>-->
        <router-view/>
      </el-main>
    </el-container>
  </el-container>


</template>

<style scoped>
 .el-header {
   #background-color:#B3C0D1;
   color: #333;
   line-height: 60px;
 }
 .el-main{
   padding: 5px;
 }
 .el-aside {
   color: #333;
 }
</style>