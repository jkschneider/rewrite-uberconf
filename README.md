# Rewrite UberConf

Producing a repos.csv that you can then use for `mod git clone csv repos.csv`

```bash
gh repo list finos --json url,defaultBranchRef,diskUsage --template '{{"cloneUrl,branch\n"}}{{range .}}{{.url}}{{","}}{{.defaultBranchRef.name}}{{"\n"}}{{end}}' > repos.csv
```

https://bit.ly/UberConfIDEplugin
