<template>
    <div class="world_content">
        <transition name="slide">
            <div v-show="this.shownav" class="information-box">
                <!--<form>
      </form>-->
                <!--<div class="user-head-picture-box"></div>-->
                <img src="../assets/images/ic_arrow_drop_up_white_36dp_1x.png" class="slide-up-icon" @click="setshow" />
            </div>
        </transition>
        <transition name="fade">
            <div v-show="this.shownav" class="background-shelter" @click="setshow"></div>
        </transition>
        <div>
            <chart id="allmapchart" class="allmapchart"></chart>
        </div>
        <div class="backbutton">
            <img src="../assets/images/ic_arrow_back_white_36dp_1x.png" @click="toworld">
        </div>
        <div>
            <chart id="jsmapchart" class="jsmapchart"></chart>
            <chart id="javamapchart" class="javamapchart"></chart>
            <chart id="pythonmapchart" class="pythonmapchart"></chart>
            <chart id="rubymapchart" class="rubymapchart"></chart>
            <chart id="phpmapchart" class="phpmapchart"></chart>
            <chart id="cssmapchart" class="cssmapchart"></chart>
            <chart id="cplusmapchart" class="cplusmapchart"></chart>
            <chart id="cmapchart" class="cmapchart"></chart>
            <chart id="shellmapchart" class="shellmapchart"></chart>
            <chart id="ocmapchart" class="ocmapchart"></chart>
        </div>
    </div>
</template>

