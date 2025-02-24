<template>
  <div>
    <label for="neo4j-tactic">选择战术:</label>
    <select id="neo4j-tactic" v-model="selectedValue">
      <option v-for="item in neo4jData" :key="item.id" :value="item.id">
        {{ item.id }}({{ item.name }})
      </option>
    </select>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name:"MyGameOne",
  data() {
    return {
      neo4jData: [], // 用于存储从Neo4j获取的数据
      selectedValue: '' // 用于存储用户选择的值
    };
  },
  mounted() {
    this.fetchNeo4jData();
  },
  methods: {
    async fetchNeo4jData() {
      try {
        const response = await axios.get(this.$httpUrl+'/nodetactic/getalltactic');
        this.neo4jData = response.data;
        }
      catch (error) {
        console.error('获取数据失败:', error);
      }
    }
  }
};
</script>

<style scoped>
/* 样式调整 */
div {
  font-family: Arial, sans-serif;
  margin: 20px;
}

label {
  font-size: 16px;
  font-weight: bold;
}

select {
  margin-left: 10px;
  padding: 5px;
  font-size: 14px;
}

p {
  margin-top: 10px;
  font-size: 14px;
  color: #333;}
</style>