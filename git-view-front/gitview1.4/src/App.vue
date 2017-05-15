<template >
<body>
  <div id="app">
    <router-view></router-view>
  </div>
</body>
</template>

<script>
export default {
  name: 'app'
}
</script>

<style>
#app{
  width: 100%;
  min-height: 600px;
  /*min-width:1366px;*/
  margin: 0px;
  background-color: #333366;
}

</style>
