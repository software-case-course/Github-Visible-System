<template >
<body>
  <div id="app">
    <router-view></router-view>
  </div>
</body>
</template>

<script>
export default {
  name: 'app',
  // created () {
  //   this.$session.start()
  // }
}
</script>

<style>
#app{
  background-color: #ccc;
}

</style>
