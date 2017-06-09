const state = {
  permsgJson: [],
  reposmsgJson: [],
  languagemsg: []
}
const getters = {
  permsgJson: state => state.permsgJson,
  reposmsgJson: state => state.reposrmsgJson,
  languagemsg: state => state.languagemsg
}
const mutations = {
  setpermsg (state, permsg) {
    state.permsgJson = permsg
  },
  setreposmsg (state, reposmsg) {
    state.reposrmsgJson = reposmsg
  },
  setlanguagemsg (state, lanmsg) {
    state.languagemsg = lanmsg
  }
}
const actions = {
  setpermsg ({commit}) {
    commit('setpermsg')
  },
  setreposmsg ({commit}) {
    commit('setreposmsg')
  },
  setlanguagemsg ({commit}) {
    commit('setlanguagemsg')
  }
}
export default {
  state,
  getters,
  actions,
  mutations
}