<script>
    import _ from 'lodash'
    import echarts from 'echarts'
    import {
        mapGetters,
        mapActions
    } from 'vuex'
    // import worldJson from '../../node_modules/map/world.json'
    export default {
        name: 'worlddis',
        mounted() {
            this.$nextTick(function () {
                var ndate = new Date()
                ndate.toLocaleDateString()
                this.year = ndate.getFullYear()
                this.getdisdata()
            })
        },
        computed: {
            ...mapGetters(['shownav'])
        },
        data() {
            return {
                worlddata: [],
                usadata: [],
                chinadata: [],
                jsdata: [],
                javadata: [],
                pythondata: [],
                rubydata: [],
                phpdata: [],
                cssdata: [],
                cplusdata: [],
                cdata:[],
                shelldata: [],
                objectcdata:[],
                chart: null,
                year: '',
                maxnum: '',
                option: {
                    width: '250%',
                    title: {
                        text: 'github 用户全球分布图',
                        subtext: '',
                        x: 'center',
                        y: 'top',
                        textStyle: {
                            color: '#ccc'
                        }
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: function (params) {
                            var value = (params.value + '').split('.')
                            return params.seriesName + '<br/>' + params.name + ' : ' + value
                        }
                    },
                    toolbox: {
                        show: true,
                        orient: 'horizontal',
                        x: 'center',
                        y: 'bottom',
                        feature: {
                            mark: {
                                show: true
                            },
                            dataView: {
                                show: true,
                                readOnly: false
                            },
                            restore: {
                                show: true
                            },
                            saveAsImage: {
                                show: true
                            }
                        }
                    },
                    dataRange: {
                        min: 0,
                        max: 200000,
                        text: ['High', 'Low'],
                        realtime: false,
                        calculable: true,
                        color: ['#368f45', '#68da7b', '#9dffad'],
                        textStyle: {
                            color: '#ccc'
                        }
                    },
                    series: [{
                        width: '100%',
                        height: '80%',
                        left: 'cenetr',
                        top: 'center',
                        name: 'github用户分布图',
                        type: 'map',
                        mapType: 'world',
                        roam: false,
                        mapLocation: {
                            y: 60
                        },
                        itemStyle: {
                            emphasis: {
                                label: {
                                    show: true
                                }
                            }
                        },
                        data: []
                    }]
                }
            }
        },
        methods: {
            ...mapActions(['changenav']),
            setshow() {
                this.changenav()
            },
            async getdisdata() {
                var data1 = []
                var data2 = []
                var data3 = []
                var landata = []
                await this.$http.get('http://www.kongin.cn/git-view/search/country').then(response => { // 获取全球数据
                    // console.log(response)
                    for (let i = 0; i < response.body.length; i++) {
                        let res = response.body[i]
                        var name = this.allfilter(res.country)
                        var value = res.users
                        // console.log(name)
                        data1.push({
                            name,
                            value
                        })
                    }
                })
                await this.$http.get('http://www.kongin.cn/git-view/search/country?country=USA').then(response => {
                    for (let i = 0; i < response.body.length; i++) {
                        let res = response.body[i]
                        var name = this.allfilter(res.location)
                        var value = res.users
                        data2.push({
                            name,
                            value
                        })
                    }
                })
                await this.$http.get('http://www.kongin.cn/git-view/search/country?country=China').then(response => {
                    for (let i = 0; i < response.body.length; i++) {
                        let res = response.body[i]
                        var name = this.allfilter(res.location)
                        var value = res.users
                        // console.log(name)
                        data3.push({
                            name,
                            value
                        })
                    }
                })
                this.worlddata = _.cloneDeep(data1)
                this.usadata = _.cloneDeep(data2)
                this.chinadata = _.cloneDeep(data3)
                landata = await this.getlanguagedata('Javascript')
                this.jsdata = _.cloneDeep(landata)
                landata = await this.getlanguagedata('Java')
                this.javadata = _.cloneDeep(landata)
                landata = await this.getlanguagedata('Python')
                this.pythondata = _.cloneDeep(landata)
                landata = await this.getlanguagedata('Ruby')
                this.rubydata = _.cloneDeep(landata)
                landata = await this.getlanguagedata('PHP')
                this.phpdata = _.cloneDeep(landata)
                landata = await this.getlanguagedata('CSS')
                this.cssdata = _.cloneDeep(landata)
                landata = await this.getlanguagedata('C%2B%2B')
                this.cplusdata = _.cloneDeep(landata)
                landata = await this.getlanguagedata('C')
                this.cdata = _.cloneDeep(landata)
                landata = await this.getlanguagedata('Shell')
                this.shelldata = _.cloneDeep(landata)
                landata = await this.getlanguagedata('Objective-C')
                this.objectcdata = _.cloneDeep(landata)
                // console.log(this.chinadata)
                // console.log(this.usadata)
                // console.log(this.worlddata)
                this.drawMap('allmapchart')
                this.drawlanguageMap('jsmapchart', 'Javascript', this.jsdata)
                this.drawlanguageMap('javamapchart', 'Java', this.javadata)
                this.drawlanguageMap('pythonmapchart', 'Python', this.pythondata)
                this.drawlanguageMap('rubymapchart', 'Ruby', this.rubydata)
                this.drawlanguageMap('phpmapchart', 'PHP', this.phpdata)
                this.drawlanguageMap('cssmapchart', 'CSS', this.cssdata)
                this.drawlanguageMap('cplusmapchart', 'C++', this.cplusdata)
                this.drawlanguageMap('cmapchart', 'C', this.cdata)
                this.drawlanguageMap('shellmapchart', 'Shell',this.shelldata)
                this.drawlanguageMap('ocmapchart', 'Objective-C', this.objectcdata)
            },
            async getlanguagedata (language) {
                var data = []
                const response = await this.$http.get('http://www.kongin.cn/git-view/search/area_language?language=' + language)
                // console.log(response)
                for (let i = 0; i < response.body.length; i++) {
                    let res = response.body[i]
                    // console.log(res)
                    var name = this.allfilter(res.areaDetail.location)
                    var value = res.users
                    data.push({name, value})
                }
                // console.log(data1)
                return data
            },
            drawMap(id) {
                this.chart = echarts.init(document.getElementById(id))
                this.option.series[0].data = []
                for (let i = 0; i < this.worlddata.length; i++) {
                    var item = this.worlddata[i]
                    this.option.series[0].data.push({
                        name: item.name,
                        value: item.value
                    })
                }
                this.option.dataRange.max = this.getdatamax(this.worlddata)
                this.chart.setOption(this.option)
                this.chart.on('click', this.chartclick)
            },
            toworld() {
                this.chart.setOption(this.option, false)
                this.option.title.text = 'github用户全球分布图'
                this.option.dataRange.max = this.getdatamax(this.worlddata)
                Object.assign(this.option.series[0], {
                    mapType: 'world',
                    width: '100%',
                    height: '80%'
                })
                console.log('toworld')
                this.drawlanguageMap('allmapchart', '', this.worlddata)
                this.drawlanguageMap('jsmapchart', 'Javascript', this.jsdata)
                this.drawlanguageMap('javamapchart', 'Java', this.javadata)
                this.drawlanguageMap('pythonmapchart', 'Python', this.pythondata)
                this.drawlanguageMap('rubymapchart', 'Ruby', this.rubydata)
                this.drawlanguageMap('phpmapchart', 'PHP', this.phpdata)
                this.drawlanguageMap('cssmapchart', 'CSS', this.cssdata)
                this.drawlanguageMap('cplusmapchart', 'C++', this.cplusdata)
                this.drawlanguageMap('cmapchart', 'C', this.cdata)
                this.drawlanguageMap('shellmapchart', 'Shell',this.shelldata)
                this.drawlanguageMap('ocmapchart', 'Objective-C', this.objectcdata)
                this.chart.setOption(this.option, true)
            },
            drawlanguageMap(id, language, data1) {
                this.chart = echarts.init(document.getElementById(id))
                // console.log(data1)
                this.option.series[0].data = []
                switch (this.option.series[0].mapType) {
                    case 'world': this.option.title.text = 'github  '+ language +' 用户全球分布图'; break;
                    case 'usa': this.option.title.text = 'github ' + language +' 用户美国分布图'; break;
                    case 'china': this.option.title.text = 'github ' + language +' 用户中国分布图'; break;
                    default: this.option.title.text = 'github' + language +'用户分布图'
                }
                for (let i = 0; i < data1.length; i++){
                    var item = data1[i]
                    this.option.series[0].data.push({name: item.name, value: item.value})
                }
                this.option.dataRange.max = this.getdatamax(data1)
                this.chart.setOption(this.option)
                this.chart.on('click',this.chartclick)
            },
            chartclick(param) {
                // console.log(param)
                this.chart.setOption(this.option, false)
                switch (param.data.name) {
                    case 'United States of America':
                        for (let i = 0; i < this.usadata.length; i++) {
                            var item = this.usadata[i]
                            this.option.series[0].data.push({
                                name: item.name,
                                value: item.value
                            })
                        }
                        this.option.title.text = 'github用户美国分布图'
                        this.option.dataRange.max = this.getdatamax(this.usadata)
                        Object.assign(this.option.series[0], {
                            mapType: 'usa',
                            width: '275%', 
                            height: '100%'
                        })
                        this.drawlanguageMap('allmapchart', '', this.usadata)
                        this.drawlanguageMap('jsmapchart', 'Javascript', this.jsdata)
                        this.drawlanguageMap('javamapchart', 'Java', this.javadata)
                        this.drawlanguageMap('pythonmapchart', 'Python', this.pythondata)
                        this.drawlanguageMap('rubymapchart', 'Ruby', this.rubydata)
                        this.drawlanguageMap('phpmapchart', 'PHP', this.phpdata)
                        this.drawlanguageMap('cssmapchart', 'CSS', this.cssdata)
                        this.drawlanguageMap('cplusmapchart', 'C++', this.cplusdata)
                        this.drawlanguageMap('cmapchart', 'C', this.cdata)
                        this.drawlanguageMap('shellmapchart', 'Shell',this.shelldata)
                        this.drawlanguageMap('ocmapchart', 'Objective-C', this.objectcdata)
                        break
                    case 'China':
                        for (let i = 0; i < this.chinadata.length; i++) {
                            var item1 = this.chinadata[i]
                            this.option.series[0].data.push({
                                name: item1.name,
                                value: item1.value
                            })
                        }
                        this.option.title.text = 'github用户中国分布图'
                        this.option.dataRange.max = this.getdatamax(this.chinadata)
                        Object.assign(this.option.series[0], {
                            mapType: 'china',
                            width: '100%',
                            height: '100%'
                        })
                        this.drawlanguageMap('allmapchart', '', this.chinadata)
                        this.drawlanguageMap('jsmapchart', 'Javascript', this.jsdata)
                        this.drawlanguageMap('javamapchart', 'Java', this.javadata)
                        this.drawlanguageMap('pythonmapchart', 'Python', this.pythondata)
                        this.drawlanguageMap('rubymapchart', 'Ruby', this.rubydata)
                        this.drawlanguageMap('phpmapchart', 'PHP', this.phpdata)
                        this.drawlanguageMap('cssmapchart', 'CSS', this.cssdata)
                        this.drawlanguageMap('cplusmapchart', 'C++', this.cplusdata)
                        this.drawlanguageMap('cmapchart', 'C', this.cdata)
                        this.drawlanguageMap('shellmapchart', 'Shell',this.shelldata)
                        this.drawlanguageMap('ocmapchart', 'Objective-C', this.objectcdata)
                        break
                }
                this.chart.setOption(this.option, true)
            },
            allfilter(cname) {
                var cityname = ''
                switch (cname) {
                    case 'New mexico':
                        cityname =  'New Mexico';
                        break
                    case 'South dakota':
                        cityname = 'South Dakota';
                        break
                    case 'South carolina':
                        cityname = 'South Carolina';
                        break
                    case 'USA':
                        cityname = 'United States of America';
                        break
                    case 'UK':
                        cityname = 'United Kingdom';
                        break
                    case 'United States':
                        cityname = 'United States of America';
                        break
                    case 'Shanxi':
                        cityname = '山西';
                        break
                    case 'Guangdong':
                        cityname = '广东';
                        break
                    case 'Neimenggu':
                        cityname = '内蒙古';
                        break
                    case 'Fujian':
                        cityname = '福建';
                        break
                    case 'Tianjin':
                        cityname = '天津';
                        break
                    case 'Chongqing':
                        cityname = '重庆';
                        break
                    case 'Hebei':
                        cityname = '河北';
                        break
                    case 'Beijing':
                        cityname = '北京';
                        break
                    case 'Jiangsu':
                        cityname = '江苏';
                        break
                    case 'Yunnan':
                        cityname = '云南';
                        break
                    case 'Henan':
                        cityname = '河南';
                        break
                    case 'Shanghai':
                        cityname = '上海';
                        break
                    case 'Xinjiang':
                        cityname = '新疆';
                        break
                    case 'Heilongjiang':
                        cityname = '黑龙江';
                        break
                    case 'Liaoling':
                        cityname = '辽宁';
                        break
                    case 'Zhejiang':
                        cityname = '浙江';
                        break
                    case 'Shandong':
                        cityname = '山东';
                        break
                    case 'Anhui':
                        cityname = '安徽';
                        break
                    case 'Hunan':
                        cityname = '湖南';
                        break
                    case 'Guangxi':
                        cityname = '广西';
                        break
                    case 'Gansu':
                        cityname = '甘肃';
                        break
                    case 'Jilin':
                        cityname = '吉林';
                        break
                    case 'Jiangxi':
                        cityname = '江西';
                        break
                    case 'Hubei':
                        cityname = '湖北';
                        break
                    case 'Sichuan':
                        cityname = '四川';
                        break
                    case 'Hong Kong':
                        cityname = '香港';
                        break
                    case 'Macau':
                        cityname = '澳门';
                        break
                    case 'Taiwan':
                        cityname = '台湾';
                        break
                    case 'Qinghai':
                        cityname = '青海';
                        break
                    case 'Guangzhou':
                        cityname = '广东';
                        break
                    case 'Hainan':
                        cityname = '海南';
                        break
                    case 'Guizhou':
                        cityname = '贵州';
                        break
                    case 'Tibet':
                        cityname = '西藏';
                        break
                    case 'Ningxia':
                        cityname = '宁夏';
                        break
                    default:
                        cityname = cname
                }
                return cityname
            },// 整体的筛选
            getdatamax (data1) {
                var maxnum = 0
                for (let i = 0; i < data1.length; i++) {
                    if (maxnum < data1[i].value) {
                        maxnum = data1[i].value
                    }
                }
                return parseInt(1.5 * maxnum)
            }
        }
    }
