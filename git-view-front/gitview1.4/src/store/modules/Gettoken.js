const state = {
  codestr: '',
  tokenstr: '',
  homeurl: '',
  islogined: false
}
const getters = {
  codestr: state => state.codestr,
  tokenstr: state => state.tokenstr,
  islogined: state => state.islogined,
  homeurl: state => state.homeurl
}
const mutations = {
  getcodestr (state, code) {
    state.codestr = code
  },
  gettokenstr (state, token) {
    state.tokenstr = token
  },
  setislogined (state, login) {
    state.islogined = login
    // console.log(state.islogined)
  },
  sethomeurl (state, url) {
    state.homeurl = url
  }
}
const actions = {
  getcodestr ({commit}) {
    commit('getcodestr')
  },
  gettokenstr ({commit}) {
    commit('gettokenstr')
  },
  setislogined ({commit}) {
    commit('setislogined')
  },
  sethomeurl ({commit}) {
    commit('sethomeurl')
  }
}
export default {
  state,
  getters,
  actions,
  mutations
}
