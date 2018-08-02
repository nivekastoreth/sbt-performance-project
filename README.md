## sbt-performance-project
Example project exhibiting assembly shade issues introduced with sbt-assembly

##### Related SBT issue:
* https://github.com/sbt/sbt-assembly/issues/335

##### To Reproduce:
```bash
$ git clone git@github.com:nivekastoreth/sbt-performance-project.git
$ sbt clean assembly
```

This project doesn't replicate the issue as often as the original project did, but it will still do it eventually

```
jmaki@jmaki-pc /build/git/sbt-performance-project
$ sbt assembly
[info] Loading settings for project sbt-performance-project-build from plugin-resolvers.sbt,plugins.sbt ...
[info] Loading project definition from /build/git/sbt-performance-project/project
[info] Loading settings for project demo from build.sbt,version.sbt ...
[info] Set current project to demo (in build file:/build/git/sbt-performance-project/)
[info] Updating common...
[info] Done updating.
[warn] There may be incompatibilities among your library dependencies.
[warn] Run 'evicted' to see detailed eviction warnings
[info] Updating preprocessor...
[info] Updating assembler...
[info] Done updating.
[warn] There may be incompatibilities among your library dependencies.
[warn] Run 'evicted' to see detailed eviction warnings
[warn] javaOptions will be ignored, fork is set to false
[info] Done updating.
[warn] There may be incompatibilities among your library dependencies.
[warn] Run 'evicted' to see detailed eviction warnings
[warn] javaOptions will be ignored, fork is set to false
[error] java.lang.RuntimeException: Could not create directory /build/git/sbt-performance-project/preprocessor/target/streams/$global/assemblyOption/$global/streams/assembly/0bb7c31cbff42e7cdea6f80b663e440f00675e2a_a1b5e58fd80cb1edc1413e904a346bfdb3a88333_d0618d3c05f3182b857cf5fbdd38afa95d0a3fe2/scala/tools/nsc/transform/patmat: file exists and is not a directory.
[error]         at scala.sys.package$.error(package.scala:27)
[error]         at sbt.io.IO$.createDirectory(IO.scala:225)
[error]         at sbt.io.OpenFile.open(Using.scala:42)
[error]         at sbt.io.OpenFile.open$(Using.scala:39)
[error]         at sbt.io.Using$$anon$2.open(Using.scala:75)
[error]         at sbt.io.Using$$anon$2.open(Using.scala:75)
[error]         at sbt.io.Using.apply(Using.scala:21)
[error]         at sbt.io.IO$.writeBytes(IO.scala:820)
[error]         at sbt.io.IO$.write(IO.scala:817)
[error]         at sbtassembly.Shader$.$anonfun$shadeDirectory$7(Shader.scala:103)
[error]         at sbtassembly.Shader$.$anonfun$shadeDirectory$7$adapted(Shader.scala:97)
[error]         at scala.collection.Iterator.foreach(Iterator.scala:944)
[error]         at scala.collection.Iterator.foreach$(Iterator.scala:944)
[error]         at scala.collection.AbstractIterator.foreach(Iterator.scala:1432)
[error]         at scala.collection.IterableLike.foreach(IterableLike.scala:71)
[error]         at scala.collection.IterableLike.foreach$(IterableLike.scala:70)
[error]         at scala.collection.AbstractIterable.foreach(Iterable.scala:54)
[error]         at sbtassembly.Shader$.shadeDirectory(Shader.scala:97)
[error]         at sbtassembly.Assembly$.$anonfun$assembleMappings$9(Assembly.scala:218)
[error]         at scala.collection.parallel.AugmentedIterableIterator.map2combiner(RemainsIterator.scala:112)
[error]         at scala.collection.parallel.AugmentedIterableIterator.map2combiner$(RemainsIterator.scala:109)
[error]         at scala.collection.parallel.immutable.ParVector$ParVectorIterator.map2combiner(ParVector.scala:62)
[error]         at scala.collection.parallel.ParIterableLike$Map.leaf(ParIterableLike.scala:1052)
[error]         at scala.collection.parallel.Task.$anonfun$tryLeaf$1(Tasks.scala:49)
[error]         at scala.runtime.java8.JFunction0$mcV$sp.apply(JFunction0$mcV$sp.java:12)
[error]         at scala.util.control.Breaks$$anon$1.catchBreak(Breaks.scala:63)
[error]         at scala.collection.parallel.Task.tryLeaf(Tasks.scala:52)
[error]         at scala.collection.parallel.Task.tryLeaf$(Tasks.scala:46)
[error]         at scala.collection.parallel.ParIterableLike$Map.tryLeaf(ParIterableLike.scala:1049)
[error]         at scala.collection.parallel.AdaptiveWorkStealingTasks$WrappedTask.internal(Tasks.scala:166)
[error]         at scala.collection.parallel.AdaptiveWorkStealingTasks$WrappedTask.internal$(Tasks.scala:153)
[error]         at scala.collection.parallel.AdaptiveWorkStealingForkJoinTasks$WrappedTask.internal(Tasks.scala:436)
[error]         at scala.collection.parallel.AdaptiveWorkStealingTasks$WrappedTask.compute(Tasks.scala:146)
[error]         at scala.collection.parallel.AdaptiveWorkStealingTasks$WrappedTask.compute$(Tasks.scala:145)
[error]         at scala.collection.parallel.AdaptiveWorkStealingForkJoinTasks$WrappedTask.compute(Tasks.scala:436)
[error]         at java.util.concurrent.RecursiveAction.exec(RecursiveAction.java:189)
[error]         at java.util.concurrent.ForkJoinTask.doExec(ForkJoinTask.java:289)
[error]         at java.util.concurrent.ForkJoinPool$WorkQueue.runTask(ForkJoinPool.java:1056)
[error]         at java.util.concurrent.ForkJoinPool.runWorker(ForkJoinPool.java:1692)
[error]         at java.util.concurrent.ForkJoinWorkerThread.run(ForkJoinWorkerThread.java:157)
[error] java.io.FileNotFoundException: /build/git/sbt-performance-project/assembler/target/streams/$global/assemblyOption/$global/streams/assembly/d0da05e6b00b2d3451689652163711f0901137a8_487a90889a8cc17f04a2ab8a290882c255a32c44_d0618d3c05f3182b857cf5fbdd38afa95d0a3fe2/scalaz/std (Is a directory)
[error]         at java.io.FileInputStream.open0(Native Method)
[error]         at java.io.FileInputStream.open(FileInputStream.java:195)
[error]         at java.io.FileInputStream.<init>(FileInputStream.java:138)
[error]         at sbt.io.Using$.$anonfun$fileInputStream$1(Using.scala:88)
[error]         at sbt.io.Using$$anon$2.openImpl(Using.scala:76)
[error]         at sbt.io.OpenFile.open(Using.scala:43)
[error]         at sbt.io.OpenFile.open$(Using.scala:39)
[error]         at sbt.io.Using$$anon$2.open(Using.scala:75)
[error]         at sbt.io.Using$$anon$2.open(Using.scala:75)
[error]         at sbt.io.Using.apply(Using.scala:21)
[error]         at sbt.io.IO$.readBytes(IO.scala:789)
[error]         at sbtassembly.Shader$.$anonfun$shadeDirectory$7(Shader.scala:98)
[error]         at sbtassembly.Shader$.$anonfun$shadeDirectory$7$adapted(Shader.scala:97)
[error]         at scala.collection.Iterator.foreach(Iterator.scala:944)
[error]         at scala.collection.Iterator.foreach$(Iterator.scala:944)
[error]         at scala.collection.AbstractIterator.foreach(Iterator.scala:1432)
[error]         at scala.collection.IterableLike.foreach(IterableLike.scala:71)
[error]         at scala.collection.IterableLike.foreach$(IterableLike.scala:70)
[error]         at scala.collection.AbstractIterable.foreach(Iterable.scala:54)
[error]         at sbtassembly.Shader$.shadeDirectory(Shader.scala:97)
[error]         at sbtassembly.Assembly$.$anonfun$assembleMappings$9(Assembly.scala:218)
[error]         at scala.collection.parallel.AugmentedIterableIterator.map2combiner(RemainsIterator.scala:112)
[error]         at scala.collection.parallel.AugmentedIterableIterator.map2combiner$(RemainsIterator.scala:109)
[error]         at scala.collection.parallel.immutable.ParVector$ParVectorIterator.map2combiner(ParVector.scala:62)
[error]         at scala.collection.parallel.ParIterableLike$Map.leaf(ParIterableLike.scala:1052)
[error]         at scala.collection.parallel.Task.$anonfun$tryLeaf$1(Tasks.scala:49)
[error]         at scala.runtime.java8.JFunction0$mcV$sp.apply(JFunction0$mcV$sp.java:12)
[error]         at scala.util.control.Breaks$$anon$1.catchBreak(Breaks.scala:63)
[error]         at scala.collection.parallel.Task.tryLeaf(Tasks.scala:52)
[error]         at scala.collection.parallel.Task.tryLeaf$(Tasks.scala:46)
[error]         at scala.collection.parallel.ParIterableLike$Map.tryLeaf(ParIterableLike.scala:1049)
[error]         at scala.collection.parallel.AdaptiveWorkStealingTasks$WrappedTask.internal(Tasks.scala:166)
[error]         at scala.collection.parallel.AdaptiveWorkStealingTasks$WrappedTask.internal$(Tasks.scala:153)
[error]         at scala.collection.parallel.AdaptiveWorkStealingForkJoinTasks$WrappedTask.internal(Tasks.scala:436)
[error]         at scala.collection.parallel.AdaptiveWorkStealingTasks$WrappedTask.compute(Tasks.scala:146)
[error]         at scala.collection.parallel.AdaptiveWorkStealingTasks$WrappedTask.compute$(Tasks.scala:145)
[error]         at scala.collection.parallel.AdaptiveWorkStealingForkJoinTasks$WrappedTask.compute(Tasks.scala:436)
[error]         at java.util.concurrent.RecursiveAction.exec(RecursiveAction.java:189)
[error]         at java.util.concurrent.ForkJoinTask.doExec(ForkJoinTask.java:289)
[error]         at java.util.concurrent.ForkJoinPool$WorkQueue.runTask(ForkJoinPool.java:1056)
[error]         at java.util.concurrent.ForkJoinPool.runWorker(ForkJoinPool.java:1692)
[error]         at java.util.concurrent.ForkJoinWorkerThread.run(ForkJoinWorkerThread.java:157)
[error] (preprocessor / assembly / assembledMappings) Could not create directory /build/git/sbt-performance-project/preprocessor/target/streams/$global/assemblyOption/$global/streams/assembly/0bb7c31cbff42e7cdea6f80b663e440f00675e2a_a1b5e58fd80cb1edc1413e904a346bfdb3a88333_d0618d3c05f3182b857cf5fbdd38afa95d0a3fe2/scala/tools/nsc/transform/patmat: file exists and is not a directory.
[error] (assembler / assembly / assembledMappings) java.io.FileNotFoundException: /build/git/sbt-performance-project/assembler/target/streams/$global/assemblyOption/$global/streams/assembly/d0da05e6b00b2d3451689652163711f0901137a8_487a90889a8cc17f04a2ab8a290882c255a32c44_d0618d3c05f3182b857cf5fbdd38afa95d0a3fe2/scalaz/std (Is a directory)

```

## Previous uses:

### sbt-performance-project
Example project exhibiting performance issues introduced with SBT 1.1.1

##### Related SBT issue:
* https://github.com/sbt/sbt/issues/4224

##### To Reproduce:
```
$ sbt "-Dsbt.version=1.1.0" evicted
[warn] sbt version mismatch, current: 1.1.0, in build.properties: "1.1.1", use 'reboot' to use the new value.
[success] Total time: 13 s, completed Jun 21, 2018 6:21:06 PM

$ sbt "-Dsbt.version=1.1.1" evicted
[success] Total time: 39 s, completed Jun 21, 2018 6:22:15 PM
```

![SBT Processor Usage by Version](sbt-performance.png)