<script>
 export default {
   name:"MyAside",
   data(){
     return{
       //isCollapse:false
       /*menu:[
         {
           menuClick:"MyAdmin",
           menuName:"管理员管理",
           menuIcon:"el-icon-s-custom"
         },
         {
           menuClick:"MyUser",
           menuName:"用户管理",
           menuIcon:"el-icon-user-solid"
         }
       ]*/
     }
   },
   computed:{
     "menu":{
       get(){
         return this.$store.state.menu
       }
     }
   },
   props:{
     isCollapse:Boolean
   }
 };
</script>

<template>
  <el-menu  background-color="#545c64"
            text-color="#fff"
            active-text-color="#ffd04b"
            style="height:100%"
            default-active="/MyHome"
            :collapse="isCollapse"
            :collapse-transition="false"
            router>
    <el-menu-item index="/MyHome">
      <i class="el-icon-s-home"></i>
      <span slot="title">首页</span>
    </el-menu-item>

    <el-menu-item :index="'/'+item.menuclick" v-for="(item,i) in menu" :key="i">
      <i :class="item.menuicon"></i>
      <span slot="title">{{ item.menuname }}</span>
    </el-menu-item>
  </el-menu>
</template>

<style scoped>

</style>