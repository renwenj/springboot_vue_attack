<template>
  <div>
    <div class="select-group">
    <label for="neo4j-tactic">选择战术:</label>
    <select id="neo4j-tactic" v-model="selectedTactic" @change="fetchTechnics">
      <option v-for="tactic in neo4jTactic" :key="tactic.id" :value="tactic.id">
        {{ tactic.id }}({{ tactic.name }})
      </option>
    </select>
    </div>

    <div class="select-group">
    <label for="neo4j-technic">选择技术:</label>
    <select id="neo4j-technic" v-model="selectedTechnic" @change="fetchTechnicDetails">
      <option v-for="technic in neo4jTechnic" :key="technic.id" :value="technic.id">
        {{ technic.id }}({{ technic.name }})
      </option>
    </select>
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

    <div v-if="technicDetails" class="table-container">
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
        <tr v-for="detail in technicDetails" :key="detail.no">
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

    <div v-if="mitigationMeasures.length > 0" class="mitigation-measures">
      <h3>缓解措施:</h3>
      <div v-for="measure in mitigationMeasures" :key="measure.id0" class="measure-item">
        <input type="checkbox" :id="measure.id" :value="measure.id" v-model="selectedMitigations">
        <label :for="measure.id">
          <strong>{{ measure.id }}: {{ measure.name }}</strong>
        </label>
      </div>
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
      neo4jTactic: [],
      neo4jTechnic:[],
      selectedTactic: '',
      selectedTechnic: '',
      technicDetails: [],
      errorMessage:'',
      mitigationMeasures: [],
      selectedMitigations: [],
      nodes: [],
      selectedNode: '',
      vulnerabilityLevel: '',
      calculationResult: ''
    };
  },
  mounted() {
    this.fetchNeo4jTactic();
    this.fetchNodes();
  },
  methods: {

    async calculate() {
      const technicResponse = await axios.get(this.$httpUrl + '/tech/findByNo?no=' + this.selectedTechnic);
      const technicCa0 = technicResponse.data.data[0].ca0;
      const technicCa1 = technicResponse.data.data[0].ca1;
      const technicPa0 = technicResponse.data.data[0].pa0;
      const technicPa1 = technicResponse.data.data[0].pa1;
      const technicR = technicResponse.data.data[0].r;
      console.log(technicCa0);
      console.log(technicCa1);
      console.log(technicPa0);
      console.log(technicPa1);
      console.log(technicR);
      if (!Array.isArray(this.selectedMitigations)) {
        console.error('selectedMitigations不是数组');
      }
      const promises = this.selectedMitigations.map(async (mitigationId) => {
        try {
          const mitiResponse = await axios.get(this.$httpUrl + '/miti/findByNo?no=' + mitigationId);
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
      const mitiResults = await Promise.all(promises);
      const mitiAllData = mitiResults.flat();

      let totalE = 0, totalCd = 0, mitiCount = 0;

      mitiAllData.forEach(item => {
        if (item.e !== undefined && item.cd !== undefined) {
          totalE += parseFloat(item.e);
          totalCd += parseFloat(item.cd);
          mitiCount++;
        }
      });
      const avgE = mitiCount > 0 ? totalE / mitiCount : 0;
      const avgCd = mitiCount > 0 ? totalCd / mitiCount : 0;
      console.log(avgE);
      console.log(avgCd);

      const nodeResponse = await axios.get(this.$httpUrl + '/node/findById?id=' + this.selectedNode);
      const nodeImp = nodeResponse.data.data[0].imp;
      console.log(nodeImp);

      const vulLevel = this.vulnerabilityLevel;
      console.log(vulLevel);

      const gameL = vulLevel * nodeImp;
      const gameR = 1.2 * gameL;

      const A = avgE - avgCd;
      const B = technicPa0 * gameR - technicCa0 - 2 * technicR;
      const C = technicPa0 * gameL - technicPa1 * gameL + avgE - avgCd;
      const D = technicPa1 * gameR - technicCa1 - 2 * technicR;
      console.log(A)
      console.log(B)
      console.log(C)
      console.log(D)
      if (A < 0 && B < 0 && C < 0 && D < 0) {
        this.calculationResult = '防御者不追加ATT&CK防御,攻击者不采取ATT&CK攻击';
        console.log(1)
      }else if(A < 0 && B < 0 && C < 0 && D > 0){
        this.calculationResult = '防御者不追加ATT&CK防御,攻击者不采取ATT&CK攻击';
        console.log(2)
      }else if(A < 0 && B < 0 && C > 0 && D < 0){
        this.calculationResult = '防御者不追加ATT&CK防御,攻击者不采取ATT&CK攻击';
        console.log(3)
      }else if(A<0 && B>0 && C<0 && D<0){
        this.calculationResult='防御者不追加ATT&CK防御,攻击者采取ATT&CK攻击';
        console.log(4)
      }else if(A<0 && B>0 && C<0 && D>0){
        this.calculationResult='防御者不追加ATT&CK防御,攻击者采取ATT&CK攻击';
        console.log(5)
      }else if(A>0 && B>0 && C<0 && D>0){
        this.calculationResult='防御者不追加ATT&CK防御,攻击者采取ATT&CK攻击';
        console.log(6)
      }else if(A>0 && B<0 && C<0 && D<0){
        this.calculationResult='防御者追加ATT&CK防御,攻击者不采取ATT&CK攻击';
        console.log(7)
      }else if(A>0 && B<0 && C>0 && D<0){
        this.calculationResult='防御者追加ATT&CK防御,攻击者不采取ATT&CK攻击';
        console.log(8)
      }else if(A>0 && B>0 && C>0 && D<0){
        this.calculationResult='防御者追加ATT&CK防御,攻击者不采取ATT&CK攻击';
        console.log(9)
      }else if(A<0 && B>0 && C>0 && D>0){
        this.calculationResult='防御者追加ATT&CK防御,攻击者采取ATT&CK攻击';
        console.log(10)
      }else if(A>0 && B>0 && C>0 && D>0){
        this.calculationResult='防御者追加ATT&CK防御,攻击者采取ATT&CK攻击';
        console.log(11)
      }else if(A>0 && B<0 && C>0 && D>0){
        this.calculationResult='防御者追加ATT&CK防御,攻击者采取ATT&CK攻击';
        console.log(12)
      }else{
        this.calculationResult='混沌';
        console.log(13)
      }
    },
    async fetchNeo4jTactic() {
      try {
        const response = await axios.get(this.$httpUrl+'/nodetactic/getalltactic');
        this.neo4jTactic = response.data;
        }
      catch (error) {
        console.error('获取战术数据失败:', error);
        this.errorMessage = '获取战术数据失败，请稍后重试';
      }
    },
    async fetchTechnics() {
      if (!this.selectedTactic) return; // 如果没有选择战术，直接返回
      try {
        const response = await axios.get(this.$httpUrl+'/nodetactic/getnodetactic?id='+this.selectedTactic);
        this.neo4jTechnic = response.data.technicRelation.map(relation => relation.nodeTechnic);
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
    async fetchTechnicDetails() {
      if (!this.selectedTechnic) return; // 如果没有选择技术，直接返回
      try {
        const detailResponse = await axios.get(this.$httpUrl + '/tech/findByNo?no=' + this.selectedTechnic);
        this.technicDetails = detailResponse.data.data;

        const mitigationResponse = await axios.get(this.$httpUrl + '/nodetechnic/getnodetechnic?id='+this.selectedTechnic);
        this.mitigationMeasures = mitigationResponse.data.mitigationRelation.map(relation => relation.nodeMitigation);
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