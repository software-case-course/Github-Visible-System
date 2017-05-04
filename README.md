#### Github-Visible-System
---
##### 5 men

搜索 api:
1. http://www.kongin.cn/git-view/search/languages
- 返回类型: jsonArray (language -> repository count)
- 参数: 无
- 结果: 不同语言的仓库数量

2. http://www.kongin.cn/git-view/search/year
- 返回类型: json (language,year -> user, count)
- 参数 (如果不写参数，返回全部数据): 
  - language: 仓库语言
  - year: 仓库创建年份
- 结果: 不同语言在不同年份的用户和仓库数量

3. http://www.kongin.cn/git-view/search/repos
- 返回类型: jsonArray
- 参数 (必须写): 
  - q: 仓库关键词
- 结果: 关键词搜索前30个仓库名字和相关资料

4. http://www.kongin.cn/git-view/search/languagelist
- 返回类型: json
- 参数: 无
- 结果: 列出所有已经记录的语言

5. http://www.kongin.cn/git-view/search/lanrepos
- 返回类型: jsonArray
- 参数 (必须写): 
  - language: 仓库语言
- 结果: 语言搜索前30个仓库名字和url