</script>
<style scoped>
    .world_content{
        overflow: hidden;
        background-color: #333366
    }
    .allmapchart {
        top: 80px;
        right: 0;
        bottom: 0;
        left: 0;
        margin: auto;
        width: 1000px;
        height: 600px;
    }
    .javamapchart{
        top: 120px;
        margin-left: auto;
        margin-right: auto;
        margin-bottom: 180px;
        width: 1000px;
        height: 600px;
    }
    .jsmapchart{
        top: 120px;
        margin-left: auto;
        margin-right: auto;
        margin-bottom: 180px;
        width: 1000px;
        height: 600px;
    }
    .pythonmapchart{
        top: 120px;
        margin-left: auto;
        margin-right: auto;
        margin-bottom: 180px;
        width: 1000px;
        height: 600px;
    }
    .rubymapchart{
        top: 120px;
        margin-left: auto;
        margin-right: auto;
        margin-bottom: 180px;
        width: 1000px;
        height: 600px;
    }
    .phpmapchart{
        top: 120px;
        margin-left: auto;
        margin-right: auto;
        margin-bottom: 180px;
        width: 1000px;
        height: 600px;
    }
    .cssmapchart{
        top: 120px;
        margin-left: auto;
        margin-right: auto;
        margin-bottom: 180px;
        width: 1000px;
        height: 600px;
    }
    .cplusmapchart{
        top: 120px;
        margin-left: auto;
        margin-right: auto;
        margin-bottom: 180px;
        width: 1000px;
        height: 600px;
    }
    .cmapchart{
        top: 120px;
        margin-left: auto;
        margin-right: auto;
        margin-bottom: 180px;
        width: 1000px;
        height: 600px;
    }
    .shellmapchart{
        top: 120px;
        margin-left: auto;
        margin-right: auto;
        margin-bottom: 180px;
        width: 1000px;
        height: 600px;
    }
    .ocmapchart{
        top: 120px;
        margin-left: auto;
        margin-right: auto;
        margin-bottom: 180px;
        width: 1000px;
        height: 600px;
    }
    .backbutton{
        position: absolute;
        top: 150px;
        right: 10%;
        display: block;
    }
</style>