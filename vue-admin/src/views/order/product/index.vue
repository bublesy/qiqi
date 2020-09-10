<template>
  <div id="print">
    <p class="font">海宁市中奇纸箱包装厂</p>
    <p class="font2">生产 制造单</p>
    <el-button v-print="'#print'" type="primary">打印</el-button>
    <!-- <div id="app" style="padding:20px;">
      <div class="panel panel-primary">
        <div class="panel-heading">用户管理</div>
        <table class="table table-bordered table-striped text-center">
          <thead>
            <tr>
              <th>序号</th>
              <th>用户名</th>
              <th>年龄</th>
              <th>毕业学校</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(user,index) in users">
              <td>{{ index+1 }}</td>
              <td>{{ user.name }}</td>
              <td>{{ user.age }}</td>
              <td>{{ user.school }}</td>
              <td><button @click="remove(index)">remove</button></td>
            </tr>
            <tr>
              <td />
              <td><input id="name" v-model="user.name" type="text"></td>
              <td><input id="age" v-model="user.age"type="text"></td>
              <td><input id="school" v-model="user.school"type="text"></td>
              <td><button @click="insert">insert</button></td>
            </tr>
          </tbody>
        </table>
      </div>
    </div> -->
  </div>
</template>

<script>
export default {
  name: 'Product'
}
</script>

<style>
.font{
  font-weight: bold;
  width:400px;
  height: 100px;
  line-height: 100px;
  font-size: 30px;
  font-family: 'Courier New', Courier, monospace;
  /* text-align: center; */
  margin-left: 40%;
}
.font2{
  width:400px;
  height: 100px;
  line-height: 10px;
  font-size: 30px;
  font-family: 'Courier New', Courier, monospace;
  /* text-align: center; */
  margin-left: 45%;
  font-weight: bold;
}

</style>
