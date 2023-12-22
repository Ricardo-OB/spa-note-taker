<template>
  <div class="container-1">
    <div class="images">
      <img alt="Vue logo" src="./assets/logo.png" width="120px">
      <img alt="Notes logo" src="./assets/logonotes.png" width="120px">
    </div>
    <div class="text-title">
      <h1>Notes Manager</h1>
    </div>
  </div>
  <HelloWorld msg="Welcome to Your Vue.js App"/>
</template>

<script>
import HelloWorld from './components/HelloWorld.vue'

export default {
  name: 'App',
  components: {
    HelloWorld
  }
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;700&display=swap');

#app {
  font-family: 'Roboto', sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

.container-1 {
  border-radius: 5px 5px 0px 0px;
  background: hsla(32, 100%, 57%, 1);
  background: linear-gradient(180deg, hsla(32, 100%, 57%, 1) 0%, hsla(15, 31%, 79%, 1) 100%);
  background: -moz-linear-gradient(180deg, hsla(32, 100%, 57%, 1) 0%, hsla(15, 31%, 79%, 1) 100%);
  background: -webkit-linear-gradient(180deg, hsla(32, 100%, 57%, 1) 0%, hsla(15, 31%, 79%, 1) 100%);
  filter: progid: DXImageTransform.Microsoft.gradient( startColorstr="#FF9A26", endColorstr="#DBC2BA", GradientType=1 );
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  align-content: center;
  justify-content: flex-start;
  align-items: center;
  padding: 30px 0px;
}

.images {
  display: flex;
  margin-left: 70px;
}

.text-title {
  margin-left: 40px;
}

.text-title h1 {
  font-size: 50px;
}

</style>
