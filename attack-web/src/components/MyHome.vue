<!--
<template>
  <div style="text-align: center;background-color: #ffffff;height: 100%;padding: 0px;margin: 0px;">
    <h1 style="font-size: 50px;">{{'欢迎你！'+user.name}}</h1>
    <el-descriptions  title="个人中心" :column="2" size="40" border>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-s-custom"></i>
          账号
        </template>
        {{user.no}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-mobile-phone"></i>
          电话
        </template>
        {{user.phone}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-location-outline"></i>
          性别
        </template>
        <el-tag
            :type="user.sex === '1' ? 'primary' : 'danger'"
            disable-transitions><i :class="user.sex==1?'el-icon-male':'el-icon-female'"></i>{{user.sex==1?"男":"女"}}</el-tag>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-tickets"></i>
          角色
        </template>
        <el-tag
            type="success"
            disable-transitions>{{user.roleId==0?"超级管理员":(user.roleId==1?"管理员":"用户")}}</el-tag>

      </el-descriptions-item>
    </el-descriptions>

    <DateUtils></DateUtils>
  </div>
</template>

<script>
import DateUtils from "./DateUtils";
export default {
  name: "MyHome",
  components: {DateUtils},
  data() {

    return {
      user:{}
    }
  },
  computed:{

  },
  methods:{
    init(){
      this.user = JSON.parse(sessionStorage.getItem('CurUser'))
    }
  },
  created(){
    this.init()
  }
}
</script>

<style scoped>
.el-descriptions{
  width:90%;

  margin: 0 auto;
  text-align: center;
}
</style>-->

<template>
  <div class="home-container">
    <div class="welcome-card">
      <h1 class="welcome-title">{{user.name}}</h1>
      <div class="user-info-grid">
        <div class="info-item">
          <i class="el-icon-s-custom"></i>
          <span class="label">账号</span>
          <span class="value">{{ user.no }}</span>
        </div>
        <div class="info-item">
          <i class="el-icon-mobile-phone"></i>
          <span class="label">电话</span>
          <span class="value">{{ user.phone }}</span>
        </div>
        <div class="info-item">
          <i class="el-icon-location-outline"></i>
          <span class="label">性别</span>
          <span class="value">
            <el-tag :type="user.sex === '1' ? 'primary' : 'danger'" disable-transitions>
              <i :class="user.sex == 1 ? 'el-icon-male' : 'el-icon-female'"></i>
              {{ user.sex == 1 ? "男" : "女" }}
            </el-tag>
          </span>
        </div>
        <div class="info-item">
          <i class="el-icon-tickets"></i>
          <span class="label">角色</span>
          <span class="value">
            <el-tag type="success" disable-transitions>
              {{ user.roleId == 0 ? "超级管理员" : user.roleId == 1 ? "管理员" : "用户" }}
            </el-tag>
          </span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "MyHome",
  data() {
    return {
      user: {},
    };
  },
  methods: {
    init() {
      this.user = JSON.parse(sessionStorage.getItem("CurUser"));
    },
  },
  created() {
    this.init();
  },
};
</script>

<style scoped>
.home-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #ffffff, #ffffff);
  padding: 10px 20px;
}

.welcome-card {
  background: rgba(255, 255, 255, 0.9);
  border-radius: 12px;
  padding: 30px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
  max-width: 600px;
  width: 100%;
  text-align: center;
  margin-top: -200px;
}

.welcome-title {
  font-size: 36px;
  color: #333;
  margin-bottom: 24px;
}

.user-info-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
}

.info-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 15px;
  background: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
}

.info-item i {
  font-size: 24px;
  color: #667eea;
  margin-bottom: 10px;
}

.label {
  font-size: 14px;
  color: #666;
  margin-bottom: 5px;
}

.value {
  font-size: 16px;
  color: #333;
}

.el-tag {
  margin-top: 5px;
}
</style>
