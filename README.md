#### Github-Visible-System
---
##### 5 men

search-api:
1. http://www.kongin.cn/git-view/search/languages
- return type: json (language -> repository count)
- params: none
- result: repository count for different language
2. http://www.kongin.cn/git-view/search/languageUsers
- return type: json (language -> user count)
- params: none
- result: user count for different language
3. http://www.kongin.cn/git-view/search/languageRepos
- return type: json (string list)
- params: 
  - language: the language of repositories
  - page: pagination param
  - per_page: pagination param
- result: user count for different language