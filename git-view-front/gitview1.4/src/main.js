// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ECharts from 'vue-echarts/components/ECharts.vue'
import 'echarts/lib/chart/bar'
import 'echarts/lib/chart/pie'
import 'echarts/lib/component/tooltip'
import VueResource from 'vue-resource'
import store from './store'
Vue.config.productionTip = false
// import VueSession from 'vue-session'
Vue.use(ECharts)
Vue.use(VueResource)
// Vue.use(VueSession)
Vue.component('chart', ECharts)
/* eslint-disable no-new */

new Vue({
  el: '#app',
  router,
  store,
  template: '<App/>',
  components: { App }
})
router.beforeEach((to, from, next) => {
  if (to.meta.requireAuth) {
    // console.log(store)
    if (store.getters.islogined) {
      next()
    } else {
      alert('请先确认你是否已经登录')
      next({
        path: '/'
      })
    }
  } else {
    next()
  }
})
