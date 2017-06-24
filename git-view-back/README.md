#### Github-Visible-System
---
##### 5 men

一、搜索 API:
1. http://www.kongin.cn/git-view/search/support_languages
- 返回类型: jsonArray (language -> repository count)
- 参数: 无
- 结果: 不同语言的仓库数量

2. http://www.kongin.cn/git-view/search/year
- 返回类型: json (language,year -> user, count)
- 参数 (如果不写参数，返回全部数据): 
  - language: 仓库语言（看搜索 api 1 得到的数据）
  - year: 仓库创建年份
- 结果: 不同语言在不同年份的用户和仓库数量

3. http://www.kongin.cn/git-view/search/languagelist
- 返回类型: json
- 参数: 无
- 结果: 列出所有已经记录的语言

二、仓库 API:
1. http://www.kongin.cn/git-view/repos/Contributor
- 返回类型: jsonArray
- 参数 (必须写): 
  - fullname: 仓库全名（用户/仓库名）
- 结果: 各个贡献者不同时间提交量

2. http://www.kongin.cn/git-view/repos/WeeklyCommit
- 返回类型: jsonArray
- 参数 (必须写): 
  - fullname: 仓库全名（用户/仓库名）
- 结果: 一周内不同天（周日至周六）提交总数

3. http://www.kongin.cn/git-view/repos/CommitActivity
- 返回类型: jsonArray
- 参数 (必须写): 
  - fullname: 仓库全名（用户/仓库名）
- 结果: 每周周日至周六提交数

4. http://www.kongin.cn/git-view/repos/PunchCard
- 返回类型:  json
- 参数 (必须写): 
  - fullname: 仓库全名（用户/仓库名）
- 结果: 一周内不同天（周日至周六）各个小时的提交量

5. http://www.kongin.cn/git-view/repos/CodeFrequency
- 返回类型:  json
- 参数 (必须写): 
  - fullname: 仓库全名（用户/仓库名）
- 结果: 不同时间代码增加和删除量

三、地区 API:
1. http://www.kongin.cn/git-view/search/support_areas
- 返回类型:  json
- 参数: 无 
- 结果: 本站点支持查询的地区和国家名

2. http://www.kongin.cn/git-view/search/country
- 返回类型:  jsonArray
- 参数 (不写时返回所有支持国家用户数量): 
  - country: 国家名（看地区 api 1 得到的数据）
- 结果: 该国家的地区用户分布数据

3. http://www.kongin.cn/git-view/search/location
- 返回类型:  json
- 参数 (必须写): 
  - location: 地区名（看地区 api 1 得到的数据）
- 结果: 该地区用户数量

4. http://www.kongin.cn/git-view/search/area_language
- 返回类型:  jsonArray
- 参数 (不写时返回所有结果): 
  - location: 地区名（看地区 api 1 得到的数据）
  - language: 语言（看搜索 api 1 得到的数据）
- 结果: 指定地区或指定语言用户数量

四、个人 API:
1. http://www.kongin.cn/git-view/private/authorize
- 返回类型：无
- 参数: redirect_uri: 授权登录之后跳转的页面 url
- 结果: 跳转到 github 页面登录授权，得到带有 code 的跳转请求

2. http://www.kongin.cn/git-view/private/gettoken
- 返回类型：String
- 参数: code (in API 1)
- 结果: access_token (可用于下面 API)

3. http://www.kongin.cn/git-view/private/user
- 返回类型:  json
- 参数: 
  - token: access_token (in API 2)
- 结果: 授权登录人的信息

3. http://www.kongin.cn/git-view/private/repos
- 返回类型:  json
- 参数: 
  - token: access_token (in API 2)
- 结果: 授权登录人的仓库

4. http://www.kongin.cn/git-view/private/languages
- 返回类型:  jsonArray
- 参数: 
  - token: access_token (in API 2)
- 结果: 授权登录人的仓库语言分布