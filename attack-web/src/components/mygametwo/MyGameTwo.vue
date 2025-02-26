<template>
  <div>
    <div class="select-group">
    <label for="neo4j-tactic1">选择战术1:</label>
    <select id="neo4j-tactic1" v-model="selectedTactic1" @change="fetchTechnics1">
      <option v-for="tactic in neo4jTactic1" :key="tactic.id" :value="tactic.id">
        {{ tactic.id }}({{ tactic.name }})
      </option>
    </select>
    </div>

    <div class="select-group">
    <label for="neo4j-technic1">选择技术1:</label>
    <select id="neo4j-technic1" v-model="selectedTechnic1" @change="fetchTechnicDetails1">
      <option v-for="technic in neo4jTechnic1" :key="technic.id" :value="technic.id">
        {{ technic.id }}({{ technic.name }})
      </option>
    </select>
    </div>

    <div v-if="technicDetails1" class="table-container">
      <table class="styled-table">
        <thead>
        <tr>
          <th>技术编号</th>
          <th>无防御攻击成本</th>
          <th>有防御攻击成本</th>
          <th>无防御攻击概率</th>
          <th>有防御攻击概率</th>
          <th>攻击风险</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="detail in technicDetails1" :key="detail.no">
          <td>{{ detail.no }}</td>
          <td>{{ detail.ca0 }}</td>
          <td>{{ detail.ca1 }}</td>
          <td>{{ detail.pa0 }}</td>
          <td>{{ detail.pa1 }}</td>
          <td>{{ detail.r }}</td>
        </tr>
        </tbody>
      </table>
    </div>

    <div v-if="mitigationMeasures1.length > 0" class="mitigation-measures">
      <h3>缓解措施1:</h3>
      <div v-for="measure in mitigationMeasures1" :key="measure.id0" class="measure-item">
        <input type="checkbox" :id="'mitigation1-' +measure.id" :value="measure.id" v-model="selectedMitigations1">
        <label :for="measure.id">
          <strong>{{ measure.id }}: {{ measure.name }}</strong>
        </label>
      </div>
    </div>

    <div class="select-group">
      <label for="neo4j-tactic2">选择战术2:</label>
      <select id="neo4j-tactic2" v-model="selectedTactic2" @change="fetchTechnics2">
        <option v-for="tactic in neo4jTactic2" :key="tactic.id" :value="tactic.id">
          {{ tactic.id }}({{ tactic.name }})
        </option>
      </select>
    </div>

    <div class="select-group">
      <label for="neo4j-technic2">选择技术2:</label>
      <select id="neo4j-technic2" v-model="selectedTechnic2" @change="fetchTechnicDetails2">
        <option v-for="technic in neo4jTechnic2" :key="technic.id" :value="technic.id">
          {{ technic.id }}({{ technic.name }})
        </option>
      </select>
    </div>

    <div v-if="technicDetails2" class="table-container">
      <table class="styled-table">
        <thead>
        <tr>
          <th>技术编号</th>
          <th>无防御攻击成本</th>
          <th>有防御攻击成本</th>
          <th>无防御攻击概率</th>
          <th>有防御攻击概率</th>
          <th>攻击风险</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="detail in technicDetails2" :key="detail.no">
          <td>{{ detail.no }}</td>
          <td>{{ detail.ca0 }}</td>
          <td>{{ detail.ca1 }}</td>
          <td>{{ detail.pa0 }}</td>
          <td>{{ detail.pa1 }}</td>
          <td>{{ detail.r }}</td>
        </tr>
        </tbody>
      </table>
    </div>

    <div v-if="mitigationMeasures2.length > 0" class="mitigation-measures">
      <h3>缓解措施2:</h3>
      <div v-for="measure in mitigationMeasures2" :key="measure.id0" class="measure-item">
        <input type="checkbox" :id="'mitigation2-' +measure.id" :value="measure.id" v-model="selectedMitigations2">
        <label :for="measure.id">
          <strong>{{ measure.id }}: {{ measure.name }}</strong>
        </label>
      </div>
    </div>

    <div class="select-group">
      <label for="neo4j-node">选择节点:</label>
      <select id="neo4j-node" v-model="selectedNode" @change="fetchNodeDetails">
        <option v-for="node in nodes" :key="node.id" :value="node.id">
          {{ node.name }}({{ node.imp }})
        </option>
      </select>
    </div>

    <div class="select-group">
      <label for="vulnerability-level">漏洞等级:</label>
      <input
          id="vulnerability-level"
          type="number"
          v-model.number="vulnerabilityLevel"
          min="1"
          max="3"
      />
    </div>



    <div class="select-group">
      <button @click="calculate" class="styled-button" style="padding: 10px 20px; background-color: #4CAF50; color: white; border: none; border-radius: 5px; cursor: pointer;">开始推演</button>
      <span v-if="calculationResult" class="result-text">推演结果为: {{ calculationResult }}</span>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name:"MyGameOne",
  data() {
    return {
      neo4jTactic1: [],
      neo4jTechnic1:[],
      selectedTactic1: '',
      selectedTechnic1: '',
      technicDetails1: [],
      errorMessage:'',
      mitigationMeasures1: [],
      selectedMitigations1: [],
      nodes: [],
      selectedNode: '',
      vulnerabilityLevel: '',
      calculationResult: '',
      neo4jTactic2: [],
      neo4jTechnic2:[],
      selectedTactic2: '',
      selectedTechnic2: '',
      technicDetails2: [],
      mitigationMeasures2: [],
      selectedMitigations2: []
    };
  },
  mounted() {
    this.fetchNeo4jTactic1();
    this.fetchNodes();
    this.fetchNeo4jTactic2();
  },
  methods: {

    async calculate() {
      const technicResponse1 = await axios.get(this.$httpUrl + '/tech/findByNo?no=' + this.selectedTechnic1);
      const technicCa0_1 = technicResponse1.data.data[0].ca0;
      const technicCa1_1 = technicResponse1.data.data[0].ca1;
      const technicPa0_1 = technicResponse1.data.data[0].pa0;
      const technicPa1_1 = technicResponse1.data.data[0].pa1;
      const technicR_1 = technicResponse1.data.data[0].r;
      console.log(technicCa0_1);
      console.log(technicCa1_1);
      console.log(technicPa0_1);
      console.log(technicPa1_1);
      console.log(technicR_1);

      const technicResponse2 = await axios.get(this.$httpUrl + '/tech/findByNo?no=' + this.selectedTechnic2);
      const technicCa0_2 = technicResponse2.data.data[0].ca0;
      const technicCa1_2 = technicResponse2.data.data[0].ca1;
      const technicPa0_2 = technicResponse2.data.data[0].pa0;
      const technicPa1_2 = technicResponse2.data.data[0].pa1;
      const technicR_2 = technicResponse2.data.data[0].r;
      console.log(technicCa0_2);
      console.log(technicCa1_2);
      console.log(technicPa0_2);
      console.log(technicPa1_2);
      console.log(technicR_2);

      if (!Array.isArray(this.selectedMitigations1)) {
        console.error('selectedMitigations不是数组');
      }
      const promises1 = this.selectedMitigations1.map(async (mitigationId) => {
        try {
          const mitiResponse = await axios.get(this.$httpUrl+'/miti/findByNo?no='+mitigationId);
          if (mitiResponse.data && mitiResponse.data.code === 200 && Array.isArray(mitiResponse.data.data)) {
            return mitiResponse.data.data;
          } else {
            console.warn(`No data found or unexpected response format for ID ${mitigationId}`);
            return [];
          }
        } catch (error) {
          console.error(`Error fetching data for mitigation ID ${mitigationId}:`, error);
          return [];
        }
      });
      const mitiResults1 = await Promise.all(promises1);
      const mitiAllData1 = mitiResults1.flat();

      let totalE1 = 0, totalCd1 = 0, mitiCount1 = 0;

      mitiAllData1.forEach(item => {
        if (item.e !== undefined && item.cd !== undefined) {
          totalE1 += parseFloat(item.e);
          totalCd1 += parseFloat(item.cd);
          mitiCount1++;
        }
      });
      const avgE1 = mitiCount1 > 0 ? totalE1 / mitiCount1 : 0;
      const avgCd1 = mitiCount1 > 0 ? totalCd1 / mitiCount1 : 0;
      console.log(avgE1);
      console.log(avgCd1);

      if (!Array.isArray(this.selectedMitigations2)) {
        console.error('selectedMitigations不是数组');
      }
      const promises2 = this.selectedMitigations2.map(async (mitigationId) => {
        try {
          const mitiResponse = await axios.get(this.$httpUrl+'/miti/findByNo?no='+mitigationId);
          if (mitiResponse.data && mitiResponse.data.code === 200 && Array.isArray(mitiResponse.data.data)) {
            return mitiResponse.data.data;
          } else {
            console.warn(`No data found or unexpected response format for ID ${mitigationId}`);
            return [];
          }
        } catch (error) {
          console.error(`Error fetching data for mitigation ID ${mitigationId}:`, error);
          return [];
        }
      });
      const mitiResults2 = await Promise.all(promises2);
      const mitiAllData2 = mitiResults2.flat();

      let totalE2 = 0, totalCd2 = 0, mitiCount2 = 0;

      mitiAllData2.forEach(item => {
        if (item.e !== undefined && item.cd !== undefined) {
          totalE2 += parseFloat(item.e);
          totalCd2 += parseFloat(item.cd);
          mitiCount1++;
        }
      });
      const avgE2 = mitiCount2 > 0 ? totalE2 / mitiCount2 : 0;
      const avgCd2 = mitiCount2 > 0 ? totalCd2 / mitiCount2 : 0;
      console.log(avgE2);
      console.log(avgCd2);

      const avgE=(avgE1+avgE2)/2;
      const avgCd=(avgCd1+avgCd2)/2;

      console.log(avgE);
      console.log(avgCd);

      const nodeResponse = await axios.get(this.$httpUrl + '/node/findById?id=' + this.selectedNode);
      const nodeImp = nodeResponse.data.data[0].imp;
      console.log(nodeImp);

      const vulLevel = this.vulnerabilityLevel;
      console.log(vulLevel);

      const gameL=vulLevel*nodeImp;
      const gameR=1.2*gameL;

      const A=avgE-avgCd+technicPa0_2*gameL-technicPa1_2*gameL;
      const B=technicCa0_2-technicCa0_1-technicR_1+technicR_2-technicPa0_2*gameR+technicPa0_1*gameR;
      const C=avgE-avgCd+technicPa0_1*gameL-technicPa1_1*gameL;
      const D=technicCa1_2-technicCa1_1-technicR_1+technicR_2-technicPa1_2*gameR+technicPa1_1*gameR;
      if( A<0 && B<0 && C<0 && D<0){
        this.calculationResult='防御者不追加ATT&CK防御,攻击者采取策略2攻击';
      }else if(A<0 && B<0 && C<0 && D>0){
        this.calculationResult='防御者不追加ATT&CK防御,攻击者采取策略2攻击';
      }else if(A<0 && B>0 && C<0 && D<0){
        this.calculationResult='防御者不追加ATT&CK防御,攻击者采取策略1攻击';
      }else if(A<0 && B>0 && C<0 && D>0){
        this.calculationResult='防御者不追加ATT&CK防御,攻击者采取策略1攻击';
      }else if(A>0 && B<0 && C>0 && D<0){
        this.calculationResult='防御者追加ATT&CK防御,攻击者采取策略2攻击';
      }else if(A>0 && B>0 && C>0 && D<0){
        this.calculationResult='防御者追加ATT&CK防御,攻击者采取策略2攻击';
      } else{
        this.calculationResult='防御者追加ATT&CK防御,攻击者采取策略1攻击';
      }
    },
    async fetchNeo4jTactic1() {
      try {
        const response = await axios.get(this.$httpUrl+'/nodetactic/getalltactic');
        this.neo4jTactic1 = response.data;
        }
      catch (error) {
        console.error('获取战术数据失败:', error);
        this.errorMessage = '获取战术数据失败，请稍后重试';
      }
    },
    async fetchNeo4jTactic2() {
      try {
        const response = await axios.get(this.$httpUrl+'/nodetactic/getalltactic');
        this.neo4jTactic2 = response.data;
      }
      catch (error) {
        console.error('获取战术数据失败:', error);
        this.errorMessage = '获取战术数据失败，请稍后重试';
      }
    },
    async fetchTechnics1() {
      if (!this.selectedTactic1) return; // 如果没有选择战术，直接返回
      try {
        const response = await axios.get(this.$httpUrl+'/nodetactic/getnodetactic?id='+this.selectedTactic1);
        this.neo4jTechnic1 = response.data.technicRelation.map(relation => relation.nodeTechnic);
      } catch (error) {
        console.error('获取技术数据失败:', error);
        this.errorMessage = '获取技术数据失败，请稍后重试';
      }
    },
    async fetchTechnics2() {
      if (!this.selectedTactic2) return; // 如果没有选择战术，直接返回
      try {
        const response = await axios.get(this.$httpUrl+'/nodetactic/getnodetactic?id='+this.selectedTactic2);
        this.neo4jTechnic2 = response.data.technicRelation.map(relation => relation.nodeTechnic);
      } catch (error) {
        console.error('获取技术数据失败:', error);
        this.errorMessage = '获取技术数据失败，请稍后重试';
      }
    },
    /*async fetchNodeDetails() {
      if (!this.selectedNode) return; // 如果没有选择节点，直接返回
      try {
        const response = await axios.get(this.$httpUrl + '/node/findById?id=' + this.selectedNode);
        console.log(response.data);
      } catch (error) {
        console.error('获取节点详细信息失败:', error);
        this.errorMessage = '获取节点详细信息失败，请稍后重试';
      }
    },*/
    async fetchNodes() {
      //if (!this.selectedNode) return; // 如果没有选择节点，直接返回
      try {
        const response = await axios.get(this.$httpUrl + '/node/list');
        this.nodes = response.data.data;
      } catch (error) {
        console.error('获取节点信息失败:', error);
        this.errorMessage = '获取节点信息失败，请稍后重试';
      }
    },
    async fetchTechnicDetails1() {
      if (!this.selectedTechnic1) return; // 如果没有选择技术，直接返回
      try {
        const detailResponse = await axios.get(this.$httpUrl + '/tech/findByNo?no=' + this.selectedTechnic1);
        this.technicDetails1 = detailResponse.data.data;

        const mitigationResponse = await axios.get(this.$httpUrl + '/nodetechnic/getnodetechnic?id='+this.selectedTechnic1);
        this.mitigationMeasures1 = mitigationResponse.data.mitigationRelation.map(relation => relation.nodeMitigation);
      } catch (error) {
        console.error('获取技术详细信息失败:', error);
        this.errorMessage = '获取技术详细信息失败，请稍后重试';
      }
    },
    async fetchTechnicDetails2() {
      if (!this.selectedTechnic2) return; // 如果没有选择技术，直接返回
      try {
        const detailResponse = await axios.get(this.$httpUrl + '/tech/findByNo?no=' + this.selectedTechnic2);
        this.technicDetails2 = detailResponse.data.data;

        const mitigationResponse = await axios.get(this.$httpUrl + '/nodetechnic/getnodetechnic?id='+this.selectedTechnic2);
        this.mitigationMeasures2 = mitigationResponse.data.mitigationRelation.map(relation => relation.nodeMitigation);
      } catch (error) {
        console.error('获取技术详细信息失败:', error);
        this.errorMessage = '获取技术详细信息失败，请稍后重试';
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

/*.select-group {
  margin-bottom: 20px;
}*/
.select-group {
  /*display: inline-block;*/
  display: block;
  margin-right: 20px; /* 调整间距 */
}

input[type="number"] {
  margin-left: 10px;
  padding: 5px;
  font-size: 14px;
  width: 50px; /* 根据需要调整宽度 */
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

.table-container {
  margin-top: 20px;
  overflow-x: auto; /* 如果表格内容过多，可以横向滚动 */
}

.styled-table {
  width: 100%;
  border-collapse: collapse;
  font-family: Arial, sans-serif;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.15); /* 添加阴影效果 */
}

.styled-table th,
.styled-table td {
  padding: 12px 15px;
  text-align: center;
}

.styled-table th {
  background-color: #009879; /* 表头背景色 */
  color: #ffffff; /* 表头文字颜色 */
  font-weight: bold;
}

.styled-table tbody tr {
  border-bottom: 1px solid #dddddd;
}

.styled-table tbody tr:nth-of-type(even) {
  background-color: #f3f3f3; /* 偶数行背景色 */
}

.styled-table tbody tr:last-of-type {
  border-bottom: 2px solid #009879; /* 最后一行底部边框 */
}

.styled-table tbody tr:hover {
  background-color: #f1f1f1; /* 悬停效果 */
  cursor: pointer;
}

/* 响应式设计：在小屏幕上调整表格样式 */
@media (max-width: 768px) {
  .styled-table {
    font-size: 14px;
  }
  .styled-table th,
  .styled-table td {
    padding: 8px 10px;
  }
}

.mitigation-measures {
  margin-top: 20px;
  padding: 15px;
  border: 1px solid #ddd;
  border-radius: 5px;
  background-color: #f9f9f9;
}

.mitigation-measures h3 {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 15px;
}

.measure-item {
  margin-bottom: 10px;
  padding: 10px;
  border: 1px solid #eee;
  border-radius: 3px;
  background-color: #fff;
}

.measure-item label {
  margin-left: 10px;
  cursor: pointer;
}

.measure-item strong {
  font-size: 16px;
  color: #333;
}

.measure-description {
  display: block;
  margin-top: 5px;
  font-size: 14px;
  color: #666;
}

.result-text {
  margin-left: 10px;
  font-size: 16px;
  color: #4CAF50; /* 绿色字体 */
  font-weight: bold;
}
</style>