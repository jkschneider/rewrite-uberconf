# Rewrite UberConf

## Setting up repos to ingest

Producing a repos.csv that you can then use for `mod git clone csv repos.csv`

```bash
gh repo list finos --json url,defaultBranchRef,diskUsage --template '{{"cloneUrl,branch\n"}}{{range .}}{{.url}}{{","}}{{.defaultBranchRef.name}}{{"\n"}}{{end}}' > repos.csv
```

https://bit.ly/UberConfIDEplugin

## Install the recipes for SAST type issues

```bash
mod config recipes jar install org.openrewrite.recipe:rewrite-static-analysis:LATEST
```
