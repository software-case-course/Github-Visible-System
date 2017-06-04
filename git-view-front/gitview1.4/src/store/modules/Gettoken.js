const state = {
  codestr: '',
  tokenstr: '',
  islogined: false
}
const getters = {
  codestr: state => state.codestr,
  tokenstr: state => state.tokenstr,
  islogined: state => state.islogined
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
    console.log(state.islogined)
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
  }
}
export default {
  state,
  getters,
  actions,
  mutations
}
