Make an issue if you are having troubles!
## How to download this?
Well uhm I don't really know how to publish a gradle plugin/dependency (psst someone publish this for me and then make an issue letting me know)

So for now you'll have to be scuffed just like me!

You'll first need to [download](https://github.com/PacifistMC/pacifist-remapper/releases) this
then in your project make a folder named plugins and paste the jar file in that
and then add this to your build.gradle file without breaking the entire universe
```groovy
import me.rancraftplayz.pacifist.remapper.RemapperPlugin

buildscript {
    dependencies{
        classpath files('plugins/pacifist-remapper.jar')
    }
}
apply plugin: RemapperPlugin
```
If something goes wrong then you can look at my project [PacifistOptimizations's build.gradle file](https://github.com/PacifistMC/PacifistOptimizations/blob/2664c715836988f68787b30e95d11ada29dc7400/build.gradle)

## How to use
```groovy
dependencies {
    remapLib "org.spigotmc:spigot:1.17.1-R0.1-SNAPSHOT:remapped-mojang"
}

spigot {
  version = "1.17.1"
}
```
Remember to change the version numbers

The **remapLib** is where it'll remap from

To remap the jar you'll need to run the task **remapJar**
You can make this task depend on [shadowJar](https://github.com/PacifistMC/pacifist-remapper#shadowjar-configuration-required-for-now) (or something simillar) to automaticly create & remap the jar

## Access Wideners
We support three namespaces (mojang, spigot, official)
#
```groovy
dependencies {
    // Note that we don't support multiple access wideners yet
    accessWidener fileTree(dir: 'src/main/resources', include: ['*.accesswidener'])
    accessWidenerLib "org.spigotmc:spigot:1.17.1-R0.1-SNAPSHOT:remapped-mojang"
}
```
The **accessWidener** configuration is the path .accesswidener file

The **accessWidenerLib** is where will it apply the access wideners

To actually apply the accsss wideners you need to run the task **applyAccessWidener**

##### ignite.mod.json
```json
"access_widener": [
    "example-obf.accesswidener"
]
  ```
  The **-obf** must be added after the name of the access widener because the actual example.accesswidener is not an access widener that Ignite understands! (unless if you're making it in spigot mappings)
  
  ## ShadowJar configuration (Required for now)
  If you're using shadow or something similar then you'll need to set the path & name to where the original jar task makes its jar
  If you're using default build stuff then you can add this to your shadowJar
  ```groovy
  shadowJar {
    archiveBaseName.set("${project.name}-${project.version}")
    archiveClassifier.set('')
    archiveVersion.set('')
  }
  ```
  
  # 
  # 
  If you wanna see a full project using this then you can look at [PacifistOptimizations](https://github.com/PacifistMC/PacifistOptimizations)
