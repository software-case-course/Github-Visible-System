import Vue from 'vue'
import vuex from 'vuex'
import * as actions from './actions'
import * as getters from './getters'
import showNav from './modules/showNav'
import gettoken from './modules/Gettoken'
import permsg from './modules/Permsg'
Vue.use(vuex)

export default new vuex.Store({
  actions,
  getters,
  modules: {showNav, gettoken, permsg}
})
