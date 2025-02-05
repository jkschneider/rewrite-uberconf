# Rewrite UberConf

## Setting license

jkschneider/rewrite-uberconf

```bash
mod config license edit MXxjb21tdW5pdHl8MjAyNDA3MzE=.TRlIyTpiouUR0K4Ek/e1yENTEnkUVAxFJZBjtf3rdtUR8xlZJpU1WFmms72BdpduAgID2Ge3v1furXZ/H+DiAA==
mod build .
```

## Setting up repos to ingest

Producing a repos.csv. Replace `finos` with your GitHub org name.

```bash
gh repo list finos --limit 1000 --json url,defaultBranchRef,diskUsage --template '{{"cloneUrl,branch\n"}}{{range .}}{{.url}}{{","}}{{.defaultBranchRef.name}}{{"\n"}}{{end}}' > repos.csv
```
Then you can then use:

```bash
mod git clone csv . repos.csv --filter=tree:0
```

https://bit.ly/UberConfIDEplugin

## Install the recipes for SAST type issues

```bash
mod config recipes jar install org.openrewrite.recipe:rewrite-static-analysis:LATEST
mod run . --recipe CommonStaticAnalysis
mod study . --last-recipe-run --data-table SourcesFileResults
```

## Java 17

```bash
mod config recipes jar install org.openrewrite.recipe:rewrite-migrate-java:LATEST
mod run . --recipe UpgradeToJava17
mod study . --last-recipe-run --data-table SourcesFileResults
```

## Publishing our workshop repository to your Maven local

One of these two depending on whether you chose Maven or Gradle:

```bash
./gradlew pTML
./mvnw install
```

Then (use the group id and artifact id you chose for your project):

```bash
mod config recipes jar install com.jkschneider:rewrite-uberconf:0.1.0-SNAPSHOT
mod run . --recipe ProjectRelationships -P "groupId=com.google*"
mod study . --last-recipe-run --data-table ProjectDependencyRelationships
```
