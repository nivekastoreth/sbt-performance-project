# sbt-performance-project
Example project exhibiting performance issues introduced with SBT 1.1.1

#### Related SBT issue:
* https://github.com/sbt/sbt/issues/4224

#### To Reproduce:
```
$ sbt "-Dsbt.version=1.1.0" evicted
[warn] sbt version mismatch, current: 1.1.0, in build.properties: "1.1.1", use 'reboot' to use the new value.
[success] Total time: 13 s, completed Jun 21, 2018 6:21:06 PM

$ sbt "-Dsbt.version=1.1.1" evicted
[success] Total time: 39 s, completed Jun 21, 2018 6:22:15 PM
```

![SBT Processor Usage by Version](sbt-performance.png)